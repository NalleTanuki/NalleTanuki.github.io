document.addEventListener('DOMContentLoaded', () => {

  // ====== FONDO ANIMADO ======
  const canvas = document.getElementById('fondoAnimado');
  const ctx = canvas.getContext('2d');

  const caracteres = 'アカサタナハマヤラワ0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ$%#*+-'.split('');
  const fontSize = 14;
  let velocidad = 0.5;
  
  let columnas = canvas.width / fontSize;
  let lluvia = [];

  function ajustarCanvas() {
    canvas.width = window.innerWidth;
    canvas.height = window.innerHeight;
    columnas = Math.floor(canvas.width / fontSize);
    lluvia = Array(columnas).fill(0);
  }

  function esModoClaro() {
    return document.body.classList.contains('modo-claro');
  }

  function dibujar() {
    ctx.fillStyle = esModoClaro()
      ? 'rgba(253, 253, 253, 0.05)'
      : 'rgba(13, 17, 23, 0.03)';
    ctx.fillRect(0, 0, canvas.width, canvas.height);

    ctx.fillStyle = esModoClaro()
      ? 'rgba(255, 179, 198, 0.3)'
      : 'rgba(88, 166, 255, 0.3)';
    ctx.font = `${fontSize}px monospace`;

    for (let i = 0; i < lluvia.length; i++) {
      const texto = caracteres[Math.floor(Math.random() * caracteres.length)];
      ctx.fillText(texto, i * fontSize, Math.floor(lluvia[i]) * fontSize);

      if (lluvia[i] * fontSize > canvas.height && Math.random() > 0.98) {
        lluvia[i] = 0;
      }
      lluvia[i] += velocidad;
    }

    requestAnimationFrame(dibujar);
  }

  ajustarCanvas();
  requestAnimationFrame(dibujar);
  window.addEventListener('resize', ajustarCanvas);

  // ====== MODO CLARO/OSCURO ======
  const btnToggle = document.getElementById('toggleTheme');

  function cambiarModo() {
    document.body.classList.toggle('modo-claro');
    localStorage.setItem('modo', esModoClaro() ? 'claro' : 'oscuro');

    // Limpiar canvas
    ctx.clearRect(0, 0, canvas.width, canvas.height);
  }

  (function aplicarModoGuardado() {
    const modoGuardado = localStorage.getItem('modo');
    if (modoGuardado === 'claro') document.body.classList.add('modo-claro');
  })();

  if (btnToggle) btnToggle.addEventListener('click', cambiarModo);

  // ====== TEXTOS MULTI-IDIOMA ======
  const btnES = document.getElementById('btnES');
  const btnEN = document.getElementById('btnEN');
  let textos = {};

  // Si no usara DOMContentLoaded... :
  // function cargarTraducciones(callback) {
  //   const xhr = new XMLHttpRequest();
  //   xhr.responseType = 'json';
  //   const timestamp = new Date().getTime();
  //   xhr.open('GET', 'js/traducciones.json?t=' + timestamp, true);

  //   xhr.onreadystatechange = function () {
  //     if (xhr.readyState === 4 && xhr.status === 200) {
  //       textos = xhr.response;
  //       callback();
  //     }
  //   };

  //   xhr.send();
  // }

  function cargarTraducciones(callback){
    const timestamp = new Date().getTime();
    fetch(`js/traducciones.json?t=${timestamp}`)
    .then(res => res.json())
    .then(data => {
      textos =data;
      callback();
    })
    .catch(err => console.error('Error al cargar las traducciones:', err));
  }

  function cambiarIdioma(lang) {
    const t = textos[lang];
    if (!t) return;

    for (let key in t) {
      const el = document.getElementById(key);
      if (el) el.innerHTML = t[key];
    }

    localStorage.setItem('idioma', lang);

    // Cambiar estado visual de banderas
    if (btnES) btnES.classList.toggle('active', lang === 'es');
    if (btnEN) btnEN.classList.toggle('active', lang === 'en');
  }

  if (btnES) btnES.addEventListener('click', () => cambiarIdioma('es'));
  if (btnEN) btnEN.addEventListener('click', () => cambiarIdioma('en'));

  // Cargar traducciones y aplicar idioma guardado
  cargarTraducciones(() => {
    const idiomaGuardado = localStorage.getItem('idioma') || 'es';
    cambiarIdioma(idiomaGuardado);
  });

  // ====== BOTON "VOLVER ARRIBA" ======
  const btnArriba = document.getElementById('btnArriba');

  window.addEventListener('scroll', () => {
    if (window.scrollY > 300) btnArriba.classList.add('visible');
    else btnArriba.classList.remove('visible');
  });

  btnArriba.addEventListener('click', () => {
    window.scrollTo({ top: 0, behavior: 'smooth' });
  });

});