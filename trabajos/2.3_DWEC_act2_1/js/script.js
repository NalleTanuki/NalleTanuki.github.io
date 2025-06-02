let datos = []; // Crear una variable global (datos) para guardar el contenido del JSON

// Funcion para cargar el JSON
function cargarJSON(){
    let xhr = new XMLHttpRequest();         // Crear solicitud
    let timestamp = new Date().getTime();   // Crear timestamp
    let url = "json.json?t=" + timestamp;   // Crear url

    xhr.open("GET", url, true);  // Abrir solicitud

    // Comprobar que la respuesta del servidor es correcta
    xhr.onload = function(){
        if(xhr.readyState === 4 && xhr.status === 200){
            datos = JSON.parse(xhr.responseText); // Parseo los datos del JSON
            cargarCategorias();  // Llamar a la funcion para cargar las categorias
        }
    };
    xhr.send(); // Enviar solicitud
}


// Funcion cargarCategorias
function cargarCategorias(){
    const categoriaSelect = document.getElementById("categoriaSelect");
    categoriaSelect.innerHTML = '<option value="">-- Selecciona una categoría --</option>';

    // Recorrer array del JSON, que categorias debe mostrar
    for(let i = 0; i < datos.length; i++){
        let opcion = document.createElement("option"); // Crear variable para crear un elemento option

        opcion.value = datos[i].value;
        opcion.text = datos[i].label;

        categoriaSelect.appendChild(opcion);  //Anhadir el option al select
    }
}


// Funcion que muestra las subcategorias
function cargarSubcategorias(){
    let categoria = document.getElementById("categoriaSelect").value;  // Variable para saber la categoria escogida

    let selectSubcategorias = document.getElementById("subcategoriaSelect"); // Coger el id de las subcategorias
    selectSubcategorias.innerHTML = '<option value="">-- Seleccione una subcategoría --</option>';

    // Recorrer las subcategorias (teniendo en cuenta la categoria escogida)
    for(let i = 0; i < datos.length; i++){
        if(datos[i].value === categoria){
            let subcategorias = datos[i].options;

            // Recorrer las subcategorias
            for(let j = 0; j < subcategorias.length; j++){
                // Crear elemento
                let opcion = document.createElement("option");
                opcion.value = subcategorias[j].value;
                opcion.text = subcategorias[j].label;

                selectSubcategorias.appendChild(opcion); // Anhadirlo
            }
        }
    }
}

// Evento de envio del formulario
document.getElementById("formulario").addEventListener("submit", function(evento){
    const subcategoria = document.getElementById("subcategoriaSelect");
    const passwordInput = document.getElementById("password");
    const passwordError = document.getElementById("errorPassword");

    const seleccion = subcategoria.options[subcategoria.selectedIndex].text;
    const password = passwordInput.value;

    // EXPRESION REGULAR
    const regex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d).{8,}$/;

    let errores = false;

    // Validar contrasenha
    if(!regex.test(password)){
        passwordError.textContent = "La contraseña debe tener al menos 8 caracteres, una mayúscula, una minúscula y un número.";
        errores = true;
    } else {
        passwordError.textContent = "";
    }

    if(!subcategoria.value){
        alert("Por favor selecciona una subcategoría.");
        errores = true;
    }

    // Si todo es correcto
    if(!errores){
        alert("Has seleccionado: " + seleccion);
    }
});

document.getElementById("categoriaSelect").addEventListener("change", cargarSubcategorias); // Evento al cambiar la categoria

cargarJSON(); // Cargar el JSON
setInterval(cargarJSON, 10000); // Recargar el JSON automaticamente cada 10s