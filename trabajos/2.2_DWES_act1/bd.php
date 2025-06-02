<?php
    /** ===================== VALIDACION =====================
     * Se carga la configuracion desde un archivo XML validado por un XSD
     */
    function leer_config($nombre, $esquema) {
        $config = new DOMDocument();
        $config -> load($nombre);
        $res = $config -> schemaValidate($esquema);

        if ($res === FALSE) {
            throw new InvalidArgumentException("Revise el fichero de configuración.");
        }

        // Extrae los datos del XML
        $datos = simplexml_load_file($nombre);
        $ip = $datos->xpath("//ip");
        $nombre = $datos->xpath("//nombre");
        $usu = $datos->xpath("//usuario");
        $clave = $datos->xpath("//clave");

        // Cadena de conexion a la BD
        $cad = sprintf("mysql:dbname=%s;host=%s", $nombre[0], $ip[0]);

        // Se devuelven los datos en un array
        $resul = [];
        $resul[] = $cad;
        $resul[] = $usu[0];
        $resul[] = $clave[0];
        return $resul;
    }



     /** ===================== COMPROBAR USUARIO ===================== 
      * Comprueba si el usuario y la contrasenja son correctos
      * Se usan consultas preparadas para evitar inyecciones SQL
      *     $user -> Nombre de usuario
      *     $pass -> Contrasenha proporcionada
      * Devuekve datos del usuario si la autenticacion es correcta o FALSE si falla
     */
     function comprobar_usuario($user, $pass) {
        //Cargar la configuracion de la BD
        $res = leer_config(dirname(__FILE__)."/configuracion.xml", dirname(__FILE__)."/configuracion.xsd");
        $bd = new PDO($res[0], $res[1], $res[2]); //Crear nueva conexion PDO a la BD

        try {
            $sql = "SELECT id_usuario, user, pass, rol FROM usuarios
                    WHERE user = :user"; // Consulta preparada
            $consultaUsuario = $bd -> prepare($sql);
            $consultaUsuario -> execute([':user' => $user]); // Asocia el valor de :user


            if ($consultaUsuario -> rowCount() === 1) {
                $row = $consultaUsuario -> fetch(PDO::FETCH_ASSOC);
                // Verificamos la contrasenha cifrada usando password_verify
                if (password_verify($pass, $row['pass'])) {
                    return $row; // Devuelve datos del usuario
                } else {
                    return FALSE; // Contrasenha incorrecta
                }
            } else {
                return FALSE; // Usuario no encontrado
            }
        } catch (Exception $e) {
            // Manejo de errores si hay algun fallo en la conexion o la consulta
            return FALSE;
        }
    }
    


    /** ===================== CIFRAR CLAVES =====================
    * Recorre la tabla de usuarios y cifra las contrasenjas si aun NO lo estan
    * Usamos password_hash() / bcrypt para cifrar las contrasenhas
    * ANOTACIONES:
    *   - Se generea una cadena de 60 caracteres
    *   - Comienzan con "$2a$", "$2y$" o "$2b$"
    */
    function cifrar_claves() {
        //Cargar la configuracion de la BD
        $res = leer_config(dirname(__FILE__)."/configuracion.xml", dirname(__FILE__)."/configuracion.xsd");
        $bd = new PDO($res[0], $res[1], $res[2]); //Crear nueva conexion PDO a la BD

        $sql01 = "SELECT id_usuario, pass FROM usuarios";
        $usuarios = $bd -> query($sql01);

        // Ciframos cada clave y actualizamos
        foreach ($usuarios as $usu) {
            $codUsuario = $usu['id_usuario'];
            $claveOriginal = $usu['pass'];

            //Si la clave ya esta cifrada, la saltamos
            if (strlen($claveOriginal) === 60 && preg_match('/^\$2[ayb]\$/', $claveOriginal)) {
                continue;
            }

            $claveCifrada = password_hash($claveOriginal, PASSWORD_BCRYPT);

            echo "Cifrada la clave: $claveOriginal como $claveCifrada - ";

            // Actualizamos la clave cifrada
            $sql02 = "UPDATE usuarios
                      SET pass = '$claveCifrada'
                      WHERE id_usuario = $codUsuario";
            try {
                $bd -> query($sql02);
            } catch (Exception $e) {
                return FALSE;
            }
        }
    }



    /** ===================== OBTENER CONTACTOS =====================
     * Devuelve todos los contactos de un usuario con sus telefonos
     */
    function obtener_contactos($id_usuario) {
        //Cargar la configuracion de la BD
        $res = leer_config(dirname(__FILE__)."/configuracion.xml", dirname(__FILE__)."/configuracion.xsd");
        $bd = new PDO($res[0], $res[1], $res[2]); //Crear nueva conexion PDO a la BD
    
        try {
            // Primero obtenemos los contactos
            $sql = "SELECT id_contacto, nombre, apellidos, email
                    FROM contactos
                    WHERE id_usuario = :id_usuario";
    
            $consulta = $bd -> prepare($sql);
            $consulta -> execute([':id_usuario' => $id_usuario]);
            $contactos = $consulta -> fetchAll(PDO::FETCH_ASSOC);
    
            // Para cada contacto, obtenemos sus teléfonos
            foreach ($contactos as &$contacto) {
                $sqlTelefonos = "SELECT telefono FROM telefonos
                                 WHERE id_contacto = :id_contacto";
                $consultaTelefonos = $bd-> prepare($sqlTelefonos);
                $consultaTelefonos -> execute([':id_contacto' => $contacto['id_contacto']]);
                $contacto['telefonos'] = $consultaTelefonos -> fetchAll(PDO::FETCH_COLUMN); // Obtenemos solo los valores
            }
    
            return $contactos;
        } catch (PDOException $e) {
            echo "Error: " . $e->getMessage();
            return [];
        }
    }
    


    /** ===================== INSERTAR CONTACTOS =====================
     * Un contacto puede repetirse en la agenda de cada usuario pero no en la misma
     * Un contacto puede tener mas de un telefono
     */
    function insertar_contacto($nombre, $apellidos, $email, $telefonos) {
        //Cargar la configuracion de la BD
        $res = leer_config(dirname(__FILE__)."/configuracion.xml", dirname(__FILE__)."/configuracion.xsd");
        $bd = new PDO($res[0], $res[1], $res[2]); //Crear nueva conexion PDO a la BD
    
        $id_usuario = $_SESSION['user']['id_usuario'];
    
        try {
            // Comprobar si ya existe un contacto con el mismo email para este usuario
            $sqlEmail = "SELECT COUNT(*) FROM contactos
                            WHERE email = :email
                            AND id_usuario = :id_usuario";
            $consultaEmail = $bd -> prepare($sqlEmail);
            $consultaEmail -> execute([':email' => $email, ':id_usuario' => $id_usuario]);
            $emailExiste = $consultaEmail -> fetchColumn();
    
            if ($emailExiste > 0) {
                return "Este email ya está registrado en tu agenda.";
            }

            // Comrpobar si ya existe alguno de los telefonos en la tabla "telefonos"
            $sqlValidarTelefono = "SELECT COUNT(*) FROM telefonos t
                                   JOIN contactos c ON t.id_contacto = c.id_contacto
                                   WHERE t.telefono = :telefono AND c.id_usuario = :id_usuario";
            $consultaTelefono = $bd -> prepare($sqlValidarTelefono);

            foreach ($telefonos as $tlf) {
                $consultaTelefono -> execute([':telefono' => $tlf, 'id_usuario' => $id_usuario]);
                if ($consultaTelefono -> fetchColumn() > 0) {
                    return "El número de teléfono $tlf ya está registrado en otro contacto.";
                }
            }
    
            // Insertar el contacto en la tabla contactos
            $sqlContacto = "INSERT INTO contactos (nombre, apellidos, email, id_usuario)
                            VALUES (:nombre, :apellidos, :email, :id_usuario)";
            $consultaContacto = $bd -> prepare($sqlContacto);
            $consultaContacto -> execute([
                ':nombre' => $nombre,
                ':apellidos' => $apellidos,
                ':email' => $email,
                ':id_usuario' => $id_usuario
            ]);
    
            //Obtener el id del contacto que se acaba de insertar
            $id_contacto = $bd->lastInsertId();
    
            // Insertar los telefonos
            $sqlTelefono = "INSERT INTO telefonos (id_contacto, telefono)
                            VALUES (:id_contacto, :telefono)";
            $consultaTelefono = $bd -> prepare($sqlTelefono);
    
            foreach ($telefonos as $telefono) {
                $consultaTelefono -> execute([
                    ':id_contacto' => $id_contacto,
                    ':telefono' => $telefono
                ]);
            }
    
            return true;
        } catch (PDOException $e) {
            return "Error al añadir el contacto: " . $e->getMessage();
        }
    }    



    /** ===================== ELIMINAR CONTACTOS =====================
     */
    function eliminar_contacto ($id_contacto) {
        //Cargar la configuracion de la BD
        $res = leer_config(dirname(__FILE__)."/configuracion.xml", dirname(__FILE__)."/configuracion.xsd");
        $bd = new PDO($res[0], $res[1], $res[2]); //Crear nueva conexion PDO a la BD

        try {
            //Verifica si el contacto existe
            $sqlVerificacion = "SELECT COUNT(*) FROM contactos
                                WHERE id_contacto = :id_contacto";
            $consultaVerificacion = $bd -> prepare($sqlVerificacion);
            $consultaVerificacion -> execute([':id_contacto' => $id_contacto]);
            $contacto_existe = $consultaVerificacion -> fetchColumn() ?: 0;

            //Si el contacgto NO existe, muestra un mensaje
            if ($contacto_existe == 0) {
                echo "El contacto no existe.";
                return FALSE;
            }

            // Si el contacto SI existe, lo eliminamos
            $sqlEliminacion = "DELETE FROM contactos WHERE id_contacto = :id_contacto";
            $consultaEliminacion = $bd -> prepare($sqlEliminacion);
            $consultaEliminacion -> execute([':id_contacto' => $id_contacto]);

            return TRUE;
        } catch (PDOException $e) {
            return FALSE;
        }
    }



    /** ===================== INSERTAR USUARIO =====================
     */
    function insertar_usuario ($user, $pass, $rol) {
        //Cargar la configuracion de la BD
        $res = leer_config(dirname(__FILE__)."/configuracion.xml", dirname(__FILE__)."/configuracion.xsd");
        $bd = new PDO($res[0], $res[1], $res[2]); //Crear nueva conexion PDO a la BD

        try {
            //Comprobacion si el usuario ya existe en la BD
            $sqlComprobacion = "SELECT COUNT(*) FROM usuarios
                                WHERE user = :user";
            $consultaComprobacion = $bd -> prepare($sqlComprobacion);
            $consultaComprobacion -> execute([':user' => $user]);
            $existe = $consultaComprobacion -> fetchColumn();

            if ($existe > 0) {
                return "El usuario '$user' ya existe";
            }

            // si el usuario NO existe entonces lo insertamos
            //Cifrar contrasenha antes de guardarla
            $pass_cifrada = password_hash($pass, PASSWORD_BCRYPT);

            $sql = "INSERT INTO usuarios (user, pass, rol)
                    VALUES (:user, :pass, :rol)";
            $consultaInserccion = $bd -> prepare($sql);
            $consultaInserccion -> execute([
                ':user' => $user,
                ':pass' => $pass_cifrada,
                ':rol' => $rol
            ]);
        
            return true;
        } catch (PDOException $e) {
                return "Hubo un error al añadir el usuario.";
        }
    }
?>