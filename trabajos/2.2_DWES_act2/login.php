<?php
    require_once 'bd.php';
    /**Formulario de login habitual
     * si va bien -> abre sesion, guarda el nombre de usuario y redirige a principal.php
     * si va mal -> mensaje de error
     */

    if ($_SERVER["REQUEST_METHOD"] == "POST") { //Se verifica si la solicitud se hizo mediante POST, es decir, que el usuario envio el formulario
        $usu = comprobar_usuario($_POST['usuario'], $_POST['clave']); //Llamada a la funcion comprobar_usuario()

        if ($usu === false) { //Si comprobar_usuario devuelve FALSE 
            $err = true;      // Creo la variable $err a TRUE y
            $usuario = $_POST['usuario']; //Guardo el usuaro ingresado para no tener q volver a escribirlo
        } else { //sino - si son correctas
            session_start(); // inicio sesion

            // $usu tiene campos correo y codRes, correo
            $_SESSION['usuario'] = $usu; //se guarda el usuario en $_SESSION['usuario']
            $_SESSION['carrito'] = []; // inicializo un array vacio en $_SESSION['carrito']
            header("Location: categorias.php"); // se redirige a categorias.php
            return; // el script se termina
        }
    }
?>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Formulario de login</title>
</head>
<body>
    <?php
        if (isset($_GET["redirigido"])) { //Si el usuario fue redirigo desde otra pagina...
            echo "<p>Haga login para continuar.</p>"; // se muestra mensaje
        }
    ?>

    <?php
        if (isset($err) and $err == true) { // si las credenciales son incorrectas...
            echo "<p> Revise usuario y contraseña.</p>"; // se muestra mensaje
        }
    ?>

    <!-- FORMULARIO -->
    <form action="<?php echo htmlspecialchars($_SERVER["PHP_SELF"]);?>" method="POST">
        <!-- htmlspecialchars -> evita inyecciones de codigo en el url // PHP_SELF -> para enviar datos al mismo PHP -->
        <label for="usuario">Usuario: </label>
        <!-- Si el usuario ingreso el nombre antes de fallar, se vuelve a mostrar el nombre en el campo -->
        <input value = "<?php if (isset($usuario)) echo $usuario; ?>" 
                        id="usuario" name="usuario" type="text" placeholder="restaurante@mail.com">

        <label for="clave">Clave: </label>
        <input id="clave" name="clave" type="password">
        
        <!-- BOTON ENVIAR -->
        <input type="submit">
    </form>
</body>
</html>