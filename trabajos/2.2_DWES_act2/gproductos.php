<?php
    /**Comprueba que el usuario haya abierto sesion o redirige */
    require 'sesiones.php'; //Verifica que el usuario haya iniciado sesion
    require_once 'bd.php'; //Contiene funciones para interacturar con la BD
    comprobar_sesion(); //Llama a la funcion que redirige a login.php si no hay sesion iniciada


    //Manipulacion de envios por POST (formularios)
    if ($_SERVER['REQUEST_METHOD'] == 'POST') { //Si el formulario es enviado
        if (isset($_POST['add'])) { // si se pulso el boton ADD, esta definida
            $nombre = $_POST['nombre']; // Captura el valor introducido de nombre
            $descripcion = $_POST['descripcion']; // Captura el valor introducido de descripcion
            $peso = $_POST['peso']; // Captura el valor introducido de peso
            $stock = $_POST['stock']; // Captura el valor introducido de stock
            $codcat = $_POST['codcat']; // Captura el valor de codCat (esta oculto)
            insertar_producto($nombre, $descripcion, $peso, $stock, $codcat); //Llamada a la funcion
        } elseif (isset($_POST['edit'])) { //Si se pulso EDIT
            $codProd = $_POST['codprod']; // Captura el valor de codprod
            $nombre = $_POST['nombre']; // Captura el valor introducido de nombre
            $descripcion = $_POST['descripcion']; // Captura el valor introducido de descripcion
            $peso = $_POST['peso']; // Captura el valor introducido de peso
            $stock = $_POST['stock']; // Captura el valor introducido de stock
            $codcat = $_POST['codcat']; // Captura el valor de codcat
            editar_producto($nombre, $descripcion, $peso, $stock, $codcat, $codProd); //Llamada a al funcion
        } elseif (isset($_POST['delete'])) { // si se pulso DELETE
            $codProd = $_POST['codprod']; // Captura el valor de codprod
            eliminar_producto($codProd); //Llamada a la funcion
        }
    }

    // Fetch all records
    $productos = cargar_gproductos(); //Recuperar todos los productos - recargar
?>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Gestionar Productos</title>
</head>
<body>
    <?php
        require 'cabecera.php'; //Insertar cabecera
    ?>

    <h1>Gestionar Productos</h1>

    <h2>Añadir Producto</h2>
    <form method="POST"> <!-- FORMULARIO ANHADIR PRODUCTO -->
        <label>Nombre:
            <input type="text" name="nombre" required>
        </label>

        <br>

        <label>Descripción: 
            <input type="text" name="descripcion" requried>
        </label>

        <br>

        <label>Peso: 
            <input type="number" step="0.01" name="peso" required>
        </label>

        <br>

        <label>Stock: 
            <input type="number" name="stock" required>
        </label>

        <br>

        <label>Categoría: 
            <select name="codcat" required> <!-- Desplegable para elegir una categoria de las disponibles -->
                <?php $categorias = cargar_gcategorias(); // que se obtienen de la funcion cargar_gcategorias
                foreach ($categorias as $categoria) { ?>
                 <option value="<?php echo $categoria['CodCat']; ?>">
                    <?php echo $categoria['Nombre']; ?>
                </option>
                <?php } ?>
            </select>
        </label> <br>
        <button type="submit" name="add">Añadir</button>
    </form>

    <!-- TABLA PRODUCTOS REGISTRADOS -->
    <h2>Productos Registrados</h2>
    <table border="1">
        <thead>
            <tr>
                <th>ID</th>
                <th>Nombre</th>
                <th>Descripción</th>
                <th>Peso</th>
                <th>Stock</th>
                <th>Categoría</th>
                <th>Acciones</th>
            </tr>
        </thead>

        <tbody>
            <?php //Recorro $productos
            foreach ($productos as $row):
            ?>
            <tr>
                <!-- ROW representa una fila individual de los resultados que se recuperaron de la BD -->
                <td><?= htmlspecialchars($row['CodProd'])?></td>
                <td><?= htmlspecialchars($row['Nombre'])?></td>
                <td><?= htmlspecialchars($row['Descripcion'])?></td>
                <td><?= htmlspecialchars($row['Peso'])?></td>
                <td><?= htmlspecialchars($row['Stock'])?></td>
                <td><?= htmlspecialchars($row['CodCat'])?></td>
                <td>
                    <!-- FORMULARIO ELIMINAR -->
                    <form method="POST" style="display:inline;">
                        <input type="hidden" name="codprod" value="<?= $row['CodProd'] ?>">
                        <button type="submit" name="delete">Eliminar</button>
                    </form>
                    
                    <hr> <!-- Linea horizontal -->

                    <!-- FORMULARIO ACTUALIZAR -->
                    <form method="POST" style="display:inline;">
                        <input type="hidden" name="codprod" value="<?php echo $row['CodProd']; ?>">
                        <label>Nombre: <input type="text" name="nombre" value="<?php echo $row['Nombre']; ?>" required> </label> <br>
                        <label>Descripción: <input type="text" name="descripcion" value="<?php echo $row['Descripcion']; ?>" required> </label> <br>
                        <label>Peso: <input type="number" step="0.01" name="peso" value="<?php echo $row['Peso']; ?>" required> </label> <br>
                        <label>Stock: <input type="number" name="stock" value="<?php echo $row['Stock']; ?>" required> </label> <br>
                        <label>Categoría:
                            <select name="codcat" required> <!-- Desplegable seleccionar categoria -->
                                <?php $categorias = cargar_gcategorias();
                                    foreach ($categorias as $categoria) {
                                ?>
                                    <option value="<?php echo $categoria['CodCat']; ?>"  <?php if ($row['CodCat'] == $categoria['CodCat']) {echo "selected";} ?>>
                                        <?php echo $categoria['Nombre']; ?>
                                    </option>
                                    <?php } ?>
                            </select>
                        </label> <br>
                        <button type="submit" name="edit">Actualizar</button>
                    </form>
                </td>
            </tr>
        <?php endforeach; ?>
        </tbody>
    </table>
</body>
</html>