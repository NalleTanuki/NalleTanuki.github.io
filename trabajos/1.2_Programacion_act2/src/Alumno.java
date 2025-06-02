public class Alumno
{
    //Atributos
    protected int numero;
    protected String nombre;
    protected String curso;


    //Constructores
    public Alumno() {}

    public Alumno(int numero, String nombre, String curso)
    {
        this.numero = numero;
        this.nombre = nombre;
        this.curso = curso;
    }

    /**
     * Devuelve una cadena del objeto Alumno
     * @return una cadena del objeto Alumno: numero, nombre y curso.
     */

    public String toString()
    {
        return "Número: " + numero + ", Nombre: " + nombre + ", Curso: " + curso;
    }

    //GETTERS
    public int getNumero()
    {
        return numero;
    }

    public String getNombre()
    {
        return nombre;
    }

    public String getCurso()
    {
        return curso;
    }


    //SETTERS - Aunque no se usen en este programa

    public void setNumero(int numero)
    {
        this.numero = numero;
    }

    public void setNombre(String nombre)
    {
        this.nombre = nombre;
    }

    public void setCurso(String curso)
    {
        this.curso = curso;
    }
}
