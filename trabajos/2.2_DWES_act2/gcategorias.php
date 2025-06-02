<!-- Gestionar categoria. Permite realizar operaciones de:
    - anhadir
    - editar
    - eliminar categorias
-->
<?php
    /**Comprueba que el usuario haya abierto sesion o redirige */
    require 'sesiones.php'; //Verifica que el usuario haya iniciado sesion
    require_once 'bd.php'; //Contiene funciones para interacturar con la BD
    comprobar_sesion(); //Llama a la funcion que redirige a login.php si no hay sesion iniciada

    // Manipulacion de envios POST (envios de formularios)
    if ($_SERVER['REQUEST_METHOD'] == 'POST') { //si la solicitud es POST, es decir, si el usuario envio un formulario
         // ANHADIR categoria *********************
        if (isset($_POST['add'])) { // si el boton "anhadir" fue presionado (boton name="add") entonces la variable $_POST['add'] esta definida
            $nombre = $_POST['nombre']; //Captura el valor nombre introducido
            $descripcion = $_POST['descripcion']; //Captura el valor descripcion introducido
            insertar_categoria($nombre, $descripcion); // Llamada a la funcion
            // EDITAR categoria ************
        } elseif (isset($_POST['edit'])) { // en cambio, si presiona boton EDIT
            $codCat = $_POST['codCat']; //Captura el codigo de la categoria que esta editando
            $nombre = $_POST['nombre']; //Captura el valor nombre introducido
            $descripcion = $_POST['descripcion']; //Captura el valor descripcion introducido
            editar_categoria($nombre, $descripcion, $codCat); //Llamada a la funcion
            // ELIMINAR categoria ************
        } elseif (isset($_POST['delete'])) { //En cambio, presiona DELETE
            $codCat = $_POST['codCat']; //Captura el codigo de la categoria que se va a eliminar (valor que esta oculto en el formulario)
            eliminar_categoria($codCat); //Llamada a la funcion
        }
    }

    // Recuperacion de todas las categorias
    $categorias = cargar_gcategorias();
?>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Gestionar Categorías</title>
</head>
<body>
    <?php
        require 'cabecera.php'; //Inserta cabecera
    ?>
    <h1>Gestionar Categorías</h1>

    <h2>Añadir Categoría</h2>
    
    <!-- FORMULARIO -->
    <form method = "POST">
        <input type="text" name="nombre" placeholder="Nombre" required>
        <input type="text" name="descripcion" placeholder="Descripción" required>

        <button type="submit" name="add">Añadir</button>
    </form>

    <h2>Categorías Registradas</h2>
    <table border="1">
        <thead>
            <tr>
                <th>Código</th>
                <th>Nombre</th>
                <th>Descripción</th>
                <th>Acciones</th>
            </tr>
        </thead>

        <tbody>
            <?php foreach($categorias as $row){ ?>
                <tr>
                    <td><?php echo $row['CodCat']; ?></td>
                    <td><?php echo $row['Nombre']; ?></td>
                    <td><?php echo $row['Descripcion']; ?></td>
                    <td>
                        <form method="POST" style="display:inline;">
                            <input type="hidden" name="codCat" value="<?php echo $row['CodCat']; ?>">
                            <button type="submit" name="delete">Eliminar</button>
                        </form>

                        <hr> <!-- Linea horizontal -->

                        <form method="POST" style="display:inline;">
                            <input type="hidden" name="codCat" value="<?php echo $row['CodCat']; ?>"> <!-- CodCat oculto -->
                            
                            <label for="nombre">Nombre: </label>
                            <input type="text" name="nombre" value="<?php echo $row['Nombre']; ?>" required> <br>

                            <label for="descripcion">Descripción: </label>
                            <input type="text" name="descripcion" value="<?php echo $row['Descripcion']; ?>" required> <br>

                            <button type="submit" name="edit">Actualizar</button>
                        </form>
                    </td>
                </tr>
            <?php } ?>
        </tbody>
    </table>
</body>
</html>