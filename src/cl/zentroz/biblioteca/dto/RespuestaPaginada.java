package cl.zentroz.biblioteca.dto;

import java.util.List;

public class RespuestaPaginada 
{
	private List<? extends Entidad> resultado;
	private Paginacion paginacion;

	public List<? extends Entidad> getResultado()
	{
		return resultado;
	}

	public void setResultado(List<? extends Entidad> resultado)
	{
		this.resultado = resultado;
	}

	public Paginacion getPaginacion()
	{
		return paginacion;
	}

	public void setPaginacion(Paginacion paginacion)
	{
		this.paginacion = paginacion;
	}
	
}
