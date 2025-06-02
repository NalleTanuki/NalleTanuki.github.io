public class Actor extends Artista {
    String nombreArtistico;

    public Actor() {
    }

    public Actor(String nombre, String paisNacimiento, String nacionalidad,
                 int edad, boolean vive, String nombreArtistico) {
        this.nombre = nombre;
        this.nombreArtistico = nombreArtistico;
        this.paisNacimiento = paisNacimiento;
        this.nacionalidad = nacionalidad;
        this.edad = edad;
        this.vive = vive;
    }

    public void print() {
        System.out.println("Nombre: " + this.nombre);
        System.out.println("Nombre artístico: " + this.nombreArtistico);
        System.out.println("País de nacimiento: " + this.paisNacimiento);
        System.out.println("Nacionalidad: " + this.nacionalidad);
        System.out.println("Edad: " + this.edad);
        System.out.println("¿Vive?: " + this.vive);
    }

    public void setNombreArtistico(String nombreArtistico) {
        this.nombreArtistico = nombreArtistico;
    }

    public String getNombreArtistico()
    {
        return nombreArtistico;
    }
}
