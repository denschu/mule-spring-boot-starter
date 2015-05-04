package de.codecentric.mule;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mule.api.MuleContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.ContextRefreshedEvent;

import de.codecentric.mule.configuration.MuleContextInitializer;


/**
 * Test for the Mule Lifecycle
 * 
 * @author Dennis Schulte
 */
public class MuleContextInitializerTest {

	private AnnotationConfigApplicationContext context;
	private MuleContextInitializer initializer;
	
	@Before
	public void setup() {
		context = new AnnotationConfigApplicationContext();
		context.register(TestApplication.class);
		context.refresh();
		initializer = new MuleContextInitializer();
		initializer.setConfig("mule-noop-config.xml");
	}
	
	@After
	public void tearDown() {
		if (context != null) {
			context.close();
		}
		if (initializer != null && initializer.getMuleContext() != null) {
			initializer.getMuleContext().dispose();
		}
	}
	
	@Test
	public void testParentContext() {
		initializer.onApplicationEvent(new ContextRefreshedEvent(context));
		assertEquals("test", initializer.getMuleContext().getRegistry().get("testBean"));
	}
	
	@Test
	public void testLifecycle() {
		initializer.onApplicationEvent(new ContextRefreshedEvent(context));
		assertNotNull(initializer.getMuleContext());
		MuleContext muleContext = initializer.getMuleContext();
		assertTrue(muleContext.isStarted());
		initializer.onApplicationEvent(new ContextClosedEvent(context));
		assertNull(initializer.getMuleContext());
		assertTrue(muleContext.isDisposed());
	}
	

	@Configuration
	public static class TestApplication {
		@Bean
		public String testBean() {
			return "test";
		}
	}
}
