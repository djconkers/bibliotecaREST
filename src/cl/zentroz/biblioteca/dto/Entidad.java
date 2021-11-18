package cl.zentroz.biblioteca.dto;

public abstract class Entidad
{
	private Long idIncremental;
	private Paginacion paginacion;

	public Entidad(Long idIncremental)
	{
		this.idIncremental = idIncremental;
		this.paginacion = null;
	}

	public Long getIdIncremental()
	{
		return idIncremental;
	}

	public void setIdIncremental(Long idIncremental)
	{
		this.idIncremental = idIncremental;
	}

	public Paginacion getPaginacion()
	{
		return paginacion;
	}

	public void setPaginacion(Paginacion paginacion)
	{
		this.paginacion = paginacion;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idIncremental == null) ? 0 : idIncremental.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Entidad other = (Entidad) obj;
		if (idIncremental == null) {
			if (other.idIncremental != null)
				return false;
		} else if (!idIncremental.equals(other.idIncremental))
			return false;
		return true;
	}

	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("Entidad [idIncremental=");
		builder.append(idIncremental);
		builder.append(", paginacion=");
		builder.append(paginacion);
		builder.append("]");
		return builder.toString();
	}
}