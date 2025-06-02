/**
 * Realizar una aplicaciónen Java que muestre el siguiente menu:
 *  1. Alta alumno.
 *  2. Baja alumno.
 *  3. Consultar alumno.
 *  4. Fin.
 *
 * - Se debe validar que la opcion seleccionada este entre 1 y 4, mostrando un mensaje de error en caso de seleccionar una opcion no valida.
 *
 * - Alta alumno: Se pedira por consola: numero, nombre y curso del alumno
 * No se podra dar de alta a un alumno con un mismo numero, es decir,
 * si se introduce un numero de un alumno que ya exista, entonces
 * se le debe indicar al usuario y se vuelve a pedir el numero.
 * Una vez dado de alta un alumno, se debe volver a mostrar el menu.
 *
 * - Baja alumno: Se pedira por consola un numero de alumno,
 * si no existe alumno con ese numero, entonces:
 * se le debe indicar al usuario con un mensaje:
 * "No existe un alumno con ese numero".
 * Si existe ese alumno, entonces:
 *
 * - Consultar alumno: Se pedira por consola un numero de alumno y mostrar los datos de este,
 * si el numero introducido no existe: se le debe indicar al usuario con un mensaje:
 * "No existe un alumno con ese numero".
 * Se debe eliminar ese alumno del ArrayList.
 *
 * - Fin: Finaliza la aplicacion.
 *
 * A tener en cuenta:
 *  - Crear una clase Alumno con los atributos:
 *      - Numero
 *      - Nombre
 *      - Curso
 *  - Crear una clase Principal donde se muestre el menú con las opciones.
 *
 *  - Utilizar un ArrayList para almacenar los alumnos.
 *
 * @author YO
 * @date 18/02/2024
 * @version 1.0
 * Módulo: Programación
 * NºActividad: 4
 */
public class Main
{
    /**
     * Metodo que arranca la aplicacion
     * @param args
     */
    public static void main(String[] args)
    {
        Principal principal = new Principal();
        principal.iniciarMenu();
    }
}