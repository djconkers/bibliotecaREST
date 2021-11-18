package cl.zentroz.biblioteca;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;


@Configuration
public class ConfiguracionHttp
{
	private static final Logger LOGGER = Logger.getLogger(ConfiguracionHttp.class);
	
	@Bean
	public RestTemplate obtieneRestTemplate()
	{
		LOGGER.info("Construyendo RestTemplate...");
		return new RestTemplate();
	}
}
