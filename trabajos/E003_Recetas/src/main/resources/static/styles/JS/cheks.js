document.addEventListener('DOMContentLoaded', function() {
    const form = document.getElementById('ingredientesForm');

    if (form) {
        form.addEventListener('submit', function(e) {

            const articulos = document.querySelectorAll('article');

            articulos.forEach(articulo => {
                const checkbox = articulo.querySelector('input[type="checkbox"]');
                const textInput = articulo.querySelector('input[type="text"]');

                if (!checkbox.checked) {
                    textInput.disabled = true;
                }
            });

            // El formulario se enviará normalmente
            // Los inputs deshabilitados NO se enviarán al servidor
        });
    } else {
        console.error('No');
    }
});