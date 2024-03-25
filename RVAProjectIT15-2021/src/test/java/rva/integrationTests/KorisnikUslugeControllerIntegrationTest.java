package rva.integrationTests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import rva.models.Banka;
import rva.models.KorisnikUsluge;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class KorisnikUslugeControllerIntegrationTest {

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

}
