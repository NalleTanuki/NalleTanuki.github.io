<!-- Este fichero se encarga de anhadir elementos al carrito
    - Recibe los datos del formulario de productos.php y los parametros de cod y unidades que recibe apra modificar el carrito
    - Primero comprueba si el codigo ya existe en el array
    - Si existe, se suma unidades al valor existente
    - Si NO existe, se crea con valor unidades.
     -->

<?php
    // Comprueba que el usuario haya abierto sesion o redirige
    require_once 'sesiones.php'; //Verifica que el usuario haya iniciado sesion
    require_once 'bd.php'; //Contiene funciones para interacturar con la BD
    comprobar_sesion(); //Llama a la funcion que redirige a login.php si no hay sesion iniciada

    // OBTENER DATOS DEL FORMULARIO DE productos.php
    $cod = $_POST['cod']; // codigo del producto
    $unidades = (int)$_POST['unidades']; //cantidad de productos que el usuario desea comprar
    $categoria = $_POST['categoria']; //codigo de la categoria para volver a la misma pag despues de anhadir

    // Realizamos las comprobaciones previas ========================
    //Comprobar si hay stock suficiente
    if (comprobar_stock($cod, $unidades)) { //llamada a la funcion comprobar_stock, si recibe un TRUE
        if (isset($_SESSION['carrito'][$cod])){ //Si en el carrito existe ese codigo
            // Incrementamos las unidades para ese codigo
            $_SESSION['carrito'][$cod] += $unidades;
        } else {
            // Si NO existe el codigo, pues lo creamos
            $_SESSION['carrito'][$cod] = $unidades;
        }
        
        actualizar_stock($cod, $unidades); //actualizar stock en la BD
        
        /** Tras anhadir los productos al carrito,
        * redirigimos a la tabla de productos, en vez de al carrito
        */
        header ("Location: productos.php?categoria=$categoria&carrito=1");
    } else {
        // Indicamos que NO se anhadio al carrito por falta de stock
        header ("Location: productos.php?categoria=$categoria&carrito=2");
    } 
?>