document.addEventListener('DOMContentLoaded', () => {

  // ====== MODO CLARO / OSCURO ======
  const btnToggle = document.getElementById('toggleTheme');

  function esModoClaro() {
    return document.body.classList.contains('modo-claro');
  }

  function cambiarModo() {
    document.body.classList.toggle('modo-claro');
    localStorage.setItem('modo', esModoClaro() ? 'claro' : 'oscuro');
  }

  // Aplicar modo guardado
  (function () {
    const modoGuardado = localStorage.getItem('modo');
    if (modoGuardado === 'claro') {
      document.body.classList.add('modo-claro');
    }
  })();

  if (btnToggle) {
    btnToggle.addEventListener('click', cambiarModo);

    btnToggle.addEventListener('keydown', (e) => {
      if(e.key === 'Enter' || e.key === ' '){
        e.preventDefault();
        cambiarModo();
      }
    });
  }

  // ====== IDIOMA ======
  const btnES = document.getElementById('btnES');
  const btnEN = document.getElementById('btnEN');
  let textos = {};

  function cargarTraducciones(callback) {
    fetch('js/traducciones.json')
      .then(res => res.json())
      .then(data => {
        textos = data;
        callback();
      })
      .catch(err => console.error('Error traducciones:', err));
  }

  function cambiarIdioma(lang) {
    const t = textos[lang];
    if (!t) return;

    for (let key in t) {
      const el = document.getElementById(key);
      if (el) el.innerHTML = t[key];
    }

    localStorage.setItem('idioma', lang);

    if (btnES) btnES.classList.toggle('active', lang === 'es');
    if (btnEN) btnEN.classList.toggle('active', lang === 'en');
  }

  if (btnES) btnES.addEventListener('click', () => cambiarIdioma('es'));
  if (btnEN) btnEN.addEventListener('click', () => cambiarIdioma('en'));

  cargarTraducciones(() => {
    const idiomaGuardado = localStorage.getItem('idioma') || 'es';
    cambiarIdioma(idiomaGuardado);
  });

  // ====== BTN ARRIBA ======
  const btnArriba = document.getElementById('btnArriba');

  window.addEventListener('scroll', () => {
    if (btnArriba) {
      btnArriba.classList.toggle('visible', window.scrollY > 300);
    }
  });

  if (btnArriba) {
    btnArriba.addEventListener('click', () => {
      window.scrollTo({ top: 0, behavior: 'smooth' });
    });
  }

});