<?php
// VALIDAR
/**Esta funcion lee la configuracion de la BD desde un archivo XML
 * valida su formato usando un esquema XSD
 * devuelve los parametros necesarios para realizar una conexion a la BD
 */
function leer_config ($nombre, $esquema) {
    $config = new DOMDocument(); //Crear nueva instancia de DOMDocument
    $config -> load($nombre); // Cargar el archivo XML
    $res = $config -> schemaValidate($esquema); //Validar XML con el esquema XSD

    if ($res === FALSE) { //Si la validacion falla, lanza una excepcion
        throw new InvalidArgumentException("Revise el fichero de configuración.");
    }

    $datos = simplexml_load_file($nombre); //Leer el XML como SimpleXML
    // Extraer valores especificos usando XPath
    $ip = $datos -> xpath("//ip");
    $nombre = $datos -> xpath("//nombre");
    $usu = $datos -> xpath("//usuario");
    $clave = $datos -> xpath("//clave");
        
    // Construye la cadena de conexion que usa en PDO
    $cad = sprintf("mysql:dbname=%s;host=%s", $nombre[0], $ip[0]);

    /** Devuelve un array con los valores necesarios para conectar:
     *      - la cadena de conexion
     *      - el usuario
     *      - la clave
    */
    $resul =   []; //Creo un array vacio
    $resul[] = $cad;
    $resul[] = $usu[0];
    $resul[] = $clave[0];
    return $resul;
}

// Conexion a la BD
function conexion_bd(){
    $res = leer_config(dirname(__FILE__)."/configuracion.xml", dirname(__FILE__)."/configuracion.xsd");
    $bd = new PDO($res[0], $res[1], $res[2]);
    $bd -> setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
    return $bd;
}

// Cifrar claves - ES MEJORABLE
function cifrar_claves(){
    $bd = conexion_bd();

    $sql01 = "SELECT id_usuario, contrasenha FROM usuario";
    $usuarios = $bd->query($sql01);

    // Cifrar cada clave y actualizar
    foreach($usuarios as $usu){
        $codUsuario = $usu['id_usuario'];
        $contrasenhaOriginal = $usu['contrasenha'];
        $contrasenhaCifrada = password_hash($contrasenhaOriginal, PASSWORD_BCRYPT);
        echo "Cifrada la contrasenha: $contrasenhaOriginal como $contrasenhaCifrada - ";

        // Actualizar clave cifrada
        $sql02 = "UPDATE usuario
                  SET contrasenha = '$contrasenhaCifrada'
                  WHERE id_usuario = $codUsuario";
        try {
            $resul = $bd->query($sql02);
        } catch (Exception $e) {
            return FALSE;
        }
    }
}

// Validar contrasenha
function validar_password_servidor($pass){
    return strlen($pass) >= 12 &&
           preg_match('/[A-Z]/', $pass) &&
           preg_match('/[a-z]/', $pass) &&
           preg_match('/[0-9]/', $pass) &&
           preg_match('/[^a-zA-Z0-9]/', $pass);
}

// Comproba de sesion
function comprobar_sesion($rol = null){
    session_start();
    if(!isset($_SESSION['correo'])){
        header("Location: login.php?redirigido=true");
        exit;
    }
}

// Comprobar usuario
function comprobar_usuario($correo, $contrasenha) {
    $bd = conexion_bd();

    // Buscar usuario por correo
    $sql = "SELECT id_usuario, nombre, correo, contrasenha, rol
            FROM usuario
            WHERE correo = :correo";

    $stmt = $bd -> prepare($sql);
    $stmt -> execute([':correo' => $correo]);
    $usuario = $stmt -> fetch(PDO::FETCH_ASSOC);

    if (!$usuario) {
        return FALSE; // Si NO existe el correo
    }

    // Verificar contrasenha cifrada
    if (password_verify($contrasenha, $usuario['contrasenha'])) {
        return $usuario; // OK -> devolver datos del usuario
    } else {
        return FALSE; // Contrasenha incorrecta
    }
}


// Comprobar sesion segun rol
function comprobar_rol($rol_esperado){
    session_start();
    if(!isset($_SESSION['correo']) || $_SESSION['rol'] !== $rol_esperado){
        header("Location: login.php?redirigido=true");
        exit;
    }
}


// Funcion CREAR USUARIO
function crear_usuario($nombre, $apellidos, $correo, $contrasenha, $rol, $curso){
    $bd = conexion_bd();

    if (!validar_password_servidor($contrasenha)){
        return false;
    }
    try{
        $bd->beginTransaction();

        // Cifrar contrasenha
        $contrasenha_cifrada = password_hash($contrasenha, PASSWORD_BCRYPT);

        // Insertar en tabla usuario
        $sql = "INSERT INTO usuario (nombre, apellidos, correo, contrasenha, rol)
                VALUES (:nombre, :apellidos, :correo, :contrasenha, :rol)";
        $stmt = $bd->prepare($sql);
        $stmt->execute([
            ':nombre' => $nombre,
            ':apellidos' => $apellidos,
            ':correo' => $correo,
            ':contrasenha' => $contrasenha_cifrada,
            ':rol' => $rol
        ]);

        // Obtener ID del ultimo insertado
        $id_usuario = $bd->lastInsertId();

        // Asociar curso si aplica
        if ($rol === 'alumno' && !empty($curso)) {
            $sql2 = "INSERT INTO alumno_curso (id_alumno, id_curso)
                     VALUES (:id_usuario, :curso)";
        } elseif ($rol === 'profesor' && !empty($curso)) {
            $sql2 = "INSERT INTO profesor_curso (id_profesor, id_curso)
                     VALUES (:id_usuario, :curso)";
        } else {
            $sql2 = null; // admin no tiene curso
        }

        if ($sql2) {
            $stmt2 = $bd->prepare($sql2);
            $stmt2->execute([
                ':id_usuario' => $id_usuario,
                ':curso' => $curso
            ]);
        }

        $bd->commit();
        return $id_usuario;

    } catch (Exception $e) {
        $bd->rollBack();
        error_log("Error al crear usuario: " . $e->getMessage());
        return false;
    }
}

// Funcoin para crear una fila AUTOMATICAMENTE en puntaje_alumno solo para rol=alumno
function crear_puntaje_inicial($id_usuario){
    $bd = conexion_bd();

    // insert IGNORE para evitar errores si ya existe ese usuario
    $sql = "INSERT IGNORE INTO puntaje_alumno(id_usuario, puntos, tiempo_total)
            VALUES (:id_usuario, 0, 0)";

    $stmt = $bd->prepare($sql);

    return $stmt->execute([
        ':id_usuario' => $id_usuario
    ]);
}

function modif_usuario($id_usuario, $nombre, $apellidos, $correo, $contrasenha, $rol, $curso){
    $bd = conexion_bd();

    // Validar contrasenha solo si se envia
    if (!empty($contrasenha) && !validar_password_servidor($contrasenha)){
        return false;
    }

    try {
        $bd->beginTransaction();

        // 1 - Actualizar datos del usuario
        if (!empty($contrasenha)) {
            $contrasenha_cifrada = password_hash($contrasenha, PASSWORD_BCRYPT);

            $sql = "UPDATE usuario
                    SET nombre = :nombre,
                        apellidos = :apellidos,
                        correo = :correo,
                        contrasenha = :contrasenha,
                        rol = :rol
                    WHERE id_usuario = :id";
            $stmt = $bd->prepare($sql);
            $stmt->execute([
                ':nombre' => $nombre,
                ':apellidos' => $apellidos,
                ':correo' => $correo,
                ':contrasenha' => $contrasenha_cifrada,
                ':rol' => $rol,
                ':id' => $id_usuario
            ]);

        } else {
            $sql = "UPDATE usuario
                    SET nombre = :nombre,
                        apellidos = :apellidos,
                        correo = :correo,
                        rol = :rol
                    WHERE id_usuario = :id";
            $stmt = $bd->prepare($sql);
            $stmt->execute([
                ':nombre' => $nombre,
                ':apellidos' => $apellidos,
                ':correo' => $correo,
                ':rol' => $rol,
                ':id' => $id_usuario
            ]);
        }

        // 2 - RELACIONES SEGUN ROL

        if ($rol === 'alumno') {
            // Borrar el curso anterior (solo ALUMNOOOOOOOOOO)
            $bd->prepare("DELETE FROM alumno_curso WHERE id_alumno = :id")
               ->execute([':id' => $id_usuario]);

            // Insertar nuevo curso si viene uno
            if (!empty($curso)) {
                $bd->prepare("INSERT INTO alumno_curso (id_alumno, id_curso)
                              VALUES (:id, :curso)")
                   ->execute([':id' => $id_usuario, ':curso' => $curso]);
            }
        }

        if ($rol === 'profesor' && !empty($curso)) {

            // Comprobar si tiene ese curso
            $stmt = $bd->prepare("SELECT 1 FROM profesor_curso
                                  WHERE id_profesor = :id AND id_curso = :curso");
            $stmt->execute([':id' => $id_usuario, ':curso' => $curso]);

            if ($stmt->rowCount() === 0) {
                // Insertar curso nuevo
                $bd->prepare("INSERT INTO profesor_curso (id_profesor, id_curso)
                              VALUES (:id, :curso)")
                   ->execute([':id' => $id_usuario, ':curso' => $curso]);
            }
        }

        $bd->commit();
        return true;

    } catch (Exception $e) {
        $bd->rollBack();
        error_log("Error al modificar usuario: " . $e->getMessage());
        return false;
    }
}


// FUNCION ELIMINAR USUARIO
function eliminar_usuario($id_usuario){
    $bd = conexion_bd();

    try {
        $bd->beginTransaction();

        // Borrar relaciones
        $bd->prepare("DELETE FROM alumno_curso WHERE id_alumno = :id")
           ->execute([':id' => $id_usuario]);

        $bd->prepare("DELETE FROM profesor_curso WHERE id_profesor = :id")
           ->execute([':id' => $id_usuario]);

        // Borrar usuario
        $bd->prepare("DELETE FROM usuario WHERE id_usuario = :id")
           ->execute([':id' => $id_usuario]);

        $bd->commit();
        return true;

    } catch (Exception $e) {
        $bd->rollBack();
        error_log("Error al eliminar usuario: " . $e->getMessage());
        return false;
    }
}

// Funcion CREAR ASIGNATURA
function crear_asignatura($nombre, $descripcion, $id_profesor, $id_curso){
    $bd = conexion_bd();

    // 1 Comprobar que el profe imparte el curso
    $stmt = $bd->prepare("
            SELECT 1 FROM profesor_curso
            WHERE id_profesor = :prof AND id_curso = :curso");
    $stmt -> execute([
        ':prof' => $id_profesor,
        ':curso' => $id_curso
    ]);

    // si NO existe la relacion -> crearla
    if($stmt->rowCount() === 0){
        $insertPC = $bd->prepare("INSERT INTO profesor_curso (id_profesor, id_curso)
                                VALUES (:prof, :curso)");
        
        $insertPC->execute([
            ':prof' => $id_profesor,
            ':curso' => $id_curso
        ]);
    }

    // 2 Comrpobar si ya existe una asignatura igual para mismo profe y mismo curos
    $comprob = $bd -> prepare(
        "SELECT 1
        FROM asignatura
        WHERE nombre = :nombre
        AND id_profesor = :id_profesor
        AND id_curso = :id_curso"
    );

    $comprob->execute([
        ':nombre' => $nombre,
        ':id_profesor' => $id_profesor,
        ':id_curso' => $id_curso
    ]);

    // entonces si...
    if($comprob -> rowCount() > 0){
        throw new Exception("La asignatura ya existe para ese profesor y curso.");
    }

    // 3 Insertar asignatura pues
    $sql = "INSERT INTO asignatura (nombre, descripcion, id_profesor, id_curso)
            VALUES (:nombre, :descripcion, :id_profesor, :id_curso)";

    $stmt = $bd->prepare($sql);
    return $stmt->execute([
        ':nombre' => $nombre,
        ':descripcion' => $descripcion,
        ':id_profesor' => $id_profesor,
        ':id_curso' => $id_curso
    ]);
}

// Funcion para obtener cursos del profesor
function obtener_cursos_profesor($id_profesor){
    $bd = conexion_bd();

    $sql = "SELECT c.id_curso, c.nombre
            FROM profesor_curso pc
            INNER JOIN curso c ON pc.id_curso = c.id_curso
            WHERE pc.id_profesor = :id_profesor";

    $stmt = $bd->prepare($sql);
    $stmt->execute([':id_profesor' => $id_profesor]);

    return $stmt->fetchAll(PDO::FETCH_ASSOC);
}

// Funcion para obtener alumnos
function obtener_alumnos_por_curso($id_curso){
    $bd = conexion_bd();

    $sql = "SELECT u.id_usuario, u.nombre, u.apellidos
            FROM usuario u
            JOIN alumno_curso ac ON u.id_usuario = ac.id_alumno
            WHERE ac.id_curso = :id_curso
            ORDER BY u.apellidos, u.nombre";

    $stmt = $bd -> prepare($sql);
    $stmt -> execute([':id_curso' => $id_curso]);

    return $stmt -> fetchAll(PDO::FETCH_ASSOC);
}

// Obtener asignaturas del profe
function obtener_asignaturas_profesor($id_profesor){
    $bd = conexion_bd();

    $sql = "SELECT a.id_asignatura, a.nombre AS nombre_asignatura, c.nombre AS nombre_curso
            FROM asignatura a
            JOIN curso c ON a.id_curso = c.id_curso
            WHERE a.id_profesor = :id_profesor
            ORDER BY c.nombre, a.nombre";

    $stmt = $bd -> prepare($sql);
    $stmt -> execute([':id_profesor' => $id_profesor]);
    
    return $stmt -> fetchAll(PDO::FETCH_ASSOC);
}

// CREAR UN TEST
function crear_test($id_profesor, $id_asignatura, $titulo){
    $bd = conexion_bd();

    try {
        // obtener curso
        $stmt = $bd->prepare("SELECT id_curso
                            FROM asignatura
                            WHERE id_asignatura = :id");
        $stmt->execute([':id' => $id_asignatura]);
        $id_curso = $stmt->fetchColumn();

        if (!$id_curso) return false;

        // comprobar que el profe imparte ese curso
        $stmtCheck = $bd->prepare("
            SELECT 1 FROM profesor_curso
            WHERE id_profesor = :p AND id_curso = :c");

        $stmtCheck->execute([
            ':p' => $id_profesor,
            ':c' => $id_curso]);

        if ($stmtCheck->rowCount() === 0) return false;

        // insertar test
        $sql = "INSERT INTO test (titulo, id_asignatura, id_profesor, id_curso)
                VALUES (:titulo, :asig, :prof, :curso)";

        $stmtTest = $bd->prepare($sql);
        $stmtTest->execute([
            ':titulo' => $titulo,
            ':asig'   => $id_asignatura,
            ':prof'   => $id_profesor,
            ':curso'  => $id_curso]);

        return $bd->lastInsertId();

    } catch (Exception $e) {
        error_log("Error: " . $e->getMessage());
        return false;
    }
}

// Anhadir preguntas
function agregar_pregunta($id_test, $enunciado, $a, $b, $c, $d, $correcta) {
    $bd = conexion_bd();
    try{
        $sql = "INSERT INTO pregunta (id_test, enunciado, opcion_a, opcion_b, opcion_c, opcion_d, correcta)
            VALUES (:id_test, :e, :a, :b, :c, :d, :corr)";

        $stmt = $bd->prepare($sql);
        return $stmt->execute([
            ':id_test' => $id_test,
            ':e' => $enunciado,
            ':a' => $a,
            ':b' => $b,
            ':c' => $c,
            ':d' => $d,
            ':corr' => $correcta
        ]);
    }catch(Exception $e){
        error_log("Error: " . $e->getMessage());
        return false;
    }
}

// Crear test CoMPLETO
function crear_test_completo($id_profesor, $id_asignatura, $titulo, array $preguntas){
    $bd = conexion_bd();
    try {
        $bd->beginTransaction();

        $id_test = crear_test($id_profesor, $id_asignatura, $titulo);
        if (!$id_test) throw new Exception("No se pudo crear test");

        foreach ($preguntas as $p) {
            // validar estructura mínima de $p antes de llamar
            if (!isset($p['enunciado'], $p['a'], $p['b'], $p['c'], $p['d'], $p['correcta'])) {
                throw new Exception("Pregunta mal formada");
            }
            $ok = agregar_pregunta($id_test, $p['enunciado'], $p['a'], $p['b'], $p['c'], $p['d'], $p['correcta']);
            if (!$ok) throw new Exception("No se pudo insertar una pregunta");
        }

        $bd->commit();
        return $id_test;
    } catch (Exception $e) {
        $bd->rollBack();
        error_log("Error crear_test_completo: " . $e->getMessage());
        return false;
    }
}

// Obtener TODOS los test
function obtener_tests(){
    $bd = conexion_bd();
    
    $stmt = $bd->prepare("SELECT * FROM test");

    return $stmt->fetch(PDO::FETCH_ASSOC);
}

// Funcion ver rendimietno
function obtener_rendimiento_alumno($id_alumno){
    $bd = conexion_bd();

    // Puntos x tarea
    $stmt = $bd->prepare("SELECT DATE(fecha) AS fecha, SUM(puntos_obtenidos) as puntos
                          FROM progreso
                          WHERE id_usuario = :id
                          GROUP BY DATE(fecha)
                          ORDER BY fecha ASC");
    
    $stmt->execute([':id' => $id_alumno]);
    $progreso_tareas = $stmt->fetchAll(PDO::FETCH_ASSOC);

    // Puntos x tests
    $stmt2 = $bd->prepare("SELECT fecha_realizacion as fecha, SUM(puntuacion) as puntos
                          FROM resultado_test
                          WHERE id_usuario = :id
                          GROUP BY fecha_realizacion
                          ORDER BY fecha_realizacion ASC");

    $stmt2->execute([':id' => $id_alumno]);
    $progreso_tests = $stmt2->fetchAll(PDO::FETCH_ASSOC);

    // Unir fechas (clave=fecha)
    $fechas = [];
    foreach ($progreso_tareas as $p) if(!empty($p['fecha'])) $fechas[$p['fecha']]= true;
    foreach ($progreso_tests as $t) if(!empty($t['fecha'])) $fechas[$t['fecha']] = true;

    $fechas = array_keys($fechas);
    sort($fechas);

    // Arrays acumulativos (datos acumulados)
    $acum_tareas = 0;
    $acum_tests = 0;

    $tareas_acum = [];
    $tests_acum = [];

    foreach ($fechas as $f) {
        // Tareas
        $p = 0;
        foreach ($progreso_tareas as $t) {
            if ($t['fecha'] == $f) $p += (int)$t['puntos'];
        }
        $acum_tareas += $p;
        $tareas_acum[] = $acum_tareas;

        // Tests
        $p = 0;
        foreach ($progreso_tests as $t){
            if ($t['fecha']==$f) $p += (int)$t['puntos'];
        }
        $acum_tests += $p;
        $tests_acum[] = $acum_tests;
    }

    return [
        'fechas' => $fechas,
        'puntos_tareas' => $tareas_acum,
        'puntos_tests' => $tests_acum
    ];
}

// Obtener curso alumno
function obtener_curso_alumno($id_alumno){
    $bd = conexion_bd();

    $stmt = $bd->prepare("SELECT id_curso
                        FROM alumno_curso
                        WHERE id_alumno = :id");
    
    $stmt->execute([':id' => $id_alumno]);
    return $stmt->fetchColumn();
}

// Devolver info del alumno
function obtener_alumno($id_alumno) {
    $bd = conexion_bd();

    $stmt = $bd->prepare("SELECT id_usuario, nombre, apellidos
                          FROM usuario
                          WHERE id_usuario = :id");
    $stmt->execute([':id' => $id_alumno]);
    return $stmt->fetch(PDO::FETCH_ASSOC);
}

// Funcion registrar tiempo
function registrar_tiempo_estudio($id_usuario, $id_tarea, $tiempo_estudio, $puntos_obtenidos){
    $bd = conexion_bd();

    $sql = "INSERT INTO progreso (id_usuario, id_tarea, tiempo_estudio, puntos_obtenidos, fecha)
            VALUES (:id_usuario, :id_tarea, :tiempo_estudio, :puntos_obtenidos, NOW())";

    $stmt = $bd->prepare($sql);

    return $stmt->execute([
        ':id_usuario' => $id_usuario,
        ':id_tarea' => $id_tarea,
        ':tiempo_estudio' => $tiempo_estudio,
        ':puntos_obtenidos' => $puntos_obtenidos
    ]);
}

// Sumar puntos
function sumar_puntos($id_usuario, $puntos, $tiempo_sumado = 0){
    $bd = conexion_bd();

    // Sumar tiempos y puntos
    $sql = "UPDATE puntaje_alumno
            SET puntos = puntos + :puntos,
                tiempo_total = tiempo_total + :tiempo
            WHERE id_usuario = :id";

    $stmt = $bd->prepare($sql);
    return $stmt->execute([
        ':puntos' => $puntos,
        ':tiempo' => $tiempo_sumado,
        ':id' => $id_usuario
    ]);
}

// Obtener puntaje alumno
function obtener_puntaje_alumno($id_usuario){
    $bd = conexion_bd();

    $stmt = $bd->prepare("SELECT puntos, tiempo_total
                        FROM puntaje_alumno
                        WHERE id_usuario = :id");

    $stmt->execute([':id' => $id_usuario]);

    return $stmt->fetch(PDO::FETCH_ASSOC);
}
?>