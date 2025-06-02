<!-- Para comprobar que solo puedan acceder a la aplicacion los usuarios que hayan hecho login,
 se usa la funcion comprobar_sesion() de este mismo fichero -->

<?php
    // Asegurar de que se inicia sesion
    function comprobar_sesion(){
        session_start(); // inicia sesion

        if (!isset($_SESSION['usuario'])){ // Comprueba si $_SESSION['usuario'] esta definida
            // si NO lo esta entonces...
            /** Redirige a login.php y
             * se anhade a la URL -> ?redirigido=true para mostrar un mensaje (login.php) */
            header("Location: login.php?redirigido=true");
        }
    }
?>