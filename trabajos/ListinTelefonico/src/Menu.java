import java.util.InputMismatchException;
import java.util.Scanner;

import com.db4o.ObjectSet;

import Tema13_BDOO.Alumno;

public class Menu
{
	private BancoDatosUsuarios bancoDatosUsuarios;
	private Scanner teclado = new Scanner(System.in);
	
	public Menu()
    {
        bancoDatosUsuarios = new BancoDatosUsuarios();
        menuOpciones();
    }
	
	
	public void menuOpciones()
	{
		int opc;
		
		try
		{
			do
			{
				System.out.println("\n" + "        Menú opciones");
		    	System.out.println("        1. Añadir nuevo cliente.");
		    	System.out.println("        2. Eliminar un cliente.");
		    	System.out.println("        3. Mostrar todos los clientes.");
		    	System.out.println("        4. Buscar un cliente.");
		    	System.out.println("        0. Salir del programa.");
		    	System.out.print("          \nIngrese una opción -> ");
		    	opc = teclado.nextInt();
		    	teclado.nextLine();
		    	
		    	switch(opc)
		    	{
			    	case 1:
			    		anhadirUsuario();
			    		break;
			    	case 2:
			    		bancoDatosUsuarios.eliminarUsuario();
			    		break;
			    	case 3:
			    		bancoDatosUsuarios.mostrarUsuarios();
			    		break;
			    	case 4:
			    		bancoDatosUsuarios.buscarCliente();
			    		break;
			    	case 0:
			    		System.out.println("\n\nFin del programa.");
			    		teclado.close();
			    		break;
			    		
			    	default:
			        	System.out.println("\n\nOpción incorrecta.");
		    	}
			}
			while(opc != 0);
		}
		catch(InputMismatchException e)
		{
			teclado.next();
			System.out.println("\n\nNo has introducido un número");
		}
	}
	
	
    public  void anhadirUsuario()
    {
        System.out.print("\nNombre: ");
        String nombre = teclado.nextLine();
        
        System.out.print("\nApellidos: ");
        String apellidos = teclado.nextLine();
        
        System.out.print("\nTeléfono: ");
        int telefono = teclado.nextInt();
        teclado.nextLine();
        
        System.out.print("\nCorreo electrónico: ");
        String correo_electronico = teclado.nextLine();
        
        System.out.print("\nDomicilio: ");
        String domicilio = teclado.nextLine();
        
        System.out.print("\nPoblación: ");
        String poblacion = teclado.nextLine();
        
        System.out.print("\nProvincia: ");
        String provincia = teclado.nextLine();
        
        if (bancoDatosUsuarios.agregarUsuario(nombre, apellidos, telefono, correo_electronico, domicilio, poblacion, provincia))
        {
        		System.out.print("\nEl cliente ha sido añadido correctamente.");
        }else {
        	System.out.print("\nError, no hay capacidad para añadir más clientes.");
        }
    }

}
