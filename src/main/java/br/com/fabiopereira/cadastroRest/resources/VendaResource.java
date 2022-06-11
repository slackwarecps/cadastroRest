package br.com.fabiopereira.cadastroRest.resources;


import br.com.fabiopereira.cadastroRest.model.Venda;
import br.com.fabiopereira.cadastroRest.repositories.VendaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping(path="/venda")
public class VendaResource {


    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    private VendaRepository vendaRepository;

    public VendaResource(VendaRepository vendaRepository) {
        super();

        this.vendaRepository = vendaRepository;
    }

    @PostMapping
    public ResponseEntity<Venda>  save(@RequestBody Venda venda){
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        logger.trace("A TRACE Message");
        logger.debug("A DEBUG Message");
        logger.info("An INFO Message");
        logger.warn("A WARN Message");
        logger.error("An ERROR Message");
        System.out.println("Esse é um log fora do padrao");


        venda.setCreated(timestamp);
        vendaRepository.save(venda);
        return new ResponseEntity<>(venda,HttpStatus.OK);
    }


    @GetMapping
    public ResponseEntity<List<Venda>> getAll(){
        List<Venda> vendas = new ArrayList<>();
        vendas = vendaRepository.findAll();
        return new ResponseEntity<>(vendas,HttpStatus.OK);
    }

    @GetMapping(path="/{id}")
    public ResponseEntity<Optional<Venda>> getById(@PathVariable Integer id){
        Optional<Venda> venda;
        try {
            venda = vendaRepository.findById(id);
            return new ResponseEntity<Optional<Venda>>(venda, HttpStatus.OK);
        }catch(NoSuchElementException nsee){
            return new ResponseEntity<Optional<Venda>>(HttpStatus.NOT_FOUND);
        }

    }

    @DeleteMapping(path="/{id}")
    public ResponseEntity<Optional<Venda>> deleteById(@PathVariable Integer id){
        try {
            vendaRepository.deleteById(id);
            return new ResponseEntity<Optional<Venda>>( HttpStatus.OK);
        }catch(NoSuchElementException nsee){
            return new ResponseEntity<Optional<Venda>>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping(path="/{id}")
    public ResponseEntity<Venda> update(@PathVariable Integer id, @RequestBody Venda newVenda){
        return vendaRepository.findById(id)
                .map(venda -> {
                    venda.setProduto_id(newVenda.getProduto_id());
                    venda.setQtd(newVenda.getQtd());
                    venda.setNome(newVenda.getNome());
                    venda.setTelefone(newVenda.getTelefone());
                    venda.setStatus(newVenda.getStatus());

                    Venda vendaUpdated = vendaRepository.save(venda);
                    return ResponseEntity.ok().body(vendaUpdated);
                }).orElse(ResponseEntity.notFound().build());
    }


}
