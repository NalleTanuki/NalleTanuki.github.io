<!-- Mostrar los productos anhadidos al carrito y
 permite eliminar unidades antes de procesar la compra -->
<?php
    //Comprueba que el usuario haya abiero sesion o redirige
    require_once 'sesiones.php'; //Verifica que el usuario haya iniciado sesion
    require_once 'bd.php'; //Contiene funciones para interacturar con la BD
    comprobar_sesion(); //Llama a la funcion que redirige a login.php si no hay sesion iniciada
?>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Carrito de la compra</title>
</head>
<body>
    <?php
        require 'cabecera.php'; //Mostrar encabezado
        /**
         * Se obtienen los productos del carrito
         * array_keys($_SESSION['carrito']) -> obtiene los codigos de los productos en el carrito
         * cargar_productos() -> carga la info de esos productos desde la BD
         */
        $productos = cargar_productos(array_keys($_SESSION['carrito']));

        // Comprobar si el carrito esta vacio
        if ($productos === FALSE) { //Si cargar_productos() devuelve FALSE (carrito vacio)
            echo "<p>No hay productos en el pedido.</p>"; //Muestro mensaje
            exit; //Finalizo la ejecucion
        }

        // Mostrar productos en tabla
        echo "<h2>Carrito de la compra</h2>";
        echo "<table>"; //Abrir la tabla
        echo "<tr>
                <th>Nombre</th>
                <th>Descripción</th>
                <th>Peso</th>
                <th>Unidades</th>
                <th>Eliminar</th>
            </tr>";
        //Recorrer los productos y mostrarlos
        foreach ($productos as $producto) {
            $cod = $producto['CodProd'];
            $nom = $producto['Nombre'];
            $des = $producto['Descripcion'];
            $peso = $producto['Peso'];
            $unidades = $_SESSION['carrito'][$cod]; //Se obtienen las unidades

            //print_r($producto);
            echo "<tr>
                    <td>$nom</td>
                    <td>$des</td>
                    <td>$peso</td>
                    <td>$unidades</td>
                    <td>
                        <!-- Formulario para eliminar unidades del carrito:
                                -Permite al usuario elegir cuantas unidades eliminar
                                -Se envian datos a eliminar.php mediante POST con el codigo del producto y las unidades a eliminar
                        -->
                        <form action='eliminar.php' method='POST'>
                            <input name='unidades' type='number' min='1' value='1'>
                            <input type='submit' value='Eliminar'>
                            <input name='cod' type='hidden' value='$cod'>
                        </form>
                    </td>
                </tr>";
        }
        echo "</table>";
    ?>
    <hr> <!-- Linea horizontal -->
    
    <!-- Enlace para procesar compra (procesar_pedido.php) -->
    <a href="procesar_pedido.php">Realizar pedido</a>
</body>
</html>