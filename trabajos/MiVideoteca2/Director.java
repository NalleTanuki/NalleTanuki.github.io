public class Director extends Artista
{
    public Director()
    {
    }
    
    public Director(String nombre, String paisNacimiento, String nacionalidad, int edad, boolean vive)
    {
        this.nombre = nombre;
        this.paisNacimiento = paisNacimiento;
        this.nacionalidad = nacionalidad;
        this.edad = edad;
        this.vive = vive;
    }
    
    public void print()
    {
        System.out.println("Nombre: " + this.nombre);
        System.out.println("País de nacimiento: " + this.paisNacimiento);
        System.out.println("Nacionalidad: " + this.nacionalidad);
        System.out.println("Edad: " + this.edad);
        System.out.println("¿Vive?: " + this.vive);
    }


    public void setNombre(String nombre)
    {
        this.nombre = nombre;
    }

    public void setPaisNacimiento(String paisNacimiento)
    {
        this.paisNacimiento = paisNacimiento;
    }

    public void setNacionalidad(String nacionalidad)
    {
        this.nacionalidad = nacionalidad;
    }

    public void setEdad(int edad)
    {
        this.edad = edad;
    }

    public void setVive(boolean vive)
    {
        this.vive = vive;
    }

    public String getNombre()
    {
        return nombre;
    }

}
