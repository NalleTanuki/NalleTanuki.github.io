
import java.util.Scanner;

/* Clase CuentaChiste
 */
public class CuentaChiste
{
    
    private BancoChistes bancoChistes;
    private Scanner teclado = new Scanner(System.in);

    /* Método constructor para CuentaChiste
     * y el metodo principal de mi programa,
     * ES EL PRIMERO ARRANCABLE
     */
    public CuentaChiste()
    {

        bancoChistes = new BancoChistes();
        //contarChiste();
        menuOpciones();
    }
    
    private void menuOpciones()
    {
    	int opcion;
    	do
    	{
	    	System.out.println("       \nMenú opciones");
	    	System.out.println("        1. Contar chiste.");
	    	System.out.println("        2. Añadir chiste.");
	    	System.out.println("        3. Eliminar chiste.");
	    	System.out.println("        4. Mostrar todos los chistes.");
	    	System.out.println("        0. Cerrar.");
	    	System.out.print("Opción -> ");
	    	opcion = teclado.nextInt();
	    	teclado.nextLine();
	    	
	    	switch(opcion)
	    	{
	    	case 1:
	    		contarChiste();
	    		break;
	    	
	    	case 2:
	    		anhadirChiste();
	    		break;
	    		
	    	case 3:
	    		borrarChiste();
	    		break;
	    		
	    	case 4:
	    		mostrarChistes();
	    		break;
	    		
	    	case 0:
	    		System.out.println("\n\nFin del programa.");
	    		teclado.close();
	    		break;
	    		
	    		default:
	        		System.out.println("\n\nOpción incorrecta.");
	    	}
    	
       } while(opcion != 0);
    }


    public void contarChiste()
    {
        if (bancoChistes.hayAlgunChisteSinLeer())
        {
            Chiste chisteAleatorio = bancoChistes.obtenerChisteAleatorio();
            chisteAleatorio.setLeido(true);
            System.out.println("\n" + chisteAleatorio.getContenido());
        } else
        {
            System.out.println("\nYa se han leído todos los chistes.");
        }
    }
    

    public void mostrarChistes()
    {
        for (int i = 0; i <= bancoChistes.getUltimoChiste(); i++)
        {
            if (!bancoChistes.getChiste(i).isLeido())
            {
                System.out.println(i + "  " + bancoChistes.getChiste(i).getContenido());
            }
        }
    }

    public  void anhadirChiste()
    {
        System.out.println("\nEscribe un nuevo chiste:");
        String nuevoChiste = teclado.nextLine();
        
        if (bancoChistes.agregarChiste(nuevoChiste))
        {
        		System.out.print("\nSe ha añadido correctamente.");
        }else {
        	System.out.print("\nError, no hay capacidad para añadir más.");
        }
        }

    public void borrarChiste()
    {
        System.out.println("\n\n¿Cuál desea eliminar?");
        int posicionChiste = teclado.nextInt();
        bancoChistes.borrarChiste(posicionChiste);
        System.out.println("\nEl chiste ha sido eliminado.");
    }



}
