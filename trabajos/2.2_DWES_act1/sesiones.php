<?php
/**
 * Comprueba si el usuario inicio sesion antes de acceder a la aplicacion
 * Su NO inicio sesion, lo redirige al login
 */
    function comprobar_sesion () {
        session_start();

        if (!isset($_SESSION['user']['id_usuario'])) {
            header("Location: login.php?redirigido=true");
            exit();
        }
    }
?>