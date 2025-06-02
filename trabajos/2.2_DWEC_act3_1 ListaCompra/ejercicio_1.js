// ELEMENTOS - Variables globales
var lista = document.getElementById("listaProductos"); //Referencia a la lista en el HTML
var nuevoProducto = document.getElementById("producto"); //Referencia al campo de entrada

// Funcion ANHADIR producto
function anhadir(){
    // Obtener el valor del campo de entrada. Uso de .trim() para eliminar espacios en blanco
    let productoTexto = nuevoProducto.value.trim();
    // Verificar que NO esta vacio
    if (productoTexto === "") {
        alert ("Por favor, introduce un producto.");
        return;
    } else if (!isNaN(productoTexto)) { //Verifica si son numeros
        alert ("Por favor, introduce texto.");
        nuevoProducto.value = "";
        return;
    }


    //Comprobamos que NO existe ya el producto en algun <li> del <ul>
    let productos = lista.getElementsByTagName('li'); //Recoge los <li>
    for (let i = 0; i < productos.length; i++) { //los recorremos los li
        if (productos[i].textContent === productoTexto) {
            alert ('El producto ya existe.');
            nuevoProducto.value = ""; //limpiamos el campo de entrada
            return;
        }
    }

    // si NO EXISTE, anhade el producto a la lista <ul>
    if (nuevoProducto) {
        let nuevoProductoAnhadir = document.createElement('li'); // crear un <li>
        nuevoProductoAnhadir.textContent = productoTexto;
        lista.appendChild(nuevoProductoAnhadir);
        nuevoProducto.value = ""; //limpiamos el campo de entrada
    }
}

// Funcion ELIMINAR
function eliminar(){
    // Obtener el valor del campo de entrada. Uso de .trim() para eliminar espacios en blanco
    let productoEliminar = nuevoProducto.value.trim();
    if (productoEliminar === "") { //Si esta en blanco
        alert ("Por favor, introduce un producto.");
        return;
    }

    // Busca el producto en la lista
    let productos = lista.getElementsByTagName('li');
    for (let i = 0; i < productos.length; i++) {
        if (productos[i].textContent === productoEliminar) {
            lista.removeChild(productos[i]);
            nuevoProducto.value = ""; // limpiar el campo de entrada
            return;
        }
    }
    alert ("El producto a eliminar NO existe. Revise la lista."); // si NO existe, mensaje
}


// Funcion CAMBIAR COLOR
function cambiarColor(){
    let nuevoColor = prompt("Indique el color de texto: (por ejemplo: 'pink', '#123456')");

    // Si es valido
    if (nuevoColor) {
        colorTexto = nuevoColor; //Guardamos el color elegido
        document.body.style.color = colorTexto; // Cambio color txt ============================================================

        // querySelectorAll('button') selecciona todos los botones 
        let botones = document.querySelectorAll('button');
        for (let i = 0; i < botones.length; i++) {
            botones[i].style.color = nuevoColor;
        }
    } else {
        alert ("Por favor, introduce un color válido.");
    }
}

// Funcion RESETEAR
function resetear(){
    lista.innerHTML = "";
    colorTexto = "";
    colorBotones = "";

    document.body.style.color = colorTexto;

    let botones = document.querySelectorAll('button');
        for (let i = 0; i < botones.length; i++) {
            botones[i].style.color = colorBotones;
        }
}