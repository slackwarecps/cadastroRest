package br.com.fabiopereira.cadastroRest.repositories;

import br.com.fabiopereira.cadastroRest.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Person,Integer> {

}
