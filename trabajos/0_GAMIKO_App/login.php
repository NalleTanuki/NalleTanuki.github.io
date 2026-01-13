<?php 
    require_once 'bd.php'; //Importar archivo de conexion y funciones de BD

    /**Formulario de login habitual:
     *  - si va bien -> abre sesion, guarda el nombre de usuario y redirige a panel (segun el rol del usuario)
     *  - si va mal -> mensaje de error
     */
    if($_SERVER["REQUEST_METHOD"] == "POST"){        // Verifica si se envio el formulario por POST
        $correo = trim($_POST['correo']);            //Limpia espacios en el correo
        $contrasenha = trim($_POST['contrasenha']);  //Limpia espacios en la contrasenha

        $usu = comprobar_usuario($correo, $contrasenha);  // comprobar_usuario definida en bd.php

        if($usu === false){              // Si NO existe el usuario o los datos NO coinciden
            $err = true;                 // activa varible err
            $correo = $_POST['correo'];  // Mantiene el corroe introducido en el formulario
        } else {                         // si el usuario es valido
            session_start();             // inicia sesion PHP

            // Guarda info sobre el usuario en variables de sesion
            $_SESSION['id_usuario'] = $usu['id_usuario'];
            $_SESSION['nombre'] = $usu['nombre'];
            $_SESSION['correo'] = $usu['correo'];
            $_SESSION['rol'] = $usu['rol'];

            // var_dump($_SESSION); // solo para probar -----------------------------------

            // Redirigir segun el rol
            switch ($usu['rol']) {
                case 'alumno':
                    header("Location: panel_alumno.php");
                    break;
                case 'profesor':
                    header("Location: panel_profesor.php");
                    break;
                case 'admin':
                    header("Location: panel_admin.php");
                    break;
                default:
                    header("Location: login.php?error=rol");
            }
            exit;  //Detiene la ejecución del script
        }
    }
?>


<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Login</title>
        <link rel="favicon Gamiko" href="img/logo/logo_nombre.png" type="image/x-icon">

        
        <link rel="stylesheet" type="text/css" href="css/estilo.css">   <!-- Estilos generales -->
        <link rel="stylesheet" type="text/css" href="css/login.css">    <!-- Estilos propios -->
    </head>

    <body>
        <!-- muestra mensaje si el usuario es redirigido desde otra pagina sin estar logueado  -->
        <?php
            if(isset($_GET["redirigido"])){
                echo "<p>Haz login para continuar.</p>";
            }
        ?>
        <!-- Mostrar mensaje de error si las credenciales son incorrectas -->
        <?php
            if(isset($err) and $err == true){
                echo "<p>Revisa el correo y la contraseña.</p>";
            }
        ?>
        
        <!-- LOGO -->
            <div class="logo-container">
                <img src="img/logo/logo_nombre.png" alt="Logo de la aplicación" class="logo">
            </div>

        <!-- FORMULARIO -->
        <form action = "<?php echo htmlspecialchars($_SERVER["PHP_SELF"]);?>" method="POST">
            <!-- CORREO -->
            <input placeholder="Correo electrónico" value="<?php if(isset($correo)) echo htmlspecialchars($correo);?>"
                id ="correo"
                name="correo"
                type="email"
                required
                pattern="[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$"
                title="Correo electronico"
            >

            <!-- CONTRASENHA -->
            <div class="pass-container">
                <input placeholder="Contraseña"
                id="contrasenha"
                name="contrasenha"
                type="password"
                required 
                title="Contrasenha"        
                >

                <!-- BOTON para mostrar/ocultar contrasenha -->
                <button type="button" id="togglePassword" aria-label="Mostrar u ocultar contraseña">
                    <img id="iconoPass" src="img/icon/ver.png" alt="Ver contraseña">
                </button>
            </div>


            <!-- boton ENVIAR -->
            <input type="submit" value="ENTRAR">
        </form>
        <!-- JS -->
        <script src="js/script.js"></script>
    </body>
</html>