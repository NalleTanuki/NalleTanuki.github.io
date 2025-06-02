
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

public class BancoChistes
{
    private Chiste[] chistes;
    private int ultimoChiste;
    private final int MAX_CHISTES = 20;
    
    Persistencia persistencia = new Persistencia();
    
    public BancoChistes()
    {
        chistes = new Chiste[MAX_CHISTES];
        for (int i = 0; i < MAX_CHISTES; i++) //Faltaba inicializar cada elemento de la matriz
        {
        	chistes[i] = new Chiste("");
        }
       ultimoChiste = persistencia.cargarChistes(chistes);      
    }
    
    public int getUltimoChiste()
    {
    	return this.ultimoChiste;
    }
    
    public Chiste getChiste(int i)
    {
    	return chistes[i];
    }


    public boolean hayAlgunChisteSinLeer()
    {
        boolean encontrado = false;
        for (int i = 0; i <= ultimoChiste && !encontrado ; i++)
        {
            if (!chistes[i].isLeido())
            {
                encontrado = true;
            }
        }
        return encontrado;
    }

    

    public static int aleatorio(int minimo, int maximo){
        Random numAleatorio = new Random();
        return numAleatorio.nextInt(maximo-minimo+1) + minimo;
    }
    
    
    /**  Devuelve un chiste no leído al azar 
         PRE: Debe existir por lo menos un chiste sin leer
         @return el chiste encontrado 
    */
    public Chiste obtenerChisteAleatorio()
    {
         int iAleatorio = aleatorio(0, ultimoChiste);
         
         Chiste chisteAleatorio = chistes[iAleatorio];
         
         while ( chisteAleatorio.isLeido() )
         {
            iAleatorio = aleatorio(0, ultimoChiste);             
            chisteAleatorio = chistes[iAleatorio];
         }
         return chisteAleatorio;
    }

    
    /** Intenta añadir un chiste en el banco de chistes. Devuelve true si lo consigue, o false si no queda espacio libre
     * @return true si puede añadir el nuevo chiste, false en caso contrario (no queda espacio libre)
     */
    public boolean agregarChiste(String contenido)
    {
        boolean exito = false;
        if ( ultimoChiste < (MAX_CHISTES -1) )
        {
            ultimoChiste++;
            chistes[ultimoChiste] = new Chiste(contenido);   
            Persistencia persistencia = new Persistencia(); 
            persistencia.guardarChistes(chistes, ultimoChiste);
            exito = true;            
       } 
        return exito;
    }

    public void borrarChiste(int posicion)
    {
        while (posicion < ultimoChiste)
        {
            chistes[posicion] = chistes[posicion + 1];
            posicion++;
        }
        ultimoChiste--;
        Persistencia persistencia = new Persistencia(); 
        persistencia.guardarChistes(chistes, ultimoChiste);
    }
    

    /* Mejorable, este método debería estar en la clase principal del proyecto */
    public boolean printChisteAleatorio()
    {
        boolean exito = false;
        if ( hayAlgunChisteSinLeer() )
        {
            Chiste chiste = obtenerChisteAleatorio();
            chiste.print();
            exito = true;
        }
        return exito;
    }

}
