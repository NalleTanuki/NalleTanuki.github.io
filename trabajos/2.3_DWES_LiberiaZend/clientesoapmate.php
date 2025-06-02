<?php
    require_once 'vendor/autoload.php';
    
    $cliente = new Zend\Soap\Client('http://localhost/dwes/cap10/servidorsoapmate.php?wsdl');
    $result = $cliente -> potencia(['base' => 2, 'exponente' => 3]);

    echo "result -> potenciaResult: " . $result -> potenciaResult . "\n - ";
    echo "Response: \n" . $cliente -> getLastResponse() . "\n";