package com.example.taferas_api;



import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity 
public class Tarefa {

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Configura a geração automática do ID pelo banco (PostgreSQL)
    private long id; // Identificador único da tarefa

    @Column(nullable = false, length = 100) // O campo não pode ser nulo e tem limite de 100 caracteres
    private String descricao; // Texto que descreve a tarefa

    @Column(columnDefinition = "boolean default false") // Define 'false' como o valor padrão no banco
    private boolean concluida; // Indica se a tarefa foi concluída ou não

    @Column(nullable = false) // O campo não pode ser nulo
    private int prioridade; // Número que representa a prioridade da tarefa

    // Getters e Setters (necessários para o Spring/JPA acessar os campos)
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
