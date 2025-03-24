document.addEventListener('DOMContentLoaded', function () {
    var botonAbrirCerrar = document.querySelector('.menu-toggle');
    var lista = document.querySelector('.lista');
    var altura = document.querySelector('.menu').offsetTop;

    botonAbrirCerrar.addEventListener('click', function () {
        lista.classList.toggle('show');
    });

    window.addEventListener('scroll', function () {
        if (window.pageYOffset > 150) {
            document.querySelector('.menu').classList.add('menu-fixed');
        } else {
            document.querySelector('.menu').classList.remove('menu-fixed');
        }
    });
});