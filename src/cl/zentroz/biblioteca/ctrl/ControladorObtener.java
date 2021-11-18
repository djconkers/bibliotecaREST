package cl.zentroz.biblioteca.ctrl;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import cl.zentroz.biblioteca.dao.DAO;
import cl.zentroz.biblioteca.dto.Entidad;
import cl.zentroz.biblioteca.dto.MensajeError;
import cl.zentroz.biblioteca.srv.Servicio;

public interface ControladorObtener <P extends Entidad> extends ControladorBasico<P>
{	
	@GetMapping(value = "/{id}")
	default ResponseEntity<Object> obtener(
			@RequestHeader(required = false, name = "Authorization") String token, 
			@PathVariable Long id)
	{
		try {
			Servicio<? extends DAO<P>,P> servicio = getServicio();
			P entidad = servicio.obtener(token, id);
			if (null != entidad) 
			{
				return new ResponseEntity<>(entidad, HttpStatus.OK);
			}
			else 
			{
				MensajeError msj = new MensajeError("E-CTRL-OBTENER-001", this.obtieneClaseEntidad().getSimpleName() + " no existe", true);
				return new ResponseEntity<>(msj, HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			MensajeError msj = new MensajeError("E-CTRL-OBTENER-000", "Ocurrió un error en obtener", false, e);
			return new ResponseEntity<>(msj, HttpStatus.BAD_REQUEST);
		}
	}

}
