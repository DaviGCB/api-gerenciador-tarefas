package com.example.taferas_api;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository 
public interface TarefaRepository extends JpaRepository<Tarefa, Long> {

    
    List<Tarefa> findByDescricaoContaining(String descricao); 

    
    List<Tarefa> findByConcluida(boolean concluida); 
}