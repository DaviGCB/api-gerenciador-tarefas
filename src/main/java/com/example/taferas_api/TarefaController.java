package com.example.taferas_api;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController 
public class TarefaController {

    @Autowired
    private TarefaRepository tarefaRepository;

    @PostMapping("/")  
    public ResponseEntity<Tarefa> criarTarefa(@RequestBody Tarefa tarefa) { 
        Tarefa novaTarefa = tarefaRepository.save(tarefa); 
        return new ResponseEntity<>(novaTarefa, HttpStatus.CREATED);  
    }

    @GetMapping("/")  
    public ResponseEntity<List<Tarefa>> listarTarefas(@RequestParam(required = false) String descricao) { 
        List<Tarefa> tarefas;
        if (descricao == null) { 
            tarefas = tarefaRepository.findAll(); 
        } else { 
            tarefas = tarefaRepository.findByDescricaoContaining(descricao); 
        }

        if (tarefas.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); 
        }
        return new ResponseEntity<>(tarefas, HttpStatus.OK); 
    }

    
    @GetMapping("/{id}") 
    public ResponseEntity<Tarefa> obterTarefaPorId(@PathVariable Long id) { 
        Optional<Tarefa> tarefa = tarefaRepository.findById(id);
        return tarefa.map(value -> new ResponseEntity<>(value, HttpStatus.OK)) 
                     .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND)); 
    }

    @PutMapping("/{id}") 
    public ResponseEntity<Tarefa> atualizarTarefa(@PathVariable Long id, @RequestBody Tarefa tarefaDetalhes) {
        return tarefaRepository.findById(id)
                .map(tarefaExistente -> { 
                    tarefaExistente.setDescricao(tarefaDetalhes.getDescricao());
                    tarefaExistente.setPrioridade(tarefaDetalhes.getPrioridade());
                    tarefaExistente.setConcluida(tarefaDetalhes.isConcluida()); 
                    Tarefa tarefaAtualizada = tarefaRepository.save(tarefaExistente);
                    return new ResponseEntity<>(tarefaAtualizada, HttpStatus.OK); 
                })
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND)); 
    }

    @DeleteMapping("/{id}") 
    public ResponseEntity<Void> deletarTarefa(@PathVariable Long id) {
        if (!tarefaRepository.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); 
        }
        tarefaRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT); 
    }

    @GetMapping("/pendentes") 
    public ResponseEntity<List<Tarefa>> listarTarefasPendentes() {
        List<Tarefa> tarefasPendentes = tarefaRepository.findByConcluida(false); 
        if (tarefasPendentes.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(tarefasPendentes, HttpStatus.OK);
    }

    @PatchMapping("/{id}/concluir") 
    public ResponseEntity<Tarefa> concluirTarefa(@PathVariable Long id) {
        return tarefaRepository.findById(id)
                .map(tarefa -> {
                    tarefa.setConcluida(true); 
                    Tarefa tarefaAtualizada = tarefaRepository.save(tarefa);
                    return new ResponseEntity<>(tarefaAtualizada, HttpStatus.OK); 
                })
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
