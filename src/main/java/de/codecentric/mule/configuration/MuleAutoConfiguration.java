package de.codecentric.mule.configuration;

import org.mule.api.MuleContext;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * Spring Boot Autoconfiguration for the MuleContext
 * 
 * @author Dennis Schulte
 */
@Configuration
@EnableConfigurationProperties
@ConditionalOnClass(MuleContext.class)
public class MuleAutoConfiguration {
	
	@Bean
	@ConditionalOnMissingBean
	@ConfigurationProperties(prefix="mule")
	protected MuleContextInitializer muleContextInitializer() {
		return new MuleContextInitializer();
	}

}
