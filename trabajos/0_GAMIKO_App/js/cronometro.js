document.addEventListener("DOMContentLoaded", () => {
    // Variables
    let segundos = 0;
    let intervalo = null;

    const display = document.getElementById("display");
    const startBtn = document.getElementById("startBtn");
    const stopBtn = document.getElementById("stopBtn");
    const resetBtn = document.getElementById("resetBtn");

    // Mostrar 00:00:00 al cargar
    function actualizarDisplay(){
        let h = Math.floor(segundos / 3600);
        let m = Math.floor((segundos % 3600) / 60);
        let s = segundos % 60;

        display.textContent =
            String(h).padStart(2, "0") + ":" +
            String(m).padStart(2, "0") + ":" +
            String(s).padStart(2, "0");
    }

    // STAAAAAAAAAAAAAAAAAAAAART
    startBtn.addEventListener("click", () => {
        if(intervalo !== null)
            return; //funcionando
        intervalo = setInterval(() => {
            segundos++;
            actualizarDisplay();
        }, 1000);
    });


    // STOOOOOOOOOOOOOOOOOOOOOOOP
    stopBtn.addEventListener("click", () => {
        if(intervalo === null)
            return;

        clearInterval(intervalo);
        intervalo = null;

        // Calcular puntos
        const puntos = Math.floor((segundos / 60) * 2);

        // Enviar al formulario oculto (en el php)
        document.getElementById("tiempo_estudio").value = segundos;
        document.getElementById("puntos").value= puntos;

        document.getElementById("form-crono").submit();
    });
    actualizarDisplay();
});