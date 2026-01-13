<?php
    require_once 'bd.php';
    comprobar_sesion('profesor');
    $bd = conexion_bd();   

    // Datos profesor
    $id_profesor = $_SESSION['id_usuario'];
    $nombre_profe = htmlspecialchars($_SESSION['nombre']);

    // Cursos y alumnos del profe
    $cursos = obtener_cursos_profesor($id_profesor);

    $cursos_menu = $cursos;

    $curso_seleccionado = $_GET['curso'] ?? null;
    $alumnos_por_curso = [];

    require_once 'cabecera.php'; 

    // Si hay curso seleccionado desde el menu...
    if ($curso_seleccionado) {
        foreach ($cursos as $curso) {
            if ($curso['id_curso'] == $curso_seleccionado) {
                $alumnos_por_curso[$curso['nombre']] = obtener_alumnos_por_curso($curso['id_curso']);
                break;
            }
        }
    }  else {
        // Mostrar todos como siempre
        foreach ($cursos as $curso) {
            $alumnos_por_curso[$curso['nombre']] = obtener_alumnos_por_curso($curso['id_curso']);
        }
    }
?>



<!-- --------------------------------------- HTML -------------------------------------- -->
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Panel del Profesor</title>

    <link rel="icon" href="img/logo/favicon/fav.png" type="image/x-icon">

    <link rel="stylesheet" type="text/css" href="css/estilo.css">   <!-- Estilos generales -->
    <link rel="stylesheet" type="text/css" href="css/panel_profesor.css">    <!-- Estilos propios -->
</head>
<body>
    <div id="contenedor-principal">
        <!-- TITULO -->
        <h1>Alumnos por curso</h1>

        <?php foreach ($alumnos_por_curso as $cursoNombre => $listaAlumnos): ?>
    
        <!-- Subtitulo h2 -->
        <h2><?php echo htmlspecialchars($cursoNombre); ?> ESO</h2>

        <?php
            // Garantizar array
            if (!is_array($listaAlumnos)) {
                $listaAlumnos = [];}
        ?>

        <div class="tabla-scroll">
            <table>
                <tr>
                    <th>Nombre</th>
                    <th>Apellidos</th>
                    <th>Acción</th>
                </tr>

                <?php if (count($listaAlumnos) === 0): ?>
                    <tr>
                        <td colspan="3" style="text-align:center;"><span>No hay alumnos en este curso</span></td>
                    </tr>
                <?php else: ?>
                    <?php foreach($listaAlumnos as $alumno): ?>
                        <tr>
                            <td><?= htmlspecialchars($alumno['nombre']) ?></td>
                            <td><?= htmlspecialchars($alumno['apellidos']) ?></td>
                            <td>
                                <!-- BOTOTN VER ALUMNO -->
                                <form action="ver_rendimiento.php" method="GET" style="display:inline;">
                                    <input type="hidden" name="id_alumno" value="<?= $alumno['id_usuario'] ?>">
                                    <button type="submit" class="btn-ver">Ver</button>
                                </form>
                            </td>
                        </tr>
                    <?php endforeach; ?>
                <?php endif; ?>
            </table>
        </div>
        <?php endforeach; ?>
    </div> <!-- FIN Contenedor pricnipal -->
</body>
</html>