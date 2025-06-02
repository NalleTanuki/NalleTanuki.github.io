<!-- Mostrar una lista de productos dentro de una categoria especifica y permite anhadirlos al carrito
    - Al mostrar la tabla de productos, se anhade a cada fila un formulario para anhadir 1 o + unidades de ese producto
    - El formulario contiene campos para el codigo, las unidades y un boton de envio
    - El campo con el codigo esta oculto
    - Se envia a anhadir.php -->
<?php
    // Comprueba que el usuario haya abierto sesion o redirige
    require 'sesiones.php'; //Verifica que el usuario haya iniciado sesion
    require_once 'bd.php'; //Contiene funciones para interacturar con la BD
    comprobar_sesion(); //Llama a la funcion que redirige a login.php si no hay sesion iniciada
?>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Tabla de productos por categoría</title>
</head>
<body>
    <?php
        require 'cabecera.php'; //Incluir cabecera

        // Obtener informacion de la categoria y productos
        $cat = cargar_categoria($_GET['categoria']); //Obtiene el parametro del URL
        $productos = cargar_productos_categoria($_GET['categoria']);

        if ($cat === FALSE) { //Si ocurre algun error, muestra mensaje
            echo "<p class='error'>Error al conectar con la base de datos.</p>";
            exit; //sale
        }

        // Si el parametro 'carrito' esta presente en el URL..
        if (isset($_GET['carrito']) && $_GET['carrito'] == 1) { //carrito=1 indica que los productos se añadieron con exito al carrito
            echo "<h3>Se han añadido los productos al <a href=\"carrito.php\">carrito</a>.<br>Puede proseguir con su pedido.</h3><hr>";
        }

        if (isset($_GET['carrito']) && $_GET['carrito'] == 2) { //carrito=0 indica que fallo debido a falta de stock
            echo "<h3>No se ha añadido el producto al carrito por no haber existencias suficientes.</h3>";
        }

        // Muestra el nombre y la descripcion de la categoria que se cargo
        echo "<h1>" . $cat['nombre'] . "</h1>";
        echo "<p>" . $cat['descripcion'] . "</p>";

        //Si NO hay productos en la categoria, mostrara un mensaje 
        if ($productos === FALSE || empty($productos)) {
            echo "<p style='color:red'>No hay productos en esta categoría.</p>";
            exit; //Termina la ejecucion del script
        } else {
            echo "<table>"; //Abrir la tabla
            echo "<tr>
                <th>Nombre</th>
                <th>Descripción</th>
                <th>Peso</th>
                <th>Stock</th>
                <th>Comprar</th>
            </tr>";

            // Establecer un valor inicial de FALSE antes de recorrer los productos
            $hay_productos_disponibles = FALSE; //No hay productos disponibles

            foreach ($productos as $producto) { //Recorremos la tabla productos
                $cod = $producto['CodProd'];
                $nom = $producto['Nombre'];
                $des = $producto['Descripcion'];
                $peso = $producto['Peso'];
                $stock = $producto['Stock'];
                $categoria = $producto['CodCat'];

            // Si hay stock disponible, se habilita un formulario para comprar el producto
            if ($stock > 0) {
                $hay_productos_disponibles = TRUE; //SI hay productos disponibles
                echo "<tr>
                    <td>$nom</td>
                    <td>$des</td>
                    <td>$peso</td>
                    <td>$stock</td>
                    <td>
                        <form action='anhadir.php' method='POST'>
                            <input name='unidades' type='number' min='1' value='1'>
                            <input type='submit' value='Comprar'>
                            
                            <input name='cod' type='hidden' value='$cod'>
                            <input name='categoria' type='hidden' value = '$categoria'>
                        </form>
                    </td>
                </tr>";
            }
        }
        echo "</table>";

        // Si después de recorrer los productos no hay stock, mostrar mensaje
    if (!$hay_productos_disponibles) {
        echo "<p style='color:red'>No hay productos disponibles en esta categoría.</p>";
    }
        }   
        /**
         * Enviamos tambien la categoria para que al anhadir un producto,
         * se vuelva a este fichero con la misma categoria --> */
    ?>
</body>
</html>