<?php
    // CONEXION A LA BBDD
    // Datos conexion y bbdd
    $cadena_conexion = 'mysql:dbname=dweschios; host=127.0.0.1';
    $bbdd = 'dweschios';
    $usuario = 'root';
    $clave = '';

    try {
        // Conectar
        $bd = new PDO($cadena_conexion, $usuario, $clave);
        echo "Conexión exitosa a la BBDD: " . $bbdd . "<br>";

        // Consulta para obtener a todos los empleados
        $consultaTodos = "SELECT * FROM empleados";
        $resultadoTodos = $bd->query($consultaTodos);

        // Consulta para obtener empleados Rol 1
        $consultaRol1 = $bd->prepare("SELECT * FROM empleados WHERE Cargo = ?");
        $consultaRol1->execute([1]);
    } catch (PDOException $e) {
        // Si no conecta
        echo "Error con la base de datos: " . $e->getMessage();
    }
?>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Mostrar Empleados</title>
</head>
<body>
    <h1>Listado de Empleados</h1>

    <!-- Tabla con todos los empleados -->
    <h2>Todos los Empleados</h2>
    <table border="1">
        <tr>
            <th>Código</th>
            <th>Nombre</th>
            <th>Cargo</th>
        </tr>

        <?php
            foreach ($resultadoTodos as $usu) {
                echo "<tr>";
                echo "<td>" . $usu['Código'] . "</td>";
                echo "<td>" . $usu['Nombre'] . "</td>";
                echo "<td>" . $usu['Cargo'] . "</td>";
                echo "</tr>";
            }
        ?>
    </table>

    <!-- Tabla con empleados con Cargo 1 -->
    <h2>Empleados con Cargo 1</h2>
    <table border="1">
        <tr>
            <th>Código</th>
            <th>Nombre</th>
            <th>Cargo</th>
        </tr>

        <?php
            foreach ($consultaRol1 as $usu) {
                echo "<tr>";
                echo "<td>" . $usu['Código'] . "</td>";
                echo "<td>" . $usu['Nombre'] . "</td>";
                echo "<td>" . $usu['Cargo'] . "</td>";
                echo "</tr>";
            }
        ?>
    </table>

    <!-- COOKIE -->
    <?php
        $resultado = $bd->query("SELECT COUNT(*) AS total FROM empleados");

        $totalEmpleados = 0; //Variable inicializada

        // Recorro los resultados
        foreach ($resultado as $fila) {
        $totalEmpleados = $fila['total'];
        }

        // Establecer la cookie
        setcookie("numEmpleados", $totalEmpleados, time() + 1800);

        echo "<p>Número total de empleados (almacenado en cookie): " . $totalEmpleados . "</p>";
    ?>

    <!-- BOTON para volver a index -->
    <a href="index.php"><button>Atrás</button></a>
</body>
</html>

<?php
    $bd = null;
?>