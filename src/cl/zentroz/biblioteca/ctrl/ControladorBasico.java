package cl.zentroz.biblioteca.ctrl;

import java.lang.reflect.ParameterizedType;

import cl.zentroz.biblioteca.dao.DAO;
import cl.zentroz.biblioteca.dto.Entidad;
import cl.zentroz.biblioteca.srv.Servicio;

public interface ControladorBasico <P extends Entidad>
{
	/**
	 * Para obtener el servicio que se usará en la implementación de esta clase.
	 * 
	 * @return
	 */
	Servicio<? extends DAO<P>, P> getServicio();
	
	/**
	 * Para obtener la clase de la entidad usando el genérico de la implementación de esta clase.
	 * @return
	 */
	@SuppressWarnings("unchecked")
	default Class<P> obtieneClaseEntidad()
	{
		return (Class<P>) ((ParameterizedType) ControladorBasico.this.getClass().getGenericInterfaces()[0]).getActualTypeArguments()[0];
	}
}
