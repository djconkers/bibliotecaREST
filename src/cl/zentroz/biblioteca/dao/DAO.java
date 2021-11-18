package cl.zentroz.biblioteca.dao;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

import cl.zentroz.biblioteca.dto.Entidad;

public abstract class DAO<C extends Entidad>
{
	private static final Logger LOGGER = Logger.getLogger(DAO.class);
	protected final SqlSession sqlSession;
	protected final String nombreEntidad;

	public DAO(SqlSession sqlSession)
	{
		this.sqlSession = sqlSession;
		this.nombreEntidad = obtieneClaseEntidad().getSimpleName();
	}

	public SqlSession getSqlSession()
	{
		return this.sqlSession;
	}

	public List<C> listar(C entidad)
	{
		LOGGER.info("["+nombreEntidad+"] Listar");
		return this.sqlSession.selectList("select" + nombreEntidad, entidad);
	}

	public List<C> listar()
	{
		LOGGER.info("["+nombreEntidad+"] Listar");
		return listar(null);
	}

	public C obtener(C entidad)
	{
		LOGGER.info("["+nombreEntidad+"] Obtener");
		return this.sqlSession.selectOne("get" + nombreEntidad, entidad);
	}

	public C agregar(C entidad)
	{
		LOGGER.info("["+nombreEntidad+"] Agregar");
		this.sqlSession.insert("insert" + nombreEntidad, entidad);
		return entidad;
	}

	public int modificar(C entidad)
	{
		LOGGER.info("["+nombreEntidad+"] Modificar");
		return this.sqlSession.update("update" + nombreEntidad, entidad);
	}

	public int eliminar(C entidad)
	{
		LOGGER.info("["+nombreEntidad+"] Eliminar");
		return this.sqlSession.delete("delete" + nombreEntidad, entidad);
	}

	public long cantidadRegistros(C entidad)
	{
		LOGGER.info("["+nombreEntidad+"] Cantidad Registros");
		return this.sqlSession.selectOne("tot" + nombreEntidad, entidad);
	}

	@SuppressWarnings("unchecked")
	protected final Class<C> obtieneClaseEntidad()
	{
		return (Class<C>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}
}