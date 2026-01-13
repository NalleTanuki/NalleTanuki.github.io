<?php
    // cerrar sesion
    session_start();      // Inicia la sesion actual
    session_unset();      // Elimina todas las variables de sesion
    session_destroy();    // Destruye sesion

    header("Location: login.php"); // Redirige a login
    
    exit;
?>