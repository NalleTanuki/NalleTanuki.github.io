<?php
// Archivo que funciona como un cliente SOAP que envia manualmente una peticion XML mediante cURL
// cURL (client for URLs): permite realizar peticiones a servidores web desde la linea de comandos o desde programas, como PHP

    // Direccion del servicio web WSDL (donde se encuentra el servicio InsertCategoria)
    $location = "http://localhost/dwes/SOAP_Service/InsertCategoria.php?wsdl";

    /** Crear el cuerpo del mensaje SOAP en formato XML
     * 
     * Este mensaje contiene los datos necesarios para invocar el metodo del servicio InsertCategoriaService
     * Se envian los datos del nuevo usuario: nombre, apellido y correo.
     */
    $request = "
        <soapenv:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ins=\"InsertCategoriaSOAP\">
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
        </soapenv:Envelope>  
    ";

    // Mostrar la peticion SOAP enviada - imprime en pantalla el XML que se esta enviando
    print("Request : <br>");
    print("<pre>" . htmlentities($request) . "</pre>");

    $action = "InsertCategoriaService"; // Nombre del metodo que se esta invocando (SOAPAction)

    // Cabeceras HTTP necesarias para la peticion SOAP
    $headers = [
        'Method: POST', //Metodo HTTP usado
        'Connection: Keep-Alive', //Mantener la conexion abierta
        'User-Agent: PHP-SOAP-CURL', //Agente identificador del cliente
        'Content-Type: text/xml; charset=utf-8', // Tipo de contenido (XML)
        'SOAPAction: "InsertCategoriaService"' // Accion SOAP que se esta llamando - Indica la operacion que queremos que se ejecute
    ];

    $ch = curl_init($location); // Inicializacion de la sesion cURL con el URL del servicio
    curl_setopt($ch, CURLOPT_RETURNTRANSFER, true); // Indica que se desea recibir la respuesta como string
    curl_setopt($ch, CURLOPT_HTTPHEADER, $headers); // Establecer cabeceras HTTP - Le dice a cURL que use las cabeceras definidas antes
    curl_setopt($ch, CURLOPT_POST, true); //Indica que se usara el metodo POST
    curl_setopt($ch, CURLOPT_POSTFIELDS, $request); // Envia el contenido de la peticion SOAP
    curl_setopt($ch, CURLOPT_HTTP_VERSION, CURL_HTTP_VERSION_1_1); //Usar version 1.1 de HTTP (que es el recomendado para servicios SOAP)
    
    $response = curl_exec($ch); // Ejecutar la peticion y guardar la respuesta (del servicio) en $response
    $err_status = curl_errno($ch); // Capturar posibles errores - Guarda el codigo del error si hay algun problema

    // Mostrar la respuesta del servicio (nos sirve para verificar si se inserto correctamente)
    print("Request : <br>");
    print("<pre>".$response."</pre>");
?>