package de.codecentric.mule.configuration;

import org.mule.api.MuleContext;
import org.mule.api.MuleException;
import org.mule.config.spring.SpringXmlConfigurationBuilder;
import org.mule.context.DefaultMuleContextFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ApplicationContextEvent;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.ContextRefreshedEvent;

/**
 * ApplicationListener to startup Mule
 * 
 * @author Dennis Schulte
 */
public class MuleContextListener implements ApplicationListener<ApplicationContextEvent> {

	private MuleContext muleContext;

	@Override
	public void onApplicationEvent(ApplicationContextEvent event) {
		if(event instanceof ContextRefreshedEvent){
			DefaultMuleContextFactory muleContextFactory = new DefaultMuleContextFactory();
			SpringXmlConfigurationBuilder configBuilder;
			try {
				configBuilder = new SpringXmlConfigurationBuilder("mule-config.xml");
				muleContext = muleContextFactory.createMuleContext(configBuilder);
				muleContext.start();
			} catch (MuleException e) {
				throw new RuntimeException(e);
			}			
		}
		if(event instanceof ContextClosedEvent && muleContext!=null){
			try {
				muleContext.stop();
				muleContext=null;
			} catch (MuleException e) {
				throw new RuntimeException(e);
			}
		}

	}

}