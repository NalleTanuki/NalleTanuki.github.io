<?php
    /** Explicacion de la Modificacion:
     *  - Formulario HTML:
     *      Permite al usuario ingresar usu_nom, usu_ape y usu_correo.
     *      Usa POST para enviar los datos al mismo script PHP.
     *  - Captura de Datos en PHP:
     *      Se usa htmlspecialchars() para prevenir inyeccion de codigo malicioso.
     *      Se almacenan los datos en variables $usu_nom, $usu_ape y $usu_correo
     *  - Generacion Dinamica del Request SOAP:
     *      Se reemplazan los valores fijos con los datos ingresados por el usuario.
     *  - Ejecucion de la solicitud SOAP via cURL:
     *      Se mantiene el codigo original con curl_exec() para enviar la peticion y recibir la respuesta
     *  - Salida de la Respuesta del Servidor:
     *      Se imprime el resultado del servicio web en un <pre> para facilitar la lectura.
     *  - Resultado:
     *      Cuando un usuario llena el formulario y presiona 'Enviar', los datos ingresados se incluiran
     *        en la solicitud SOAP, y el servidor devolvera una respuesta con el resultado de la operacion.
     */

    //  Si el formulario fue enviado por POST...
    if($_SERVER["REQUEST_METHOD"] == "POST")
    {
        // Capturamos los datos del formulario y los limpia con htmlspecialchars
        $usu_nom = htmlspecialchars($_POST['usu_nom']);
        $usu_ape = htmlspecialchars($_POST['usu_ape']);
        $usu_correo = htmlspecialchars($_POST['usu_correo']);

        // URL del archivo WSDL del servicio web al que se enviara la solicitud
        $location = "http://localhost/dwes/SOAP_Service/InsertCategoria.php?wsdl";

        // Construccion manual del cuerpo XML del mensaje SOAP con los datos del formulario
        // Envia 3 parametros: usu_nom, usu_ape y usu_correo
        $request = "<soapenv:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ins=\"InsertCategoriaSOAP\">
            <soapenv:Header/>
            <soapenv:Body>
                <ins:InsertCategoriaService soapenv:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\">
                    <InsertCategoria xsi:type=\"ins:InsertCategoria\">
                        <!--Parametros enviados al servicio-->
                        <usu_nom xsi:type=\"xsd:string\">$usu_nom</usu_nom>
                        <usu_ape xsi:type=\"xsd:string\">$usu_ape</usu_ape>
                        <usu_correo xsi:type=\"xsd:string\">$usu_correo</usu_correo>
                    </InsertCategoria>
                </ins:InsertCategoriaService>
            </soapenv:Body>
            </soapenv:Envelope>";

        // Define los encabezados HTTP necesarios para la solicitud SOAP
        $headers = [
            'Method: POST', //Metodo HTTP usado
            'Connection: Keep-Alive', //Mantener la conexion abierta
            'User-Agent: PHP-SOAP-CURL', //Agente identificador del cliente
            'Content-Type: text/xml; charset=utf-8', // Tipo de contenido (XML)
            'SOAPAction: InsertCategoriaService' // SOAPAction se usa para indicar la operacion que se quiere ejecutar
        ];

        // Inicializar cURL con el URL del servicio web
        $ch = curl_init($location); // Inicializacion de la sesion cURL con el URL del servicio
        curl_setopt($ch, CURLOPT_RETURNTRANSFER, true); //Indica q queremos recibir la respuesta como una cadena (en vez de mostrarla directamente)
        curl_setopt($ch, CURLOPT_HTTPHEADER, $headers); //se aplican los encabezados HTTP definidos antes
        curl_setopt($ch, CURLOPT_POST, true); // Establece que la peticion se enviara como POST
        curl_setopt($ch, CURLOPT_POSTFIELDS, $request);  // Envia el contenido de la peticion SOAP
        curl_setopt($ch, CURLOPT_HTTP_VERSION, CURL_HTTP_VERSION_1_1); //Define la version HTTP a usar. En servicios SOAP se suele usar HTTP 1.1

        // Ejecutar la solicitud SOAP
        $response = curl_exec($ch); //Ejecuta la peticion SOAP y almacena la respuesta del servidor
        $err_status = curl_errno($ch); // Verifica si hubo errores

        curl_close($ch); // Cierra la sesion de cURL

        // Mostrar la respuesta del servicio web en pantalla
        echo "<h3>Respuesta del Servidor:</h3>";
        echo "<pre>" . htmlentities($response) . "</pre>";
    }
?>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Formulario SOAP</title>
</head>
<body>
    <h2>Enviar Datos al Servicio SOAP</h2>
    
    <form method="post"> <!-- El formulario usara el metodo POST para enviar los datos al mismo archivo PHP -->
        <!-- Campo de entrada para NOMBRE -->
        <label for="usu_nom">Nombre:</label>
        <input type="text" id="usu_nom" name="usu_nom" required>
        <br><br>

        <!-- Campo de entrada para APELLIDO -->
        <label for="usu_ape">Apellido:</label>
        <input type="text" id="usu_ape" name="usu_ape" required>
        <br><br>

        <!-- Campo de entrada para el CORREO -->
        <label for="usu_correo">Correo Electrónico:</label>
        <input type="email" id="usu_correo" name="usu_correo" required>
        <br><br>

        <!-- BOTON que envia el formulario y activa todo el bloque PHP-->
         <button type="submit">Enviar</button>
    </form>
</body>
</html>