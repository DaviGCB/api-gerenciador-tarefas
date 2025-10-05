package com.example.taferas_api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController // Anotação que combina @Controller e @ResponseBody, indicando que os retornos dos métodos serão o corpo da resposta HTTP
public class TarefaController {

    @Autowired // Injeção de dependência: o Spring gerencia e fornece uma instância do TarefaRepository
    private TarefaRepository tarefaRepository;

    // Endpoint para criar uma nova tarefa [cite: 41]
    @PostMapping("/") // Mapeia requisições HTTP POST para a URL raiz do controlador 
    public ResponseEntity<Tarefa> criarTarefa(@RequestBody Tarefa tarefa) { // @RequestBody converte o JSON do corpo da requisição para um objeto Tarefa
        // A tarefa recebida ainda não tem o campo 'concluida' definido, por padrão será false.
        Tarefa novaTarefa = tarefaRepository.save(tarefa); // Salva a tarefa no banco de dados
        return new ResponseEntity<>(novaTarefa, HttpStatus.CREATED); // Retorna a tarefa criada com o status 201 Created 
    }

    // Endpoint para listar todas as tarefas com filtro opcional por descrição 
    @GetMapping("/") // Mapeia requisições HTTP GET para a URL raiz do controlador 
    public ResponseEntity<List<Tarefa>> listarTarefas(@RequestParam(required = false) String descricao) { // @RequestParam busca um parâmetro na URL (ex: /?descricao=texto) 
        List<Tarefa> tarefas;
        if (descricao == null) { // Se não houver filtro
            tarefas = tarefaRepository.findAll(); // Busca todas as tarefas
        } else { // Se houver filtro
            tarefas = tarefaRepository.findByDescricaoContaining(descricao); // Busca por descrição
        }

        if (tarefas.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Retorna 204 No Content se a lista estiver vazia [cite: 45, 46]
        }
        return new ResponseEntity<>(tarefas, HttpStatus.OK); // Retorna a lista com status 200 OK
    }

    // Endpoint para obter uma tarefa pelo seu ID [cite: 47]
    @GetMapping("/{id}") // Mapeia requisições GET para URLs como /1, /2, etc. [cite: 47]
    public ResponseEntity<Tarefa> obterTarefaPorId(@PathVariable Long id) { // @PathVariable pega o valor do {id} da URL
        Optional<Tarefa> tarefa = tarefaRepository.findById(id);
        return tarefa.map(value -> new ResponseEntity<>(value, HttpStatus.OK)) // Se encontrar, retorna a tarefa com status 200 OK
                     .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND)); // Se não encontrar, retorna 404 Not Found [cite: 47]
    }

    // Endpoint para atualizar uma tarefa completa [cite: 48]
    @PutMapping("/{id}") // Mapeia requisições HTTP PUT [cite: 49]
    public ResponseEntity<Tarefa> atualizarTarefa(@PathVariable Long id, @RequestBody Tarefa tarefaDetalhes) {
        return tarefaRepository.findById(id)
                .map(tarefaExistente -> { // Se encontrou a tarefa com o ID fornecido
                    tarefaExistente.setDescricao(tarefaDetalhes.getDescricao());
                    tarefaExistente.setPrioridade(tarefaDetalhes.getPrioridade());
                    tarefaExistente.setConcluida(tarefaDetalhes.isConcluida()); // Atualiza todos os campos [cite: 50]
                    Tarefa tarefaAtualizada = tarefaRepository.save(tarefaExistente);
                    return new ResponseEntity<>(tarefaAtualizada, HttpStatus.OK); // Retorna a tarefa atualizada [cite: 51]
                })
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND)); // Se não encontrou, retorna 404
    }

    // Endpoint para deletar uma tarefa pelo seu ID [cite: 52]
    @DeleteMapping("/{id}") // Mapeia requisições HTTP DELETE [cite: 52]
    public ResponseEntity<Void> deletarTarefa(@PathVariable Long id) {
        if (!tarefaRepository.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Retorna 404 se tentar deletar algo que não existe
        }
        tarefaRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Retorna status 204 No Content em caso de sucesso [cite: 52]
    }

    // Endpoint para listar apenas as tarefas não concluídas [cite: 53]
    @GetMapping("/pendentes") // Mapeia requisições GET para /pendentes [cite: 53]
    public ResponseEntity<List<Tarefa>> listarTarefasPendentes() {
        List<Tarefa> tarefasPendentes = tarefaRepository.findByConcluida(false); // Usa o método customizado do repositório
        if (tarefasPendentes.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(tarefasPendentes, HttpStatus.OK);
    }

    // Endpoint para marcar uma tarefa como concluída [cite: 54]
    @PatchMapping("/{id}/concluir") // Mapeia requisições HTTP PATCH para /id/concluir [cite: 55]
    public ResponseEntity<Tarefa> concluirTarefa(@PathVariable Long id) {
        return tarefaRepository.findById(id)
                .map(tarefa -> {
                    tarefa.setConcluida(true); // Altera apenas o campo 'concluida' para true [cite: 56]
                    Tarefa tarefaAtualizada = tarefaRepository.save(tarefa);
                    return new ResponseEntity<>(tarefaAtualizada, HttpStatus.OK); // Retorna a tarefa atualizada [cite: 57]
                })
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
