// Contenedor donde se agregan las preguntas
const contenedor = document.getElementById("contenedorPreguntas");
const btnAgregar = document.getElementById("btnAgregar");


// Funcoin numerar rpeguntas
function numerarPreguntas(){
    const boxes = document.querySelectorAll(".pregunta-box");
    boxes.forEach((box, index) => {
        const numero = index + 1;
        box.querySelector(".titulo-pregunta").textContent = `Pregunta ${numero}`;
    });
}

// Funcion nuenva pregunta
function nuevaPregunta(){
    const div = document.createElement("div");
    div.classList.add("pregunta-box");

    div.innerHTML = `
        <h2 class="titulo-pregunta"></h2>

        <label>Enunciado:</label>
        <textarea name="enunciado[]" required></textarea><br>
        
        <label>Opción A:</label>
        <input type="text" name="opcion_a[]" required><br>
        
        <label>Opción B:</label>
        <input type="text" name="opcion_b[]" required><br>
        
        <label>Opción C:</label>
        <input type="text" name="opcion_c[]" required><br>
        
        <label>Opción D:</label>
        <input type="text" name="opcion_d[]" required><br>
        
        <label>Correcta (a/b/c/d):</label>
        <input type="text" name="correcta[]" pattern="[a-dA-D]" required><br>
        
        <button type="button" class="btnEliminar">Eliminar pregunta</button>`;

        contenedor.appendChild(div);

        // Boton borrar pregunta
        div.querySelector(".btnEliminar").onclick = () => {
            div.remove();
            numerarPreguntas();
        };
        // para que tras anhadir, numerar
        numerarPreguntas();
}

// Cargar pregunta iniciAl
document.addEventListener("DOMContentLoaded", () => {
    nuevaPregunta();
});

// Anhadir + preguntas
if(btnAgregar){
    btnAgregar.addEventListener("click", nuevaPregunta);
}