<!-- Este archivo sirve para exponer los datos del feed en formato JSON,
 es util si queremos consumirlo desde JS (AJAX) o desde otra aplicacion-->

<?php
    //Crear instancia de DOMDocument para manejar XML
    $fichero = new DOMDocument();

    // CArgar el feed RSS desde el URL de El Pais
    $fichero -> load("https://feeds.elpais.com/mrss-s/pages/ep/site/elpais.com/section/ultimas-noticias/portada");

    $salida = array(); //Inicializa un array donde se guardaran las noticias

    // Obtener todos los elementos <item> del RSS (cada uno es una noticia)
    $noticias = $fichero -> getElementsByTagName("item");

    // Recorrer cada noticia
    foreach($noticias as $entry)
    {
        $nuevo = array();
        $nuevo["pubDate"] = $entry -> getElementsByTagName("pubDate")[0] -> nodeValue; // Extraer la fecha de publicacion
        $nuevo["title"] = $entry -> getElementsByTagName("title")[0] -> nodeValue;     // Extraer el titulo
        $nuevo["link"] = $entry -> getElementsByTagName("link")[0] -> nodeValue;       // Extraer el enlace a la noticia

        // Anhae la noticia al array principal
        $salida[] = $nuevo;
    }

    // Convertir el array PHP a formato JSON y mostrarlo
    echo json_encode($salida);
?>