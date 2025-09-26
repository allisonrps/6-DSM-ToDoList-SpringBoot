import React, { useState, useEffect } from 'react';
import axios from 'axios';
import TarefaItem from './TarefaItem';

const API_URL = 'http://localhost:8080/tarefas';

function App() {
    const [tarefas, setTarefas] = useState([]);
    
    // Estados para o formulário de criação
    const [novaTarefaNome, setNovaTarefaNome] = useState('');
    const [novaTarefaDescricao, setNovaTarefaDescricao] = useState('');
    const [novaTarefaObservacoes, setNovaTarefaObservacoes] = useState('');

    useEffect(() => {
        fetchTarefas();
    }, []);

    const fetchTarefas = async () => {
        try {
            const response = await axios.get(API_URL);
            setTarefas(response.data);
        } catch (error) {
            console.error("Erro ao carregar tarefas. O backend está rodando?", error);
            setTarefas([{ id: 'error', nome: 'Erro de conexão com o backend.', status: 'PENDENTE' }]);
        }
    };

    const handleCriarTarefa = async (e) => {
        e.preventDefault();
        if (novaTarefaNome.trim() === '') return;

        const novaTarefa = {
            nome: novaTarefaNome,
            descricao: novaTarefaDescricao, 
            status: 'PENDENTE',
            observacoes: novaTarefaObservacoes,
        };

        try {
            const response = await axios.post(API_URL, novaTarefa);
            setTarefas([...tarefas, response.data]);
            
            // Limpa todos os campos
            setNovaTarefaNome('');
            setNovaTarefaDescricao('');
            setNovaTarefaObservacoes('');

        } catch (error) {
            console.error("Erro ao criar tarefa:", error);
            alert("Erro ao criar tarefa. Verifique o console e o backend.");
        }
    };

    const handleUpdate = (tarefaAtualizada) => {
        setTarefas(
            tarefas.map(t => (t.id === tarefaAtualizada.id ? tarefaAtualizada : t))
        );
    };

    const handleDelete = (idTarefaDeletada) => {
        setTarefas(tarefas.filter(t => t.id !== idTarefaDeletada));
    };

    return (
        <div className="container">
            <h1>TO DO LIST (Spring Boot + React)</h1>
            
            {/* FORMULÁRIO */}
            <form onSubmit={handleCriarTarefa}>
                
                <input
                    type="text"
                    placeholder="Nome da Tarefa (Obrigatório)"
                    value={novaTarefaNome}
                    onChange={(e) => setNovaTarefaNome(e.target.value)}
                    required
                    style={{ gridColumn: 'span 2' }}
                />
                
                <input
                    type="text"
                    placeholder="Descrição"
                    value={novaTarefaDescricao}
                    onChange={(e) => setNovaTarefaDescricao(e.target.value)}
                />

                <input
                    type="text"
                    placeholder="Observações"
                    value={novaTarefaObservacoes}
                    onChange={(e) => setNovaTarefaObservacoes(e.target.value)}
                />
                
                <button 
                    type="submit"
                    style={{ gridColumn: 'span 2' }}
                >
                    Adicionar Nova Tarefa
                </button>

            </form>

            {/* LISTA DE TAREFAS */}
            <ul>
                {tarefas.map(tarefa => (
                    <TarefaItem
                        key={tarefa.id}
                        tarefa={tarefa}
                        onUpdate={handleUpdate}
                        onDelete={handleDelete}
                    />
                ))}
            </ul>
        </div>
    );
}

export default App;