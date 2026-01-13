<?php
    require_once 'bd.php';
    comprobar_rol('alumno');
    $bd = conexion_bd();
    $id_profesor = $_SESSION['id_usuario'];
    require_once 'cabecera.php';

    $mensaje = ''; // Mensajes


    // Obtener cursos alumno
    $stmt = $bd->prepare("SELECT id_curso
                        FROM alumno_curso");
    $stmt = $bd->prepare($sql);
    $stmt->execute([':id_profesor' => $id_profesor]);

    $asignaturas = $stmt->fetchAll(PDO::FETCH_ASSOC);



    // Insertar tarea
    if ($_SERVER['REQUEST_METHOD'] === 'POST') {
        $titulo = trim($_POST['titulo']);
        $descripcion = trim($_POST['descripcion']);
        $fecha_entrega = $_POST['fecha_entrega'];
        $id_asignatura = $_POST['id_asignatura'];


        // 1 - Obtener curso (real) de la asignatura
        $sqlCurso = "SELECT id_curso
                    FROM asignatura
                    WHERE id_asignatura = :id_asignatura";

        $stmtCurso = $bd->prepare($sqlCurso);
        $stmtCurso->execute([':id_asignatura' => $id_asignatura]);
        
        $id_curso = $stmtCurso->fetchColumn();

        if (!$id_curso) {
            $mensaje = "Error: no se pudo obtener el curso.";
            $tipoMensaje = "error";
        } else {
            // 2. Asegurar q la carpeta uploads existe, sino crearla
            if (!is_dir('uploads')) {
                mkdir('uploads', 0777, true);
            }

            if (isset($_FILES['archivo']) && $_FILES['archivo']['error'] === UPLOAD_ERR_OK) {
            
                // 1. Obtener extension del archivo
                $extension = pathinfo($_FILES['archivo']['name'], PATHINFO_EXTENSION);

                // 2. Crear nombre unico y seguro
                $nombre_archivo = uniqid("tarea_") . "." . $extension;

                // 3- Ruta final donde se guardara el archivo
                $ruta = "uploads/" . $nombre_archivo;

                // 4. Mover archivo a carpeta uploads
                if(!move_uploaded_file($_FILES['archivo']['tmp_name'], $ruta)){
                    $mensaje = "Error al subir el archivo.";
                    $tipoMensaje = "error";
                } else {
                    // 5. Insertar la tarea con el archivo incl.
                    $sql = "INSERT INTO tarea (titulo, descripcion, fecha_entrega, archivo, id_asignatura, id_curso)
                            VALUES (:titulo, :descripcion, :fecha_entrega, :archivo, :id_asignatura, :id_curso)";

                    $stmt = $bd->prepare($sql);
                    $stmt->execute([
                        ':titulo' => $titulo,
                        ':descripcion' => $descripcion,
                        ':fecha_entrega' => $fecha_entrega,
                        ':archivo' => $nombre_archivo,
                        ':id_asignatura' => $id_asignatura,
                        ':id_curso' => $id_curso
                    ]);
                    $mensaje = "Archivo subido correctamente.";
                    $tipoMensaje = "success";
                }
            }
        }
    }
?>

<!-- --------------------------------------- HTML -------------------------------------- -->
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Subir deberes</title>
    
    <link rel="icon" href="img/logo/favicon/fav.png" type="image/x-icon"> <!-- favicon -->
 
    <link rel="stylesheet" type="text/css" href="css/estilo.css">         <!-- Estilos generales -->
    <link rel="stylesheet" type="text/css" href="css/subir_deberes.css">    <!-- Estilos propios -->

    <!-- POP UP alerta -->
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
</head>
<body>
    <h1>Subir archivos</h1>

    <!-- Formulario especial para subir archivos (enctype) -->
    <form method="POST" enctype="multipart/form-data">
        <!-- titulo -->
        <label>Título:</label>
        <input type="text" name="titulo" required><br>

        <!-- descripcion -->
        <label>Descripción:</label>
        <textarea name="descripcion"></textarea><br>

        <!-- Fecha entrega -->
        <label>Fecha de entrega:</label>
        <input type="date" name="fecha_entrega" required><br>
        
        <!-- Asignatura -->
        <label>Asignatura:</label>
        <select name="id_asignatura" required>
            <?php foreach ($asignaturas as $asig): ?>
                <option value="<?= $asig['id_asignatura'] ?>">
                    <?= htmlspecialchars($asig['nombre'] . " - " . $asig['nom_curso']) ?>
                </option>
            <?php endforeach; ?>
        </select><br>

        <!-- archivo -->
        <label>Archivo (PDF):</label>
        <input type="file" name="archivo" accept="application/pdf" required><br>
        
        <!-- boton -->
        <input type="submit" value="Subir">
    </form>

    <!-- MENSAJE - POP UP -->
    <?php if (!empty($mensaje)): ?>
        <script>
            Swal.fire({
                title: "Información",
                text: "<?= htmlspecialchars($mensaje) ?>",
                icon: "<?= $tipoMensaje ?>",
                showConfirmButton: false,
                timer: 1500
            });
        </script>
    <?php endif; ?>
</body>
</html>