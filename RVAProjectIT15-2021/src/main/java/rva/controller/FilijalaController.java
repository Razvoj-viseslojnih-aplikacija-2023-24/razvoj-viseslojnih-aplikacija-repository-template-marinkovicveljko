package rva.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import rva.models.Banka;
import rva.models.Filijala;
import rva.services.BankaService;
import rva.services.FilijalaService;

@RestController
public class FilijalaController {

@Autowired
private FilijalaService service;

@Autowired
private BankaService bankaService;


@GetMapping("/filijala")
//@RequestMapping(method = RequestMethod.GET, path = "/filijala")
public List<Filijala> getAllFilijalas(){
return service.getAll();
}


@GetMapping("/filijala/banka/{foreignKey}")
public ResponseEntity<?> getFilijalaByBanka(@PathVariable int foreignKey){

Optional<Banka> banka = bankaService.findById(foreignKey);
if(banka.isPresent()) {

List<Filijala> filijale = service.getByForeignKey(banka.get());
if(!filijale.isEmpty()) {
return ResponseEntity.ok(filijale);
}
return ResponseEntity.status(404).body("Resource with foreign key: " + foreignKey + " do not exist!");
}
return ResponseEntity.status(404).body("Invalid foreign key!");
}


@GetMapping("/filijala/id/{id}")
public ResponseEntity<?> getFilijalaById(@PathVariable int id){

Optional<Filijala> filijala = service.findById(id);
if(filijala.isPresent()) {
return ResponseEntity.ok(filijala.get());
}
return ResponseEntity.status(404).body("Resource with requested ID: " + id + " does not exist!");
}


@GetMapping("/filijala/adresa/{adresa}")
public ResponseEntity<?> getFilijalasByAdresa(@PathVariable String adresa){
List<Filijala> filijale = service.getFilijalasByAdresa(adresa);
if(filijale.isEmpty()) {
return ResponseEntity.status(404).body("Resources with adresa: " + adresa + " do not exist!");
}
return ResponseEntity.ok(filijale);
}


@PostMapping("/filijala")
public ResponseEntity<?> createFilijala(@RequestBody Filijala filijala){
if(service.existsById(filijala.getId())) {
return ResponseEntity.status(409).body("Resources already exists!");
}
Filijala savedFilijala = service.create(filijala);
URI uri = URI.create("filijala/id/" + savedFilijala.getId());
return ResponseEntity.created(uri).body(savedFilijala);
}

@PutMapping("/filijala/id/{id}")
public ResponseEntity<?> updateFilijala(@RequestBody Filijala filijala, @PathVariable int id){
Optional<Filijala> updatedFilijala = service.update(filijala, id);
if(updatedFilijala.isPresent()) {
return ResponseEntity.ok(updatedFilijala.get());
}
return ResponseEntity.status(404).body("Resource with requested ID: " + id + " could not be updated" + " as it doesn't exist!");
}

@DeleteMapping("/filijala/id/{id}")
public ResponseEntity<?> deleteFilijala(@PathVariable int id){
if(service.existsById(id)) {
service.delete(id);
return ResponseEntity.ok("Resource with ID: " + id + " successfully deleted!");
}
return ResponseEntity.status(404).body("Resource with requested ID: " + id + " could not be deleted" + " as it doesn't exist!");
}

}

