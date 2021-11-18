package cl.zentroz.biblioteca.dto;

import java.util.Map;

import org.apache.log4j.Logger;

/**
 * errorInterno: true (negocio) false (sistema)
 */
public class MensajeError
{
	private String codigo;
	private String descripcion;
	private Boolean errorInterno;
	private Map<String, String> erroresCampos;

	private static final Logger LOGGER = Logger.getLogger(MensajeError.class);

	public MensajeError(String codigo, String descripcion, boolean errorInterno)
	{
		this(codigo, descripcion, errorInterno, null);
	}

	public MensajeError(String codigo, String descripcion, boolean errorInterno, Exception ex)
	{
		this(codigo, descripcion, null, errorInterno, ex);
	}

	public MensajeError(String codigo, String descripcion, Map<String, String> erroresCampo, boolean errorInterno, Exception ex)
	{
		this.codigo = codigo;
		this.descripcion = descripcion;
		this.errorInterno = errorInterno;
		this.setErroresCampos(erroresCampo);
		LOGGER.error("Ha ocurrido un error de " + (errorInterno ? "negocio" : "sistema") + ", codigo=" + codigo + ", descripcion=" + descripcion, ex);
	}

	public String getCodigo()
	{
		return codigo;
	}

	public String getDescripcion()
	{
		return descripcion;
	}

	public void setDescripcion(String descripcion)
	{
		this.descripcion = descripcion;
	}

	public boolean isErrorInterno()
	{
		return errorInterno;
	}

	public Map<String, String> getErroresCampos()
	{
		return erroresCampos;
	}

	public void setErroresCampos(Map<String, String> erroresCampos)
	{
		this.erroresCampos = erroresCampos;
	}
}
