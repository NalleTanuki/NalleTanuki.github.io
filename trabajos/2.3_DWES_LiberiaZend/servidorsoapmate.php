<?php
    require_once 'vendor/autoload.php';
    require "mate.php";

    $serverUrl = "http://localhost/dwes/cap10/servidorsoapmate.php";

    if (isset($_GET['wsdl']))
    {
        $soapAutoDiscover = new \Zend\Soap\AutoDiscover
        (new \Zend\Soap\Wsdl\ComplexTypeStrategy\ArrayOfTypeSequence());

        $soapAutoDiscover -> setBindingStyle(array('style' => 'document'));
        $soapAutoDiscover -> setOperationBodyStyle(array('use' => 'literal'));
        $soapAutoDiscover -> setClass('mate');
        $soapAutoDiscover -> setUri($serverUrl);

        header("Content-Type: text/xml");

        echo $soapAutoDiscover -> generate() -> toXml();
    }
    else
    {
        $soap = new \Zend\Soap\Server($serverUrl . '?wsdl');
        $soap -> setObject(new \Zend\Soap\Server\DocumentLiteralWrapper(new mate()));
        $soap -> handle();
    }