package de.codecentric.mule.http;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import de.codecentric.mule.TestApplication;

@SpringApplicationConfiguration(classes = TestApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class HttpTest {

	@Test
	public void testHttpRequest() {
		// Given
		// When
		ResponseEntity<String> response = new TestRestTemplate().getForEntity("http://localhost:8081/echo", String.class);
		// Then
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals("/echo", response.getBody());
	}

}
