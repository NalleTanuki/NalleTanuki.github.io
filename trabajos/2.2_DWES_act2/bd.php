<?php
    // VALIDAR
    /**Esta funcion lee la configuracion de la BD desde un archivo XML
     * valida su formato usando un esquema XSD
     * devuelve los parametros necesarios para realizar una conexion a la BD
     */
    function leer_config ($nombre, $esquema) {
        $config = new DOMDocument(); //Crear nueva instancia de DOMDocument
        $config -> load($nombre); // Cargar el archivo XML
        $res = $config -> schemaValidate($esquema); //Validar XML con el esquema XSD

        if ($res === FALSE) { //Si la validacion falla, lanza una excepcion
            throw new InvalidArgumentException("Revise el fichero de configuración.");
        }

        $datos = simplexml_load_file($nombre); //Leer el XML como SimpleXML
        // Extraer valores especificos usando XPath
        $ip = $datos -> xpath("//ip");
        $nombre = $datos -> xpath("//nombre");
        $usu = $datos -> xpath("//usuario");
        $clave = $datos -> xpath("//clave");
        
        // Construye la cadena de conexion que usa en PDO
        $cad = sprintf("mysql:dbname=%s;host=%s", $nombre[0], $ip[0]);

        /** Devuelve un array con los valores necesarios para conectar:
         *      - la cadena de conexion
         *      - el usuario
         *      - la clave
        */
        $resul =   []; //Creo un array vacio
        $resul[] = $cad;
        $resul[] = $usu[0];
        $resul[] = $clave[0];
        return $resul;
    }

    // CARGAR CATEGORIAS - Devuelve un cursor con el codigo y nombre de las categorias de la BD
    function cargar_categorias () {
        // Leer la configuracion de la BD
        $res = leer_config(dirname(__FILE__) . "/configuracion.xml", dirname(__FILE__) . "/configuracion.xsd");
        // Establecer la conexion a la BD usando PDO
        $bd = new PDO($res[0], $res[1], $res[2]);
        
        // Consulta SQL para obtener el codigo y nombre de todas las categorias
        $ins = "SELECT codCat, nombre FROM categorias";
        $resul = $bd -> query($ins); //Ejecutar la consulta

        //Si NO se pudo ejecutar la consulta
        if (!$resul) {
            return FALSE; // devuelve FALSE
        }

        // Si NO hay categorias (es decir, no hay filas en el resultado)
        if ($resul -> rowCount() === 0) {
            return FALSE; //devuelve FALSE
        }
        //Si hay 1 o mas...
        return $resul;
    }

    // CARGAR CATEGORIA - Devuelve un array con su nombre y descripcion
    function cargar_categoria ($codCat) {
        // Leer la configuracion de la BD
        $res = leer_config(dirname(__FILE__) . "/configuracion.xml", dirname(__FILE__) . "/configuracion.xsd");
        // establecer la conexion a la BD usando PDO
        $bd = new PDO($res[0], $res[1], $res[2]);

        /** Consulta SQL para obtener el nombre y la descripcion
         * de una categoria especifica por su codigo ($codCat)
         */
        $ins = "SELECT nombre, descripcion FROM categorias WHERE codcat = $codCat";
        $resul = $bd -> query($ins); //Ejecutar la consulta

        if (!$resul) { //Si NO se pudo ejecutar la consulta
            return FALSE; //devuelve FALSE
        }

        //si NO se encuentra la categoria (es decir, no hay filas en el resultado)
        if ($resul -> rowCount() === 0) {
            return FALSE; // devuelve FALSE
        }

        //Si hay 1 o mas...
        return $resul -> fetch();
        /**se usa fetch() para obtener una unica fila del conjunto de resultados de la consulta */
    }

    // COMPROBAR USUARIO - Se usa para comprobar los datos del formulario de login
    function comprobar_usuario ($nombre, $clave) {
        // Leer configuracion de la BD
        $res = leer_config(dirname(__FILE__) . "/configuracion.xml", dirname(__FILE__) . "/configuracion.xsd");
        //Establecer la conexion a la BD usando PDO
        $bd = new PDO($res[0], $res[1], $res[2]);

        // Consulta SQL para obtener los datos del restaurante (usuario por correo)
        $ins = "SELECT codRes, correo, clave, rol FROM restaurantes WHERE correo = '$nombre'";
        $resul = $bd -> query($ins); //Ejecutar la consulta

        // Si la consulta devuelve 1 fila, es decir, hay 1 unico usuario con ese correo
        if ($resul -> rowCount() === 1) {
            $row = $resul -> fetch(PDO::FETCH_ASSOC); //obtener los datos del usuario
            //var_dump($row); //COMPROBACION PROPIA

            $claveCifrada = $row['clave'];// Recuperar la clave cifrada que se guarda en la BD
            // echo "$clave - $claveCifrada";

            //Verificar si la clave cifrada coincide con la clave cifrada en la BD
            if (password_verify($clave, $claveCifrada)) {
                // echo "Contraseña verificada.";
                /** Si la clave es correcta devuelve los datos del usuario
                 *  (incluyendo codRes, correo, rol, etc.) */
                $resul = $bd -> query($ins);
                return $resul -> fetch(); //Devuelve la fila con la info del usuario
            } else { //si la clave NO es correcta
                return FALSE; //devuelve FALSE
            }
        }
    }

    // CARGAR PRODUCTOS DE CATEGORIA - Devuelve un cursor con los productos de la categoria que recibe como argumento
    function cargar_productos_categoria ($codCat) {
        // Leer la configuracion de la BD
        $res = leer_config(dirname(__FILE__) . "/configuracion.xml", dirname(__FILE__) . "/configuracion.xsd");
        // Establecer la conexion a la BD usando PDO
        $bd = new PDO($res[0], $res[1], $res[2]);

        // Consulta SQL para obtener todos los productos de una categoria especifica
        $sql = "SELECT * FROM productos WHERE codcat = $codCat";
        $resul = $bd -> query($sql); //ejecutar la consulta

        // Comprobar si la consulta se ejecuto correctamente
        if (!$resul) { //si la consulta falla
            return FALSE; //devuelve FALSE
        }

        // Comprobar si no se encontraron productos en esa categoria
        if ($resul -> rowCount() === 0) { // si NO hay productos
            return FALSE; // devuleve FALSE
        }
        //Si hay 1 o mas...
        return $resul; // Devuelve el conjunto de resultados (productos encontrados)
    }

    /**Recibe un array de codigos de productos
     * devuelve un cursor con los datos de esos productos
     */
    function cargar_productos ($codigosProductos) {
        // Verificar si el array ($codigoProductos) esta vacio
        if (empty($codigosProductos)) { //si esta vacio
            return FALSE;  // devuelve FALSE
        }

        // Leer la configuracion de la BD
        $res = leer_config(dirname(__FILE__) . "/configuracion.xml", dirname(__FILE__) . "/configuracion.xsd");
        $bd = new PDO($res[0], $res[1], $res[2]); //Conectar a la BD con PDO

        // Crear la lista de codigos de productos
        /**La funcion implode() toma el array $codigosProductos y lo convierte en una
         * cadena de texto con los codigos separados por comas
         * esto es util para la consulta SQL
         */
        $texto_in = implode(",", $codigosProductos); //Mete una , en toda la lista de codigos para separar los elementos y asi luego que pueda buscarlo bien en la parte de abajo
        
        // Definir consulta SQL: donde el codigo del producto (codProd) esta en la lista de codigos proporcionada en $texto_in
        $ins = "SELECT * FROM productos WHERE codProd IN ($texto_in)";

        //OJO: si no hay productos en el carro, debe mostrar el mensaje correspondiente
        try {
            // Ejecutar la consulta
            $resul = $bd -> query($ins);
        } catch (Exception $e) { //si ocurre cualquier error durante la ejecucion captura la excepcion
            return FALSE; // y devuelve FALSE
        }
        // Si fue exitosa la consulta
        return $resul -> fetchAll(PDO::FETCH_ASSOC); //Devuelve los datos de los productos como un array asociativo
    }

    // INSERTAR PEDIDO - Devuelve el codigo del nuevo pedido
    function insertar_pedido ($carrito, $codRes) {
        // Leer la configuracion de la BD
        $res = leer_config(dirname(__FILE__) . "/configuracion.xml", dirname(__FILE__) . "/configuracion.xsd");
        // Establecer la conexion a la BD usando PDO
        $bd = new PDO($res[0], $res[1], $res[2]);

        $bd -> beginTransaction(); // iniciar transaccion
        $hora = date("Y-m-d H:i:s", time()); //obtener fecha y hora actual

        // Insertar el pedido en la tabla pedidos
        $sql = "INSERT INTO pedidos(fecha, enviado, restaurante) VALUES ('$hora', 0, $codRes)";
        $resul = $bd -> query($sql); // ejecutar la consulta

        if (!$resul) { // si la insercion NO se realizo correctamente
            return FALSE; //devuelve FALSE
        }

        // Obtener el ID del nuevo pedido insertado para las filas detalle
        $pedido = $bd -> lastInsertId();

        // Insertar los productos del carrito en pedidoproductos
        foreach ($carrito as $codProd => $unidades) {
            $sql = "INSERT INTO pedidosproductos(codped, codprod, Unidades)
                    VALUES ($pedido, $codProd, $unidades)";
            $resul = $bd -> query($sql); //Ejecuto la consulta

            if (!$resul) { // si algo falla en la insercion de productos
                $bd -> rollBack(); // la transaccion es revertida, deshace todas las inserciones realizadas
                return FALSE; // devuelve FALSE
            }
        }
        $bd -> commit(); //confirmar transaccion
        return $pedido; // devolver el ID del pedido recien creado
    }


    // ******************************************************************* MODIFICACIONES *******************************************************************


    // CIFRAR CLAVES
    function cifrar_claves () {
        // Leer la configuracion de la BD
        $res = leer_config(dirname(__FILE__)."/configuracion.xml", dirname(__FILE__)."/configuracion.xsd");
        // Establecer la conexion a la BD usando PDO
        $bd = new PDO($res[0], $res[1], $res[2]);

        // Seleccionar todas las claves actuales de los restaurantes
        $sql01 = "SELECT CodRes, Clave FROM restaurantes"; //Consulta sql para seleccionar el codRes y la clave de la tabla restaurantes
        $restaurantes = $bd -> query($sql01); // ejecutar consulta

        // Recorrer los resultados(cada restaurante) y ciframos las claves
        // Ciframos cada clave y actualizamos la BD
        foreach ($restaurantes as $restau) {
            // Obtener el codigo y clave original del restaurante
            $codRestaurante = $restau['CodRes'];
            $claveOriginal = $restau['Clave'];
            // Luego usa la funcion password_hash() con el algoritmo PASSWORD_BCRYPT para cifrar la clave
            $claveCifrada = password_hash($claveOriginal, PASSWORD_BCRYPT);

            echo "Cifrada la clave: $claveOriginal como $claveCifrada - ";

            // Actualizamos la clave cifrada
            $sql02 = "UPDATE restaurantes SET Clave = '$claveCifrada' WHERE CodRes = $codRestaurante";
            try { //Ejecutamos la consulta sql
                $resul = $bd -> query($sql02);
            } catch (Exception $e) { // si algo sale mal
                return FALSE;  //devuelve FALSE
            }
        }
    }

    // COMPROBAR SI HAY STOCK SUFICIENTE AL ANHADIR UN PRODUCTO AL CARRITO
    function comprobar_stock ($CodProd, $unidades) {
        // Leer la configuracion de la BD
        $res = leer_config(dirname(__FILE__)."/configuracion.xml", dirname(__FILE__)."/configuracion.xsd");
        //Establecer la conexion de la BD usando PDO
        $bd = new PDO($res[0], $res[1], $res[2]);

        //Consultar la cantidad de stock del producto en la BD
        $sql = "SELECT stock FROM productos WHERE codprod = $CodProd";
        $resul = $bd -> query($sql); //Ejecutar consulta
        //obtener el resultado de la consulta (1 fila) y accede al valor del stock de esa fila
        $row = $resul -> fetch(PDO::FETCH_ASSOC);
        $stock = $row['stock']; //este valor es almacenado en la variable $stock

        // Verificar si hay suficiente stock
        if ($unidades <= $stock) { //si las unidades solicitadas son menores o iguales al stock disponible
            return TRUE; //devuelve TRUE = hay suficiente stock para agregar el producto al carrito
        } else { // si las unidaes solicitadas superan el stock disponible
            return FALSE; //devuelve FALSE = no hay suficiente stock
        }
    }

    // FUNCION PARA ACTUALIZAR EL STOCK AL ANHADIR UN PRODUCTO AL CARRITO
    function actualizar_stock ($CodProd, $unidades) {
        // Leer configuracion de la BD
        $res = leer_config(dirname(__FILE__)."/configuracion.xml", dirname(__FILE__)."/configuracion.xsd");
        //Establecer la conexion de la BD usando PDO
        $bd = new PDO($res[0], $res[1], $res[2]);

        // Consultar el stock actual del producto
        $sql = "SELECT stock FROM productos WHERE codprod = $CodProd";
        $resul = $bd -> query($sql); // Ejecutar consulta
        $row = $resul -> fetch(PDO::FETCH_ASSOC); //Devuelve la siguiente fila del resultado de la consulta SQL
        $stock = $row['stock']; //Accede al valor asociado a la clave stock en el array $row y lo asigna a $stock

        // Verificar si hay suficiente stock
        // si las unidades son menores o iguales al stock disponible($stock)
        if ($unidades <= $stock) {
            // Actualizamos el stock
            $nuevoStock = $stock - $unidades; //se calcula el nuevo stock restando las unidades compradas al stock actual
            $sql2 = "UPDATE productos SET stock = $nuevoStock
                     WHERE CodProd = $CodProd"; // Actualizamos al nuevo stock

            // Actualizar el stock
            try {
                // Ejecutar la consulta SQL para actualizar el stock en la BD
                $resul = $bd -> query($sql2);
            } catch (Exception $e) { //Se maneja una posible excepcion
                return FALSE; //se devuelve FALSE si ocurre cualquier error
            }

            return TRUE; //se devuelve TRUE si la actualizacion se realiza con exito

        } else {
            return FALSE;//si NO hay suficiente stock, devuelve FALSE sin hacer cambios en la BD
        }
    }


    // ===========================================================================================================================
    /** GESTION DE RESTAURANTES */
    // INSERTAR un restaurante
    function insertar_restaurante ($correo, $clave, $pais, $cp, $ciudad, $direccion, $rol) {
        // Leer configuracion de la BD
        $res = leer_config(dirname(__FILE__)."/configuracion.xml", dirname(__FILE__)."/configuracion.xsd");
        // establecer la conexion de la BD usando PDO
        $bd = new PDO($res[0], $res[1], $res[2]);

        // Insertar el restaurante
        $sql = "INSERT INTO restaurantes(Correo, Clave, Pais, CP, Ciudad, Direccion, Rol)
                VALUES ('$correo', '$clave', '$pais', $cp, '$ciudad', '$direccion', $rol)";

        // Se intenta ejecutar la consulta SQL
        try {
            // Si la consulta es correcta, inserta el restaurante en la BD
            $resul = $bd -> query($sql);
        } catch (Exception $e) { // Si hay un error, lanza una excepcion
            return FALSE; //devuelve FALSE
        }
    }

    // EDITAR un restuarante
    function editar_restaurante ($correo, $clave, $pais, $cp, $ciudad, $direccion, $rol, $codRes) {
        // Leer configuracion de la BD
        $res = leer_config(dirname(__FILE__)."/configuracion.xml", dirname(__FILE__)."/configuracion.xsd");
        // Establecer la conexion de la BD usando PDO
        $bd = new PDO($res[0], $res[1], $res[2]);

        // Editar el restaurante
        if ($clave) { // Si clave tiene un valor (es decir, que el usuario quiere actualizar su clave) se ejecuta el siguiente SQL
            $sql = "UPDATE restaurantes
                    SET Correo = '$correo', Clave = '$clave', Pais = '$pais', CP = $cp, Ciudad = '$ciudad', Direccion = '$direccion', Rol = '$rol'
                    WHERE CodRes = $codRes";
        } else { //Si NO es la clave, es decir, son otros datos...
            $sql = "UPDATE restaurantes
                    SET Correo = '$correo', Pais = '$pais', CP = $cp, Ciudad = '$ciudad', Direccion = '$direccion', Rol = '$rol'
                    WHERE CodRes = $codRes";
        }

        // Captura de excepciones
        try {
            $resul = $bd -> query($sql); //se ejecuta la consulta
        } catch (Exception $e) { //si hay un error, se captura la excepcion
            return FALSE; // devuelve FALSE
        }
    }

    // ELIMINAR restaurante
    /**
     * Entrada: $codRes - Codigo de Restaurante
     */
    function eliminar_restaurante ($codRes) {
        // Leer la configuracion de la BD
        $res = leer_config(dirname(__FILE__)."/configuracion.xml", dirname(__FILE__)."/configuracion.xsd");
        // Establecer la conexion de la BD usando PDO
        $bd = new PDO($res[0], $res[1], $res[2]);

        // Eliminar el restaurante
        $sql = "DELETE FROM restaurantes WHERE CodRes = $codRes";
        // Captura de excepciones
        try {
            $resul = $bd -> query($sql); //se ejecuta la consulta
        } catch (Exception $e) { // si ocurre un error, se captura la excepcion
            return FALSE; // devuelve FALSE
        }
    }

    // CARGAR restaurantes
    function cargar_restaurantes () {
        // Leer configuracion de la BD
        $res = leer_config(dirname(__FILE__)."/configuracion.xml", dirname(__FILE__)."/configuracion.xsd");
        // Establecer la conexion de la BD usando PDO
        $bd = new PDO($res[0], $res[1], $res[2]);

        // Cargar un restaurante
        $sql = "SELECT * FROM restaurantes"; //se define la consulta SQL
        $resul = $bd -> query($sql); //se ejecuta la consulta SQL

        // Comprobaciones
        if (!$resul) { // si la consulta falla
            return FALSE; // devuelve FALSE
        }
        if ($resul -> rowCount() === 0) { //si NO se encontraron registros en la tabla
            return FALSE; //devuelve FALSE
        }

        //Si hay 1 o mas...
        return $resul;
    }


    // ===========================================================================================================================
    /** GESTION DE CATEGORIAS */

    // INSERTAR una categoria
    function insertar_categoria ($nombre, $descripcion) {
        // Leer configuracion de la BD
        $res = leer_config(dirname(__FILE__)."/configuracion.xml", dirname(__FILE__)."/configuracion.xsd");
        // Establecer la conexion con la BD usando PDO
        $bd = new PDO($res[0], $res[1], $res[2]);

        try{
            // Comprobar si la categoria ya existe
            // Se prepara una consulta SQL que cuenta cuantas filas en la tabla categorias tienen el mismo nombre
            $sql_comprobacion = "SELECT COUNT(*) FROM categorias WHERE Nombre = ?";
            $comprobacion = $bd -> prepare($sql_comprobacion);
            $comprobacion -> execute([$nombre]); // se ejecuta la consulta reemplazando ? por el valor $nombre
            $existe = $comprobacion -> fetchColumn(); // Obtiene el numero de coincidencias encontradas

            if ($existe) { //si la categoria existe, se muestra mensaje de error en rojo
                echo "<p style='color: red;'>Error: La categoría '$nombre' ya existe.</p>";
                return false; // devuelve FALSE
            }

            // Insertar la categoria
            // Se prepara consulta SQL para insertar una nueva categoria
            $sql = "INSERT INTO categorias (Nombre, Descripcion) VALUES (?, ?)";
            $insert = $bd -> prepare($sql);
            $insert -> execute([$nombre, $descripcion]); // se ejecuta la consulta

            // Si la inserccion es exitosa, se muestra mensaje en verde
            echo "<p style='color: green;'> Categoría '$nombre' añadida correctamente.</p>";
            return true; // y la funcion devuelve TRUE
        } catch (Exception $e) { //si ocurre un error en la consulta SQL, se muestra un mensaje de error
            echo "<p style='color: red;' Error al insertar la categoría: " . $e->getMessage() . "</p>";
            return false; // devuelve FALSE
        }
    }

    // EDITAR una categoria
    function editar_categoria ($nombre, $descripcion, $codCat) {
        // Leer configuracion de la BD
        $res = leer_config(dirname(__FILE__)."/configuracion.xml", dirname(__FILE__)."/configuracion.xsd");
        // Establecer conexion a la BD usando PDO
        $bd = new PDO($res[0], $res[1], $res[2]);

        // Crear consulta SQL para editar la categoria
        $sql = "UPDATE categorias SET Nombre = '$nombre', Descripcion = '$descripcion'
                WHERE CodCat = $codCat";
        
        try {
            $resul = $bd -> query($sql); // ejecutar consulta sql
        } catch (Exception $e) { // si ocurre un error, se captura
            return FALSE; // devuelve FALSE
        }
    }

    // ELIMINAR una categoria
    function eliminar_categoria ($codCat) {
        // Leer configuracion de la BD
        $res = leer_config(dirname(__FILE__)."/configuracion.xml", dirname(__FILE__)."/configuracion.xsd");
        // Establecer conexion a la BD usando PDO
        $bd = new PDO($res[0], $res[1], $res[2]);

        // Crear consulta SQL para eliminar la categoria
        $sql = "DELETE FROM categorias WHERE CodCat = $codCat";

        try {
            $resul = $bd -> query($sql); // ejecutar la consulta SQL
        } catch (Exception $e) { // si ocurre un error, se captura
            return FALSE; // devuelve FALSE
        }
    }

    // CARGAR categorias
    function cargar_gcategorias () {
        // Leer configuracion de la BD
        $res = leer_config(dirname(__FILE__)."/configuracion.xml", dirname(__FILE__)."/configuracion.xsd");
        // Establecer conexion a la BD usando PDO
        $bd = new PDO($res[0], $res[1], $res[2]);

        // Crear consulta SQL para mostrar todas las categorias de las categorias
        $sql = "SELECT * FROM categorias";
        $resul = $bd -> query($sql); //Ejecutar la consulta SQL

        // Comprobar si hay resultados
        if (!$resul) { // si $resul es FALSE, significa que ocurrio un error en la consulta SQL
            return FALSE; // devuelve FALSE
        }

        if ($resul -> rowCount() === 0) { // Si NO hay registros en categorias
            return FALSE; // devuelve FALSE
        }
        //Si hay 1 o mas...
        return $resul;
    }


    // ===========================================================================================================================
    /** GESTION DE PRODUCTOS */

    // INSERTAR un producto
    function insertar_producto ($nombre, $descripcion, $peso, $stock, $codcat) {
        // Leer configuracion de la BD
        $res = leer_config(dirname(__FILE__)."/configuracion.xml", dirname(__FILE__)."/configuracion.xsd");
        //Establecer conexion a la BD usando PDO
        $bd = new PDO($res[0], $res[1], $res[2]);

        //Crear consulta SQL para insertar un producto en la BD
        $sql = "INSERT INTO productos(Nombre, Descripcion, Peso, Stock, CodCat)
                VALUES ('$nombre', '$descripcion', $peso, $stock, $codcat)";

        try {
            $resul = $bd -> query($sql); // Ejecutar consulta SQL
        } catch (Exception $e) { // si ocurre algun error, lo captura
            return FALSE; // devuelve FALSE
        }
    }

    // Editar un producto
    function editar_producto ($nombre, $descripcion, $peso, $stock, $codcat, $codProd) {
        // Leer configuracion de la BD
        $res = leer_config(dirname(__FILE__)."/configuracion.xml", dirname(__FILE__)."/configuracion.xsd");
        // Establecer conexion a la BD usando PDO
        $bd = new PDO($res[0], $res[1], $res[2]);

        //Crear consulta SQL para editar un producto
        $sql = "UPDATE productos SET Nombre = '$nombre', Descripcion = '$descripcion', Peso = $peso, Stock = $stock, CodCat = $codcat
                WHERE CodProd = $codProd";
        try {
            $resul = $bd -> query($sql); // Ejecutar la consulta
        } catch (Exception $e) { // si ocurre algun error, lo capturo
            return FALSE; // devuelve FALSE
        }
    }

    //Eliminar un producto
    function eliminar_producto ($codProd) {
        // Leer configuracion de la BD
        $res = leer_config(dirname(__FILE__)."/configuracion.xml", dirname(__FILE__)."/configuracion.xsd");
        // Establecer conexion a la BD usando PDO
        $bd = new PDO($res[0], $res[1], $res[2]);

        //Crear consulta SQL para eliminar el producto
        $sql = "DELETE FROM productos WHERE CodProd = $codProd";

        try {
            $resul = $bd -> query($sql); //ejecuto la consulta SQL
        } catch (Exception $e) { // si hay algun error, lo capturo
            return FALSE; // devuelvo FALSE
        }
    }

    // Cargar gproductos
    function cargar_gproductos () {
        // Leer configuracion de la BD
        $res = leer_config(dirname(__FILE__)."/configuracion.xml", dirname(__FILE__)."/configuracion.xsd");
        // Establecer conexion a la BD usando PDO
        $bd = new PDO($res[0], $res[1], $res[2]);

        // Creo la consulta para cargar los productos
        $sql = "SELECT * FROM productos";
        $resul = $bd -> query($sql); // ejecuto la consulta SQL

        // Comprobaciones
        if (!$resul) { //si $resul es FALSE, es decir, ocurrio un error en la consulta SQL
            return FALSE; // devuelve FALSE
        }

        if ($resul -> rowCount() === 0) { // si NO hay registros
            return FALSE; // devuelve FALSE
        }

        //Si hay 1 o mas...
        return $resul;
    }

    // ===========================================================================================================================
    /** GESTION DE PEDIDOS */

    // EDITAR un pedido
    function editar_pedido ($enviado, $codPed) {
        // Leer configuracion de la BD
        $res = leer_config(dirname(__FILE__)."/configuracion.xml", dirname(__FILE__)."/configuracion.xsd");
        // Establecer conexion a la BD usando PDO
        $bd = new PDO($res[0], $res[1], $res[2]);

        // Crear consulta SQL para editar un pedido (si esta enviado o no)
        $sql = "UPDATE pedidos SET Enviado = $enviado
                WHERE CodPed = $codPed";
        try {
            $resul = $bd -> query($sql); // ejecutar consulta
        } catch (Exception $e) { // si hay algun error, lo captura
            return FALSE; // devuelve FALSE
        }
    }

    // ELIMINAR un pedido
    function eliminar_pedido ($codPed) {
        // Leer la configuraciond de la BD
        $res = leer_config(dirname(__FILE__)."/configuracion.xml", dirname(__FILE__)."/configuracion.xsd");
        // Establecer conexion a la BD usando PDO
        $bd = new PDO($res[0], $res[1], $res[2]);

        $bd -> beginTransaction(); // Iniciar transaccion

        /** Primero eliminamos los registros correspondientes en la tabla pedidosproductos,
         * incrementando el stock correspondiente a cada producto en la tabla productos
         */
        // Obtener los productos del pedido, cargar_gpedidosproductos devuelve un array con los productos del pedido
        $productosped = cargar_gpedidosproductos($codPed);
        // Recorrer los productos
        foreach ($productosped as $productoped) {
            // Incrementamos el stock del producto en las unidades que se eliminan del pedido
            // Consultamos el stock del producto
            $CodProd = $productoped['CodProd'];
            // Consulta SQL para consultar el stock actual del producto
            $sql = "SELECT stock FROM productos WHERE codprod = $CodProd";

            try {
                $resul1 = $bd -> query($sql); // ejecutar consulta SQL
                // Si es exitosa la consulta, se obtiene el resultado de la misma
            } catch (Exception $e) { // si hay algun error, lo captura
                $bd -> rollback(); // se revierte todo
                return FALSE; // devuelve FALSE
            };

            // Obtener el stock del producto
            /**Una vez ejecutada la consulta SQL, se obtiene la fila de resultados
             * con fetch(PDO::FETCH_ASSOC) que devuelve un array asociativo donde
             * la clave es el nombre de la columna y el valor es el dato correspondiente de la fila
             */
            $row = $resul1 -> fetch(PDO::FETCH_ASSOC);
            $stock = $row['stock'];

            // Calcular el nuevo stock
            $unidades = $productoped['Unidades']; //obtiene el nº de unidades de ese producto que se eliminan del pedido
            //Actualizamos el stock del producto
            // es decir, se calcula el nuevo stock sumando esas unidades al stock actual
            $nuevoStock = $stock + $unidades;
            // Crear consulta SQL para actualizar el stock
            $act = "UPDATE productos SET stock = $nuevoStock
                    WHERE CodProd = $CodProd";

            try { //ejecutar actualizacion del stock
                $resul2 = $bd -> query($act); // Ejecutar consulta SQL
            } catch (Exception $e) { //si ocurre un error, captura la excepcion
                $bd -> rollback(); // se revierte toda la transaccion
                return FALSE; // devuelve FALSE
            }


            // Crear consulta SQL para eliminar el registro del producto en pedidosproductos
            $del = "DELETE FROM pedidosproductos
                    WHERE CodPed = $codPed
                    AND CodProd = $CodProd";
            
            try {
                $resul = $bd -> query($del); // ejecutar consulta sQL
            } catch (Exception $e) { //si hay algun error, lo captura
                $bd -> rollback(); // se revierte toda la transaccion
                return FALSE; // devuelve FALSE
            }
        }
        // Crear consulta SQL para eliminar un pedido
        $sql = "DELETE FROM pedidos WHERE CodPed = $codPed";
        try {
            $resul = $bd -> query($sql); // ejecutar consulta SQL
        } catch (Exception $e) { // si hay algun error, lo captura
            $bd -> rollBack(); // revierte toda la transaccion
            return FALSE; // devuelve FALSE
        }
        $bd -> commit(); // si todo sale bien, se confirma
    }


    //CARGAR pedido
    function cargar_gpedidos () {
        // Leer configuracion de la BD
        $res = leer_config(dirname(__FILE__)."/configuracion.xml", dirname(__FILE__)."/configuracion.xsd");
        // Establecer conexion a la BD usando PDO
        $bd = new PDO($res[0], $res[1], $res[2]);

        // Crear consulta SQL para seleccionar todos los registros de pedidos
        $sql = "SELECT * FROM pedidos";
        $resul = $bd -> query($sql); // ejecutar consulta SQL

        // ESTO ES UNA PRUEBA PARA CONOCER POSIBLE ERROR
        // var_dump($resul->fetchAll(PDO::FETCH_ASSOC));
        
        // Verificar si la consulta fue exitosa
        if (!$resul) { // si $resul es FALSE
            return FALSE; // devuelve FALSE
        }
        // Verificar si hay registros
        if ($resul -> rowCount() === 0) { // si no hay ningun registro
            // return FALSE; // devuelve FALSE
            return []; // devuelve un array vacio
        }

        // Convertir los resultados de la columna en un array asociatvio
        $pedidos = $resul -> fetchAll(PDO::FETCH_ASSOC);

        // Verificar si el array esta vacio
        if (empty($pedidos)) { //si el array pedidos esta vacio
            return [];  // devuelve array vacio
        }
    
        //Si hay 1 o mas
        return $pedidos; // Devuele un array en lugar de PDOStatement
        // return $resul;
    }

    // CARGAR pedidos productos
    function cargar_gpedidosproductos ($codPed) {
        // Leer configuracion de la BD
        $res = leer_config(dirname(__FILE__)."/configuracion.xml", dirname(__FILE__)."/configuracion.xsd");
        // Establecer la conexion a la BD usando PDO
        $bd = new PDO($res[0], $res[1], $res[2]);

        // Crear consulta SQL para seleccionar todos los registros de pedidosproductos donde coincida el codPed
        $sql = "SELECT * FROM pedidosproductos WHERE CodPed = $codPed";
        $resul = $bd -> query($sql); // ejecutar consulta SQL

        // Comprobaciones
        if (!$resul) { //si $resul da FALSE
            return FALSE; // devuelve FALSE
        }

        // Verificar si hay registros
        if ($resul -> rowCount() === 0) { // si no devuelve ninguna fila
            return FALSE; // devuelve FALSE
        }
        //Si hay 1 o mas
        return $resul;
    }

    //Devuelve el nombre (correo) del restaurante en vez del codigo
    function restaurante_pedido ($codRes) {
        // Leer configuracion de la BD
        $res = leer_config(dirname(__FILE__)."/configuracion.xml", dirname(__FILE__)."/configuracion.xsd");
        // Establecer conexion con la BD usando PDO
        $bd = new PDO($res[0], $res[1], $res[2]);

        // Crear consulta SQL para obtener el correo del restaurante
        $sql = "SELECT Correo FROM restaurantes WHERE CodRes = $codRes";
        $resul = $bd -> query($sql); //Ejecutar consulta SQL

        // Comrpobaciones
        if (!$resul) { // si $resul da FALSE
            return FALSE; //devuelve FALSE
        }

        // Verificar si hay registros
        if ($resul -> rowCount() === 0) { // si no devuelve ninguna fila
            return FALSE; // devuelve FALSE
        }

        // Si hay 1 o mas...
        return $resul -> fetch();
    }

    function cargar_gproductospedido ($codPed) {
        // Leer configuracion de la BD
        $res = leer_config(dirname(__FILE__)."/configuracion.xml", dirname(__FILE__)."/configuracion.xsd");
        // Establecer conexion a la BD usando PDO
        $bd = new PDO($res[0], $res[1], $res[2]);

        // Crear consulta SQL para obtener los productos de un pedido especifico
        $sql = "SELECT a.CodProd, b.Nombre, b.Descripcion, a.Unidades FROM pedidosproductos a
                JOIN productos b ON b.CodProd = a.CodProd WHERE CodPed = $codPed";
        $resul = $bd -> query($sql); //Ejecutar la consulta SQL

        // Comrpobaciones
        if (!$resul) { //si la consulta ha fallado
            return FALSE; // devuelve FALSE
        }

        if ($resul -> rowCount() === 0) { //si no devuelve ningun registro
            return FALSE; // devuelve FALSE
        }
        // Si hay 1 o mas
        return $resul;
    }

    //Eliminar cantidades de un producto del carrito
    function eliminar_productocarrito ($codProd, $unidades) {
        // Leer configuracion de la BD
        $res = leer_config(dirname(__FILE__)."/configuracion.xml", dirname(__FILE__)."/configuracion.xsd");
        // Establecer conexion a la BD usando PDO
        $bd = new PDO($res[0], $res[1], $res[2]);

        $bd -> beginTransaction(); //Iniciar transaccion

        // Incrementamos el stock del producto en las unidades que se eliminan del carrito
        $sql = "SELECT stock FROM productos 
                WHERE codprod = $codProd"; //Consultamos el stock del producto

        try {
            $resul1 = $bd -> query($sql); //Ejecutar consulta
        } catch (Exception $e) { //si hay algun error, lo capturo
            $bd -> rollBack(); // revierte la transaccion
            return FALSE; // devuelve FALSE
        };

        // Si la consulta es exitosa, se obtiene el resultado y se almacena en $row
        $row = $resul1 -> fetch(PDO::FETCH_ASSOC); //lo de fetch devuelve un array asociativo
        // Se extrae el valor del stock actual de la respuesta de la consulta y se guarda en $stock
        $stock = $row['stock'];

        // Actualizamos el stock del producto
        $nuevoStock = $stock + $unidades; // Calculamos el nuevo stock
        // Crear consulta para actualizar el stock del producto
        $act = "UPDATE productos SET stock = $nuevoStock
                WHERE CodProd = $codProd";

        try {
            $resul2 = $bd -> query($act); // Ejecutar consulta
        } catch (Exception $e) { //Si ocurre un error, lo capturo
            $bd -> rollBack(); // Revertir transaccion
            return FALSE; // devuelve FALSE
        }
        $bd -> commit(); // Guardar transaccion
    }

    // Funcion para eliminar el carrito y devolver el stock
    function vaciar_carrito ($carrito) {
        $productos = cargar_productos(array_keys($carrito));

        if ($productos === FALSE)  { //Si NO hay productos
            return; // salir sin mas
        }

        // Si los productos fueron cargados correctamente, recorreomos $productos
        foreach ($productos as $producto) {
            $codProd = $producto['CodProd']; // Se obtiene el codigo del producto actual
            $unidades = $_SESSION['carrito'][$codProd]; //se obtiene la cantidad de unidades que estan en el carrito
            eliminar_productocarrito($codProd, $unidades);
        }
    }
?>