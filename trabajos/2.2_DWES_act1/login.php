<?php
    require_once 'bd.php';
    session_start();
    /** ============== Formulario de login ==============
     * Se maneja la autenticacion de usuarios y la creacion de nuevos usuarios
     * 
     *Si el usuario intenta acceder:
     *   - Si las credenciales son CORRECTAS:
     *      -> Se inicia sesion y se guardan los datos del usuario en $_SESSION
     *      -> Se redirige a agenda.php
     *   - Si las credenciales son INCORRECTAS:
     *      - Se muestra mensaje de error
     * 
     * Si el usuario intenta "dar de alta" un nuevo usuario:
     *   - Si las credenciales son INCORRECTAS:
     *      -> Se muestra un mensaje de error
     *   - Si el usuario es administrador (rol = 1):
     *      - Se guarda la sesion y se redirige a crear_usuario.php
     *   - Si el usuario NO es administrador (rol=0):
     *      - Se muestra un mensaje de error indicando falta de privilegios.
    */
    if ($_SERVER['REQUEST_METHOD'] == "POST") {
        if (isset($_POST['accion']) && $_POST['accion'] == "acceder") {
            //Intento de inicio de sesion
            $usu = comprobar_usuario($_POST['user'], $_POST['pass']);
            
            if ($usu === FALSE) {
                //Si las credenciales son incorrectas, muestra error
                $error = TRUE;
                $usuario = $_POST['user']; // Mantenemos el usuario en el campo de entrada
            } else {
                // Sino (credenciales correctas), se guardan los datos del usuario en la sesion
                $_SESSION['user'] = [
                    'user' => $usu['user'],
                    'rol' => $usu['rol'],
                    'id_usuario' => $usu['id_usuario']
                ];

                //Redirigimos a la agenda
                header("Location: agenda.php");
                exit();
            }
        } elseif (isset($_POST['accion']) && $_POST['accion'] == "dar_de_alta") {
            // Intento de dar de alta un usuario
            $usu = comprobar_usuario($_POST['user'], $_POST['pass']);
            
            if ($usu === FALSE) {
                //Si las credenciales son incorrectas -> se muestra un mensaje
                $error = TRUE;
                $usuario = $_POST['user'];
            } elseif ($usu['rol'] == 1) {
                // Si el usuario tiene rol=1 (administrador) -> se redirige a crear_usuario.php
                $_SESSION['user'] = [
                    'user' => $usu['user'],
                    'rol' => $usu['rol'],
                    'id_usuario' => $usu['id_usuario']
                ];
                
                //Redirigir a la pagina de creacion de usuario
                header("Location: crear_usuario.php");
                exit();
            } else {
                // Si el usuario NO tiene privilegios (es decir, rol=0), se muestra mensaje de
                $error_privilegios = TRUE;
            }
        }
    }
?>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Formulario de Login</title>
</head>
<body>
    <!-- TITULO -->
    <h2>Identificación de Usuario</h2>
    <?php
    //MENSAJE si el usuario fue redirigido sin estar autenticado
        if (isset($_GET["redirigido"])) {
            echo "<p>Haga login para continuar.</p>";
        }
    ?>

    <?php
        // Mostrar error si las credenciales son incorrectas
        if (isset($error) && $error == true) {
            echo "<p>Revise usuario y contraseña.</p>";
        }

        // Mostrar error si el usuario NO tiene permisos
        if (isset($error_privilegios) && $error_privilegios == true) {
            echo "<p>No tiene los privilegios necesarios para dar de alta nuevos usuarios.</p>";
        }
    ?>

    <!-- FORMULARIO -->
    <form action="<?php echo htmlspecialchars($_SERVER["PHP_SELF"]);?>" method="POST">
        <label for="usuario">Usuario: </label>
        <input value = "<?php if (isset($usuario)) echo $usuario; ?>" id="usuario" name="user" type="text" placeholder="correo@correo.com" required>

        <label for="clave">Clave: </label>
        <input id="clave" name="pass" type="password" placeholder="****" required>
        
        <!-- BOTON para ACCEDER -->
        <button type="submit" name="accion" value="acceder">Acceder</button>
        
        <!-- BOTON para DAR DE ALTA -->
        <button type="submit" name="accion" value="dar_de_alta">Dar de alta</button>
    </form>
</body>
</html>