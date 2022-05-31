package br.com.fabiopereira.cadastroRest.applicationLayer;


import br.com.fabiopereira.cadastroRest.model.Empresa;
import br.com.fabiopereira.cadastroRest.repositories.EmpresaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping(path="/api/empresas")
public class EmpresaController {

    private EmpresaRepository empresaRepository;

    public EmpresaController(EmpresaRepository empresaRepository) {
        super();
        this.empresaRepository = empresaRepository;
    }

    @PostMapping
    public ResponseEntity<Empresa>  save(@RequestBody Empresa empresa){
        empresaRepository.save(empresa);
        return new ResponseEntity<>(empresa,HttpStatus.OK);
    }


    @GetMapping
    public ResponseEntity<List<Empresa>> getAll(){
        List<Empresa> empresas = new ArrayList<>();
        empresas = empresaRepository.findAll();
        return new ResponseEntity<>(empresas,HttpStatus.OK);
    }

    @GetMapping(path="/{id}")
    public ResponseEntity<Optional<Empresa>> getById(@PathVariable Integer id){
        Optional<Empresa> empresa;
        try {
            empresa = empresaRepository.findById(id);
            return new ResponseEntity<Optional<Empresa>>(empresa, HttpStatus.OK);
        }catch(NoSuchElementException nsee){
            return new ResponseEntity<Optional<Empresa>>(HttpStatus.NOT_FOUND);
        }

    }

    @DeleteMapping(path="/{id}")
    public ResponseEntity<Optional<Empresa>> deleteById(@PathVariable Integer id){
        try {
            empresaRepository.deleteById(id);
            return new ResponseEntity<Optional<Empresa>>( HttpStatus.OK);
        }catch(NoSuchElementException nsee){
            return new ResponseEntity<Optional<Empresa>>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping(path="/{id}")
    public ResponseEntity<Empresa> update(@PathVariable Integer id, @RequestBody Empresa newEmpresa){
        return empresaRepository.findById(id)
                .map(empresa -> {
                    empresa.setNome(newEmpresa.getNome());
                    empresa.setFantasia(newEmpresa.getFantasia());
                    Empresa empresaUpdated = empresaRepository.save(empresa);
                    return ResponseEntity.ok().body(empresaUpdated);
                }).orElse(ResponseEntity.notFound().build());
    }


}
