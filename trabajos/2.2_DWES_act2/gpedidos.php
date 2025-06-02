<?php
    //Comprueba que el usuario haya abierto sesion o redirige
    require 'sesiones.php'; //Verifica que el usuario haya iniciado sesion
    require_once 'bd.php'; //Contiene funciones para interacturar con la BD
    comprobar_sesion(); //Llama a la funcion que redirige a login.php si no hay sesion iniciada

    //Manipulacion de envios POST
    if ($_SERVER['REQUEST_METHOD'] == 'POST') { //Si se recibe solicitud POST, es decir, cuando un formulario es enviado
        if (isset($_POST['edit'])) { //Si el boton pulsado es EDIT - DEFINIDA
            $codPed = $_POST['codped']; //Captura el codigo del pedido
            $enviado = $_POST['enviado']; //Captura el envio
            editar_pedido($enviado, $codPed); //Llamada a la funcion
        } elseif (isset($_POST['delete'])) { //Si el boton pulsado es DELETE
            $codPed = $_POST['codped']; // Captura el codigo del pedido
            eliminar_pedido($codPed); // Llamada a la funcion
        }
    }

    //Fetch all records
    $pedidos = cargar_gpedidos(); //Muestra/Recarga todos los pedidos
    // var_dump($pedidos); Comprobar un error
?>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Gestionar Pedidos</title>
</head>
<body>
    <?php
        require 'cabecera.php'; //Inserta la cabecera
    ?>

    <h1>Gestionar Pedidos</h1>

    <h2>Pedidos Registrados</h2>

    <!-- TABLA -->
    <table border="1">
        <thead>
            <tr>
                <th>CodPed</th>
                <th>Fecha</th>
                <th>Enviado</th>
                <th>Restaurante</th>
                <th>Productos</th>
                <th>Acciones</th>
            </tr>
        </thead>

        <tbody>
            <!-- Recorre $pedidos -->
            <?php foreach ($pedidos as $row): ?>
                <tr>
                    <td><?= htmlspecialchars($row['CodPed']) ?></td>
                    <td><?= htmlspecialchars($row['Fecha']) ?></td>
                    <td><?= htmlspecialchars($row['Enviado']) ?>
                        <?php if ($row['Enviado'] == 0) {
                                echo " - No enviado";
                            } elseif ($row['Enviado'] == 1) {
                                echo " - Enviado";
                            } else {
                                echo "Indeterminado";
                            } ?>
                    </td>
                    <td><?= htmlspecialchars($row['Restaurante']) ?>
                        <?php $prestaurante = restaurante_pedido($row['Restaurante']); ?>
                        <?php echo " - $prestaurante[0]"; ?>
                    </td>
                    <td>
                        <?php $productosped = cargar_gproductospedido($row['CodPed']); ?>
                        <?php foreach ($productosped as $rowprod): ?>
                            Producto: <?= htmlspecialchars($rowprod[0]) ?> .
                                      <?= htmlspecialchars($rowprod[1]) ?> (<?= htmlspecialchars($rowprod[2]) ?>) - Unidades: 
                                      <?= htmlspecialchars($rowprod[3]) ?> <br>
                        <?php endforeach; ?>
                    </td>
                    <td>
                        <!-- Formulario para ELIMINAR -->
                        <form method="POST" style="display:inline;">
                            <input type="hidden" name="codped" value="<?php echo $row['CodPed']; ?>">
                            <button type="submit" name="delete">Eliminar</button>
                        </form>

                        <hr> <!-- Linea horizontal -->

                        <!-- Formulario para ACTUALIZAR su ESTADO -->
                        <form method="POST" style="display:inline;">
                            <input type="hidden" name="codped" value="<?php echo $row['CodPed']; ?>">
                            <label for="enviado">Enviado: </label>
                            <select name="enviado" id="enviado" required>
                                <!-- NO ENVIADO -->
                                <option value="0" <?php if ($row['Enviado'] == 0) {
                                    echo "selected";
                                    } ?>>No enviado
                                </option>
                                <!-- ENVIADO -->
                                <option value="1" <?php if ($row['Enviado'] == 1) {
                                    echo "selected";
                                    } ?>>Enviado
                                </option>
                            </select> <br>
                            <button type="submit" name="edit">Actualizar</button>
                        </form>
                    </td>
                </tr>
            <?php endforeach; ?>
        </tbody>
    </table>
</body>
</html>