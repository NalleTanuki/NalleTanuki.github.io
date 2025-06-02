<?php
    // Importar la clase PHPMailer desde la biblioteca PHPMailer
    use PHPMailer\PHPMailer\PHPMailer;
    /** Incluir el archivo autoload.php generado por Composer
     * que permite cargar las dependencias necesarias de PHPMailer*/
    require "vendor/autoload.php";

    function enviar_correos ($carrito, $pedido, $correo) {
        // crear_correo llama a esta funcion para crear el cuerpo del correo
        $cuerpo = crear_correo($carrito, $pedido, $correo);
        /**
         * luego llama a enviar_correo_multiples para enviar el correo a varias direcciones:
         *      - al restaurante
         *      - al correo proporcionado (dwes@afleal.es)
         */
        return enviar_correo_multiples("$correo, dwes@afleal.es", $cuerpo, "Pedido $pedido confirmado");
    }

    /** Esta funcion genera el contenido HTML del correo y recibe:
     *      - $carrito: el carrito de compras
     *      - $pedido: el numero de pedido
     *      - $correo: el correo del restaurante
    */
   function crear_correo ($carrito, $pedido, $correo) {
    // Encabezado con el nº de pedido y el nombre del restaurante
    $texto = "<h1>Pedido n&uacute;mero $pedido </h1><h2>Restaurante: $correo </h2>";
    $texto .= "Detalle del pedido:";

    // Se consulta la BD para obtener los productos en el carrito
    $productos = cargar_productos(array_keys($carrito));
    $texto .= "<table>"; // Abrir la tabla
    $texto .= "<tr>
                <th>Nombre</th>
                <th>Descripción</th>
                <th>Peso</th>
                <th>Unidades</th>
            </tr>";

    $pesoTotalPedido = 0.0; //Creo variable inicilizada a 0 para guardar el peso de todo el pedido

    foreach ($productos as $producto) { //Recorro todos los productos que hay en el carrito
        $cod = $producto['CodProd'];
        $nom = $producto['Nombre'];
        $des = $producto['Descripcion'];
        $peso = $producto['Peso'];

        $unidades = $_SESSION['carrito'][$cod]; //Obtenemos las unidades de los productos
        $pesoTotalPedido = $pesoTotalPedido + $peso * $unidades; // Calcular peso del pedido

        $texto .= "<tr>
                    <td>$nom</td>
                    <td>$des</td>
                    <td>$peso</td>
                    <td>$unidades</td>
                </tr>";
    }
    $texto .= "</table>";
    $texto .= "<hr><h3>Peso total del pedido: $pesoTotalPedido</h3>";

    return $texto; //Devuelve el cuerpo (la tabla)
   }


   function enviar_correo_multiples ($lista_correos, $cuerpo, $asunto = "Pedido realizado")  {
    // Configurar PHPMailer para enviar el correo a traves de un servidor SMTP
    $mail = new PHPMailer();
    $mail -> IsSMTP();
    $mail -> SMTPDebug   = 0; // Cambiar a 1 o 2 para ver errores
    $mail -> SMTPAuth   = true;
    $mail -> SMTPSecure = "tls";
    $mail -> Host       = "smtp.ionos.es";
    $mail -> Port       = 587;
    $mail -> Username   = "dwes@afleal.es"; // Usuario de ionos
    $mail -> Password   = "Ch1nd4sv1nt0."; // Contrasenha de ionos
    $mail -> SetFrom ('dwes@afleal.es', 'Sistema de pedidos');
    $mail -> Subject    = $asunto;
    $mail -> MsgHTML($cuerpo);
    $mail -> CharSet    = "UTF-8";

    // Partir la lista de correos por la coma
        // explode se usa para dividir una cadena de txt en un arreglo
    $correos = explode(",", $lista_correos);

    // Recorrer cada correo en el array $correos
    foreach ($correos as $correo) {
        // Se usa AddAddres para anhadir el correo como destinatario del mensaje
        $mail -> AddAddress ($correo, $correo);
    }

    // Verificacion de envio
    if (!$mail -> Send()) { //Si falla el envio entonces
        return $mail -> ErrorInfo; // devuelve mensaje de error que contiene la info sobre porque no pudo enviarse
    } else { // si SI se pudo enviar
        return TRUE; // devuelve TRUE
    }
   }
?>