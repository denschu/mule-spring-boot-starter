package de.codecentric.mule;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Test;
import org.mule.api.MuleContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.ContextRefreshedEvent;

import de.codecentric.mule.configuration.MuleContextListener;

public class MuleContextListenerTest {

	private AnnotationConfigApplicationContext context;
	
	@After 
	public void tearDown() {
		if (context != null) {
			context.close();
		}
	}
	
	@Test
	public void test_parentContext() {
		load();
		
		MuleContextListener listener = new MuleContextListener();
		listener.onApplicationEvent(new ContextRefreshedEvent(context));
		
		assertEquals("test", listener.getMuleContext().getRegistry().get("testBean"));
	}
	
	@Test
	public void test_lifecycle() {
		load();
		
		MuleContextListener listener = new MuleContextListener();
		listener.onApplicationEvent(new ContextRefreshedEvent(context));
		assertNotNull(listener.getMuleContext());
		MuleContext muleContext = listener.getMuleContext();
		assertTrue(muleContext.isStarted());
		
		listener.onApplicationEvent(new ContextClosedEvent(context));
		assertNull(listener.getMuleContext());
		assertTrue(muleContext.isStopped());
	}
	
	private void load() {
		context = new AnnotationConfigApplicationContext();
		context.register(TestApplication.class);
		context.refresh();
	}

	@Configuration
	public static class TestApplication {
		@Bean
		public String testBean() {
			return "test";
		}
	}
}
