package com.example.taferas_api;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository // Anotação que marca esta interface como um componente de repositório do Spring
public interface TarefaRepository extends JpaRepository<Tarefa, Long> {

    // O Spring Data JPA cria a consulta SQL automaticamente a partir do nome do método.
    // "Encontre tarefas Onde a Descrição Contém..."
    List<Tarefa> findByDescricaoContaining(String descricao); // Para o filtro opcional do endpoint GET / [cite: 62]

    // "Encontre tarefas Pelo campo 'concluida'..."
    List<Tarefa> findByConcluida(boolean concluida); // Para o endpoint GET /pendentes [cite: 62]
}