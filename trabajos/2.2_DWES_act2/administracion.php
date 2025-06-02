<?php
    // Comprueba que el usuario haya abierto sesion o redirige
    require 'sesiones.php'; //Verifica que el usuario haya iniciado sesion
    require_once 'bd.php'; //Contiene funciones para interacturar con la BD
    comprobar_sesion(); //Llama a la funcion que redirige a login.php si no hay sesion iniciada

    // Si el usuario NO es administrador -> lo redirigimos a categorias.php por seguridad
    if ($_SESSION['usuario']['rol'] <> 1) {
        header ("Location: categorias.php");
    }
?>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Panel de Administración</title>
</head>
<body>
    <?php
        require 'cabecera.php'; //Incluimos la cabecera
    ?>

    <h1>Panel de Administración</h1>

    <hr> <!-- Linea horizontal -->

    <!-- LISTA -->
    <ul>
        <li><a href="restaurantes.php">[Gestionar Restaurantes]</a></li> <!-- gestion RESTAURANTES -->
        <li><a href="gcategorias.php">[Gestionar Categorías]</a></li>    <!-- gestion CATEGORIAS -->
        <li><a href="gproductos.php">[Gestionar Productos]</a></li>      <!-- gestion PRODUCTOS -->
        <li><a href="gpedidos.php">[Gestionar Pedidos]</a></li>      <!-- gestion PEDIDOS -->
    </ul>
</body>
</html>