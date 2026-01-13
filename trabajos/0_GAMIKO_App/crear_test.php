<?php
    require_once 'bd.php';
    comprobar_rol('profesor');
    $bd = conexion_bd();
    $id_profesor = $_SESSION['id_usuario'];
    require_once 'cabecera.php';

    $mensaje = ''; // Mensajes

    if ($_SERVER['REQUEST_METHOD'] === 'POST') {
        $titulo = trim($_POST['titulo']);
        $id_asignatura = $_POST['id_asignatura'];

        // arrays preguntas
        $enunciados = $_POST['enunciado'];
        $opA = $_POST['opcion_a'];
        $opB = $_POST['opcion_b'];
        $opC = $_POST['opcion_c'];
        $opD = $_POST['opcion_d'];
        $correctas = $_POST['correcta'];

        // Crear test
        $id_test = crear_test($id_profesor, $id_asignatura, $titulo);
        
        if($id_test){
            // Insertar preguntas
            for($i = 0; $i < count($enunciados); $i++){
                agregar_pregunta($id_test, $enunciados[$i], $opA[$i], $opB[$i], $opC[$i], $opD[$i], $correctas[$i]);
            }

            $mensaje = "Test creado correctamente con " . count($enunciados) . " preguntas.";
            $tipoMensaje = "success";
        } else {
            // insertar pregunta
            $mensaje = "Error al crear el test.";
            $tipoMensaje = "error";
        }
    }

    // Asignaturas del profesor
    $asignaturas = obtener_asignaturas_profesor($id_profesor);
?>


    <!-- --------------------------------------- HTML -------------------------------------- -->
    <!DOCTYPE html>
    <html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Crear test</title>
        <link rel="icon" href="img/logo/favicon/fav.png" type="image/x-icon"> <!-- favicon -->
        
        <link rel="stylesheet" type="text/css" href="css/estilo.css">
        <link rel="stylesheet" type="text/css" href="css/crear_test.css">

        <!-- POP UP alerta -->
        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
</head>
    <body>
        <div id="contenedor-principal">
            <!-- titulo -->
            <h1>Crear Test</h1>

            <?php if (!empty($mensaje)): ?>
                <script>
                    Swal.fire({
                        title: "Información",
                        text: "<?= $mensaje ?>",
                        icon: "<?= $tipoMensaje ?>",
                        timer: 1800,
                        showConfirmButton: false
                    });
                </script>
            <?php endif; ?>


            <!-- FORMULARIO -->
            <form method="POST" id="contenedorSup">
                <!-- Titulo -->
                <label>Título:</label>
                <input type="text" name="titulo" required>

                <!-- Asignatura -->
                <label>Asignatura:</label>
                <select name="id_asignatura" required>
                    <?php foreach ($asignaturas as $asig): ?>
                        <option value="<?php echo $asig['id_asignatura']; ?>">
                            <?php echo htmlspecialchars($asig['nombre_asignatura'] . " - " . $asig['nombre_curso']); ?>
                        </option>
                    <?php endforeach; ?>
                </select>

                <!-- Contenedos preguntas -->
                <div id="contenedorPreguntas"></div>

                <!-- BOTON -->
                <button id="btnAgregar" type="button">Añadir otra pregunta</button>
                <br><br>

                <!-- GUARDAR -->
                <input type="submit" value="Guardar test">
            </form>
        </div>

        <!-- JS -->
        <script src="js/crear_test.js"></script>
    </body>
</html>