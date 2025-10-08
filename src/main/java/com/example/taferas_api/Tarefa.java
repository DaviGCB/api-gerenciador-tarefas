package com.example.taferas_api;



import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity 
public class Tarefa {

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private long id; 

    @Column(nullable = false, length = 100) 
    private String descricao; 

    @Column(columnDefinition = "boolean default false") 
    private boolean concluida; 

    @Column(nullable = false) 
    private int prioridade; 
    
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public boolean isConcluida() {
        return concluida;
    }

    public void setConcluida(boolean concluida) {
        this.concluida = concluida;
    }

    public int getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(int prioridade) {
        this.prioridade = prioridade;
    }
}
