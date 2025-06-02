// Creamos un array para almacenar los numeros
var arrayNumeros = [];

// Funcion AGREGARNUMERO
function agregarNumero() {
    var nuevoNumero = parseFloat(document.getElementById('numero').value);

    // Valido que sea un numero
    if (isNaN(nuevoNumero)) {
        alert('Ingresa un número.');
        return;
    }

    // Agrego al array el numero
    arrayNumeros.push(nuevoNumero);

    // Muestro el numero ingresado junto al resto (al array)
    document.getElementById('numerosAgregados').innerHTML = arrayNumeros.join(', ');

    // Limpio el input
    document.getElementById('numero').value = '';
}


function realizarOperaciones(){
    // Primero valido que SI haya numeros en el array
    if(arrayNumeros.length === 0){
        alert ('No hay números en el array para hacer las operaciones.');
        return;
    }

    // Creo variables para el maximo, minimo y la suma (para el promedio)
    var numeroMax = arrayNumeros[0]; //El numeroMax sera el primer numero del array (al empezar el recorrido)
    var numeroMin = arrayNumeros[0]; //El numeroMin sera el primer numero del array (al empezar el recorrido)
    var sumaTotal = 0;

    // Bucle FOR para recorrer el array
    for(var i = 0; i < arrayNumeros.length; i++){
        var numero = arrayNumeros[i];

        // Numero MAXIMO
        if(numero > numeroMax){
            numeroMax = numero;
        }

        // Numero MINIMO
        if(numero < numeroMin){
            numeroMin = numero;
        }

        // SUMA
        sumaTotal += numero;
        // PROMEDIO
        var promedio = sumaTotal / arrayNumeros.length;

        // Muestro los resultados
        document.getElementById('masGrande').innerHTML = "" + numeroMax;
        document.getElementById('masPeque').innerHTML = numeroMin;
        document.getElementById('promedio').innerHTML = promedio.toFixed(2);
    }
}