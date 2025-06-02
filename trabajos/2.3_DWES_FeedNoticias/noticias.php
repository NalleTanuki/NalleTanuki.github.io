<?php
    // Crear una instancia de DOMDocument par apoder leer el XML
    $fichero = new DOMDocument();

    // Cargar el feed RSS desde el URL de El Pais
    $fichero -> load('https://feeds.elpais.com/mrss-s/pages/ep/site/elpais.com/section/ultimas-noticias/portada');

    // Inicializar array para guardar las noticias
    $salida = array();

    // Obtenemos todos los elementos <item> del RSS (donde cada uno es una noticia)
    $noticias = $fichero -> getElementsByTagName("item");


    // Recorrer cada noticia
    foreach($noticias as $entry)
    {
        $nuevo = array();

        $nuevo["pubDate"] = $entry -> getElementsByTagName("pubDate")[0] -> nodeValue; // Extraer la fecha de publicacion
        $nuevo["title"] = $entry -> getElementsByTagName("title")[0] -> nodeValue;     // Extraer dl titulo
        $nuevo["link"] = $entry -> getElementsByTagName("link")[0] -> nodeValue;       // Extraer el enlace a la noticia

        // Anhadir la noticia al array de salida
        $salida[] = $nuevo;
    }
?>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Últimas noticias</title>

    <!-- Vincular el CSS para los estilos -->
    <link rel="stylesheet" href="css/estilo.css">
</head>
<body>
    <!-- TITUTLO principal -->
    <h1>Últimas noticias de El País</h1>

        <?php
            // Mostrar cada noticia procesada en HTML
            foreach($salida as $elemento)
            {
                // Guardamos los valores para imprimirlos
                $fecha = $elemento["pubDate"];
                $titulo = $elemento["title"];
                $url = $elemento["link"];

                // Imprime la noticia con el titulo, la fecha y el nelace
                echo "
                    <div class='noticia'>
                    <div class='titulo'>$titulo</div>
                    <div class='fecha'>Publicado: $fecha</div>
                    <a class='enlace' href='$url' target='_blank'>Leer más</a>
                </div>
                ";
            }  
        ?>
</body>
</html>