<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    
    <link rel="stylesheet" type="text/css" href="css/estilo.css">
    <link rel="stylesheet" type="text/css" href="css/cabecera.css">
</head>

<body>

<?php
    if (session_status() === PHP_SESSION_NONE) {
        session_start();
    }

    $rol = $_SESSION['rol'] ?? null;
    $nombre = htmlspecialchars($_SESSION['nombre'] ?? '');

    $cursos_menu = $cursos_menu ?? [];
?>

<header>
    <!-- ======= MENU MOVIL ======= -->
    <div id="header-movil">
        <div id="movil-usuario">
            <?php if ($rol === "alumno"): ?>
                <p class="usuario-nombre"><?= $nombre ?></p>
            <?php elseif ($rol === "profesor"): ?>
                <p class="usuario-nombre">Profesor: <?= $nombre ?></p>
            <?php elseif ($rol === "admin"): ?>
                <p class="usuario-nombre">Administrador: <?= $nombre ?></p>
            <?php endif; ?>
        </div>

        <button id="btn-menu">&#9776;</button>
    </div>

    <!-- ======= MENU MOVIL ======= -->
    <nav id="menu-movil">
        <!-- ADMIN -->
        <?php if ($rol === "admin"): ?>

            <a href="panel_admin.php#crear-usuario">Crear usuario</a>
            <a href="panel_admin.php#crear-asignatura">Crear asignatura</a>
            <a href="panel_admin.php#ver-usuarios">Usuarios registrados</a>
            <a href="panel_admin.php#ver-asignaturas">Asignaturas registradas</a>

        <!-- PROFESOR -->
        <?php elseif ($rol === "profesor"): ?>

            <a href="panel_profesor.php">Alumnos ▾</a>
            <!-- Ver todos los cursos -->
             <a href="panel_profesor.php" class="submenu">Todos los cursos</a>

            <?php foreach ($cursos_menu as $c): ?>
                <a class="submenu" href="panel_profesor.php?curso=<?= $c['id_curso'] ?>">
                    <?= htmlspecialchars($c['nombre']) ?> ESO
                </a>
            <?php endforeach; ?>

            <a href="subir_deberes.php">Subir archivos</a>
            <a href="crear_test.php">Crear test</a>

        <!-- ALUMNO -->
        <?php elseif ($rol === "alumno"): ?>
            <a href="panel_alumno.php">Asignaturas</a>
            <a href="subir_deberes_alumno.php">Subir archivo</a>
            <a href="cronometro.php">Cronómetro</a>
            <a href="#">Premios</a>
        <?php endif; ?>

        <a href="logout.php" class="logout">Cerrar sesión</a>
    </nav>

    <!-- ======= MENU ESCRITORIO ======= -->
    <nav id="menu-escritorio">

        <!-- INFO -->
        <div id="info-usuario">
            <?php if ($rol === "alumno"): ?>
                <img src="img/premio/platino.png" alt="insignia">
                <p class="usuario-nombre"><?= $nombre ?></p>
            <?php elseif ($rol === "profesor"): ?>
                <p class="usuario-nombre">Profesor: <?= $nombre ?></p>
            <?php elseif ($rol === "admin"): ?>
                <p class="usuario-nombre">Administrador: <?= $nombre ?></p>
            <?php endif; ?>
        </div>

        <!-- ADMIN -->
        <?php if ($rol === "admin"): ?>

            <a class="enlace" href="panel_admin.php#crear-usuario">Crear usuario</a>
            <a class="enlace" href="panel_admin.php#crear-asignatura">Crear asignatura</a>
            <a class="enlace" href="panel_admin.php#ver-usuarios">Usuarios registrados</a>
            <a class="enlace" href="panel_admin.php#ver-asignaturas">Asignaturas registradas</a>

        <!-- PROFESOR -->
        <?php elseif ($rol === "profesor"): ?>

            <div class="submenu">
                <a class="enlace submenu-titulo" href="#">Alumnos ▾</a>

                <div class="submenu-opciones">
                    <!-- Ver todos -->
                    <a href="panel_profesor.php">Todos los cursos</a>

                    <?php foreach ($cursos_menu as $c): ?>
                        <a href="panel_profesor.php?curso=<?= $c['id_curso'] ?>">
                            <?= htmlspecialchars($c['nombre']) ?> ESO
                        </a>
                    <?php endforeach; ?>
                </div>
            </div>

            <a class="enlace" href="subir_deberes.php">Subir archivos</a>
            <a class="enlace" href="crear_test.php">Crear test</a>

        <!-- ALUMNO -->
        <?php elseif ($rol === "alumno"): ?>

            <a class="enlace" href="panel_alumno.php">Asignaturas</a>
            <a class="enlace" href="subir_deberes_alumno.php">Subir archivo</a>
            <a class="enlace" href="cronometro.php">Cronómetro</a>
            <a class="enlace" href="#">Premios</a>

        <?php endif; ?>

        <a href="logout.php" class="logout">Cerrar sesión</a>
    </nav>

</header>

<!-- JS -->
<script src="js/script_cabecera.js"></script>

</body>
</html>