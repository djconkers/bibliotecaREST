package cl.zentroz.biblioteca.dto;

public class Conexion
{
	private String cliente;
	private String jndi;
	private String driver;
	private String dominio;

	public String getCliente()
	{
		return cliente;
	}

	public void setCliente(String cliente)
	{
		this.cliente = cliente;
	}

	public String getJndi()
	{
		return jndi;
	}

	public void setJndi(String jndi)
	{
		this.jndi = jndi;
	}

	public String getDriver()
	{
		return driver;
	}

	public void setDriver(String driver)
	{
		this.driver = driver;
	}

	public String getDominio()
	{
		return dominio;
	}

	public void setDominio(String dominio)
	{
		this.dominio = dominio;
	}
}