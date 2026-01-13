<?php
    require_once 'bd.php';
    comprobar_sesion('alumno');
    $bd = conexion_bd();
    require_once 'cabecera.php';
    $id_usuario = $_SESSION['id_usuario'];

    $mensaje = ''; // Mensajes

    // Procesar tiempo
    if($_SERVER['REQUEST_METHOD'] === 'POST'){
        // Recibir valores del JS/AJAX
        $tiempo = floatval($_POST['tiempo_estudio']); //segundos totales
        $puntos = intval(($tiempo / 60) * 2); // 2 puntos x min

        $id_tarea = null;

        // Insertar en BD usando la funcion
        if(registrar_tiempo_estudio($id_usuario, $id_tarea, $tiempo, $puntos)){
            // Actualizar el puntaje
            sumar_puntos($id_usuario, $puntos, $tiempo);
        }
    }
?>

<!-- --------------------------------------- HTML -------------------------------------- -->
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Cronómetro</title>

    <link rel="icon" href="img/logo/favicon/fav.png" type="image/x-icon"> <!-- favicon -->
 
    <link rel="stylesheet" type="text/css" href="css/estilo.css">         <!-- Estilos generales -->
    <link rel="stylesheet" type="text/css" href="css/cronometro.css">    <!-- Estilos propios -->
</head>

<body>
    <h1>Cronómetro de estudio</h1>

    <!-- NUMEROS CRONO -->
    <div class="cronometro-container">
        <div id="display">00:00:00</div>

        <!-- BOTONES CRONO -->
        <div class="botones-crono">
            <!-- Start -->
            <button id="startBtn" class="btn-crono">Start</button>
            <!-- Stop -->
            <button id="stopBtn" class="btn-crono">Stop</button>
        </div>
    </div>

    <!-- Formulario oculto qe envia los datos -->
    <form action="" id="form-crono" method="POST" style="display:none;">
        <input type="hidden" name="tiempo_estudio" id="tiempo_estudio">
        <input type="hidden" name="puntos" id="puntos">
    </form>

    <!-- JS -->
     <script src="js/cronometro.js"></script>
</body>
</html>