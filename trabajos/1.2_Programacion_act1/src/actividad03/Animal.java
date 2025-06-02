package actividad03;

//Clase abstracta Animal
public abstract class Animal
{
    //Atributos
    protected String nombre;
    protected int edad;
    protected String raza;


    //Constructores
    public Animal()
    {
    }


    public Animal(String nombre, int edad, String raza)
    {
        this.nombre = nombre;
        this.edad = edad;
        this.raza = raza;
    }


    /**
     * Creacion de los metodos abstractos:
     * 		- emitirSonido();
     * 		- realizarTruco();
     */
    public abstract void emitirSonido();
    public abstract void realizarTruco();


    //GETTERS
    public String getNombre()
    {
        return nombre;
    }

    public int getEdad()
    {
        return edad;
    }

    public String getRaza()
    {
        return raza;
    }


    //SETTERS
    public void setNombre()
    {
        this.nombre = nombre;
    }

    public void setEdad()
    {
        this.edad = edad;
    }

    public void setRaza()
    {
        this.raza = raza;
    }
}
