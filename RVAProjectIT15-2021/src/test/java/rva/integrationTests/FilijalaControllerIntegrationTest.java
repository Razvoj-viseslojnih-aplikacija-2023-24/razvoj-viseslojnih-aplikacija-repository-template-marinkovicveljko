package rva.integrationTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import rva.models.Banka;
import rva.models.Filijala;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class FilijalaControllerIntegrationTest {
	
	@Autowired
	TestRestTemplate template; // za slanje http zahteva
	
	void createHighestId() {
		 ResponseEntity<List<Filijala>> response = template.exchange("/filijala", HttpMethod.GET,
				 null, new ParameterizedTypeReference<List<Filijala>>() {});
		 ArrayList<Filijala> list = (ArrayList<Filijala>) response.getBody();
		 for(int i=0; i<list.size();i++) {
			 if(highestId<=list.get(i).getId()) {
				 highestId=list.get(i).getId()+1;
			 }
		 }
	}
	
	void getHighestId() {
		createHighestId();
		highestId--;
	}
	
	int highestId;
	
	@Test
	@Order(1)
	void testGetAllFilijalas() {
	 ResponseEntity<List<Filijala>> response = template.exchange("/filijala", HttpMethod.GET,
			 null, new ParameterizedTypeReference<List<Filijala>>() {});
	 
	 int statusCode = response.getStatusCode().value();
	 List<Filijala> filijale = response.getBody();
	 
	 assertEquals(200, statusCode);
	 assertTrue(!filijale.isEmpty());
	 
	}
	
	@Test
	@Order(2)
	void testGetFilijalaByBanka() {
		int bankaId = 1;
		ResponseEntity<List<Filijala>> response = template.exchange("/filijala/banka/" + bankaId, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<Filijala>>(){});
		int statusCode = response.getStatusCode().value();
		List<Filijala> filijale =  response.getBody();
		
		assertEquals(200, statusCode );
		assertNotNull(filijale.get(0));
		for(Filijala f: filijale) {
			assertTrue(f.getBanka().getId() == 1);
		}
	}
	
	@Test
	@Order(3)
	void testGetFilijalaById() {
		int id = 1;
		ResponseEntity<Filijala> response = template.exchange("/filijala/id/" + id, HttpMethod.GET,
				null, Filijala.class);
		
		int statusCode = response.getStatusCode().value();
		
		assertEquals(200, statusCode);
		assertNotNull(response.getBody());
		assertEquals(id, response.getBody().getId());
	}
	
	@Test
	@Order(4)
	void testGetFilijalasByAdresa() {
		String adresa = "Bulevar cara Lazara 66";
		ResponseEntity<List<Filijala>> response = template.exchange("/filijala/adresa/" + adresa, HttpMethod.GET,
				null, new ParameterizedTypeReference<List<Filijala>>() {});
		
		int statusCode = response.getStatusCode().value();
		List<Filijala> filijale = response.getBody();
		
		assertEquals(200, statusCode);
		assertNotNull(filijale.get(0));
	    for(Filijala f: filijale) {
	    	assertTrue(f.getAdresa().contains(adresa));
	    }
	}
	
	@Test
	@Order(5)
	void testCreateFilijala() {
		Filijala filijala = new Filijala();
		filijala.setAdresa("POST adresa");
		filijala.setBrojPultova(0);
		filijala.setPosedujeSef(false);
		
		HttpEntity<Filijala> entity = new HttpEntity<Filijala>(filijala);
		createHighestId();
		
		ResponseEntity<Filijala> response = template.exchange("/filijala", HttpMethod.POST,
				entity, Filijala.class);
		
		assertEquals(201, response.getStatusCode().value());
		assertEquals("/filijala/id/" + highestId, response.getHeaders().getLocation().getPath());
		assertEquals(filijala.getAdresa(), response.getBody().getAdresa());
		assertEquals(filijala.getBrojPultova(), response.getBody().getBrojPultova());
		assertEquals(filijala.isPosedujeSef(), response.getBody().isPosedujeSef());
	}
	
	@Test
	@Order(6)
	void testUpdateFilijala() {
		Filijala filijala = new Filijala();
		filijala.setAdresa("PUT adresa");
		filijala.setBrojPultova(0);
		filijala.setPosedujeSef(false);
		
		HttpEntity<Filijala> entity = new HttpEntity<Filijala>(filijala);
		getHighestId();
		
		ResponseEntity<Filijala> response = template.exchange("/filijala/id/" + highestId, HttpMethod.PUT,
				entity, Filijala.class);
		
		assertEquals(200, response.getStatusCode().value());
		assertEquals(filijala.getAdresa(), response.getBody().getAdresa());
		assertEquals(filijala.getBrojPultova(), response.getBody().getBrojPultova());
		assertEquals(filijala.isPosedujeSef(), response.getBody().isPosedujeSef());
	}
	
	@Test
	@Order(7)
	void testDeleteFilijala() {
		getHighestId();
		ResponseEntity<String> response = template.exchange("/filijala/id/" + highestId, HttpMethod.DELETE,
				null, String.class);
		
		assertEquals(200, response.getStatusCode().value());
		assertTrue(response.getBody().contains("successfully deleted!"));
	}
	

}
