//Funcion para que un contacto pueda tener varios telefonos
function agregarTelefono () {
    let div = document.createElement("div");
    div.innerHTML = `<label>Teléfono: <input type="text" name="telefonos[]" pattern="\\d{9}" required></label>`;
    document.getElementById("telefonos").appendChild(div);
}