package de.codecentric.mule.configuration;

import org.mule.api.MuleContext;
import org.mule.api.MuleException;
import org.mule.config.spring.SpringXmlConfigurationBuilder;
import org.mule.context.DefaultMuleContextFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

/**
 * ApplicationListener to startup Mule
 * 
 * @author Dennis Schulte
 */
public class MuleContextListener implements ApplicationListener<ContextRefreshedEvent> {

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		DefaultMuleContextFactory muleContextFactory = new DefaultMuleContextFactory();
		SpringXmlConfigurationBuilder configBuilder;
		try {
			configBuilder = new SpringXmlConfigurationBuilder("mule-config.xml");
			MuleContext muleContext = muleContextFactory.createMuleContext(configBuilder);
			muleContext.start();
		} catch (MuleException e) {
			throw new RuntimeException(e);
		}
	}

}
