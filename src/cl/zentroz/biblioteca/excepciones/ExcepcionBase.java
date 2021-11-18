package cl.zentroz.biblioteca.excepciones;

public class ExcepcionBase extends RuntimeException 
{
	private static final long serialVersionUID = 1L;
	private String codigo;

	public ExcepcionBase()
	{
		super();
	}
	
	public ExcepcionBase(String mensaje)
	{
		super(mensaje);
	}
	
	public ExcepcionBase(String mensaje, String codigo)
	{
		super(mensaje);
		this.codigo = codigo;
	}
	
	public ExcepcionBase(Throwable excepcion)
	{
		super(excepcion);
	}	
	
	public ExcepcionBase(String mensaje, Throwable excepcion)
	{
		super(mensaje, excepcion);
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
}
