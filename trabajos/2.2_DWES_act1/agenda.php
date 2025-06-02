<?php
    // Archivos necesarios para manejar sesiones y la BD
    require_once 'sesiones.php';
    require_once 'bd.php';

    //Se verifica si el usuario tiene una sesion activa
    comprobar_sesion();

    // Obtencion del ID del usuario desde la sesion iniciada
    $id_usuario = $_SESSION['user']['id_usuario'];

    // Se obtienen los contactos del usuario mediante una funcion (obtener_contactos) en bd.php
    $contactos = obtener_contactos($id_usuario);
?>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Agenda</title>

    <style>
        /* Estilos para mejorar la presentacion de la tabla */
        table {
            width: 100%;
            border-collapse: collapse;
        }
        th, td {
            padding: 8px;
            text-align: left;
            border: 1px solid black;
        }
    </style>

</head>
<body>
    <?php
    //Se incluye la cabecera
    require 'cabecera.php';    
    ?>
    
    <h2>Agenda de Contactos</h2>

    <!-- Enlace para anhadir un nuevo contacto -->
    <a href="alta_contacto.php">Añadir nuevo contacto</a> <br>


    <!-- Se muestra un mensaje si se recibe un parametro 'mensaje' en el URL -->
    <?php
        if (isset($_GET['mensaje'])) {
            echo "<p>" . htmlspecialchars($_GET['mensaje']) . "</p>";
        }
    ?>

    <hr>

    <!-- Tabla que muestra los contactos del usuario -->
    <table border="1">
        <tr>
            <th>Nombre</th>
            <th>Apellidos</th>
            <th>Email</th>
            <th>Teléfono</th>
            <th>Acciones</th>
        </tr>

        <!-- Verificacion para saber si hay contactos registrados -->
        <?php if (!empty($contactos)) { ?>
            <!-- Se recorren y muestran los contactos -->
            <?php foreach ($contactos as $contacto) { ?>
                <tr>
                    <td><?php echo htmlspecialchars($contacto['nombre']); ?></td>
                    <td><?php echo htmlspecialchars($contacto['apellidos']); ?></td>
                    <td><?php echo htmlspecialchars($contacto['email']); ?></td>
                    <td>
                        <!-- Se muestra una lista de telefonos si el contacto tiene -->
                            <?php foreach ($contacto['telefonos'] as $telefono) { ?>
                                <li><?php echo htmlspecialchars($telefono); ?></li>
                            <?php } ?>
                        </ul>
                    </td>

                <td>
                    <!-- Enlace para eliminar el contacto con una confirmacion -->
                    <a href="baja_contacto.php?id_contacto=<?php echo $contacto['id_contacto']; ?>" onclick="return confirm('¿Seguro que quieres eliminar este contacto?');">
                        Eliminar
                    </a>
                </td>
            </tr>
        <?php } ?>
        <?php } else { ?>
            <!-- Mensaje cuando NO hay contactos registrados -->
            <tr>
                <td colspan="4">No tienes contactos guardados.</td>
            </tr>
        <?php } ?>
    </table>
</body>
</html>