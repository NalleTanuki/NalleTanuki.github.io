function mostrarInfo(pelicula) {
    // Definimos un var con la info (titulo / descripcion / imagen) de cada pelicula
    var titulo, descripcion, imagen;

    //Bucle "if" para mostrar una cosa u otra segun el boton clicado
    if (pelicula === "halloween") {
        titulo = "Halloween";
        descripcion = `Un clásico de terror donde el asesino psicópata <b>Michael Myers</b> regresa a su ciudad natal en la noche de Halloween después de escapar de
        un psiquiátrico tras 15 años de encarcelamiento por el asesinato de su hermana mayor. Comienza a acechar y matar a un grupo de adolescentes, incluída
        <b>Laurie Strode</b>, quien se convierte en su objetivo principal. A medida que la noche avanza, Laurie y sus amigos deben luchar por sobrevivir mientras
        Michael desata su brutalidad en la comunidad.`;
        imagen = "images/Halloween.jpg";
    } else if (pelicula === "senorAnillos") {
        titulo = "El Señor de los Anillos";
        descripcion = `Una épica aventura en la Tierra Media que sigue la historia de <b>Frodo Bolsón</b>, un joven hobbit que hereda un anillo mágico de su tio Bilbo.
        Este anillo resulta ser el <b>Anillo Único</b>, creado por el oscuro señor <b>Sauron</b> para dominar la Tierra Media. Frodo, junto a su compañía de ayudantes,
        debe destruir el Anillo Único.`;
        imagen = "images/senorAnillos.jfif";
    } else { //Se podria poner tambien: else if(pelicula === alienPredator)
        titulo = "Alien vs. Predator 2";
        descripcion = `Un enfrentamiento en la Tierra entre dos icónicas especies alienígenas: los <b>Xenomorfos</b> (alienígenas) y los <b>Predator</b> (cazadores).
        Un Predator que ha venido a cazar a los Xenomorfos accidentalmente libera a estas criaturas en la población. La ciudad se convierte en un campo de batalla
        mortal entre los alienígenas y los cazadores, mientras que un grupo de humanos lucha por escapar del caos y la destrucción.`;
        imagen = "images/AlienPredator.jpg";
    }

    // Mostrar alerta con el titulo escogido
    alert("Película seleccionada: " + titulo);

    // Mostrar la info de la pelicula en el contenedor div
    document.getElementById("infoPelicula").innerHTML =
        "<h2>" + titulo + "</h2>" +
        "<p>" + descripcion + "</p>" +
        "<img src='" + imagen + "' alt='" + titulo + "' style='width:400px;'>";
}
