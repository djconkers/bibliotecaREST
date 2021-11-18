package cl.zentroz.biblioteca.srv;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.function.ToLongFunction;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import cl.zentroz.biblioteca.dao.DAO;
import cl.zentroz.biblioteca.dto.Entidad;
import cl.zentroz.biblioteca.dto.Paginacion;
import cl.zentroz.biblioteca.dto.RespuestaPaginada;
import cl.zentroz.biblioteca.excepciones.ExcepcionBase;



public abstract class Servicio<C extends DAO<P>, P extends Entidad>
{
	private static final Logger LOGGER = Logger.getLogger(Servicio.class);

	@Autowired
	protected C dao;
	
	private static final String YA_EXISTE = " ya existe";

	// ------------------------------------------------------------------------------
	// ---------- Métodos públicos --------------------------------------------------
	// ------------------------------------------------------------------------------
	/**
	 * Para listar datos de forma paginada.
	 * 
	 * @param token token para realizar alguna validación en caso de sobrescritura de este método.
	 * @param pagina número de página.
	 * @param numreg numero de registros por página.
	 * @param orden campo para ordenar la lista.
	 * @param ordenDecendente indicador de orden descendente o ascendente segun el parametro orden o usando el orden por defecto.
	 * @param entidad entidad con datos para realizar algún filtrado de información.
	 * @return
	 */
	public RespuestaPaginada listar(String token, int pagina, int numreg, String orden, boolean ordenDecendente, P entidad)
	{
		return listar(token, pagina, numreg, orden, ordenDecendente, entidad, e -> dao.cantidadRegistros(e), r -> dao.listar(r));
	}

	/**
	 * Para listar datos de forma paginada.
	 * 
	 * @param token token para realizar alguna validación en caso de sobrescritura de este método.
	 * @param pagina número de página.
	 * @param numreg numero de registros por página.
	 * @param orden campo para ordenar la lista.
	 * @param ordenDecendente indicador de orden descendente o ascendente segun el parametro orden o usando el orden por defecto.
	 * @param entidad entidad con datos para realizar algún filtrado de información.
	 * @param numRegistros funcion para la cantidad de registros.
	 * @param resultado funncion para el resultado del DAO.
	 * @return
	 */
	protected RespuestaPaginada listar(String token, int pagina, int numreg, String orden, boolean ordenDecendente, P entidad, ToLongFunction<P> numRegistros, Function<P, List<P>> resultado)
	{
		Paginacion paginacion = null;
		if (pagina > 0) {
			int desplazar = (pagina - 1) * numreg + 1;
			int limite = pagina * numreg;
			long cantidadRegistros = numRegistros.applyAsLong(entidad);
			List<String> columnas = new ArrayList<>();
			if (!orden.isEmpty())
				columnas = Arrays.asList(orden.split(","));
			paginacion = new Paginacion(desplazar, limite, numreg, cantidadRegistros, columnas, ordenDecendente);
			if(LOGGER.isTraceEnabled())
				LOGGER.trace("Se aplica paginar busqueda " + paginacion + "");
		} else {
			paginacion = null;
		}
		entidad.setPaginacion(paginacion);
		List<P> listaE = resultado.apply(entidad);
		RespuestaPaginada respuesta = new RespuestaPaginada();
		respuesta.setResultado(listaE);
		respuesta.setPaginacion(paginacion);
		return respuesta;
	}

	/**
	 * Para obtner solo una entidad según su identificador.
	 * 
	 * @param token token para realizar alguna validación en caso de sobrescritura de este método.
	 * @param id identificador de la entidad.
	 * @return
	 */
	public P obtener(String token, Long id)
	{
		P entidad = crearEntidad();
		entidad.setIdIncremental(id);
		entidad = dao.obtener(entidad);
		return entidad;
	}

	/**
	 * Para agregar una entidad a algún repositorio de datos.
	 * 
	 * @param token token para realizar alguna validación en caso de sobrescritura de este método.
	 * @param entidad entidad a agregar. 
	 * @return
	 */
	public P agregar(String token, P entidad)
	{
		return dao.agregar(entidad);
	}

	/**
	 * Para eliminar una entidad según su identificador.
	 * 
	 * @param token token para realizar alguna validación en caso de sobrescritura de este método.
	 * @param id
	 * @return
	 */
	public P eliminar(String token, Long id)
	{
		P entidad = crearEntidad();
		entidad.setIdIncremental(id);
		if (dao.eliminar(entidad) > 0)
			return entidad;
		return null;
	}

	/**
	 * Para modificar una entidad con la entidad ingresada en el parámetro, debe tener un identificador para hacer el reemplazo.
	 * 
	 * @param token token para realizar alguna validación en caso de sobrescritura de este método.
	 * @param entidad
	 * @return
	 */
	public P modificar(String token, P entidad)
	{
		if (validarDuplicados(entidad)) {
			throw new RuntimeException("E-CTRL-MODIFICAR-002" + this.obtieneClaseEntidad().getSimpleName() + YA_EXISTE);
		}
		if (dao.modificar(entidad) > 0)
			return entidad;
		return null;
	}

	
	// ------------------------------------------------------------------------------
	// ---------- Métodos protegidos ------------------------------------------------
	// ------------------------------------------------------------------------------
	/**
	 * Para sobreescribir en caso que se requiera validar duplicidad de entidad al agregar o modificar contra algún repositorio de datos como puede ser una base de datos.
	 * 
	 * @param entidad entidad a validad para verificar duplicado con algún repositorio de datos.
	 * @return <code>true</code> en caso que sea un duplicado, <code>false</code> en otro caso (defecto).
	 */
	protected boolean validarDuplicados(P entidad) 
	{
		return false;
	}

	/**
	 * Para uso en metodos de agregar/modificar y eliminar y obtener el nombre de la entidad segun el genérico de la implementación de esta clase.
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected final Class<P> obtieneClaseEntidad()
	{
		return (Class<P>) ((ParameterizedType) Servicio.this.getClass().getGenericSuperclass()).getActualTypeArguments()[1];
	}

	/**
	 * Crea una crear una entidad usando el contructor vacío del tipo según el genérico de la implementación de esta clase.
	 * 
	 * @return
	 */
	protected P crearEntidad()
	{
		Class<P> clase = obtieneClaseEntidad();
		try {
			return clase.getConstructor().newInstance();
		} catch (InstantiationException e) {
			throw new ExcepcionBase("No se puede instanciar la clase: ", e);
		} catch (IllegalAccessException e) {
			throw new ExcepcionBase("Constructor de la clase no es accesible:", e);
		} catch (IllegalArgumentException e) {
			throw new ExcepcionBase("Los argumentos del constructor no son correctos:", e);
		} catch (InvocationTargetException e) {
			throw new ExcepcionBase("No se logro invocar la clase:", e);
		} catch (NoSuchMethodException e) {
			throw new ExcepcionBase("El metodo de la clase no existe:", e);
		} catch (SecurityException e) {
			throw new ExcepcionBase("Error de Seguridad:", e);
		}
	}	

}
