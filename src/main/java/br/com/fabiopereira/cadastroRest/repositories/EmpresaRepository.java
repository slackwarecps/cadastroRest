package br.com.fabiopereira.cadastroRest.repositories;

import br.com.fabiopereira.cadastroRest.model.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmpresaRepository extends JpaRepository<Empresa,Integer> {

}
