<?php
    require_once 'bd.php';
    comprobar_sesion('alumno');
    $bd = conexion_bd();
    require_once 'cabecera.php';
?>

<!-- --------------------------------------- HTML -------------------------------------- -->
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Panel de alumno</title>
    <link rel="icon" href="img/logo/favicon/fav.png" type="image/x-icon"> <!-- favicon -->

    <link rel="stylesheet" type="text/css" href="css/estilo.css">   <!-- Estilos generales -->
    <link rel="stylesheet" type="text/css" href="css/panel_alumno.css">    <!-- Estilos propios -->
</head>
<body>
    <div class="contenedor">
        <!-- Titulo -->
        <h1>Panel del Alumno</h1>

        <!-- Asignaturas -->
        <div class="img_contenedor">
            <img src="img/asignaturas/biologia.jpg" alt="Asignatura de Biología">
            <img src="img/asignaturas/historia.jpg" alt="Asignatura de Historia">

            <img src="img/asignaturas/biologia.jpg" alt="Asignatura de Biología">
            <img src="img/asignaturas/historia.jpg" alt="Asignatura de Historia">

            <img src="img/asignaturas/biologia.jpg" alt="Asignatura de Biología">
            <img src="img/asignaturas/historia.jpg" alt="Asignatura de Historia">
        </div>

        <!-- TEST -->
        <h2>Test disponibles</h2>
        <?php
            $tests = obtener_test();

            if($tests){
                echo '<ul>';
                foreach($tests as $test){
                    echo '<li>'.$test['pregunta'].' <a href="realizar_test.php?id='.$test['id_test'].'">Realizar</a></li>';
                }
                    echo '</ul>';
            } else {
                echo "<p>No hay tests disponibles.</p>";
            }
        ?>
    </div>
</body>
</html>