<header>
    Usuario: <?php echo $_SESSION['usuario']['correo'] ?> <!-- Para mostrar el correo del usuario -->

    <?php
        if($_SESSION['usuario']['rol'] == 1) { //si el usuario tiene rol=1 (administrador)
    ?>
    <b> <hr> <!--pon en negrita y una linea horizontal-->
    <?php
        }
    ?>

    <a href="categorias.php">[HOME]</a>
    <a href="carrito.php">[Ver carrito]</a>

    <?php
        // si el usuario tiene rol=1 (adminsitrador)
        if ($_SESSION['usuario']['rol'] == 1) {
    ?>
        <a href="administracion.php" > [Administración] </a>
    <?php
        }
    ?>

    <a href="logout.php">[Cerrar sesión]</a>

    <?php
    // si el usuario tiene rol=1 (adminsitrador)
        if ($_SESSION['usuario']['rol'] == 1) {
    ?> 
    </b> <!-- cierra negrita -->
    <?php
        }
    ?>
</header>
<hr> <!-- linea horizontal -->