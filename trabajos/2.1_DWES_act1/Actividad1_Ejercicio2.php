<?php
    /*
    Crea una aplicación en PHP que mediante el uso de funciones
    determine si una palabra es palíndroma o no
    */

    //Creo funcion para verificar si una palabra es palíndroma o no
    function esPalindroma($palabra){
        //Antes de nada, valido que la palabra tenga más de 3 caracteres
        if(strlen($palabra) < 3){
            throw new Exception("La palabra debe tener más de 3 caracteres.<br>");
        }

        //Convierto la palabra a minúsculas
        $palabra = strtolower($palabra);

        //Verifico si la palabra es igual a su reverso
        return $palabra === strrev($palabra);
    }

    //EJEMPLO DE USO:
    $palabras = ["Ana", "patata", "hola", "oso", "reconocer", "fe"];
    
    foreach($palabras as $palabra){
        try{
            if(esPalindroma($palabra)){
                echo "'$palabra' es palíndroma.<br>";
            } else {
                echo "'$palabra' NO es palíndroma.<br>";
            }
        } catch(Exception $e){
            echo "Error: " . $e->getMessage();
        }
    }
?>