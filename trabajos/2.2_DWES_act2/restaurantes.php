<?php
    //Comprueba que el usuario haya abierto sesion o redirige
    require 'sesiones.php'; //Verifica que el usuario haya iniciado sesion
    require_once 'bd.php'; //Contiene funciones para interacturar con la BD
    comprobar_sesion(); //Llama a la funcion que redirige a login.php si no hay sesion iniciada


    // Manipulacion de envios - Formulario (POST)
    if ($_SERVER['REQUEST_METHOD'] == 'POST') {
        if (isset($_POST['add'])) {      //Si se pulsa el boton ADD
            $correo = $_POST['correo'];
            $clave = password_hash($_POST['clave'], PASSWORD_BCRYPT); // Ciframos la clave y asi se almacena ya cifrada
            $pais = $_POST['pais'];
            $cp = $_POST['cp'] ?? null; // si NO se rellena, lo guardamos como "null" ya que no es un campo obligatorio
            $ciudad = $_POST['ciudad'];
            $direccion = $_POST['direccion'];
            $rol = $_POST['rol'];
            insertar_restaurante($correo, $clave, $pais, $cp, $ciudad, $direccion, $rol); //Llamada a la funcion insertar_restaurante()
        } elseif (isset($_POST['edit'])) { // Si por el contrario, es una EDICION...
            $codRes = $_POST['codRes'];
            $correo = $_POST['correo'];
            $clave = !empty($_POST['clave']) ? password_hash($_POST['clave'], PASSWORD_BCRYPT) : null; //Si no esta vacia la clave, entonces hacemos el hash de la clave usando BCRYPT y sino lo ponemos a null
            $pais = $_POST['pais'];
            $cp = $_POST['cp'] ?? null;
            $ciudad = $_POST['ciudad'];
            $direccion = $_POST['direccion'];
            $rol = $_POST['rol'];
            editar_restaurante($correo, $clave, $pais, $cp, $ciudad, $direccion, $rol, $codRes); //Llamada a la funcion editar_restaurante()
        } elseif (isset($_POST['delete'])) { // Si es para ELIMINAR
            $codRes = $_POST['codRes'];
            eliminar_restaurante($codRes); //Llamada a la funcion eliminar_restaurante()
        }
    }

    // Fetch all records
    $restaurantes = cargar_restaurantes(); // Cargamos todos los restaurantes para mostrarlos por una pantalla
?>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Gestionar Restaurantes</title>
</head>
<body>
    <?php
        require 'cabecera.php'; // para que muestre la cabecera
    ?>
    <h1>Gestionar Restaurantes</h1>

    <h2>Añadir Restaurante</h2>
    <form method = "POST">
        <input type="email" name = "correo" placeholder = "Correo" required>
        <input type="password" name = "clave" placeholder = "Clave" required>
        <input type="text"  name = "pais" placeholder = "País" required>
        <input type="number" name = "cp" placeholder = "Códido Postal">
        <input type="text" name = "ciudad" placeholder = "Ciudad" required>
        <input type="text" name = "direccion" placeholder = "Dirección" required>

        <!-- <input type = "number" name = "rol" placeholder = "Rol" required> -->

        <label for="rol">Rol: </label> 
        <select name="rol" id="rol" required> <!-- Desplegable -->
            <option value="0">Usuario</option>
            <option value="1">Administrador</option>
        </select>

        <button type = "submit" name = "add">Añadir</button>
    </form>

    <h2>Restaurantes Registrados</h2>
    <table border = "1">
        <thead>
            <tr>
                <th>Código</th>
                <th>Correo</th>
                <th>País</th>
                <th>Código Postal</th>
                <th>Ciudad</th>
                <th>Dirección</th>
                <th>Rol</th>
                <th>Acciones</th>
            </tr>
        </thead>
        <tbody>
            <?php
                foreach ($restaurantes as $row) { //Recorremos $restaurantes para mostrar los datos
            ?>
            <tr>
                <td><?php echo $row['CodRes']; ?></td>
                <td><?php echo $row['Correo']; ?></td>
                <td><?php echo $row['Pais']; ?></td>
                <td><?php echo $row['CP']; ?></td>
                <td><?php echo $row['Ciudad']; ?></td>
                <td><?php echo $row['Direccion']; ?></td>
                <td><?php echo $row['Rol']; ?></td>
                <td>
                    <form method = "POST" style = "display:inline;">
                        <input type = "hidden" name = "codRes" value = "<?php echo $row['CodRes']; ?>"> <!--Lo enviamos como campo oculto -->
                        <button type = "submit" name = "delete">Eliminar</button>
                    </form>

                    <hr> <!-- Linea horizontal -->

                    <!-- Formulario de Actualizacion -->
                    <form method = "POST" style = "display:inline;">
                        <input type = "hidden" name = "codRes" value = "<?php echo $row['CodRes']; ?>">
                    
                        <label for = "email">Correo: </label>
                        <input type = "email" name = "correo" value = "<?php echo $row['Correo']; ?>" required> <br>
                    
                        <label for = "password">Clave: </label>
                        <input type = "password" name = "clave" placeholder = "Nueva Clave (opcional)"> <br>

                        <label for = "pais">País: </label>
                        <input type = "text" name = "pais" value = "<?php echo $row['Pais']; ?>" required> <br>

                        <label for = "cp">Código Postal: </label>
                        <input type = "number" name = "cp" value = "<?php echo $row['CP']; ?>"> <br>

                        <label for = "ciudad">Ciudad: </label>
                        <input type = "text" name = "ciudad" value = "<?php echo $row['Ciudad']; ?>" required> <br>

                        <label for = "direccion">Dirección: </label>
                        <input type = "text" name = "direccion" value = "<?php echo $row['Direccion']; ?>" required> <br>

                        <!-- <label for = "rol">Rol (0 - Usuario / 1 - Administrador): </label>
                        <input type = "number" name = "rol" value = "<?php echo $row['Rol']; ?>" required> <br> -->

                        <label for = "rol">Rol: </label>
                        <select id = "rol" name = "rol" required> <!-- Muestra el rol que tiene el restaurante por defecto -->
                            <option value = "0" <?php if ($row['Rol'] == 0) {echo "selected";} ?>>Usuario</option>
                            <option value = "1" <?php if ($row['Rol'] == 1) {echo "selected";} ?>>Administrador</option>
                        </select> <br>
                        
                        <button type = "submit" name = "edit">Actualizar</button>
                    </form>
                </td>
            </tr>
            <?php } ?>
        </tbody>
    </table>
</body>
</html>