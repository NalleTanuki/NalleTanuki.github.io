<?php
    /*
    Crear una aplicación desarrollada en PHP que mediante el uso de funciones
    permita resolver una ecuación de segundo grado:

    ax2 + bx + c = 0, a≠0

    Si la ecuación no tiene soluciones reales
    se mostrará un mensaje de error
    */

    //Creo la función para resolver la ecuación de segundo grado
    function resolverEcuacion($a, $b, $c){
        //Valido que 'a' no sea cero
        if($a == 0){
            throw new Exception("'a' no puede ser cero.<br>");
        }

        //Calculo el discriminante
        $discriminante = ($b * $b) - (4 * $a * $c);

        //Verifico si el discriminante es negativo (por lo que no hay soluciones reales)
        if($discriminante < 0){
            throw new Exception("La ecuación no tiene soluciones reales.<br>");
        }

        //Calculo las dos soluciones
        $solucion1 = (-$b + sqrt($discriminante)) / (2 * $a);
        $solucion2 = (-$b - sqrt($discriminante)) / (2 * $a);


        //Devuelvo las soluciones
        return "Las soluciones son:<br>x1 = $solucion1 <br>x2 = $solucion2";
    }

    try{
        //EJEMPLO DE USO 1:
        $a = 1;
        $b = -3;
        $c = 2;

        $resultado = resolverEcuacion($a, $b, $c);
        echo $resultado . "<br><br>";

    
        //EJEMPLO DE USO 2:
        $a = 0;
        $b = 8;
        $c = -4;

        $resultado = resolverEcuacion($a, $b, $c);
        echo $resultado . "<br><br>";

    } catch(Exception $e){
        //Atrapo la excepcion y muestro el mensaje de error
        echo "Error: " . $e->getMessage() . "<br>";
    }

    //Hago otro bloque try-catch para poder ver los 3 ejemplos en pantalla juntos
        //EJEMPLO DE USO 3: Discriminante < 0
    try{
        $a = 1;
        $b = 2;
        $c = 5;

        $resultado = resolverEcuacion($a, $b, $c);
        echo $resultado;
    } catch(Exception $e){
        echo "Error: " . $e->getMessage();
    }
?>