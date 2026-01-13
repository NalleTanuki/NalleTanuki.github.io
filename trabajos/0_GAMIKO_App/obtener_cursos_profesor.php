<?php
    require_once 'bd.php';

    if(!isset($_GET['id_profesor'])){
        echo json_encode([]);
        exit;
    }

    $id_profesor = (int)$_GET['id_profesor'];
    $bd = conexion_bd();

    $sql = "SELECT c.id_curso, c.nombre 
            FROM profesor_curso pc
            JOIN curso c ON pc.id_curso = c.id_curso
            WHERE pc.id_profesor = :id_profesor";

    $stmt = $bd->prepare($sql);
    $stmt->execute([':id_profesor' => $id_profesor]);

    echo json_encode($stmt->fetchAll(PDO::FETCH_ASSOC));