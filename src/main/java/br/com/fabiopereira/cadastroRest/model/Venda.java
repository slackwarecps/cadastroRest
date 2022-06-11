package br.com.fabiopereira.cadastroRest.model;

import javax.persistence.*;

@Entity
public class Venda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int venda_id;
    private int produto_id;

    @Column(name = "created")
    private java.sql.Timestamp created;
    private float qtd;
    private String nome;
    private String telefone;
    private String status;
}
