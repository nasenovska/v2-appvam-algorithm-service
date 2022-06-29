package com.appvam.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.appvam.api.model.Algorithm;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AlgorithmControllerIntegrationTest {
	@Autowired
	private TestRestTemplate restTemplate;

	@LocalServerPort
	private int port;

	private String getRootUrl() {
		return "http://localhost:" + port;
	}

	@Test
	public void contextLoads() {

	}

	@Test
	public void testGetAllAlgorithms() {
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);

		ResponseEntity<String> response = restTemplate.exchange(getRootUrl() + "/algorithms",
				HttpMethod.GET, entity, String.class);
		
		assertNotNull(response.getBody());
	}

	@Test
	public void testGetAlgorithmById() {
		Algorithm algorithm = restTemplate.getForObject(getRootUrl() + "/algorithms/1", Algorithm.class);
		System.out.println(algorithm.getTitle());
		assertNotNull(algorithm);
	}

	@Test
	public void testCreateAlgorithm() {
		Algorithm algorithm = new Algorithm();
		algorithm.setTitle("Some title");
		algorithm.setKey("key");
		algorithm.setAnimationPath("var/folder1/folder2/key");

		ResponseEntity<Algorithm> postResponse = restTemplate.postForEntity(getRootUrl() + "/algorithms", algorithm, Algorithm.class);
		assertNotNull(postResponse);
		assertNotNull(postResponse.getBody());
	}

	@Test
	public void testUpdateAlgorithm() {
		int id = 1;
		Algorithm algorithm = restTemplate.getForObject(getRootUrl() + "/algorithms/" + id, Algorithm.class);
		algorithm.setTitle("New title");

		restTemplate.put(getRootUrl() + "/algorithms/" + id, algorithm);

		Algorithm updatedAlgorithm = restTemplate.getForObject(getRootUrl() + "/algorithms/" + id, Algorithm.class);
		assertNotNull(updatedAlgorithm);
	}

	@Test
	public void testDeleteAlgorithm() {
		int id = 2;
		Algorithm algorithm = restTemplate.getForObject(getRootUrl() + "/algorithms/" + id, Algorithm.class);
		assertNotNull(algorithm);

		restTemplate.delete(getRootUrl() + "/algorithms/" + id);

		try {
			restTemplate.getForObject(getRootUrl() + "/algorithms/" + id, Algorithm.class);
		} catch (final HttpClientErrorException e) {
			assertEquals(e.getStatusCode(), HttpStatus.NOT_FOUND);
		}
	}
}
