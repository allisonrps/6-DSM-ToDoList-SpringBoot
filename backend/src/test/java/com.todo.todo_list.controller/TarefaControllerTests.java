/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.todo.todo_list.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.todo.todo_list.entity.Tarefa;
import com.todo.todo_list.service.TarefaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// Inicializa o contexto do Spring para testar APENAS a camada web (Controller)
@WebMvcTest(TarefaController.class)
class TarefaControllerTest {

    // Injeta o MockMvc para simular as requisições HTTP
    @Autowired
    private MockMvc mockMvc; 

    // Simula o Serviço (dependência do Controller) para que a lógica não seja executada
    @MockBean
    private TarefaService service; 

    // Utilitário para converter objetos Java em JSON e vice-versa
    @Autowired
    private ObjectMapper objectMapper; 

    private Tarefa tarefaExemplo;
    private final String ENDPOINT = "/tarefas";

    @BeforeEach
    void setUp() {
        tarefaExemplo = new Tarefa();
        tarefaExemplo.setId(1L);
        tarefaExemplo.setNome("Teste de Controller");
        tarefaExemplo.setDescricao("Verificar requisições HTTP");
        tarefaExemplo.setStatus("PENDENTE");
    }

    @Test
    void deveCriarTarefaComPOSTERetornarStatus200() throws Exception {
        // Configuração do mock: simula que o serviço criou e retornou o objeto
        when(service.criar(any(Tarefa.class))).thenReturn(tarefaExemplo);

        // Simulação da requisição POST para /tarefas
        mockMvc.perform(post(ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(tarefaExemplo)))
                
                // Asserções: verifica status HTTP e conteúdo JSON
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome", is("Teste de Controller")))
                .andExpect(jsonPath("$.status", is("PENDENTE")));

        // Verifica se o método de serviço foi chamado UMA vez
        verify(service, times(1)).criar(any(Tarefa.class));
    }

    @Test
    void deveListarTodasAsTarefasComGETERetornarStatus200() throws Exception {
        List<Tarefa> lista = List.of(tarefaExemplo, new Tarefa());
        
        // Configuração do mock
        when(service.listar()).thenReturn(lista);

        // Simulação da requisição GET para /tarefas
        mockMvc.perform(get(ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON))

                // Asserções
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(2)))
                .andExpect(jsonPath("$[0].nome", is("Teste de Controller")));
        
        verify(service, times(1)).listar();
    }
    
    @Test
    void deveBuscarTarefaPorIdComGETERetornarStatus200() throws Exception {
        Long id = 1L;
        
        // Configuração do mock
        when(service.buscarPorId(id)).thenReturn(Optional.of(tarefaExemplo));

        // Simulação da requisição GET para /tarefas/1
        mockMvc.perform(get(ENDPOINT + "/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))

                // Asserções
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.descricao", is("Verificar requisições HTTP")));
    }

    @Test
    void deveAtualizarTarefaComPUTERetornarStatus200() throws Exception {
        tarefaExemplo.setNome("Tarefa Atualizada");
        Long id = 1L;
        
        // Configuração do mock
        when(service.atualizar(any(Tarefa.class))).thenReturn(tarefaExemplo);

        // Simulação da requisição PUT para /tarefas/1
        mockMvc.perform(put(ENDPOINT + "/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(tarefaExemplo)))

                // Asserções
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome", is("Tarefa Atualizada")));
    }

    @Test
    void deveDeletarTarefaComDELETEERetornarStatus200() throws Exception {
        Long id = 1L;
        
        // Simulação da requisição DELETE para /tarefas/1
        mockMvc.perform(delete(ENDPOINT + "/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))

                // Asserção: Status 200 (OK) para deleção bem sucedida (método void)
                .andExpect(status().isOk());
        
        // Verifica se o método de serviço (deletar) foi chamado
        verify(service, times(1)).deletar(id);
    }
}