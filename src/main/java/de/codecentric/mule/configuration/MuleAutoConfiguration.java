package de.codecentric.mule.configuration;

import org.mule.api.MuleContext;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

/**
 * Spring Boot Autoconfiguration for the MuleContext
 * 
 * @author Dennis Schulte
 */
@Configuration
@EnableConfigurationProperties
@ConditionalOnClass(MuleContext.class)
public class MuleAutoConfiguration {

	private MuleContextInitializer muleContextInitializer;
	
	@Bean
	@ConditionalOnMissingBean
	@ConfigurationProperties(prefix="mule")
	protected MuleContextInitializer muleContextInitializer() {
		this.muleContextInitializer =  new MuleContextInitializer();
		return this.muleContextInitializer;
	}

	/**
	 * Bean must be @Lazy to ensure the mulecontext has been initialized
	 *
	 * @return the MuleContext
	 */
	@Lazy
	@Bean
	protected MuleContext muleContext()
	{
		return this.muleContextInitializer.getMuleContext();
	}

}
