<?php
libxml_use_internal_errors(true); // Habilita la captura de errores XML

$doc = new DOMDocument();
$doc->load("configuracion.xml");

if ($doc->schemaValidate("configuracion.xsd")) {
    echo "La validación del XML con el XSD fue exitosa.";
} else {
    echo "La validación del XML con el XSD falló. <br>";
    foreach (libxml_get_errors() as $error) {
        echo "🔹 Error: " . htmlspecialchars($error->message) . "<br>";
    }
}
?>