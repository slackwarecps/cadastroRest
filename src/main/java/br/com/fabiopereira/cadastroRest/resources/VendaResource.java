package br.com.fabiopereira.cadastroRest.resources;


import br.com.fabiopereira.cadastroRest.model.Venda;
import br.com.fabiopereira.cadastroRest.repositories.VendaRepository;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
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


    Counter authUserSuccess;
    Counter authUserErrors;



    public VendaResource(MeterRegistry registry, VendaRepository vendaRepository) {
        super();


        authUserSuccess = Counter.builder("auth_user_success")
                .description("usuarios autenticados do Fabio")
                .register(registry);

        authUserErrors = Counter.builder("auth_user_error")
                .description("erros de login do Fabio")
                .register(registry);
        logger.info("Registrou as metricas de prometheus");

        this.vendaRepository = vendaRepository;
    }

    @PostMapping(path="/autenticaOk")
    public ResponseEntity<Object> autenticacaoOk() {
        authUserSuccess.increment();
        logger.info("usuario autenticou fake... ");
        return ResponseEntity.ok().build();
    }

    @PostMapping(path="/autenticaErro")
    public ResponseEntity<Object> autenticacaoErro() {
        authUserErrors.increment();
        logger.info("usuario com erro de autenticação fake... ");
        return ResponseEntity.badRequest().build();
    }

    @PostMapping
    public ResponseEntity<Venda>  save(@RequestBody Venda venda){
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());



        venda.setCreated(timestamp);
        vendaRepository.save(venda);
        logger.trace("NOVA VENDA "+ venda);
        return new ResponseEntity<>(venda,HttpStatus.OK);
    }


    @GetMapping
    public ResponseEntity<List<Venda>> getAll(){
        logger.trace("BUSCOU TODOS OS REGISTROS");
        logger.debug("BUSCOU TODOS OS REGISTROS");
        logger.info("BUSCOU TODOS OS REGISTROS");
        logger.warn("BUSCOU TODOS OS REGISTROS");


        List<Venda> vendas = new ArrayList<>();
        vendas = vendaRepository.findAll();
        return new ResponseEntity<>(vendas,HttpStatus.OK);
    }

    @GetMapping(path="/{id}")
    public ResponseEntity<Optional<Venda>> getById(@PathVariable Integer id){
        Optional<Venda> venda = vendaRepository.findById(id);
        if (venda.isPresent()){
            logger.info("BUSCOU POR ID "+ venda);
            return new ResponseEntity<Optional<Venda>>(venda, HttpStatus.OK);
        }
        logger.warn("Não localizou o registro ! " + id);
        return ResponseEntity.notFound().build();

    }

    @DeleteMapping(path="/{id}")
    public ResponseEntity<Optional<Venda>> deleteById(@PathVariable Integer id){
        try {
            vendaRepository.deleteById(id);
            logger.trace("Apagou o registro " + id);
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
                    logger.trace("Registro Atualizado: "+ newVenda.toString());
                    Venda vendaUpdated = vendaRepository.save(venda);
                    logger.trace("Registro Atualizado: "+ vendaUpdated.toString());
                    return ResponseEntity.ok().body(vendaUpdated);
                }).orElse(ResponseEntity.notFound().build());
    }


}
