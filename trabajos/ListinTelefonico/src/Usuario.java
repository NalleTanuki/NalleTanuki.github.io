
public class Usuario
{
	private String nombre;
	private String apellidos;
	private int telefono;
	private String correo_electronico;
	private String domicilio;
	private String poblacion;
	private String provincia;
	
    
    public Usuario() {}

    public Usuario(String nombre, String apellidos, int telefono, String correo_electronico, String domicilio, String poblacion, String provincia)
    {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.telefono = telefono;
        this.correo_electronico = correo_electronico;
        this.domicilio = domicilio;
        this.poblacion = poblacion;
        this.provincia = provincia;
    }
    

    public String getNombre()
    {
		return nombre;
	}

	public void setNombre(String nombre)
	{
		this.nombre = nombre;
	}

	public String getApellidos()
	{
		return apellidos;
	}

	public void setApellidos(String apellidos)
	{
		this.apellidos = apellidos;
	}

	public int getTelefono()
	{
		return telefono;
	}

	public void setTelefono(int telefono)
	{
		this.telefono = telefono;
	}

	public String getCorreo_electronico()
	{
		return correo_electronico;
	}

	public void setCorreo_electronico(String correo_electronico)
	{
		this.correo_electronico = correo_electronico;
	}

	public String getDomicilio()
	{
		return domicilio;
	}

	public void setDomicilio(String domicilio)
	{
		this.domicilio = domicilio;
	}

	public String getPoblacion()
	{
		return poblacion;
	}

	public void setPoblacion(String poblacion)
	{
		this.poblacion = poblacion;
	}

	public String getProvincia()
	{
		return provincia;
	}

	public void setProvincia(String provincia)
	{
		this.provincia = provincia;
	}

	@Override
	public String toString() {
		return "Usuario [Nombre: " + nombre + ", Apellidos: " + apellidos + ", Telefono: " + telefono
				+ ", Correo electrónico: " + correo_electronico + ", Domicilio: " + domicilio + ", Poblacion: " + poblacion
				+ ", Provincia: " + provincia + "]";
	}
	
	
}
