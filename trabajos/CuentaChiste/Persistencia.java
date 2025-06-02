import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Persistencia
{
	private String ruta = "chistes.dat";
	
	public Persistencia(String urlFichero)
	{
		this.ruta = urlFichero;
	}
	
	public Persistencia() {}
	
	
    //Carga los chistes desde el fichero en el array
    public int cargarChistes(Chiste[] chistes)
    {
    	String linea = new String();
        
        int i = 0;
        
        try
        {
	    	FileReader lector = new FileReader(ruta);
	    	BufferedReader br = new BufferedReader(lector);
	    	
	    	//Para leer linea a linea, TODO el fichero hasta llegar a null
	    	linea = br.readLine();
	    	while(linea != null)
	    	{
	    		chistes[i].setContenido (linea);
	    		chistes[i].setLeido(false);
	    		i++;
	    		//System.out.println(linea);
	    		
		    	linea = br.readLine();
	    	}
	    	lector.close();
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
        return i-1;
    }
    
    public void guardarChistes(Chiste[] chistes, int ultimoChiste)
    {
    	int i = 0;
    	
    	String linea = "";
    	
    	FileWriter escritor = null;
    	try
    	{
//    		File fichero = new File(ruta);
    		escritor = new FileWriter(ruta);
    		for(i = 0; i <= ultimoChiste; i++)
    		{
    			escritor.write(chistes[i].getContenido());
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
    	finally
    	{
    		
    	}
    }
    
}
