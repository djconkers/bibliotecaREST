package cl.zentroz.biblioteca;

import javax.mail.Session;

import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import cl.zentroz.biblioteca.util.BuscarObjectoJndi;

public abstract class ConfiguracionMail 
{
	private final String jndiMail;
	public ConfiguracionMail(String jndiMail) 
	{
		this.jndiMail=jndiMail;
	}
	@Bean
    public JavaMailSenderImpl mailSender()
    {
 		BuscarObjectoJndi bodd= new BuscarObjectoJndi();
    	Session session = bodd.obtenerJavaMailSession(this.jndiMail);
    	JavaMailSenderImpl senderImpl = new JavaMailSenderImpl();
    	senderImpl.setSession(session);
    	return senderImpl;
    }
}
