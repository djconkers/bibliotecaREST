package cl.zentroz.biblioteca.util;

import java.net.URL;

import javax.mail.Session;
import javax.naming.NamingException;

import org.springframework.jndi.JndiLocatorSupport;
import org.springframework.jndi.JndiLookupFailureException;

public class BuscarObjectoJndi extends JndiLocatorSupport
{
	
	
	public BuscarObjectoJndi()
	{
		setResourceRef(true);
	}
	
	public URL obtenerDatoURL(String nombreJndi) 
	{
		return obtenerDatoURL(nombreJndi,true);
	}
	public URL obtenerDatoURL(String nombreJndi,boolean requerido) 
	{
		return (URL) obtenerObjectoDato(nombreJndi,URL.class,requerido);
	}
	
	public Boolean obtenerDatoBoolean(String nombreJndi) 
	{
		return obtenerDatoBoolean(nombreJndi,true);
	}
	public Boolean obtenerDatoBoolean(String nombreJndi,boolean requerido) 
	{
		return (Boolean) obtenerObjectoDato(nombreJndi,Boolean.class,requerido);
	}

	public Integer obtenerDatoInteger(String nombreJndi) 
	{
		return obtenerDatoInteger(nombreJndi,true);
	}
	public Integer obtenerDatoInteger(String nombreJndi,boolean requerido) 
	{
		return (Integer) obtenerObjectoDato(nombreJndi,Integer.class,requerido);
	}
	
	public String obtenerDatoString(String nombreJndi) 
	{
		return obtenerDatoString(nombreJndi,true);
	}
	public String obtenerDatoString(String nombreJndi,boolean requerido) 
	{
		return (String) obtenerObjectoDato(nombreJndi,String.class,requerido);
	}
	
	public Session obtenerJavaMailSession(String nombreJndi)
	{
		return (Session) obtenerObjectoDato(nombreJndi, Session.class,true);	
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private Object obtenerObjectoDato(String nombreJndi, Class clase,boolean requerido)
	{
		try 
		{
			return lookup(nombreJndi,clase);
		}
		catch (NamingException ex) 
		{	
			if(requerido)
				throw new JndiLookupFailureException("Failed to look up JNDI with name '" + nombreJndi + "'", ex);
			else
				return null;
		}
	}
}
