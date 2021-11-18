package cl.zentroz.biblioteca.dto;

import java.util.ArrayList;
import java.util.List;

public class Paginacion
{
	private int desplazar;
	private long limite;
	private long numreg;
	private long cantidadRegistros;
	private List<String> orden;
	private boolean ordenDescendente;
	
	public Paginacion()
	{
		this.desplazar = 0;
		this.limite = Long.MAX_VALUE;
		this.numreg = Long.MAX_VALUE;
		this.cantidadRegistros = 0;
		this.orden = new ArrayList<>();
		this.ordenDescendente=true;
	}

	public Paginacion(int desplazar, int limite, int numreg, long cantidadRegistros)
	{
		this.desplazar = desplazar;
		this.limite = limite;
		this.numreg = numreg;
		this.cantidadRegistros = cantidadRegistros;
		this.orden = new ArrayList<>();
		this.ordenDescendente=true;
	}
	
	public Paginacion(int desplazar, int limite, int numreg, long cantidadRegistros,List<String> orden, boolean ordenDescendente)
	{
		this(desplazar,limite,numreg,cantidadRegistros);
		this.orden = orden;
		this.ordenDescendente = ordenDescendente;
	}

	public int getDesplazar()
	{
		return desplazar;
	}

	public void setDesplazar(int desplazar)
	{
		this.desplazar = desplazar;
	}

	public long getLimite()
	{
		return limite;
	}

	public void setLimite(long limite)
	{
		this.limite = limite;
	}

	public long getNumreg()
	{
		return numreg;
	}

	public void setNumreg(long numreg)
	{
		this.numreg = numreg;
	}

	public long getCantidadRegistros()
	{
		return cantidadRegistros;
	}

	public void setCantidadRegistros(long cantidadRegistros)
	{
		this.cantidadRegistros = cantidadRegistros;
	}

	public List<String> getOrden() {
		return orden;
	}

	public void setOrden(List<String> orden) {
		this.orden = orden;
	}

	public boolean isOrdenDescendente() {
		return ordenDescendente;
	}

	public void setOrdenDescendente(boolean ordenDescendente) {
		this.ordenDescendente = ordenDescendente;
	}

	@Override
	public String toString() {
		return "Paginacion [desplazar=" + desplazar + ", limite=" + limite + ", numreg=" + numreg
				+ ", cantidadRegistros=" + cantidadRegistros + ", orden=" + orden + ", ordenDescendente="
				+ ordenDescendente + "]";
	}	
	
}