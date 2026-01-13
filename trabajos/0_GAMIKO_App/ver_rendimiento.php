<?php
    require_once 'bd.php';
    comprobar_rol('profesor');
    require 'cabecera.php';

    $id_alumno = $_GET['id_alumno'] ?? null;

    if(!$id_alumno){
        die("ID de alumno no válido.");
    }

    $alumno = obtener_alumno($id_alumno);
    $datos = obtener_rendimiento_alumno($id_alumno);
    $puntajeTotal = obtener_puntaje_alumno($id_alumno);
    $totalPuntos = $puntajeTotal['puntos'] ?? 0;
    // Total acumu,ado
    $puntaje_linea = array_fill(0, count($datos['fechas']), $totalPuntos);

?>


<!-- --------------------------------------- HTML -------------------------------------- -->
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Rendimiento de <?php echo htmlspecialchars($alumno['nombre'].' '.$alumno['apellidos']); ?></title>
    
    <link rel="stylesheet" type="text/css" href="css/estilo.css"> <!-- Estilos generales -->
    <link rel="stylesheet" type="text/css" href="css/ver_rendimiento.css">    <!-- Estilos propios -->
    
    <!-- GRAFICSO -->
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
</head>

<body>
    <!-- titulo -->
    <h1>Rendimiento de <?php echo htmlspecialchars($alumno['nombre'].' '.$alumno['apellidos']); ?></h1>
    
    <!-- contenedor graficos -->
    <div class="chart-container">
        <canvas id="rendimientoChart"></canvas>
    </div>

<script>
    const ctx = document.getElementById('rendimientoChart').getContext('2d');
    
    const chart = new Chart(ctx, {
        type: 'line',
        data: {
            labels: <?php echo json_encode($datos['fechas']); ?>,
            datasets: [
                {
                    label: 'Puntos tareas',
                    data: <?php echo json_encode($datos['puntos_tareas']); ?>,
                    borderColor: '#FF4D6D',
                    backgroundColor: 'rgba(255, 107, 129, 0.2)',
                    borderWidth: 3,
                    pointRadius: 3,
                    tension: 0.3,
                    fill: true,
                    yAxisID: 'yPuntos'
                },
                {
                    label: 'Puntos tests',
                    data: <?php echo json_encode($datos['puntos_tests']); ?>,
                    borderColor: '#FFD166',
                    backgroundColor: 'rgba(255, 209, 102, 0.2)',
                    borderWidth: 3,
                    pointRadius: 3,
                    tension: 0.3,
                    fill: true,
                    yAxisID: 'yPuntos'
                },
                {
                    label: 'Puntaje total acumulado',
                    data: <?php echo json_encode($puntaje_linea); ?>,
                    borderColor: '#3A7DFF',
                    backgroundColor: 'rgba(50, 205, 50, 0.2)',
                    borderWidth: 3,
                    pointRadius: 3,
                    tension: 0.3,
                    fill: false,
                    yAxisID: 'yPuntos'
                }
            ]
        },
        options: {
            responsive: true,
            maintainAspectRatio: false, //para adaptarse
            plugins: {
                legend: {
                    labels: {
                        font: {
                            size: window.innerWidth < 600 ? 10 : 14
                        }
                    },
                    position: 'top'
                }
            },
            scales: {
                x: { title: { display: true, text: 'Fecha' } },
                yPuntos: {
                    type: 'linear',
                    position: 'right',
                    beginAtZero: true,
                    title: { display: true, text: 'Puntos acumulados' }
                }
            }
        }
    });
</script>
</body>
</html>