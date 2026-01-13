document.addEventListener('DOMContentLoaded', () => {
    /* ================ BOTON "Modificar usuario" ================ */
    document.querySelectorAll('.btn-modificar').forEach(btn => {
        btn.addEventListener('click', () => {
            const fila = btn.closest('tr');

            document.getElementById('edit-id').value = btn.dataset.id;
            document.getElementById('edit-nombre').value = fila.cells[1].textContent.trim();
            document.getElementById('edit-apellidos').value = fila.cells[2].textContent.trim();
            document.getElementById('edit-correo').value = fila.cells[3].textContent.trim();

            const rol = fila.cells[5].textContent.trim().toLowerCase();
            document.getElementById('edit-rol').value = rol;

            const cursoId = fila.getAttribute('data-curso-id') || "";
            document.getElementById('edit-curso').value = cursoId;

            const editor = document.getElementById('editar-usuario');
            editor.style.display = 'block';
            editor.scrollIntoView({ behavior: 'smooth' });
        });
    });

    /* ================ BOTON Cancelar edicion ================*/
    document.getElementById('cancelar-edicion').addEventListener('click', () => {
        document.getElementById('editar-usuario').style.display = 'none';
    });


    /* ================ VALIDACION CREAR CONTRASEMHA ================*/
    const inputCrear = document.getElementById('contrasenha');
    const formCrear = document.getElementById('formu-crear');

    // MOSTRAR Y ACTUALIZAR REQUISITOS EN TIEMPO REAL
    const reqMin12 = document.getElementById('min12');
    const reqMayus = document.getElementById('mayus');
    const reqMinus = document.getElementById('minus');
    const reqNum = document.getElementById('num');
    const reqCaracter = document.getElementById('caracter');

    inputCrear.addEventListener('input', () => {
        const val = inputCrear.value;

        // Activar lista (que aparezca solo al escribir)
        document.getElementById('pass-requisitos').classList.add('mostrar');

        // Comprobar requisitos 1x1
        reqMin12.style.color = val.length >= 12 ? "green" : "red";
        reqMayus.style.color = /[A-Z]/.test(val) ? "green" : "red";
        reqMinus.style.color = /[a-z]/.test(val) ? "green" : "red";
        reqNum.style.color = /[0-9]/.test(val) ? "green" : "red";
        reqCaracter.style.color = /[^a-zA-Z0-9]/.test(val) ? "green" : "red";
    });


    formCrear.addEventListener('submit', (e) => {
        const val = inputCrear.value;

        const valido =
            val.length >= 12 &&
            /[A-Z]/.test(val) &&
            /[a-z]/.test(val) &&
            /[0-9]/.test(val) &&
            /[^a-zA-Z0-9]/.test(val);

        // POP-UP
        if (!valido) {
            e.preventDefault();
            Swal.fire({
                title: "Contraseña inválida",
                text: "La contraseña no cumple los requisitos mínimos.",
                icon: "error",
                confirmButtonColor: "#3A7DFF"
            });
            inputCrear.focus();
        }
    });


    /* ================ VALIDACION MODIFICAR CONTRASENHA ================*/
    const inputEditar = document.getElementById('edit-contrasenha');
    const formEditar = document.getElementById('form-editar-usu');

    formEditar.addEventListener('submit', (e) => {
        const val = inputEditar.value.trim();

        // Si esta vacio el campo -> No se valida
        if (val === "") return;

        // Si se escribe algo -> debe cumplir requisitos
        const valido =
            val.length >= 12 &&
            /[A-Z]/.test(val) &&
            /[a-z]/.test(val) &&
            /[0-9]/.test(val) &&
            /[^a-zA-Z0-9]/.test(val);

        if (!valido) {
            e.preventDefault();
            Swal.fire({
                title: "Contraseña no válida",
                text: "Si deseas cambiar la contraseña debe cumplir los requisitos.",
                icon: "error",
                confirmButtonColor: "#3A7DFF"
            });
            inputEditar.focus();
        }
    });

    /* ===================== MOSTRAR / OCULTAR CONTRASENHA ===================== */
    const toggleBtn = document.getElementById("togglePassword");
    const passInput = document.getElementById("contrasenha");
    const iconoPass = document.getElementById("iconoPass");

    if (toggleBtn) {
        toggleBtn.addEventListener("click", () => {
            const tipoActual = passInput.getAttribute("type");

            if (tipoActual === "password") {
                passInput.setAttribute("type", "text");
                iconoPass.src = "img/icon/ocultar.png";
            } else {
                passInput.setAttribute("type", "password");
                iconoPass.src = "img/icon/ver.png";
            }
        });
    }


    /* ================ BOTON "Modificar asignatura" ================ */
    document.querySelectorAll('.btn-modificar-asig').forEach(btn => {
    btn.addEventListener('click', () => {

        const fila = btn.closest('tr');

        document.getElementById('edit-id-asig').value = btn.dataset.id;
        document.getElementById('edit-nombre-asig').value = fila.cells[1].textContent.trim();
        document.getElementById('edit-descripcion-asig').value = fila.cells[2].textContent.trim();

        const editor = document.getElementById('editar-asignatura');
        editor.style.display = 'block';
        editor.scrollIntoView({ behavior: 'smooth' });
    });
});

    // Boton cancelar edicion asignatura
    document.getElementById('cancelar-edicion-asig').addEventListener('click', () => {
        document.getElementById('editar-asignatura').style.display = 'none';
    });


    // Mostrar cursos del profe al crear asignarura
    const selectProfesor = document.querySelector("select[name='profesor_asig']");
    const selectCurso = document.querySelector("select[name='curso_asig']");

    selectProfesor.addEventListener("change", function() {

        const idProfesor = this.value;

        if (!idProfesor) {
            selectCurso.innerHTML = '<option value="">Seleccionar curso</option>';
            return;
        }

        fetch("obtener_cursos_profesor.php?id_profesor=" + idProfesor)
            .then(res => res.json())
            .then(cursos => {
                selectCurso.innerHTML = '<option value="">Seleccionar curso</option>';
                cursos.forEach(curso => {
                    selectCurso.innerHTML += `
                        <option value="${curso.id_curso}">
                            ${curso.nombre}
                        </option>`;
                });
            });
    });
});