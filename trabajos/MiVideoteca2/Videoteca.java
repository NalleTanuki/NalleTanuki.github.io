import java.util.Scanner;

public class Videoteca
{
    Scanner teclado = new Scanner(System.in);
    
    Director[] directores= new Director [20];
    Actor[] actores = new Actor [20];
    Pelicula[] peliculas = new Pelicula [100];
    int contadorDirectores, contadorActores, contadorPeliculas = 0;

    public Videoteca()
    {
        inicializar();
    }
    
        private void inicializar()
    {
        for(int i = 0; i <= directores.length; i++)
        {
            directores[i] = new Director();
            //directores[i].nombre = null;
            //actores[i].nombre = null;
        }

        for(int i = 0; i < actores.length; i++)
        {
            actores[i] = new Actor();
        }
        
        for(int i = 0; i <= peliculas.length; i++)
        {
            peliculas[i] = new Pelicula();
            //peliculas[i].tituloOriginal = "";
        }
        
        Director dir = new Director ("Peter", "EEUU", "estadounidense",56, true);
        Actor act1 = new Actor ("Manolo", "Inglaterra", "inglesa", 34, true, "Manolito");
        Actor act2 = new Actor ("Maria", "Inglaterra", "inglesa", 40, false, "Maria de la O");
        
        Director [] miDirector = new Director[1];
        miDirector[0] = dir;
        Actor [] misActores = new Actor[2]; 
        misActores[0] = act1;
        misActores[1] = act2;
         
        
        Pelicula pel1 = new Pelicula("Brain Dead","Zombies por enfermedad", "Ingles",
                    "Serie B", "estadounidense", 1990,
                    (float) 1.36, miDirector, misActores);
        
        
        Pelicula pel2 = new Pelicula("Clowns","Zombies por enfermedad", "Ingles",
                    "Serie B", "estadounidense", 1990,
                    (float) 1.36, miDirector, misActores);
        
    }

    
    
    Actor buscarActor(){
		System.out.print("Nombre del actor a buscar........  ");
		String nombre = teclado.nextLine();
		Actor actor = null;
		boolean encontrado = false;
		int i = 0;
		while (!encontrado && i < (actores.length))
		{
			if (actores[i].nombreArtistico.equalsIgnoreCase(nombre))
			{
				encontrado = true;
			    actor = actores[i];
			}
			i++;
		}
		return actor;
	};
    
    public void  addActores(Pelicula pelicula)
    {
         int cuantos = 0;
            System.out.print("¿Cuántos actores vamos a añadir? -> ");
            int iActores = 0;
            while (iActores < cuantos)
            {
		Actor actor = buscarActor();
		if (actor != null)
			pelicula.actores[iActores] = actor;
		iActores ++;
            }
            while (iActores < 20)
            {
		pelicula.actores[iActores] = null;
		iActores ++;
	    }
                    
    }


    public void anhadirPelicula(Pelicula pelicula)
    {
        if(contadorPeliculas < 100)
        {
            System.out.print("Nombre de la película: ");
            String tituloOriginal = teclado.nextLine();
            
            System.out.print("Argumento: ");
            String argumento = teclado.nextLine();
            
            System.out.print("Idioma original: ");
            String idioma = teclado.nextLine();
                
            System.out.print("Género: ");
            String genero = teclado.nextLine();
                
            System.out.print("Nacionalidad: ");
            String nacionalidad = teclado.nextLine();
                
            System.out.print("Año de estreno: ");
            int anhoEstreno = teclado.nextInt();
            teclado.nextLine();
                
            System.out.print("Duración: ");
            float duracion = teclado.nextFloat();
            teclado.nextLine();
            
            Pelicula nuevaPelicula = new Pelicula(tituloOriginal,argumento, idioma, genero, nacionalidad, anhoEstreno, duracion);
            addActores(nuevaPelicula);
            addDirectores(nuevaPelicula);
            
                        
            peliculas[contadorPeliculas] = nuevaPelicula;
            contadorPeliculas++;
            System.out.println("Añadido correctamente.");
        }
        else
        {
            System.out.println("La videoteca está llena.");
        }
    }
    
    
    public Actor anhadirActor()
    {
        if (contadorActores < 20)
        {
            System.out.print("Nombre: ");
            String nombre = teclado.nextLine();

            System.out.print("País de nacimiento: ");
            String paisNacimiento = teclado.nextLine();

            System.out.print("Nacionalidad: ");
            String nacionalidad = teclado.nextLine();

            System.out.print("Edad: ");
            int edad = teclado.nextInt();
            teclado.nextLine();

            System.out.print("¿Vive? (true/false): ");
            boolean vive = teclado.nextBoolean();
            teclado.nextLine();

            System.out.print("Nombre artístico: ");
            String nombreArtistico = teclado.nextLine();

            Actor nuevoActor = new Actor(nombre, paisNacimiento, nacionalidad, edad, vive, nombreArtistico);
            actores[contadorActores] = nuevoActor;
            contadorActores++;
            System.out.println("\nEl actor " + nombre + " ha sido añadido correctamente.");

            return nuevoActor;
        }
        else
        {
            System.out.println("\nNo se puede añadir más actores.");
            return null;
        }
    }


    Director buscarDirector()
    {
        System.out.print("Nombre del director a buscar -> ");
        String nombre = teclado.nextLine();
        Director director = null;
        boolean encontrado = false;
        int i = 0;
        while (!encontrado && i < (directores.length))
        {
            if (directores[i].nombre.equalsIgnoreCase(nombre))
            {
                encontrado = true;
                director = directores[i];
            }
            i++;
        }
        return director;
    }


    public void addDirectores(Pelicula pelicula)
    {
        Director director = buscarDirector();
        if(director != null)
        {
            pelicula.setDirector(director);
        }
        else
        {
            if(contadorDirectores < 20)
            {
                System.out.println("Director no encontrado. Ingrese al nuevo director:");
                Director nuevoDirector = anhadirDirector();
                directores[contadorDirectores] = nuevoDirector;
                contadorDirectores++;
                pelicula.setDirector(nuevoDirector);
            }
            else
            {
                System.out.println("No se pueden añadir más directores.");
            }
        }
    }

    
    public Director anhadirDirector()
    {
        if(contadorDirectores < 20)
        {
            System.out.print("Nombre: ");
            String nombre = teclado.next();
            
            System.out.print("País de Nacimiento: ");
            String paisNacimiento = teclado.next();
            
            System.out.print("Nacionalidad: ");
            String nacionalidad = teclado.next();
            
            System.out.print("Edad: ");
            int edad = teclado.nextInt();
            teclado.nextLine();
            
            System.out.print("¿Vive? (true/false): ");
            boolean vive = teclado.nextBoolean();
            
            Director nuevoDirector = new Director(nombre, paisNacimiento, nacionalidad, edad, vive);
            directores[contadorDirectores] = nuevoDirector;
            contadorDirectores++;
            System.out.println("\nEl director " + nombre + " ha sido añadido correctamente.");

            return nuevoDirector;
        }
        else
        {
            System.out.println("\nNo se puede añadir más directores.");
            return null;
        }
    }
    
    public void borrarPelicula(Pelicula pelicula)
    {
        if(contadorPeliculas == 0)
        {
            System.out.println("\nNo hay películas para borrar.");
        }
        
        listarPeliculas();
        System.out.print("\nSeleccione el número de película que quieres eliminar -> ");
        int numeroPelicula = teclado.nextInt();
        teclado.nextLine();
            
        if(numeroPelicula < 1 || numeroPelicula > contadorPeliculas)
        {
            System.out.println("\nError. Ingresa un número válido.");
        }
        
        int indicePelicula = numeroPelicula - 1;
        Pelicula peliculaAEliminar = peliculas[indicePelicula];
        
        for(int i = indicePelicula; i < contadorPeliculas - 1; i++)
        {
            peliculas[i] = peliculas[i + 1];
        }
        
        peliculas[contadorPeliculas - 1] = null;
        contadorPeliculas--;
        System.out.println("\n La película \"" + peliculaAEliminar.tituloOriginal + "\" ha sido eliminada.");
    }
        
    
    
    public void borrarActor(Actor actor)
    {
        for (int i = 0; i < contadorActores; i++)
        {
            if (actores[i] == actor)
            {
                actores[i] = null;
                
                for (int j = i; j < contadorActores - 1; j++)
                {
                    actores[j] = actores[j + 1];
                }
                
                actores[contadorActores - 1] = null;
                contadorActores--;
                System.out.println("\nEliminado correctamente.");
            }
        }
    }
    
    public void borrarDirector(Director director)
    {
        for (int i = 0; i < contadorDirectores; i++)
        {
            if (directores[i] == director)
            {
                directores[i] = null;

                for (int j = i; j < contadorDirectores - 1; j++)
                {
                    directores[j] = directores[j + 1];
                }
                
                directores[contadorDirectores - 1] = null;
                contadorDirectores--;
                System.out.println("\nEliminado correctamente.");
            }
        }
    }
    
    public void listarPeliculas()
    {
        if(contadorPeliculas == 0)
        {
            System.out.println("\nNo hay películas en la videoteca.");
        }
        else
        {
            System.out.println("\n** Películas en la videoteca: **");
            for(int i = 0; i < contadorPeliculas; i++)
            {
                System.out.println("\n" + (i + 1) + ". " + peliculas[i].tituloOriginal);
            }
        }
    }

    public void listarActores()
    {
        if(contadorActores == 0)
        {
            System.out.println("\nNo hay actores en la videoteca.");
        }
        else
        {
            System.out.println("\n**Actores en la videoteca **");
            for(int i = 0; i < contadorActores; i++)
            {
                System.out.println("\n" + (i + 1) + ". " + actores[i].nombreArtistico);
            }
        }
    }


    public void listarDirectores()
    {
        if(contadorDirectores == 0)
        {
            System.out.println("\nNo hay directores en la videoteca.");
        }
        else
        {
            System.out.println("\n** Directores en la videoteca **");
            for(int i = 0; i < contadorDirectores; i++)
            {
                System.out.println("\n" + (i + 1) + ". " + directores[i].nombre);
            }
        }
    }
}