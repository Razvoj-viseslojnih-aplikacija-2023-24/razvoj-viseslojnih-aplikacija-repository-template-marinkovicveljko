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

import rva.models.Filijala;
import rva.models.KorisnikUsluge;
import rva.models.Usluga;
import rva.services.FilijalaService;
import rva.services.KorisnikUslugeService;
import rva.services.UslugaService;

@RestController
public class UslugaController {

@Autowired
private UslugaService service;

@Autowired
private FilijalaService filijalaService;

@Autowired
private KorisnikUslugeService korisnikUslugeService;

@GetMapping("/usluga")
//@RequestMapping(method = RequestMethod.GET, path = "/usluga")
public List<Usluga> getAllUslugas(){
return service.getAll();
}


@GetMapping("/usluga/filijala/{foreignKey}")
public ResponseEntity<?> getUslugaByFilijala(@PathVariable int foreignKey){

Optional<Filijala> filijala = filijalaService.findById(foreignKey);
if(filijala.isPresent()) {

List<Usluga> usluge = service.getByForeignKey(filijala.get());
if(!usluge.isEmpty()) {
return ResponseEntity.ok(usluge);
}
return ResponseEntity.status(404).body("Resource with foreign key: " + foreignKey + " do not exist!");
}
return ResponseEntity.status(404).body("Invalid foreign key!");
}

@GetMapping("/usluga/korisnikUsluge/{foreignKey}")
public ResponseEntity<?> getUslugaByKorisnikUsluge(@PathVariable int foreignKey){

Optional<KorisnikUsluge> korisnikUsluge = korisnikUslugeService.findById(foreignKey);
if(korisnikUsluge.isPresent()) {

List<Usluga> usluge = service.getByForeignKey(korisnikUsluge.get());
if(!usluge.isEmpty()) {
return ResponseEntity.ok(usluge);
}
return ResponseEntity.status(404).body("Resource with foreign key: " + foreignKey + " do not exist!");
}
return ResponseEntity.status(404).body("Invalid foreign key!");
}

@GetMapping("/usluga/id/{id}")
public ResponseEntity<?> getUslugaById(@PathVariable int id){

Optional<Usluga> usluga = service.findById(id);
if(usluga.isPresent()) {
return ResponseEntity.ok(usluga.get());
}
return ResponseEntity.status(404).body("Resource with requested ID: " + id + " does not exist!");
}

@GetMapping("/usluga/naziv/{naziv}")
public ResponseEntity<?> getUslugaByNaziv(@PathVariable String naziv){
List<Usluga> usluge = service.getUslugaByNaziv(naziv);
if(usluge.isEmpty()) {
return ResponseEntity.status(404).body("Resources with naziv: " + naziv + " do not exist!");
}
return ResponseEntity.ok(usluge);
}

@PostMapping("/usluga")
public ResponseEntity<?> createUsluga(@RequestBody Usluga usluga){
if(service.existsById(usluga.getId())) {
return ResponseEntity.status(409).body("Resources already exists!");
}
Usluga savedUsluga = service.create(usluga);
URI uri = URI.create("usluga/id/" + savedUsluga.getId());
return ResponseEntity.created(uri).body(savedUsluga);
}

@PutMapping("/usluga/id/{id}")
public ResponseEntity<?> updateUsluga(@RequestBody Usluga usluga, @PathVariable int id){
Optional<Usluga> updatedUsluga = service.update(usluga, id);
if(updatedUsluga.isPresent()) {
return ResponseEntity.ok(updatedUsluga.get());
}
return ResponseEntity.status(404).body("Resource with requested ID: " + id + " could not be updated" + " as it doesn't exist!");
}

@DeleteMapping("/usluga/id/{id}")
public ResponseEntity<?> deleteUsluga(@PathVariable int id){
if(service.existsById(id)) {
service.delete(id);
return ResponseEntity.ok("Resource with ID: " + id + " successfully deleted!");
}
return ResponseEntity.status(404).body("Resource with requested ID: " + id + " could not be deleted" + " as it doesn't exist!");
}
}
