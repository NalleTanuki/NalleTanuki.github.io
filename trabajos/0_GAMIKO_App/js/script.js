/* ------------------- BOTON mostrar/ocultar contrasenha ------------------- */
document.addEventListener('DOMContentLoaded', () => {
  const input = document.getElementById('contrasenha');
  const toggleBtn = document.getElementById('togglePassword');
  const icon = document.getElementById('iconoPass');

  toggleBtn.addEventListener('click', () => {
    icon.classList.add('fade');

    setTimeout(() => {
      if (input.type === 'password') {
        input.type = 'text';
        input.classList.add('pass-campo');
        icon.src = 'img/icon/ocultar.png';
        icon.alt = 'Ocultar contraseña';
      } else {
        input.type = 'password';
        input.classList.add('pass-campo');
        icon.src = 'img/icon/ver.png';
        icon.alt = 'Ver contraseña';
      }
      icon.classList.remove('fade');
    }, 200);
  });
});