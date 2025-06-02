import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Principal
{
    //Variables globales
    private Scanner teclado = new Scanner(System.in);
    //Creo un ArrayList
    private ArrayList<Alumno> listaAlumnos = new ArrayList<>();

    /**
     * Método que inicia el Menu Principal, muestra por pantalla las opciones
     * El metodo se ejecuta continuamente hasta que el usuario seleccione la opcion 4. Fin de programa.
     */
    public void iniciarMenu() {
        int opcion;

        do
        {
            opcion = mostrarMenu();

                switch (opcion)
                {
                    case 1:
                        altaAlumno();
                        break;
                    case 2:
                        bajaAlumno();
                        break;
                    case 3:
                        consultarAlumno();
                        break;
                    case 4:
                        System.out.println("Fin del programa. Hasta pronto.");
                        teclado.close();
                        break;
                    default:
                        System.out.println("Opción inválida. Inténtalo de nuevo.");
                        break;
                }
        }
        while (opcion != 4);
    }

    /**
     *Metodo que muestra el menu al usuario y solicita una opcion valida.
     * Asegurandose que la opcion ingresada por el usuario este dentro del rango valido y
     * manejando las excepciones que puedan ocurrir
     * @return La opcion seleccionada por el usuario (entre 1 y 4 inclusives)
     */
    private int mostrarMenu() {
        int opcion = 0;
        do
        {
            try
            {
                System.out.println("\n\n** MENÚ **");
                System.out.println("1. Alta de alumno.");
                System.out.println("2. Baja de alumno.");
                System.out.println("3. Consulta de alumno.");
                System.out.println("4. Fin.");
                System.out.print("Introduce una opción-> ");
                opcion = teclado.nextInt();

                if (opcion < 1 || opcion > 4)
                {
                    System.out.println("\nOpción no válida: 1-4");
                }
            }
            catch (InputMismatchException e)
            {
                teclado.nextLine(); //Limpiar el buffer
                System.out.println("\nError. Introduzca un número.");
            }
        }
        while (opcion < 1 || opcion > 4); //Mientras que opcion sea menor que 1 y mayor que 4

        return opcion;
    }

    /**
     * Metodo para dar de alta a un alumno
     * Asegurando que se introduzcan valores validos y unicos para el numero
     * y con un nombre de mas de 2 caracteres.
     */
    private void altaAlumno()
    {
        int numero = 0;
        boolean numeroExistente;

        do
        {
            try
            {
                do
                {
                    System.out.print("\nIntroduzca el número del alumno-> ");
                    numero = teclado.nextInt();
                    numeroExistente = buscarAlumno(numero) != null;

                    if (numeroExistente) //numeroExistente == true;
                    {
                        System.out.println("\nYa existe un alumno con ese número.");
                    }
                }
                while(numeroExistente); // Mientras numeroExistente == true;

                if(numero <= 0)
                {
                    System.out.println("\nError. Introduce un número mayor que 0.");
                    /*
                    Para que salte el resto de codigo dentro del bucle y
                    vuelva a solicitar al usuario un nuevo numero, ponemos continue
                     */
                    continue;
                }

                String nombre;
                do
                {
                    System.out.print("Nombre del alumno: ");
                    nombre = teclado.next();

                    if (nombre.length() <= 2)
                    {
                            System.out.println("\nError. El nombre debe tener más de 2 caracteres.");
                    }
                }
                while (nombre.length() <= 2);

                System.out.print("Curso del alumno: ");
                String curso = teclado.next();

                Alumno alumno = new Alumno(numero, nombre, curso);
                listaAlumnos.add(alumno);
                System.out.println("\nAlumno añadido correctamente.");

            }
            catch (InputMismatchException e)
            {
                teclado.nextLine(); //Limpiar el buffer
                System.out.println("\nError. Introduzca un número.");
            }
        }
        while(numero <= 0); //Mientras el numero ingresado sea menor o igual a 0
        }


    /**
     * Metodo para dar de baja a un alumno
     * El bucle do-while se ejecutara hasta que la variable
     * alumnoEncontrado = true;
     */
    private void bajaAlumno()
        {
            int numero = 0;
            boolean alumnoEncontrado = false;

            do
            {
                try
                {
                    System.out.print("\nIntroduzca el número del alumno-> ");
                    numero = teclado.nextInt();

                    Alumno alumno = buscarAlumno(numero); //LLamada al metodo buscarAlumno

                    if(alumno != null) //Si se encuentra el alumno
                    {
                        listaAlumnos.remove(alumno); //Se elimina del ArrayList
                        alumnoEncontrado = true; //Establecemos la variable a true
                        System.out.println("\nAlumno eliminado correctamente.");
                    }
                    else //NO se encuentra
                    {
                        System.out.println("\nNo existe ningún alumno con ese número.");
                    }
                }
                catch(InputMismatchException e)
                {
                    teclado.nextLine(); //Limpiar el buffer
                    System.out.println("\nError. Introduzca un número.");
                }

            }
            while(!alumnoEncontrado);  // Mientras alumnoEncontrado == false
        }


    /**
     * Metodo para consultar un alumno ingresando su numero
     * El bucle do-while se ejecutara hasta que la variable
     * alumnoEncontrado = true;
     */
    private void consultarAlumno()
        {
            int numero = 0;
            boolean alumnoEncontrado = false;

            do
            {
                try
                {
                    System.out.print("\nIntroduzca el número del alumno-> ");
                    numero = teclado.nextInt();
                    Alumno alumno = buscarAlumno(numero); //Llamada al metodo buscarAlumno

                    if (alumno != null) //Si se encuentra al alumno
                    {
                        alumnoEncontrado = true;
                        System.out.println("\n** Detalles del alumno **");
                        System.out.println(alumno);

                    }
                    else //Si NO se encuentra al alumno
                    {
                        System.out.println("\nNo existe ningún alumno con ese número.");
                    }
                }
                catch (InputMismatchException e)
                {
                    teclado.nextLine(); //Limpiar el buffer
                    System.out.println("\nError. Introduzca un número.");
                    alumnoEncontrado = false;
                }

            }
            while(!alumnoEncontrado); // Mientras alumnoEncontrado == false
        }


    /**
     * Metodo para buscar en el ArrayList el numero de alumno,
     * devolvera entonces:
     *  - Existe ese numero asociado a un alumno.
     *  - Si no existe ese numero y por lo tanto, tampoco existe un alumno.
     *
     *  Recibe como parametro: Numero
     *  Devuelve:
     *      - alumno: si encuentra el numero
     *      - null: si no encuentra el numero
     * @param numero
     * @return Alumno asociado con el numero dado, o null si no se encuentra ningun alumno
     */
    private Alumno buscarAlumno(int numero)
        {
            for(Alumno alumno : listaAlumnos)
            {
                if (alumno.getNumero() == numero)
                {
                    return alumno;
                }
            }
            return null;
        }
}
