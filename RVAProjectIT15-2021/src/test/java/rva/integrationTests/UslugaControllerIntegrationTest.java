package rva.integrationTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Date;
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
import rva.models.Usluga;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UslugaControllerIntegrationTest {

	@Autowired
	TestRestTemplate template; // za slanje http zahteva
	
	void createHighestId() {
		 ResponseEntity<List<Usluga>> response = template.exchange("/usluga", HttpMethod.GET,
				 null, new ParameterizedTypeReference<List<Usluga>>() {});
		 ArrayList<Usluga> list = (ArrayList<Usluga>) response.getBody();
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
	void testGetAllUslugas() {
	 ResponseEntity<List<Usluga>> response = template.exchange("/usluga", HttpMethod.GET,
			 null, new ParameterizedTypeReference<List<Usluga>>() {});
	 
	 int statusCode = response.getStatusCode().value();
	 List<Usluga> usluge = response.getBody();
	 
	 assertEquals(200, statusCode);
	 assertTrue(!usluge.isEmpty());
	 
	}
	
	@Test
	@Order(2)
	void testGetUslugaByFilijala() {
		int filijalaId = 1;
		ResponseEntity<List<Usluga>> response = template.exchange("/usluga/filijala/" + filijalaId, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<Usluga>>(){});
		int statusCode = response.getStatusCode().value();
		List<Usluga> usluge =  response.getBody();
		
		assertEquals(200, statusCode );
		assertNotNull(usluge.get(0));
		for(Usluga u: usluge) {
			assertTrue(u.getFilijala().getId() == 1);
		}
	}
	
	@Test
	@Order(3)
	void testGetUslugaByKorisnikUsluge() {
		int korisnikUslugeId = 1;
		ResponseEntity<List<Usluga>> response = template.exchange("/usluga/korisnikUsluge/" + korisnikUslugeId, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<Usluga>>(){});
		int statusCode = response.getStatusCode().value();
		List<Usluga> usluge =  response.getBody();
		
		assertEquals(200, statusCode );
		assertNotNull(usluge.get(0));
		for(Usluga u: usluge) {
			assertTrue(u.getKorisnik().getId() == 1);
		}
	}
	
	@Test
	@Order(4)
	void testGetUslugaById() {
		int id = 1;
		ResponseEntity<Usluga> response = template.exchange("/usluga/id/" + id, HttpMethod.GET,
				null, Usluga.class);
		
		int statusCode = response.getStatusCode().value();
		
		assertEquals(200, statusCode);
		assertNotNull(response.getBody());
		assertEquals(id, response.getBody().getId());
	}
	
	@Test
	@Order(5)
	void testGetUslugaByNaziv() {
		String naziv = "Izrada kartice";
		ResponseEntity<List<Usluga>> response = template.exchange("/usluga/naziv/" + naziv, HttpMethod.GET,
				null, new ParameterizedTypeReference<List<Usluga>>() {});
		
		int statusCode = response.getStatusCode().value();
		List<Usluga> usluge = response.getBody();
		
		assertEquals(200, statusCode);
		assertNotNull(usluge.get(0));
	    for(Usluga u: usluge) {
	    	assertTrue(u.getNaziv().contains(naziv));
	    }
	}
	
	@Test
	@Order(6)
	void testCreateUsluga() {
		Usluga usluga = new Usluga();
		Date datumUgovora = new Date();
		usluga.setNaziv("POST naziv");
		usluga.setOpisUsluge("POST opis usluge");
		usluga.setDatumUgovora(datumUgovora);
		usluga.setProvizija(0);
		
		HttpEntity<Usluga> entity = new HttpEntity<Usluga>(usluga);
		
	    createHighestId();
		
		ResponseEntity<Usluga> response = template.exchange("/usluga", HttpMethod.POST,
						entity, Usluga.class);
		
		assertEquals(201, response.getStatusCode().value());
		assertEquals("/usluga/id/" + highestId, response.getHeaders().getLocation().getPath());
		assertEquals(usluga.getProvizija(), response.getBody().getProvizija());
		assertEquals(usluga.getNaziv(), response.getBody().getNaziv());
		assertEquals(usluga.getDatumUgovora(), response.getBody().getDatumUgovora());
		assertEquals(usluga.getOpisUsluge(), response.getBody().getOpisUsluge());
		
	}
	
	@Test
	@Order(7)
	void testUpdateUsluga() {
		Usluga usluga = new Usluga();
		Date datumUgovora = new Date();
		usluga.setNaziv("PUT naziv");
		usluga.setOpisUsluge("PUT opis usluge");
		usluga.setDatumUgovora(datumUgovora);
		usluga.setProvizija(0);
		
		HttpEntity<Usluga> entity = new HttpEntity<Usluga>(usluga);
		getHighestId();
		
		ResponseEntity<Usluga> response = template.exchange("/usluga/id/" + highestId, HttpMethod.PUT,
				entity, Usluga.class);
		
		assertEquals(200, response.getStatusCode().value());
		assertEquals(usluga.getNaziv(), response.getBody().getNaziv());
		assertEquals(usluga.getOpisUsluge(), response.getBody().getOpisUsluge());
		assertEquals(usluga.getDatumUgovora(), response.getBody().getDatumUgovora());
		assertEquals(usluga.getProvizija(), response.getBody().getProvizija());
	}
	
	@Test
	@Order(8)
	void testDeleteUsluga() {
		getHighestId();
		ResponseEntity<String> response = template.exchange("/usluga/id/" + highestId, HttpMethod.DELETE,
				null, String.class);
		
		assertEquals(200, response.getStatusCode().value());
		assertTrue(response.getBody().contains("successfully deleted!"));
	}
	

}
