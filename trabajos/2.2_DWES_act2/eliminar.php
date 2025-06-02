<!-- Se encarga de eliminar productos del carrito y actualizar el stock en la BD
    - Comprueba que los parametros cod y unidades esten presentes
    - Para modificar el carrito, primero comprueba si el codigo ya existe en el array
        Si existe, se resta unidades al valor existente
        Si el valor resultante es menor o igual a 0, se elimina ese elemento del array
-->
<?php
    //Comprueba que el usuario haya abierto sesion o redirige
    require_once 'sesiones.php'; //Verifica que el usuario haya iniciado sesion
    require_once 'bd.php'; //Contiene funciones para interacturar con la BD
    comprobar_sesion(); //Llama a la funcion que redirige a login.php si no hay sesion iniciada

    // Captura datos enviados por POST
    /**Recibe el codigo del producto (cod) y la cantidad a elimimnar (unidades)
     * desde el formulario de carrito.php
    */
    $cod = $_POST['cod'];
    $unidades = $_POST['unidades'];

    // Comprobacion y eliminacion de productos del carrito =============================================
    //Si existe el codigo, restamos las unidades, con minimo de 0
    if (isset($_SESSION['carrito'][$cod])) { //Verifica si el producto esta en el carrito
        $unidadespedidas = $_SESSION['carrito'][$cod]; //Guarda en $unidadespedidas la cantidad actual en el carrito
        $_SESSION['carrito'][$cod] -= $unidades; //Resta las unidades solicitadas
        
        if ($_SESSION['carrito'][$cod] <= 0) { //si la cantidad del producto llega a 0 o menos
            unset($_SESSION['carrito'][$cod]); //lo elimina del carrito con UNSET
        }
        // Actualizamos el stock del producto
        if ($unidades >= $unidadespedidas) { //si el usuario intenta eliminar mas unidades de las que hay en el carro
            $unidades = $unidadespedidas; //ajusta $unidades al maximo permitido
        }
        eliminar_productocarrito($cod, $unidades); //llama a eliminar_productocarrito que actualiza el stock en la BD
    }
    header("Location: carrito.php"); //Tras eliminar el producto -> redireccion a carrito.php para actualizar la vista
?>