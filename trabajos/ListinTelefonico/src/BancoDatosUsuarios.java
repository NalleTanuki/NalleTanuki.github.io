
public class BancoDatosUsuarios
{
	private Usuario[] usuarios;
    private int ultimoUsuario;
    private final int MAX_USUARIOS = 500;
    
    Persistencia persistencia = new Persistencia();
    
    public boolean agregarUsuario(String nombre, String apellidos, int telefono, String correo_electronico, String domicilio, String poblacion, String provincia)
    {
    	boolean exito = false;
        if ( ultimoUsuario < (MAX_USUARIOS -1) )
        {
            ultimoUsuario++;
            usuarios[ultimoUsuario] = new Usuario(nombre, apellidos, telefono, correo_electronico, domicilio, poblacion, provincia);   
            Persistencia persistencia = new Persistencia(); 
            persistencia.guardarUsuarios(usuarios, ultimoUsuario);
            exito = true;
       } 
        return exito;
    }
    
    
    public void eliminarUsuario()
    {
    	
    }
    
    
    public void mostrarUsuarios()
    {
        if (ultimoUsuario == 0)
        {
            System.out.println("\nNo hay clientes registrados.");
        }
        else
        {
            System.out.println("\n\nListado de clientes:");
            
            for (int i = 0; i < ultimoUsuario; i++)
            {
                Usuario usuario = usuarios[i];
                
                System.out.println("Nombre: " + usuario.getNombre());
                System.out.println("Apellidos: " + usuario.getApellidos());
                System.out.println("Teléfono: " + usuario.getTelefono());
                System.out.println("Correo electrónico: " + usuario.getCorreo_electronico());
                System.out.println("Domicilio: " + usuario.getDomicilio());
                System.out.println("Población: " + usuario.getPoblacion());
                System.out.println("Provincia: " + usuario.getProvincia());
                System.out.println();
            }
        }
    }
    
    
    public void buscarCliente()
    {
    	
    }
    
}
