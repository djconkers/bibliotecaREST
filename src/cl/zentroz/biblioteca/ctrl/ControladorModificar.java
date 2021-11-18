package cl.zentroz.biblioteca.ctrl;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import cl.zentroz.biblioteca.dao.DAO;
import cl.zentroz.biblioteca.dto.Entidad;
import cl.zentroz.biblioteca.dto.MensajeError;
import cl.zentroz.biblioteca.srv.Servicio;

public interface ControladorModificar <P extends Entidad> extends ControladorBasico<P>
{

	@PutMapping(value = "/modificar/{id}")
	default ResponseEntity<Object> modificar(
			@RequestHeader(required = false, name = "Authorization") String token, 
			@PathVariable Long id, 
			@RequestBody P entidad)
	{
		try {
			if (id.compareTo(entidad.getIdIncremental()) != 0) {
				MensajeError msj = new MensajeError("E-CTRL-MODIFICAR-003", "El idIncremental  [" + entidad.getIdIncremental() + "] no es consistente con la variable url [" + id + "] ", true);
				return new ResponseEntity<>(msj, HttpStatus.CONFLICT);
			}
			Servicio<? extends DAO<P>,P> servicio = getServicio();
			entidad = servicio.modificar(token,entidad);
			if (entidad!=null) {
				return new ResponseEntity<>(entidad, HttpStatus.OK);
			}
			else 
			{
				MensajeError msj = new MensajeError("E-CTRL-MODIFICAR-001", this.obtieneClaseEntidad().getSimpleName() + " no existe", true);
				return new ResponseEntity<>(msj, HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			MensajeError msj = new MensajeError("E-CTRL-MODIFICAR-000", "Ocurrió un error en modificar", false, e);
			return new ResponseEntity<>(msj, HttpStatus.BAD_REQUEST);
		}
	}
}
