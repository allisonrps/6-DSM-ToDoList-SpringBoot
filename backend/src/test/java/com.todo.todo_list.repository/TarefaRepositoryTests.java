/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author allis
 */
package com.todo.todo_list.repository;

import com.todo.todo_list.entity.Tarefa;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

// Anotação essencial: configura o Spring para testar APENAS a camada JPA.
// Isso inclui o EntityManager e o DataSource configurado.
@DataJpaTest
class TarefaRepositoryTest {

    @Autowired
    private TarefaRepository repository;

    // Utilitário para realizar operações de BD diretas (como persistir antes do teste)
    @Autowired
    private TestEntityManager entityManager;

    @Test
    void deveSalvarUmaNovaTarefa() {
        // 1. Cria o objeto
        Tarefa novaTarefa = new Tarefa();
        novaTarefa.setNome("Configurar Testes Unitários");
        novaTarefa.setDescricao("Garantir cobertura do Service e Controller.");
        novaTarefa.setStatus("PENDENTE");

        // 2. Salva no banco de dados
        Tarefa tarefaSalva = repository.save(novaTarefa);

        // 3. Verifica se a entidade foi persistida corretamente
        assertThat(tarefaSalva).isNotNull();
        // Verifica se o ID foi gerado pelo banco de dados (SERIAL PRIMARY KEY)
        assertThat(tarefaSalva.getId()).isNotNull(); 
        assertThat(tarefaSalva.getNome()).isEqualTo("Configurar Testes Unitários");
        
        // Verifica se as datas foram preenchidas automaticamente
        assertThat(tarefaSalva.getDataCriacao()).isNotNull();
        assertThat(tarefaSalva.getDataAtualizacao()).isNotNull();
    }

    @Test
    void deveBuscarTarefaPorId() {
        // 1. Prepara o banco de dados: Persiste diretamente via EntityManager
        Tarefa tarefaPersistida = new Tarefa();
        tarefaPersistida.setNome("Teste de Busca");
        tarefaPersistida.setStatus("PENDENTE");
        
        // O EntityManager garante que a transação seja comitada para a busca
        tarefaPersistida = entityManager.persistAndFlush(tarefaPersistida);
        Long id = tarefaPersistida.getId();

        // 2. Executa a busca
        Optional<Tarefa> resultado = repository.findById(id);

        // 3. Verifica
        assertTrue(resultado.isPresent());
        assertThat(resultado.get().getNome()).isEqualTo("Teste de Busca");
    }
    
    @Test
    void deveAtualizarOStatusDaTarefa() {
        // 1. Prepara
        Tarefa tarefaPersistida = new Tarefa();
        tarefaPersistida.setNome("Tarefa Antiga");
        tarefaPersistida.setStatus("PENDENTE");
        tarefaPersistida.setDataAtualizacao(LocalDateTime.now().minusHours(1)); // Data antiga
        
        tarefaPersistida = entityManager.persistAndFlush(tarefaPersistida);
        
        // 2. Modifica a entidade
        tarefaPersistida.setStatus("CONCLUIDA");
        tarefaPersistida.setDataAtualizacao(LocalDateTime.now()); // Simula a atualização do Service
        
        // 3. Salva e executa a busca
        repository.save(tarefaPersistida);
        Optional<Tarefa> resultado = repository.findById(tarefaPersistida.getId());
        
        // 4. Verifica
        assertTrue(resultado.isPresent());
        assertThat(resultado.get().getStatus()).isEqualTo("CONCLUIDA");
        
        // Embora a comparação de datas seja complexa em testes, verificamos se ela existe
        assertThat(resultado.get().getDataAtualizacao()).isNotNull(); 
    }
    
    @Test
    void deveDeletarTarefaPorId() {
        // 1. Prepara
        Tarefa tarefa1 = entityManager.persistAndFlush(new Tarefa());
        tarefa1.setNome("Tarefa para deletar");
        Tarefa tarefa2 = entityManager.persistAndFlush(new Tarefa());
        tarefa2.setNome("Tarefa para manter");

        // 2. Executa a deleção
        repository.deleteById(tarefa1.getId());
        repository.flush(); // Garante que a operação foi executada no BD

        // 3. Verifica
        // Verifica se a tarefa 1 foi deletada
        assertThat(repository.findById(tarefa1.getId())).isEmpty(); 
        // Verifica se a tarefa 2 ainda existe
        assertThat(repository.findById(tarefa2.getId())).isPresent(); 
    }
}
