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
    <title>Lista de categorías</title>
</head>
<body>
    <?php
        require 'cabecera.php'; //Incluye la cabecera    
    ?>

    <h1>Lista de categorías</h1>
    <!-- Lista de vinculos con la forma productos.php?categoria=1 -->
     <?php
        $categorias = cargar_categorias(); //cargar categorias desde la BD
        
        if ($categorias === false) { //si devuelve FALSE
            echo "<p class='error'>Error al conectar con la base de datos.</p>"; //muestra mensaje
        } else { //sino, muestro categorias
            echo "<ul>"; //Abrir la lista
            foreach ($categorias as $cat) { //Recorro la tabla categorias
                /**$cat['nombre'] $cat['codCat'] */
                $url = "productos.php?categoria=" . $cat['codCat']; //Genero un enlace con dicha estructura
                echo "<li><a href='$url'>" . $cat['nombre'] . "</a></li>";
            }
            echo "</ul>"; //Cierro lista
        }
     ?>
</body>
</html>