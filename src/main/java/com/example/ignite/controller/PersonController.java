package com.example.ignite.controller;

import com.example.ignite.domain.Person;
import com.example.ignite.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;
import java.util.UUID;

@RestController
public class PersonController {

    @Autowired
    private PersonRepository repository;

    @GetMapping("/person")
    public ResponseEntity<Iterable<Person>> getPerson(){
        Iterable<Person> persons = repository.findAll();
        return ResponseEntity.ok(persons);
    }

    @PostMapping("/person")
    public ResponseEntity<Person> createPerson(@RequestBody Person person){
        UUID uuid = UUID.randomUUID();
        person.setId(uuid);
        person = repository.save(uuid,person);
        return ResponseEntity.ok(person);
    }

    @DeleteMapping("/person/{id}")
    public ResponseEntity<Boolean> deletePerson(@PathVariable( name = "id", required = true) UUID id){
        repository.deleteById(id);
        return ResponseEntity.ok(true);
    }

}
