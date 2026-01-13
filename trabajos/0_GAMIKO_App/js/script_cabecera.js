document.getElementById("btn-menu").addEventListener("click", function() {
    const menu = document.getElementById("menu-movil");
    menu.style.display = (menu.style.display === "flex") ? "none" : "flex";
});

window.addEventListener('resize', () => {
    if (window.innerWidth > 900) {
        document.getElementById('menu-movil').style.display = 'none';
    }
});