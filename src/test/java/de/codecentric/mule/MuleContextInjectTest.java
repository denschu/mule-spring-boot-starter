package de.codecentric.mule;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mule.api.MuleContext;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;

@SpringApplicationConfiguration(classes = MuleContextInjectTest.TestApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
@IntegrationTest("mule.config=mule-noop-config.xml")
public class MuleContextInjectTest
{
    @Inject
    private MuleContext muleContext;

    @Test
    public void testHttpRequest() {
        assertNotNull(muleContext);
    }

    @Configuration
    @EnableAutoConfiguration
    public static class TestApplication {
    }
}
