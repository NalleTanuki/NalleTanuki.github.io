<?php
    // Archivos necesarios para manejar sesiones y la BD
    require_once 'sesiones.php';
    require_once 'bd.php';

    //Se verifica si el usuario tiene una sesion activa
    comprobar_sesion();

    // se verifica si se recibio un id de contacto valido en el URL
    if (isset($_GET["id_contacto"])) {
        //Obtener el id_contacto del URL
        $id_contacto = $_GET["id_contacto"];

        // Llamada a la funcion que elimina el contacto de la BD
        $resultado = eliminar_contacto($id_contacto);

        if ($resultado) {
            // si la eliminacion sale bien -> redirige a la agenda con un mensaje
            header("Location: agenda.php?mensaje=Contacto eliminado");
            exit;
        } else {
            // Si hay un error al eliminar -> se muestra un mensaje
            echo "Hubo un error al eliminar el contacto.";
        }
    } else {
        // Mensaje de error si no se recibe un ID valido
        echo "ID de contacto no válido.";
    }
?>