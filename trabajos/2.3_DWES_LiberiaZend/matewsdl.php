<?php
    require_once 'vendor/autoload.php';
    require "mate.php";
    
    $serverUrl = "http://localhost/dwes/cap10/matewsdl.php";
    $soapAutoDiscover = new \Zend\Soap\AutoDiscover(new \Zend\Soap\Wsdl\ComplexTypeStrategy\ArrayOfTypeSequence());
    $soapAutoDiscover -> setBindingStyle(array('style' => 'document'));
    $soapAutoDiscover -> setOperationBodyStyle(array('use' => 'literal'));
    $soapAutoDiscover -> setClass('mate');
    $soapAutoDiscover -> setUri($serverUrl);

    header("Content-Type: text/xml");
    echo $soapAutoDiscover -> generate() -> toXml();