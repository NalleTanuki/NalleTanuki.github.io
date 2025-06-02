
public class Pelicula
{
    Director director[];
    Actor actores[];
    String   tituloOriginal;
    String   argumento;
    String   idioma;
    String   genero;
    String   nacionalidad;
    int      anhoEstreno;
    float   duracion;

    public Pelicula(){}
    public Pelicula(String tituloOriginal,String argumento, String idioma, String genero, String nacionalidad, int anhoEstreno, float duracion, Director director[], Actor actores[])
    {
        this.director = director;
        this.actores = actores;
        this.tituloOriginal = tituloOriginal;
        this.argumento = argumento;
        this.idioma = idioma;
        this.genero = genero;
        this.nacionalidad = nacionalidad;
        this.anhoEstreno = anhoEstreno;
        this.duracion = duracion;
    }
        
        
    public Pelicula(String tituloOriginal,String argumento, String idioma, String genero, String nacionalidad, int anhoEstreno, float duracion)
    {
        this.tituloOriginal = tituloOriginal;
        this.argumento = argumento;
        this.idioma = idioma;
        this.genero = genero;
        this.nacionalidad = nacionalidad;
        this.anhoEstreno = anhoEstreno;
        this.duracion = duracion;
    }


    public void setDirector(Director director)
    {
        this.director[0] = director;
    }

    public void print()
    {
        System.out.println("Título original: " + this.tituloOriginal);
        System.out.println("Año de estreno: " + this.anhoEstreno);
        System.out.println("Nacionalidad: " + this.nacionalidad);
        System.out.println("Dirigida por: " + this.director[0].getNombre());
        System.out.println("Protagonizada por: " + this.actores[0].getNombreArtistico());
    }

}
