package actividad03;

//Clase Perro que hereda de la clase Animal
public class Perro extends Animal
{
  //Atributos de perro
  private double numPelos;

  //Constructores
  public Perro()
  {  
  }
  
  public Perro(String nombre, int edad, String raza, double numPelos)
  {
      super(nombre, edad, raza);
      this.numPelos = numPelos;
  }


  /**
   * Implementacion del metodo abstracto emitirSonido en la clase Perro
   */
  public void emitirSonido()
  {
      System.out.println("\nEl perro " + getNombre() + " con una edad de " + getEdad() + " anho/s y de la raza " + getRaza() + " emite el sonido: \n\t¡Guau, guau!");
  }

  /**
   * Implementacion del metodo abstracto realizarTruco en la clase Perro
   */
  public void realizarTruco()
  {
      System.out.println("El perro " + getNombre() + " con una edad de " + getEdad() + " anho/s y de la raza " + getRaza() + " realiza el truco: \n\tDar la pata.");
  }
}
