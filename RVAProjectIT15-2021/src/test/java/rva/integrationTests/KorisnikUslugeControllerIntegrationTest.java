package rva.integrationTests;

import static org.junit.jupiter.api.Assertions.*;

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
import rva.models.KorisnikUsluge;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class KorisnikUslugeControllerIntegrationTest {
	
	void createHighestId() {
		 ResponseEntity<List<KorisnikUsluge>> response = template.exchange("/korisnikUsluge", HttpMethod.GET,
				 null, new ParameterizedTypeReference<List<KorisnikUsluge>>() {});
		 ArrayList<KorisnikUsluge> list = (ArrayList<KorisnikUsluge>) response.getBody();
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


	@Autowired
	TestRestTemplate template;
	
	@Test
	@Order(1)
	void testGetAllKorisniciUsluge() {
		ResponseEntity<List<KorisnikUsluge>> response = template.exchange("/korisnikUsluge", HttpMethod.GET,
				null, new ParameterizedTypeReference<List<KorisnikUsluge>>() {});
		
		int statusCode = response.getStatusCode().value();
		List<KorisnikUsluge> korisnici = response.getBody();
		
		assertEquals(200, statusCode);
		assertTrue(!korisnici.isEmpty());
	}
	
	@Test
	@Order(2)
	void testGetKorisnikUslugeById() {
		int id = 1;
		ResponseEntity<KorisnikUsluge> response = template.exchange("/korisnikUsluge/id/" + id, HttpMethod.GET,
				null, KorisnikUsluge.class);
		
		int statusCode = response.getStatusCode().value();
		
		assertEquals(200, statusCode);
		assertNotNull(response.getBody());
		assertEquals(id, response.getBody().getId());
	}
	
	@Test
	@Order(3)
	void testGetKorisnikUslugeByMaticniBroj() {
		String maticniBroj = "1907002790067";
		ResponseEntity<List<KorisnikUsluge>> response = template.exchange("/korisnikUsluge/maticniBroj/" + maticniBroj, HttpMethod.GET,
				null, new ParameterizedTypeReference<List<KorisnikUsluge>>() {});
		
		int statusCode = response.getStatusCode().value();
		List<KorisnikUsluge> korisnici = response.getBody();
		
		assertEquals(200, statusCode);
		assertNotNull(korisnici.get(0));
	    for(KorisnikUsluge k: korisnici) {
	    	assertTrue(k.getMaticniBroj().contains(maticniBroj));
	    }
	}
	
	@Test
	@Order(4)
	void testCreateKorisnikUsluge() {
		KorisnikUsluge korisnik = new KorisnikUsluge();
		korisnik.setIme("POST ime");
		korisnik.setPrezime("POST prezime");
		korisnik.setMaticniBroj("POST maticni broj");
		
		HttpEntity<KorisnikUsluge> entity = new HttpEntity<KorisnikUsluge>(korisnik);
		createHighestId();
		
		ResponseEntity<KorisnikUsluge> response = template.exchange("/korisnikUsluge", HttpMethod.POST,
				entity, KorisnikUsluge.class);
		
		assertEquals(201, response.getStatusCode().value());
		assertEquals("/korisnikUsluge/id/" + highestId, response.getHeaders().getLocation().getPath());
		assertEquals(korisnik.getIme(), response.getBody().getIme());
		assertEquals(korisnik.getPrezime(), response.getBody().getPrezime());
		assertEquals(korisnik.getMaticniBroj(), response.getBody().getMaticniBroj());
	}
	
	
	@Test
	@Order(5)
	void testUpdateKorisnikUsluge() {
		KorisnikUsluge korisnik = new KorisnikUsluge();
		korisnik.setIme("PUT ime");
		korisnik.setPrezime("PUT prezime");
		korisnik.setMaticniBroj("PUT maticni broj");
		
		HttpEntity<KorisnikUsluge> entity = new HttpEntity<KorisnikUsluge>(korisnik);
		getHighestId();
		
		ResponseEntity<KorisnikUsluge> response = template.exchange("/korisnikUsluge/id/" + highestId, HttpMethod.PUT,
				entity, KorisnikUsluge.class);
		
		assertEquals(200, response.getStatusCode().value());
		assertEquals(korisnik.getIme(), response.getBody().getIme());
		assertEquals(korisnik.getPrezime(), response.getBody().getPrezime());
		assertEquals(korisnik.getMaticniBroj(), response.getBody().getMaticniBroj());
	}
	
	
	@Test
	@Order(6)
	void testDeleteKorisnikUsluge() {
		getHighestId();
		ResponseEntity<String> response = template.exchange("/korisnikUsluge/id/" + highestId, HttpMethod.DELETE,
				null, String.class);
		
		assertEquals(200, response.getStatusCode().value());
		assertTrue(response.getBody().contains("successfully deleted!"));
	}
	
	

}
