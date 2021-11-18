package cl.zentroz.biblioteca;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;
import org.springframework.transaction.PlatformTransactionManager;

public abstract class ConfiguracionBD 
{
	private final String jndiDB;
	
	public ConfiguracionBD(String jndiDB) 
	{
		this.jndiDB=jndiDB;
	}
	
	@Bean(destroyMethod = "")
    public DataSource dataSource() 
    {
        JndiDataSourceLookup dataSourceLookup = new JndiDataSourceLookup();
        return dataSourceLookup.getDataSource(this.jndiDB);
    }
	
    @Bean
	public PlatformTransactionManager txManager() {
		return new DataSourceTransactionManager(dataSource());
	}
}
