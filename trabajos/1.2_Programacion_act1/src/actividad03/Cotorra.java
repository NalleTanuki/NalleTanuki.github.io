package actividad03;

	//Clase Cotorra que hereda de la clase Animal
	public class Cotorra extends Animal
	{
	    //Atributos
	    private double numPlumas;

	    //Constructores
	    public Cotorra()
	    {
	    }
	    
	    public Cotorra(String nombre, int edad, String raza, double numPlumas)
	    {
	        super(nombre, edad, raza);
	        this.numPlumas = numPlumas;
	    }

	    /**
	     * Implementacion del metodo abstracto emitirSonido en la clase Cotorra
	     */
	    public void emitirSonido()
	    {
	        System.out.println("\nLa cotorra " + getNombre() + " con una edad de " + getEdad() + " anho/s y de la raza " + getRaza() + " emite el sonido: \n\t¡Aaaaaargh!");
	    }

	    /**
	     * Implementacion del metodo abstracto realizarTruco en la clase Cotorra
	     */
	    public void realizarTruco()
	    {
	        System.out.println("La cotorra " + getNombre() + " con una edad de " + getEdad() + " anho/s y de la raza " + getRaza() + " realiza el truco: \n\tHablar y cotorrear.");
	    }
	}
