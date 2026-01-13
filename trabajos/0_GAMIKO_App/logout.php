<?php
    /** Eliminar la info de la sesion:
     *      Aqui se vacia la variable global de sesion eliminando todos los valores
     *      almacenados dentro de la sesion. Borrando asi cualquier dato que fuera guardado
     */
    session_start();
    $_SESSION = array();

    session_destroy(); //Eliminar-Destruir la sesion
    setcookie(session_name(), '', time() - 3600); //Eliminar la cookie
?>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Sesión cerrada</title>
    <link rel="icon" href="img/logo/favicon/fav.png" type="image/x-icon">
 
    <link rel="stylesheet" type="text/css" href="css/estilo.css">         <!-- Estilos generales -->
    <link rel="stylesheet" type="text/css" href="css/logout.css">    <!-- Estilos propios -->
</head>
<body>
    <!-- LOGO -->
    <div class="logo-container">
        <a href="login.php"></a><img src="img/logo/logo_nombre.png" alt="Logo de la aplicación" class="logo">
    </div>

    <!-- CONTENEDOR txt y boton -->
    <div id="contenedor">
        <p>La sesión se cerró correctamente, <span>sayonara</span>.</p>
        <button type="button" id="pagInicio" onclick="location.href='login.php'">Ir a la página de login.</button>
    </div>
</body>
</html>