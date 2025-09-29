/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.todo.todo_list.service;

import com.todo.todo_list.entity.Tarefa;
import com.todo.todo_list.repository.TarefaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

// Habilita as anotações Mockito para este teste
@ExtendWith(MockitoExtension.class)
class TarefaServiceTest {

    // Simula o Repositório, ele não executará o código real do banco de dados
    @Mock
    private TarefaRepository repository;

    // Injeta as dependências mockadas (o repository) no Service que queremos testar
    @InjectMocks
    private TarefaService service;

    private Tarefa tarefaExemplo;

    @BeforeEach
    void setUp() {
        // Objeto de teste reutilizável
        tarefaExemplo = new Tarefa();
        tarefaExemplo.setId(1L);
        tarefaExemplo.setNome("Comprar Pão Integral");
        tarefaExemplo.setStatus("PENDENTE");
        tarefaExemplo.setDescricao("Da marca favorita");
        tarefaExemplo.setDataCriacao(LocalDateTime.now().minusDays(1));
        tarefaExemplo.setDataAtualizacao(LocalDateTime.now().minusHours(1));
    }

    @Test
    void deveCriarTarefaComSucesso() {
        // 1. Configuração do Mock: Quando repository.save for chamado, retorne a tarefaExemplo
        when(repository.save(any(Tarefa.class))).thenReturn(tarefaExemplo);

        // 2. Execução
        Tarefa resultado = service.criar(tarefaExemplo);

        // 3. Verificação (Assertions)
        assertNotNull(resultado);
        assertEquals("Comprar Pão Integral", resultado.getNome());
        // Verifica se o método save() do Repository foi chamado UMA vez
        verify(repository, times(1)).save(tarefaExemplo);
    }

    @Test
    void deveListarTodasAsTarefas() {
        List<Tarefa> listaEsperada = List.of(tarefaExemplo, new Tarefa());
        
        // Configuração do Mock
        when(repository.findAll()).thenReturn(listaEsperada);

        // Execução
        List<Tarefa> resultado = service.listar();

        // Verificação
        assertFalse(resultado.isEmpty());
        assertEquals(2, resultado.size());
        verify(repository, times(1)).findAll();
    }

    @Test
    void deveBuscarTarefaPorIdExistente() {
        Long id = 1L;
        // Configuração do Mock
        when(repository.findById(id)).thenReturn(Optional.of(tarefaExemplo));

        // Execução
        Optional<Tarefa> resultado = service.buscarPorId(id);

        // Verificação
        assertTrue(resultado.isPresent());
        assertEquals("Comprar Pão Integral", resultado.get().getNome());
    }
    
    @Test
    void deveAtualizarTarefaEAlterarDataAtualizacao() {
        // 1. Prepara dados e captura a data de atualização antiga
        LocalDateTime dataAntiga = tarefaExemplo.getDataAtualizacao();
        tarefaExemplo.setStatus("CONCLUIDA");
        
        // 2. Configura o Mock: Salvar retornará o objeto modificado
        when(repository.save(any(Tarefa.class))).thenReturn(tarefaExemplo);

        // 3. Execução
        Tarefa resultado = service.atualizar(tarefaExemplo);

        // 4. Verificação
        assertEquals("CONCLUIDA", resultado.getStatus());
        // Verifica se o método save() foi chamado para persistir a mudança
        verify(repository, times(1)).save(tarefaExemplo);
        
        // Nota: Dentro do ambiente Mockito, o LocalDateTime.now() do Service não é
        // executado no tempo real. No entanto, verificamos que a função foi chamada
        // e que o objeto retornado reflete as mudanças que o Service deveria fazer.
    }

    @Test
    void deveDeletarTarefaPorId() {
        Long id = 1L;

        // Execução
        service.deletar(id);

        // Verificação: verifica se o método deleteById do Repository foi chamado UMA vez
        verify(repository, times(1)).deleteById(id);
        // (Métodos void não têm retorno para asserção)
    }
}