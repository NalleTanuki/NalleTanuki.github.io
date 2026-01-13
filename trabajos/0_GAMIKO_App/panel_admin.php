<?php
    require_once 'bd.php';
    comprobar_sesion('admin');
    $bd = conexion_bd();
    require_once 'cabecera.php';

    $mensaje = ''; // Mensajes

    // CREAR USUARIO
    if(isset($_POST['accion']) && $_POST['accion'] === 'crear'){
        $nombre = trim($_POST['nombre']);
        $apellidos = trim($_POST['apellidos']);
        $correo = trim($_POST['correo']);
        $contrasenha = trim($_POST['contrasenha']);
        $rol = trim($_POST['rol']);
        $curso = isset($_POST['curso']) ? (int)$_POST['curso'] : null;

        // 1 - BUSCAR SI YA EXISTE EL CORREO
        $stmt = $bd->prepare("SELECT id_usuario, rol FROM usuario WHERE correo = ?");
        $stmt->execute([$correo]);
        $usuarioExistente = $stmt->fetch(PDO::FETCH_ASSOC);

        if($usuarioExistente){
            // 2- -YA EXISTE → SOLO ASIGNAR OTRO CURSO
            $id_usuario = $usuarioExistente['id_usuario'];
            $rolExistente = $usuarioExistente['rol'];

            // si existe y es profe -> puede tener + cursos
            if($rolExistente === "profesor" && $rol === "profesor"){
                // Evitar duplicar curso
                $stmt = $bd->prepare("SELECT 1 FROM profesor_curso
                                        WHERE id_profesor = ?
                                        AND id_curso = ?");
                $stmt->execute([$id_usuario, $curso]);

                if($stmt->fetch()){
                    $mensaje = "El profesor ya tiene asignado este curso.";
                    $tipoMensaje = "error";
                } else {
                    $stmt = $bd->prepare("INSERT INTO profesor_curso (id_profesor, id_curso) VALUES (?, ?)");
                    $stmt->execute([$id_usuario, $curso]);

                    $mensaje = "Curso añadido al profesor.";
                    $tipoMensaje = "success";
                }
            // Si existe y es alumno -> No puede tener + cursos
            } else {
                $mensaje= "Este alumno ya existe.";
                $tipoMensaje = "error";
            }

        } else {
            // 3️ - NO EXISTE → CREARLO NORMAL
            $id_nuevo = crear_usuario($nombre, $apellidos, $correo, $contrasenha, $rol, $curso);

            if($id_nuevo){
                // Si el usuario tiene rol=alumno -> crear fila en puntaje_alumno
                if($rol === "alumno"){
                    crear_puntaje_inicial($id_nuevo);
                }
                    $mensaje = "Usuario creado correctamente.";
                    $tipoMensaje = "success";
            } else {
                    $mensaje = "Error al crear el usuario.";
                    $tipoMensaje = "error";
            }
        }
    }

    // MODIFICAR USUARIO
    if(isset($_POST['accion']) && $_POST['accion'] === 'modificar'){
        $id_usuario = (int)$_POST['id_usuario'];
        $nombre = trim($_POST['nombre']);
        $apellidos = trim($_POST['apellidos']);
        $correo = trim($_POST['correo']);
        $contrasenha = trim($_POST['contrasenha']);
        $rol = trim($_POST['rol']);
        $curso = isset($_POST['curso']) ? (int)$_POST['curso'] : null;

        if(modif_usuario($id_usuario, $nombre, $apellidos, $correo, $contrasenha, $rol, $curso)){
            $mensaje = "Usuario modificado correctamente.";
            $tipoMensaje = "success";
        } else {
            $mensaje = "Error al modificar el usuario.";
            $tipoMensaje = "error";
        }
    }

    // ELIMINAR USUARIO
    if(isset($_POST['accion']) && $_POST['accion'] === 'eliminar'){
        $id_usuario = (int)$_POST['id_usuario'];

        if(eliminar_usuario($id_usuario)){
            $mensaje = "Usuario eliminado correctamente";
            $tipoMensaje = "success";
        } else {
            $mensaje = "Error al eliminar el usuario.";
            $tipoMensaje = "error";
        }
    }

    // CREAR ASIGNATURA
    if(isset($_POST['accion']) && $_POST['accion'] === 'crear_asignatura'){
        $nombre = trim($_POST['nombre_asig']);
        $descripcion = trim($_POST['descripcion_asig']);
        $id_profesor = (int)$_POST['profesor_asig'];
        $id_curso = (int)$_POST['curso_asig'];

        try{
            if(crear_asignatura($nombre, $descripcion,$id_profesor, $id_curso)){
                $mensaje = "Asignatura creada correctamente.";
                $tipoMensaje = "success";
            }
        }catch(Exception $e){
            $mensaje = $e->getMessage();
            $tipoMensaje = "error";
        }
    }


    //ELIMINAR ASIGNATURA
    if(isset($_POST['accion']) && $_POST['accion'] === 'eliminar_asignatura'){
        $id_asignatura = (int)$_POST['id_asignatura'];

        try{
            $stmt = $bd -> prepare("DELETE FROM asignatura
                                    WHERE id_asignatura = :id");
            $stmt -> execute([':id' => $id_asignatura]);

            $mensaje = "Asignatura eliminada correctamente";
            $tipoMensaje = "success";
        }catch(Exception $e){
            $mensaje = "Error al eliminar la asignatura.";
            $tipoMensaje = "error";
        }
    }

    // MODIFICAR ASIGNATURA
    if(isset($_POST['accion']) &&  $_POST['accion'] === 'modificar_asignatura'){
        $id_asig = (int)$_POST['id_asignatura'];
        $nombre = trim($_POST['nombre']);
        $descripcion = trim($_POST['descripcion']);

        try{
            $stmt = $bd->prepare("UPDATE asignatura
                                    SET nombre = :nombre, descripcion = :descripcion
                                    WHERE id_asignatura = :id");

            $stmt->execute([
                ':nombre' => $nombre,
                ':descripcion' => $descripcion,
                ':id' => $id_asig
            ]);

            $mensaje = "Asignatura modificada correctamente.";
            $tipoMensaje = "success";
        }catch(Exception $e){
            $mensaje = "Error al modificar la asignatura.";
            $tipoMensaje = "error";
        }
    }

    // Obtener cursos
    try {
        $stmtCursos = $bd -> query("SELECT id_curso, nombre
                                    FROM curso
                                    ORDER BY nombre ASC");
        $cursos = $stmtCursos -> fetchAll(PDO::FETCH_ASSOC);
    } catch (Exception $e) {
        $cursos = [];
    }

    // Obtener lista susuarios
    try {
    $sql = "(SELECT u.id_usuario, u.nombre, u.apellidos, u.correo, u.rol,
                    c.nombre AS curso, c.id_curso
            FROM usuario u
            LEFT JOIN alumno_curso ac ON u.id_usuario = ac.id_alumno
            LEFT JOIN profesor_curso pc ON u.id_usuario = pc.id_profesor
            LEFT JOIN curso c ON c.id_curso = COALESCE(ac.id_curso, pc.id_curso))
            ORDER BY u.id_usuario ASC";

    $stmt = $bd->query($sql);
    $usuarios = $stmt->fetchAll(PDO::FETCH_ASSOC);

    } catch (Exception $e) {
        $usuarios = [];
        $mensaje .= "<br>Error al obtener la lista de usuarios.";
    }

    // Obtener profes
    try {
        $stmtProfes = $bd -> query("SELECT id_usuario, nombre, apellidos
                                    FROM usuario WHERE rol='profesor'
                                    ORDER BY nombre ASC");
        $profesores = $stmtProfes -> fetchAll(PDO::FETCH_ASSOC);
    } catch (Exception $e) {
        $profesores = [];
    }

    //Obtener asignaturas con nombre profes y cursos
    try{
        $sqlAsig = "SELECT a.id_asignatura, a.nombre, a.descripcion,
                        CONCAT(u.nombre, ' ' , u.apellidos) AS profesor,
                        c.nombre AS curso
                    FROM asignatura a
                    JOIN usuario u ON a.id_profesor = u.id_usuario
                    JOIN curso c ON a.id_curso = c.id_curso
                    ORDER BY a.id_asignatura ASC";

        $stmtAsig = $bd->query($sqlAsig);
        $asignaturas = $stmtAsig->fetchAll(PDO::FETCH_ASSOC);

    }catch(Exception $e){
        $asignaturas = [];
        $mensaje .= "<br>Error al cargar asignaturas.";
    }
?>



<!-- --------------------------------------- HTML -------------------------------------- -->
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Panel Admin</title>
    <link rel="icon" href="img/logo/favicon/fav.png" type="image/x-icon"> <!-- favicon -->
 
    <link rel="stylesheet" type="text/css" href="css/estilo.css">         <!-- Estilos generales -->
    <link rel="stylesheet" type="text/css" href="css/panel_admin.css">    <!-- Estilos propios -->

    <!-- POP UP alerta -->
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
</head>
<body>
    <!-- -------------- CREAR USUARIOS -------------- -->
    <div id="contenedor_acciones">
        <!-- Titulo -->
        <h1 id="crear-usuario">Crear Usuario</h1>

        <!-- Formulario -->
        <form method="POST" action="" class="form-crear" id="formu-crear">
            <input type="hidden" name="accion" value="crear">

            <div class="fila">
                <input type="text" name="nombre" placeholder="Nombre">
                <input type="text" name="apellidos" placeholder="Apellidos">
            </div>

            <div class="fila">
                <input type="email" name="correo" placeholder="Correo" required>

                <!-- LABEL para cumplir la ACCESIBILIDAD -->
                <label for="curso" class="visually-hidden">Seleccionar curso</label>
                <select name="curso" required aria-label="Curso">
                    <?php foreach ($cursos as $c): ?>
                        <option value="<?= $c['id_curso'] ?>"><?= htmlspecialchars($c['nombre']) ?></option>
                    <?php endforeach; ?>
                </select>
            </div>
            
            <div class="fila">
                <!-- LABEL para cumplir la ACCESIBILIDAD -->
                <label for="rol" class="visually-hidden">Seleccionar rol</label>
                <select name="rol" required aria-label="Rol">
                    <option value="alumno">Alumno</option>
                    <option value="profesor">Profesor</option>
                    <option value="admin">Admin</option>
                </select>

                <div class="campo-pass">
                    <input type="password" id="contrasenha" name="contrasenha" placeholder="Contraseña" required>
                    <!-- BOTON para mostrar/ocultar contrasenha -->
                    <button type="button" id="togglePassword" aria-label="Mostrar u ocultar contraseña">
                        <img id="iconoPass" src="img/icon/ver.png" alt="Ver contraseña">
                    </button>
                </div>
            </div>
            
            <!-- Requisitos contrasenha -->
            <ul id="pass-requisitos" class="pass-requisitos">
                <li id="min12">12 caracteres o más</li>
                <li id="mayus">Incluye mayúsculas</li>
                <li id="minus">Incluye minúsculas</li>
                <li id="num">Incluye números</li>
                <li id="caracter">Incluye caracteres especiales (!,+#...)</li>
            </ul>

            <!-- Boton ENVIAR -->
            <div class="fila fila-final">
                <input type="submit" class="btn-crear" value="Crear Usuario">
            </div>
        </form>

        <!-- MENSAJE - POP UP -->
        <?php if (!empty($mensaje)): ?>
            <script>
                Swal.fire({
                    title: "Información",
                    text: "<?= htmlspecialchars($mensaje) ?>",
                    icon: "<?= $tipoMensaje ?>",
                    showConfirmButton: false,
                    timer: 1500
                    // confirmButtonColor: "#3A7DFF"
                });
            </script>
        <?php endif; ?>
    </div>


    
    <!-- -------------- CREAR ASIGNATURAS -------------- -->
    <div id="contenedor-asignatura">
        <!-- Titulo -->
        <h1 id="crear-asignatura">Crear Asignatura</h1>

        <!-- Formulario -->
        <form method="POST" action="" class="form-crear">
            <input type="hidden" name="accion" value="crear_asignatura">

            <div class="fila">
                <input type="text" name="nombre_asig" placeholder="Nombre" required>
                <input type="text" name="descripcion_asig" placeholder="Descripción">
            </div>

            <div class="fila">
                <!-- Profe responsable -->
                <label for="profesor_asig" class="visually-hidden">Profesor responsable</label>
                <select name="profesor_asig" required aria-label="Profesor responsable">
                    <option value="">Seleccionar profesor</option>
                    <?php foreach($profesores as $p): ?>
                        <option value="<?= $p['id_usuario'] ?>">
                            <?= htmlspecialchars($p['nombre'] . ' ' . $p['apellidos']) ?>
                        </option>
                    <?php endforeach; ?>
                </select>
            </div>

            <div class="fila">
                <!-- Curso asociado -->
                <label for="curso_asig" class="visually-hidden">Curso asociado</label>
                <select name="curso_asig" required aria-label="Curso asociado" id="select-curso-asig">
                    <option value="">Seleccionar curso</option>
                </select>
            </div>

            <!-- Boton ENVIAR -->
            <div class="fila fila-final">
                <input type="submit" class="btn-crear" value="Crear Asignatura">
            </div>
        </form>

        <!-- MENSAJE - POP UP -->
        <?php if (!empty($mensaje)): ?>
            <script>
                Swal.fire({
                    title: "Información",
                    text: "<?= htmlspecialchars($mensaje) ?>",
                    icon: "<?= $tipoMensaje ?>",
                    showConfirmButton: false,
                    timer: 1500
                    // confirmButtonColor: "#3A7DFF"
                });
            </script>
        <?php endif; ?>
    </div>



    <!-- -------------- USUARIOS REGISTRADOS -------------- -->
    <div id="contenedor-registros">
        <!-- TITULO -->
        <h1 id="ver-usuarios">Usuarios Registrados</h1>

        <!-- tabla -->
        <div class="tabla-scroll">
            <table>
                <tr>
                    <th>ID</th>
                    <th>Nombre</th>
                    <th>Apellidos</th>
                    <th>Email</th>
                    <th>Curso</th>
                    <th>Rol</th>
                    <th>Acción</th>
                </tr>

                <?php if (!empty($usuarios)): ?>
                    <?php foreach ($usuarios as $usu): ?>
                        <tr data-curso-id="<?= htmlspecialchars($usu['id_curso']) ?>">
                            <td><?= $usu['id_usuario'] ?></td>
                            <td><?= htmlspecialchars($usu['nombre']) ?></td>
                            <td><?= htmlspecialchars($usu['apellidos']) ?></td>
                            <td><?= htmlspecialchars($usu['correo']) ?></td>
                            <td><?= htmlspecialchars($usu['curso']) ?></td>
                            <td><?= htmlspecialchars($usu['rol']) ?></td>
                            <td>
                                <div class="acciones">
                                    <!-- boton MODIFICAR -->
                                    <form method="POST" style="display:inline;">
                                        <input type="hidden" name="id_usuario" value="<?= $usu['id_usuario'] ?>">
                                        <button type="button" class="btn-modificar" data-id="<?= $usu['id_usuario'] ?>">Modificar</button>
                                    </form>

                                    <!-- boton ELIMINAR -->
                                    <form method="POST" style="display:inline;">
                                        <input type="hidden" name="accion" value="eliminar">
                                        <input type="hidden" name="id_usuario" value="<?= $usu['id_usuario'] ?>">
                                        <button type="submit" class="btn-eliminar" onclick="return confirm('¿Seguro que quieres eliminar a este usuario?');">Eliminar</button>
                                    </form>
                                </div>
                            </td>
                        </tr>
                    <?php endforeach; ?>
                    <?php else: ?>
                        <tr>
                            <td colspan="7">No hay usuarios registrados.</td>
                        </tr>
                <?php endif; ?>
            </table>
        </div>


        <div id="editar-usuario" class="card">
            <h2>Modificar Usuario</h2>  <!-- SUBtitulo -->
                <form method="POST" action="" id="form-editar-usu">
                    <input type="hidden" name="accion" value="modificar">
                    <input type="hidden" name="id_usuario" id="edit-id">

                    <input type="text" name="nombre" id="edit-nombre" placeholder="Nombre" required>
                    <input type="text" name="apellidos" id="edit-apellidos" placeholder="Apellidos" required>
                    <input type="email" name="correo" id="edit-correo" placeholder="Correo" required>
                    <input type="password" name="contrasenha" id="edit-contrasenha" placeholder="Nueva contraseña (opcional)">
                    <select name="curso" id="edit-curso">
                        <option value="">-- Selecciona un curso --</option>
                            <?php foreach ($cursos as $c): ?>
                                <option value="<?= $c['id_curso'] ?>"><?= htmlspecialchars($c['nombre']) ?></option>
                            <?php endforeach; ?>
                    </select>
                    <select name="rol" id="edit-rol">
                        <option value="alumno">Alumno</option>
                        <option value="profesor">Profesor</option>
                        <option value="admin">Admin</option>
                    </select>
                    
                    <input type="submit" value="Guardar Cambios">
                    <button type="button" id="cancelar-edicion">Cancelar</button>
                </form>
        </div>
    </div>



    <!-- -------------- ASIGNATURAS REGISTRADAS -------------- -->
    <div id="contenedor-registros">
        <!-- TITULO -->
        <h1 id="ver-asignaturas">Asignaturas Registradas</h1>

        <!-- Tabla -->
        <div class="tabla-scroll">
            <table>
                <tr>
                    <th>ID</th>
                    <th>Nombre</th>
                    <th>Descripción</th>
                    <th>Profesor</th>
                    <th>Curso</th>
                    <th>Acción</th>
                </tr>

                <?php if (!empty($asignaturas)): ?>
                    <?php foreach ($asignaturas as $asig): ?>
                        <tr>
                            <td><?= $asig['id_asignatura'] ?></td>
                            <td><?= htmlspecialchars($asig['nombre']) ?></td>
                            <td><?= htmlspecialchars($asig['descripcion']) ?></td>
                            <td><?= htmlspecialchars($asig['profesor']) ?></td>
                            <td><?= htmlspecialchars($asig['curso']) ?></td>
                            <td>
                                <div class="acciones">
                                    <!-- boton MODIFICAR -->
                                    <form method="POST" style="display:inline;">
                                        <input type="hidden" name="id_asignatura" value="<?= $asig['id_asignatura'] ?>">
                                        <button type="button" class="btn-modificar-asig" data-id="<?= $asig['id_asignatura'] ?>">Modificar</button>
                                    </form>

                                    <!-- boton ELIMINAR -->
                                    <form method="POST" style="display:inline;">
                                        <input type="hidden" name="accion" value="eliminar_asignatura">
                                        <input type="hidden" name="id_asignatura" value="<?= $asig['id_asignatura'] ?>">
                                        <button type="submit" class="btn-eliminar" onclick="return confirm('¿Seguro que quieres eliminar esta asignatura?');">Eliminar</button>
                                    </form>
                                </div>
                            </td>
                        </tr>
                    <?php endforeach; ?>
                    <?php else: ?>
                        <tr>
                            <td colspan="6">No hay asignaturas registradas.</td>
                        </tr>
                <?php endif; ?>
            </table>
        </div>


        <!-- Editar asignatura -->
        <div id="editar-asignatura" class="card">
            <h2>Modificar Asignatura</h2>
                <form method="POST" action="" id="form-editar-asig">
                    <input type="hidden" name="accion" value="modificar_asignatura">
                    <input type="hidden" name="id_asignatura" id="edit-id-asig">

                    <input type="text" name="nombre" id="edit-nombre-asig" placeholder="Nombre" required>
                    <input type="text" name="descripcion" id="edit-descripcion-asig" placeholder="Descripción" required>
                    
                    <input type="submit" value="Guardar Cambios">
                    <button type="button" id="cancelar-edicion-asig">Cancelar</button>
                </form>
        </div>
    </div>

    <!-- JS -->
    <script src="js/script_admin.js"></script>
</body>
</html>