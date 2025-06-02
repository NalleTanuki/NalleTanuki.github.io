<!-- Se encarga de procesar un pedido,
 insertarlo en la BD
 enviar un correo de confirmacion y
 vaciar el carrito de la compra -->
<?php
    //Comprueba que el usuario haya abierto sesion o redirige
    require 'correo.php'; //Contiene funciones para el envio de correos
    require 'sesiones.php'; //Verifica que el usuario haya iniciado sesion
    require_once 'bd.php'; //Contiene funciones para interacturar con la BD
    comprobar_sesion(); //Llama a la funcion que redirige a login.php si no hay sesion iniciada
?>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Pedidos</title>
</head>
<body>
    <?php
        require 'cabecera.php'; //Cabecera

        /**Inserta el pedido en la BD, tomando el contenido del carrito $_SESSION['carrito']
         * y el codigo del restaurante $_SESSION['usuario']['codRes']
         * Si la insercion falla, insertar_pedido() devuelve FALSE
         */
        $resul = insertar_pedido($_SESSION['carrito'], $_SESSION['usuario']['codRes']);

        // Mensaje de exito o error al procesar el pedido
        if ($resul === FALSE) { // si insertar_pedido devuelve FALSE
            echo "No se ha podido realizar el pedido.<br>"; //muestra mensaje de ERROR
        } else { //sino, si el pedido se inserta correctamente...
            $correo = $_SESSION['usuario']['correo']; //obtiene el correo del usuario
            echo "Pedido realizado con éxito. Se enviará un correo de confirmación a: $correo"; //muestra mensaje de EXITO
            
            //Envio de correo y confirmacion
            /**La funcion enviar_correos se encarga de enviar un correo de confirmacion al usuario sobre su pedido
             * Recibe el:
             *      - carrito $_SESSION['carrito']
             *      - ID del pedido $resul
             *      - Correo del usuario $correo
             */
            $conf = enviar_correos($_SESSION['carrito'], $resul, $correo);
            if ($conf !== TRUE) { //si el envio del correo falla
                echo "Error al enviar: $conf <br>"; // Mostrar el error
            };

            //Vaciar carrito
            $_SESSION['carrito'] = [];
        }
    ?>
</body>
</html>