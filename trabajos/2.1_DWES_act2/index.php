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

        // Si se envia el formulario, se procesan los datos
        if ($_SERVER['REQUEST_METHOD'] === 'POST') {
            // Capturo los datos ingresados
            $nombre = strtolower($_POST['nombre']); //para verificar que no se repita ningun nombre por mayusc. o minusc.
            $clave = $_POST['clave'];
            $cargo = $_POST['cargo'];

            try {
                $bd->beginTransaction();
                $insertar = $bd->prepare("INSERT INTO empleados(Nombre, Clave, Cargo) VALUES (?, ?, ?)");
                $insertar->execute([$nombre, $clave, $cargo]);
                $bd->commit();
                echo "<p>El empleado <strong>$nombre</strong> ha sido insertado correctamente.</p><br>";
            } catch (PDOException $e) {
                $bd->rollBack();
                echo "<p>Error al insertar el empleado: " . $e->getMessage() . "</p>";
            }
            
        }
        
    } catch (PDOException $e) {
        // Si no se conecta
        echo "<p>Ha ocurrido un error. Verifica los datos.</p><br>";
        echo "Error con la base de datos: " . $e->getMessage();
    }
?>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Actividad 02 - Empleados</title>
</head>
<body>
    <h1>Gestión de Empleados</h1>

    <!-- CREACION FORMULARIO -->
    <h2>Agregar empleado</h2>
    <form action="index.php" method="POST">
        <!-- Nombre -->
        <label for="nombre">Nombre: </label>
        <input type="text" id="nombre" name="nombre" placeholder="Ingrese nombre" title="El nombre solo puede contener letras y espacios" pattern="[A-Za-záéíúóÁÉÍÓÚÑñ\s]+" required>

        <!-- Clave -->
        <label for="clave">Clave: </label>
        <input type="password" id="clave" name="clave" placeholder="Ingrese clave" required>

        <!-- Cargo -->
        <label for="cargo">Cargo:</label>
        <input type="number" id="cargo" name="cargo" placeholder="Ingrese el cargo" required>

        <!-- BOTON para insertar el empleado -->
        <button type="submit">Insertar empleado</button>
    </form>


    <!-- Mostrar TODOS los empleados -->
    <a href="mostrar_empleados.php">
        <button type="button" name="mostrarTodos">Mostrar empleados</button>
    </a>
</body>
</html>

<?php
    // Cierro la conexion a la BBDD
    $bd = null;
?>