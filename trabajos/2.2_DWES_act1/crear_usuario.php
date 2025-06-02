<?php
    // Archivos necesarios para manejar sesiones y la BD
    require_once 'sesiones.php';
    require_once 'bd.php';

    //Se verifica si el usuario tiene una sesion activa
    comprobar_sesion();

    // Si el formulario se envio por metodo POST
    if ($_SERVER['REQUEST_METHOD'] == "POST") {
        //Se obtienen y se limpian los datos del formulario
        $user = trim($_POST["user"]);   //Nombre de usuario
        $pass = trim($_POST["pass"]);   // Contrasenha
        $rol = trim($_POST['rol']);     //Rol del usuario

        // Validamos que el usuario y la contrasenha no esten vacios
        if (!empty($user) && !empty($pass)) {
            // Se inserta el nuevo usuario en la BD
            $resultado = insertar_usuario($user, $pass, $rol);

            // Si la insercion sale bien -> redirige a la agenda (agenda.php) con un mensaje
            if ($resultado === true) {
                header("Location: agenda.php?mensaje=Usuario añadido");
                exit;
            } else {
                //Si hay error -> se muestra un mensaje en rojo
                echo "<p style='color:red;'>" . htmlspecialchars($resultado) . "</p>"; //Se muestra un mensaje de error
            }
        } else {
            // Si el usuario y la contrasenha estan vacios -> se muestra un mensaje
            echo "<p style='color:red;'>El usuario y la contraseña son obligatorios.</p>";
        }
    }
?>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Añadir Usuario</title>
</head>
<body>
    <?php
    //Se incluye la cabecera
    require 'cabecera.php';    
    ?>

    <!-- TITULO -->
    <h2>Añadir un nuevo usuario</h2>

    <!-- FORMULARIO para anhadir un nuevo usuario -->
    <form action="crear_usuario.php" method="POST">
        <label>User: <input type="text" name="user" placeholder = "correo@correo.com" required></label><br>
        <label>Contraseña: <input type="password" name="pass" required></label><br>
        <label>Rol: 
            <select name="rol">
                <option value=""></option>
                <option value="0">Sin privilegios</option>
                <option value="1">Con privilegios</option>
            </select>
        </label><br>

        <input type="submit" value="Añadir usuario">
    </form> <br>
    <!-- ENLACE apra volver a la agenda (agenda.php) -->
    <a href="agenda.php">Volver a la agenda</a>
</body>
</html>