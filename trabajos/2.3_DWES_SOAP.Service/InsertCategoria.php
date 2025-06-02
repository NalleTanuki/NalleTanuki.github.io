<?php
    /**Servicio SOAP que permite insertar un usuario mediante una estructura compleja.
     * Se usa la libreria NuSOAP (v. 0.9.11) instalada via Composer.
     * 
     * Se definen tipos complejos, configura WSDL y atiende peticiones con un metodo insert_usuario.
     */

     
    /** Incluir la libreria nusoap que instalamos mediante Composer
     * 
     * Esta libreria nos permite crear y consumir servicios web SOAP con PHP.
     * Es necesario indicar la ruta desde la carpeta vendor que genera Composer
    */
    require_once "vendor/econea/nusoap/src/nusoap.php";


    /** DEFINIR ESPACIO DE NOMBRES (namespace) del servicio web
     * 
     * Esto se utiliza para identificar de forma unica el servicio y asi evitar conflictos con otros servicios
    */
    $namespace = "InsertCategoriaSOAP"; //Nombre del servicio SOAP que estamos creando
    

    /** CREAR UNA NUEVA INSTANCIA SOAP_SERVER
     * 
     * Este objeto es el encargado de manejar las peticiones y respuestas SOAP
    */
    $server = new soap_server();


    /** CONFIGURAR EL WSDL (Web Service Description Language) para este servidor
     * 
     * Parametros:
     *      - Primero: es el nombre del servicio (para identificarlo)
     *      - Segundo: es el namespace que definimos antes 
    */
    $server -> configureWSDL("InsertCategoria", $namespace); // Declarar un server


    /** ESTABLECER EL ESPACIO DE NOMBRES (namespace) que se utilizara en el esquema XML generado
     * 
     * Esto es importante para que los clientes SOAP puedan entender la estructura de los mensajes
    */
    $server -> wsdl -> schemaTargetNamespace = $namespace;


    /** DEFINIR ESTRUCTURA DEL SERVICIO SOAP
     * 
     * Mediante addComplexType se crea una estructura (struct) llamada InsertCategoria que recibe 3 campos de tipo string:
     *      usu_nom: nombre del usuario
     *      usu_ape: apellido del usuario
     *      usu_correo: correo del usuario
    */
    $server -> wsdl -> addComplexType(
        'InsertCategoria', // Nombre del tipo complejo
        'complexType',     // Tipo del tipo
        'struct',          // uso de estructura tipo "struct" (estructura con campos nombrados)
        'all',             // todos los campos deben estar presentes sin importar el orden
        '',                // no se usa un tipo base, se deja vacio xq no se hereda de ningun otro tipo base
        array(
            'usu_nom' => array('name' => 'usu_nom', 'type' => 'xsd:string'),
            'usu_ape' => array('name' => 'usu_ape', 'type' => 'xsd:string'),
            'usu_correo' => array('name' => 'usu_correo', 'type' => 'xsd:string')
        )
    );


    /** DEFINIR ESTRUCTURA DE LA RESPUESTA DEL SERVICIO SOAP
     * 
     * Se crea otro tipo complejo llamado Response que representa la respuesta del servicio. Contiene un unico campo:
     *      - Resultado: de tipo booleano, indica si la operacion se realizo correctamente
     * 
     * Esta estructura sera usada como salida del metodo SOAP registrado.
    */
    $server -> wsdl -> addComplexType(
        'response',        // Nombre del tipo complejo
        'complexType',     // Tipo del tipo
        'struct',          // uso de estructura tipo "struct"
        'all',             // todos los campos deben estar presentes sin importar el orden
        '',                // no se usa un tipo base, se deja vacio xq no se hereda de ningun otro tipo base
        array(
            'Resultado' => array('name' => 'Resultado', 'type' => 'xsd:boolean')
        )
    );


    /** REGISTRO DEL METODO DEL SERVICIO WEB
     * 
     * Este metodo se llama InsertCategoriaService y se registra para atender peticiones SOAP con los siguientes datos:
    */
    $server -> register(
        "InsertCategoriaService", // Nombre de la funcion PHP que atiende la peticion
        array("InsertCategoria" => "tns:InsertCategoria"), // Array de parametros de entrada, de tipo tns:InsertCategoria
        array("InsertCategoria" => "tns:response"), //Array de parametros de salida, de tipo tns:response
        $namespace, // namespace que se utiliza y definido previamente
        false, // valor por defecto para SOAPAction
        "rpc", // Modo/Estilo de llamada
        "encoded", // Codificacion
        "Inserta una categoría" // Descripcion

        //tns -> hace referencia al espacio de nombres definido, es obligatorios para realcionar correctamente los tipos complejos
    );


    /**
     * Definir la funcion PHP que gestiona la operacion del servicio
     * 
     * La funcion devuelve siempre Resultado como true.
     * En un caso real, aqui:
     *      - se procesarian los datos recibidos
     *      - se insertarian en la BD y
     *      - se devolveria el resultado
    */
    function InsertCategoriaService($request)
    {
        // NOTA: si fuese una implementacion real deberiamos validar y sanear los datos antes de insertarlos en la BD
        require_once "config/conexion.php"; // Se incluye el archivo que establece la conexion a la BD
        require_once "models/Usuario.php"; //Se incluye el modelo que contiene la logica para insertar un usuario

        $usuario = new Usuario(); //Crear nuevo objeto de la clase Usuario

        /** Llamada al metodo "insert_usuario" pasandole los datos recibidos en el $request
         * Este metodo es el encargado de insertar al nuevo usuario en la BD
         */
        $usuario -> insert_usuario($request["usu_nom"], $request["usu_ape"], $request["usu_correo"]);

        return array( // se devuelve un array asociativo como respuesta del servicio
            "Resultado" => true // el cliente SOAP recibe esta respuesta indicando que la operacion fue exitosa
        );
    }



    /** LEER LOS DATOS RECIBIDOS A TRAVES DE LA PETICION SOAP
     * 
     * Se usa php://input para obtener el contenido de la peticion HTTP, es decir,
     * permite recibir la solicitud XML sin depender de $_POST (no es util en servicios SOAP)
    */
    $POST_DATA = file_get_contents("php://input");

    /** EJECUTAR EL SERVICIO SOAP ENVIANDO LA PETICION RECIBIDA
     * 
     * Se pasa el contenido de $POST_DATA para que el servidor procese la solicitud y genere la respuesta SOAP adecuada
    */
    $server -> service($POST_DATA);
    exit(); // Finalizar script para evitar que se ejecute codigo adicional despues de enviar la respuesta al cliente SOAP 
?>