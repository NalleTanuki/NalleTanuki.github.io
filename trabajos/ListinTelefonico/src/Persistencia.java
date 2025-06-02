import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

public class Persistencia
{
private String ruta = "ListinTelefonico.dat";
	
	public Persistencia(String urlFichero)
	{
		this.ruta = urlFichero;
	}
	
	public Persistencia() {}

	public void guardarUsuarios(Usuario[] usuarios, int ultimoUsuario)
	{
		int i = 0;
    	String linea = "";
    	
    	FileWriter escritor = null;
    	try
    	{
    		escritor = new FileWriter(ruta);
    		for(i = 0; i <= ultimoUsuario; i++)
    		{
    			escritor.write(usuarios[i].getNombre());
    		}
    		escritor.close();
    	}
    	catch(FileNotFoundException e)
    	{
    		System.out.println("\n\nError en guardarChistes(Chiste[]): El fichero no existe. Descricpción del error:\n" + e.getMessage());
    	}
    	catch(IOException e)
    	{
    		System.out.println("\n\nError. No se puede acceder al fichero.");
    	}
    	catch(Exception e)
    	{
    		System.out.println("\n\nError no identificado.");
    	}
    }
}