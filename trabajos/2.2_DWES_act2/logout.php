<?php
    require_once 'sesiones.php'; //Verifica que el usuario haya iniciado sesion
    require_once 'bd.php'; //Contiene funciones para interacturar con la BD
    comprobar_sesion(); //Llama a la funcion que redirige a login.php si no hay sesion iniciada
    vaciar_carrito($_SESSION['carrito']); //Llamo a la funcion vaciar_carrito()

    /** Eliminar la info de la sesion:
     *      Aqui se vacia la variable global de sesion eliminando todos los valores
     *      almacenados dentro de la sesion. Borrando asi cualquier dato que fuera guardado
     */
    $_SESSION = array();

    session_destroy(); //Eliminar-Destruir la sesion
    setcookie(session_name(), 123, time() - 1000); //Eliminar la cookie
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
    <a href="login.php">Ir a la página de login.</a>
</body>
</html>