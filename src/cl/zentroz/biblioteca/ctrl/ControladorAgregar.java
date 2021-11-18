package cl.zentroz.biblioteca.ctrl;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import cl.zentroz.biblioteca.dao.DAO;
import cl.zentroz.biblioteca.dto.Entidad;
import cl.zentroz.biblioteca.dto.MensajeError;
import cl.zentroz.biblioteca.srv.Servicio;
import cl.zentroz.biblioteca.util.Constantes;

public interface ControladorAgregar<P extends Entidad> extends ControladorBasico<P> {
	
	@PostMapping(value = "/agregar")
	default ResponseEntity<Object> agregar(
			 @RequestHeader(required = false, name = Constantes.AUTORIZACION_HEADER) String token,
			 @RequestBody P entidad)
	{
		try {
			Servicio<? extends DAO<P>,P> servicio = getServicio();
			P e = servicio.agregar(token, entidad);
			if (e.getIdIncremental() > -1) {
				return new ResponseEntity<>(e, HttpStatus.OK);
			}
			else {
				MensajeError msj = new MensajeError("E-CTRL-AGREGAR-001", this.obtieneClaseEntidad().getSimpleName() + " no existe", true);
				return new ResponseEntity<>(msj, HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			MensajeError msj = new MensajeError("E-CTRL-AGREGAR-000", "Ocurrió un error en agregar", false, e);
			return new ResponseEntity<>(msj, HttpStatus.BAD_REQUEST);
		}
	}
}
