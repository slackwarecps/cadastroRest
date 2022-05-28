package br.com.fabiopereira.cadastroRest.repositories;

import br.com.fabiopereira.cadastroRest.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

interface EmployeeRepository extends JpaRepository<Employee, Long> {

}