package cl.zentroz.biblioteca.ctrl;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import cl.zentroz.biblioteca.dao.DAO;
import cl.zentroz.biblioteca.dto.Entidad;
import cl.zentroz.biblioteca.dto.MensajeError;
import cl.zentroz.biblioteca.srv.Servicio;

public interface ControladorEliminar <P extends Entidad> extends ControladorBasico<P>
{
	@DeleteMapping(value = "/eliminar/{id}")
	default ResponseEntity<Object> eliminar(
			 @RequestHeader(required = false, name = "Authorization") String token, 
			 @PathVariable Long id)
	{
		try 
		{
			Servicio<? extends DAO<P>,P> servicio = getServicio();
			P entidad = servicio.eliminar(token, id);
			if (entidad !=null) {
				
				return new ResponseEntity<>(entidad, HttpStatus.OK);
			}
			else {
				MensajeError msj = new MensajeError("E-CTRL-ELIMINAR-001", this.obtieneClaseEntidad().getSimpleName() + " no existe", true);
				return new ResponseEntity<>(msj, HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			MensajeError msj = new MensajeError("E-CTRL-ELIMINAR-000", "Ocurrió un error en eliminar", false, e);
			return new ResponseEntity<>(msj, HttpStatus.BAD_REQUEST);
		}
	}
	
}
