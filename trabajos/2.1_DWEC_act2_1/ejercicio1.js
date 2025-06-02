function calcularPrecioFinal(){
    // Recogemos, en una variable, el dato introducido en PRECIO
    var precio = parseFloat(document.getElementById('precio').value);

    // Recogemos, en una variable, el dato introducido en DESCUENTO
    var descuento = parseFloat(document.getElementById('descuento').value);
    
    /** Valido que:
     * - precio sea:
     *      un numero
     *      mayor que 1
     *      que no este vacio el campo
     * - descuento sea:
     *      un numero
     *      que este entre 0 y 100
     *      que no este vacio el campo*/

    if(isNaN(precio)){
        alert ('Por favor, ingresa un número en el campo precio.');
        return;
    } else if(precio < 0) {
        alert ('El precio debe ser mayor  que 0€.');
        return;
    } else if (isNaN(descuento)){
        alert ('Por favor, ingresa un número en el campo descuento.');
        return;
    } else if(descuento < 0 || descuento > 100) {
        alert ('El descuento debe estar entre 0 y 100.');
        return;
    }

    // Creo una variable donde calculo el precio final con el descuento
    var precioFinal = precio - (precio * (descuento / 100));

    // Muestro el resultado, ajustando los decimales
    document.getElementById('mensaje').innerHTML = 'El precio final es: €' + precioFinal.toFixed(2);
}