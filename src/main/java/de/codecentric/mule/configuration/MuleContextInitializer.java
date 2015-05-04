package de.codecentric.mule.configuration;

import org.mule.api.MuleContext;
import org.mule.api.MuleException;
import org.mule.config.spring.SpringXmlConfigurationBuilder;
import org.mule.context.DefaultMuleContextFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ApplicationContextEvent;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.ContextRefreshedEvent;

/**
 * ApplicationListener to startup Mule
 * 
 * @author Dennis Schulte
 */
public class MuleContextInitializer implements ApplicationListener<ApplicationContextEvent> {

	private MuleContext muleContext;
	
	private String config = "mule-config.xml";

	@Override
	public void onApplicationEvent(ApplicationContextEvent event) {
		if (event instanceof ContextRefreshedEvent && event.getApplicationContext().getParent() == null) {
			startMuleContext(event.getApplicationContext());
		} else if (event instanceof ContextClosedEvent) {
			stopMuleContext();
		}
	}

	private void startMuleContext(ApplicationContext parent) {
		if (muleContext == null) {
			DefaultMuleContextFactory muleContextFactory = new DefaultMuleContextFactory();
			SpringXmlConfigurationBuilder configBuilder;
			try {
				configBuilder = new SpringXmlConfigurationBuilder(config);
				configBuilder.setParentContext(parent);
				muleContext = muleContextFactory.createMuleContext(configBuilder);
				muleContext.start();
			} catch (MuleException e) {
				throw new RuntimeException(e);
			}			
		}
	}

	private void stopMuleContext() {
		if (muleContext != null) {
			muleContext.dispose();
			muleContext = null;
		}
	}

	public MuleContext getMuleContext() {
		return muleContext;
	}
	
	public void setConfig(String config) {
		this.config = config;
	}
	
	public String getConfig() {
		return config;
	}
}
