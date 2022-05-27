package br.com.fabiopereira.cadastroRest;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import br.com.fabiopereira.cadastroRest.model.Person;
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


}
