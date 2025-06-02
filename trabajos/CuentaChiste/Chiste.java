public class Chiste {
    private String contenido;
    private boolean leido;

    public Chiste(String contenido)
    {
        this.contenido = contenido;
        this.leido = false;
    }
    

    public String getContenido()
    {
        return contenido + "\n";
    }
    

    public boolean isLeido()
    {
        return leido;
    }
    

    public void setLeido(boolean leido)
    {
        this.leido = leido;
    }
    
    
    public void print() 
    { 
        System.out.println(this.contenido);
    }
    

	public void setContenido(String contenido) {
		this.contenido = contenido;
	}
    
}