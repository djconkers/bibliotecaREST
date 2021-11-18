package cl.zentroz.biblioteca.ctrl;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import cl.zentroz.biblioteca.dao.DAO;
import cl.zentroz.biblioteca.dto.Entidad;
import cl.zentroz.biblioteca.dto.MensajeError;
import cl.zentroz.biblioteca.dto.RespuestaPaginada;
import cl.zentroz.biblioteca.srv.Servicio;
import cl.zentroz.biblioteca.util.Constantes;

public interface ControladorListar <P extends Entidad> extends ControladorBasico<P>
{
	@GetMapping(value = "")
	default ResponseEntity<Object>  listar(
			@RequestHeader(required = false, name = Constantes.AUTORIZACION_HEADER) String token, 
			@RequestParam(required = false, defaultValue = "1") int pagina,
			@RequestParam(required = false, defaultValue = "25") int numreg,
			@RequestParam(required = false, defaultValue = "") String orden,
			@RequestParam(required = false, defaultValue = "true") boolean ordenDecendente, 
			P entidad)
	{
		try 
		{
			Servicio<? extends DAO<P>,P> servicio = getServicio();
			RespuestaPaginada respuesta = servicio.listar(token, pagina, numreg, orden, ordenDecendente, entidad);
			if (null != respuesta.getResultado()) {
				
				return new ResponseEntity<>(respuesta, HttpStatus.OK);
			}
			else {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
		} catch (Exception e) {
			MensajeError msj = new MensajeError("E-CTRL-LISTAR-000", "Ocurrió un error en listar", false, e);
			return new ResponseEntity<>(msj, HttpStatus.BAD_REQUEST);
		}
	}
	
}
