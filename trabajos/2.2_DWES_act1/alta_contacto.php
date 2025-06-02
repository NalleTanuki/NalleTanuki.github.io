<?php
    // Archivos necesarios para manejar sesiones y la BD
    require_once 'sesiones.php';
    require_once 'bd.php';

    //Se verifica si el usuario tiene una sesion activa
    comprobar_sesion();

    // Si el formulario se envio por metodo POST
    if ($_SERVER['REQUEST_METHOD'] == "POST") {
        //Se obtienen y se limpian los datos del formulario
        $nombre = trim($_POST["nombre"]);
        $apellidos = trim($_POST["apellidos"]);
        $email = trim($_POST['email']);
        $telefonos = $_POST['telefonos']; // Se reciben los telefonos en un array
        $id_usuario = $_SESSION['user']['id_usuario']; //Se obtiene el ID del usuario

        //Verificacion para que todos los campos sean ingresados
        if (!empty($nombre) && !empty($apellidos) && !empty($email) && !empty($telefonos)) {
            // Llamada a la funcionque inserta el contacto en la BD
            $resultado = insertar_contacto($nombre, $apellidos, $email, $telefonos);

            if ($resultado === true) {
                //Si la insercion sale bien -> se redirige a la agenda (agenda.php) con un mensaje
                header("Location: agenda.php?mensaje=Contacto añadido");
                exit;
            } else {
                //Si hay un error -> se muestra mensaje en rojo
                echo "<p style='color:red;'>" . htmlspecialchars($resultado) . "</p>";
            }
        } else {
            //Mensaje de error si algun campo esta vacio
            echo "Todos los campos son obligatorios.";
        }
    }
?>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Añadir Contacto</title>
</head>
<body>
    <?php
    //Se incluye la cabecera
    require 'cabecera.php';    
    ?>

    <!-- TITULO -->
    <h2>Añadir un nuevo contacto</h2>

    <!-- FORMULARIO para anhadir un contacto -->
    <form action="alta_contacto.php" method="POST">
        <label>Nombre: <input type="text" name="nombre" required></label><br>
        <label>Apellidos: <input type="text" name="apellidos" required></label><br>
        <label>Email: <input type="email" name="email" placeholder="correo@correo.com" required></label><br>
        <!-- Para ingresar VARIOS telefonos -->
        <div id="telefonos">
            <label>Teléfono: <input type="text" name="telefonos[]" pattern="\d{9}" required></label>
        </div>

        <!-- BOTON para anhadir mas telefonos de forma dinamica -->
        <button type="button" onclick="agregarTelefono()">Añadir otro teléfono</button> <br> <br>
        
        <input type="submit" value="Añadir contacto"> <br> <br>
    </form>
    <!-- ENLACE para volver a la agenda (agenda.php) -->
    <a href="agenda.php">Volver a la agenda</a>

    <!-- Script para que permita agregar mas telefonos dinamicamente -->
    <script src="agregarTelefonos.js"></script>
</body>
</html>