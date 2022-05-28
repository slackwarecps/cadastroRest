package br.com.fabiopereira.cadastroRest.resources;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import br.com.fabiopereira.cadastroRest.model.Person;
import br.com.fabiopereira.cadastroRest.repositories.PersonRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="/persons")
public class PersonResource {

    private PersonRepository personRepository;

    public PersonResource(PersonRepository personRepository) {
        super();
        this.personRepository = personRepository;
    }

    @PostMapping
    public ResponseEntity<Person>  save(@RequestBody Person person){
        personRepository.save(person);
        return new ResponseEntity<>(person,HttpStatus.OK);
    }


    @GetMapping
    public ResponseEntity<List<Person>> getAll(){
        List<Person> persons = new ArrayList<>();
        persons = personRepository.findAll();
        return new ResponseEntity<>(persons,HttpStatus.OK);
    }

    @GetMapping(path="/{id}")
    public ResponseEntity<Optional<Person>> getById(@PathVariable Integer id){
        Optional<Person> person;
        try {
            person = personRepository.findById(id);
            return new ResponseEntity<Optional<Person>>(person, HttpStatus.OK);
        }catch(NoSuchElementException nsee){
            return new ResponseEntity<Optional<Person>>(HttpStatus.NOT_FOUND);
        }

    }

    @DeleteMapping(path="/{id}")
    public ResponseEntity<Optional<Person>> deleteById(@PathVariable Integer id){
        try {
            personRepository.deleteById(id);
            return new ResponseEntity<Optional<Person>>( HttpStatus.OK);
        }catch(NoSuchElementException nsee){
            return new ResponseEntity<Optional<Person>>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping(path="/{id}")
    public ResponseEntity<Person> update(@PathVariable Integer id, @RequestBody Person newPerson){
        return personRepository.findById(id)
                .map(person -> {
                    person.setName(newPerson.getName());
                    person.setAge(newPerson.getAge());
                    Person personUpdated = personRepository.save(person);
                    return ResponseEntity.ok().body(personUpdated);
                }).orElse(ResponseEntity.notFound().build());
    }


}
