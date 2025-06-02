<?php
    // Archivos necesarios para manejar sesiones y la BD
    require_once 'sesiones.php';
    require_once 'bd.php';

    //Se verifica si el usuario tiene una sesion activa
    comprobar_sesion();

    // Vaciar todas las variables de sesion
    $_SESSION = array();

    // Eliminar la sesion
    session_destroy();

    //Eliminar la cookie de sesion
    setcookie(session_name(), 123, time() - 1000);
?>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Sesión cerrada</title>
</head>
<body>
    <p>La sesión se cerró correctamente, hasta la próxima.</p>


    <!-- ENLACE para volver a la pagina de login -->
    <a href="login.php">Ir a la página de login.</a>
</body>
</html>