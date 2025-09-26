import React, { useState } from 'react';
import axios from 'axios';

const API_URL = 'http://localhost:8080/tarefas';

// Função auxiliar para formatar datas
const formatarData = (dataString) => {
    if (!dataString) return 'N/A';
    const data = new Date(dataString);
    // Formato: DD/MM/AAAA HH:MM:SS
    return data.toLocaleString('pt-BR', { year: 'numeric', month: '2-digit', day: '2-digit', hour: '2-digit', minute: '2-digit', second: '2-digit' });
};

function TarefaItem({ tarefa, onUpdate, onDelete }) {
    const [isEditing, setIsEditing] = useState(false);
    
    // Estados para todos os campos
    const [novoNome, setNovoNome] = useState(tarefa.nome);
    const [novaDescricao, setNovaDescricao] = useState(tarefa.descricao || ''); 
    const [novaObservacoes, setNovaObservacoes] = useState(tarefa.observacoes || '');

    // --- LÓGICA DE MANIPULAÇÃO (DELETE, TOGGLE, SAVE) ---
    const handleDelete = async () => {
        try {
            await axios.delete(`${API_URL}/${tarefa.id}`);
            onDelete(tarefa.id);
        } catch (error) {
            console.error("Erro ao deletar a tarefa:", error);
            alert("Erro ao deletar. Verifique o console e o backend.");
        }
    };

    const handleToggleStatus = async () => {
        const novoStatus = tarefa.status === 'CONCLUIDA' ? 'PENDENTE' : 'CONCLUIDA';
        const tarefaAtualizada = { ...tarefa, status: novoStatus };

        try {
            const response = await axios.put(`${API_URL}/${tarefa.id}`, tarefaAtualizada);
            onUpdate(response.data);
        } catch (error) {
            console.error("Erro ao atualizar o status:", error);
            alert("Erro ao atualizar status. Verifique o console.");
        }
    };

    const handleSaveEdit = async () => {
        const tarefaAtualizada = { 
            ...tarefa, 
            nome: novoNome,
            descricao: novaDescricao, 
            observacoes: novaObservacoes
        };

        try {
            const response = await axios.put(`${API_URL}/${tarefa.id}`, tarefaAtualizada);
            onUpdate(response.data);
            setIsEditing(false);
        } catch (error) {
            console.error("Erro ao salvar a edição:", error);
            alert("Erro ao salvar. Verifique o console.");
        }
    };
    // -----------------------------------------------------

    const statusColor = tarefa.status === 'CONCLUIDA' ? 'var(--cor-sucesso)' : 'var(--cor-principal)';

    return (
        <li className={tarefa.status === 'CONCLUIDA' ? 'tarefa-concluida' : ''}>
            
            <div onClick={() => !isEditing && setIsEditing(true)}>
                {isEditing ? (
                    // 1. ÁREA DE EDIÇÃO (Input simples, cada um na sua linha por causa do CSS)
                    <div style={{ paddingBottom: '10px' }}>
                        <input type="text" value={novoNome} onChange={(e) => setNovoNome(e.target.value)} placeholder="NOME" autoFocus />
                        <input type="text" value={novaDescricao} onChange={(e) => setNovaDescricao(e.target.value)} placeholder="DESCRIÇÃO" />
                        <input type="text" value={novaObservacoes} onChange={(e) => setNovaObservacoes(e.target.value)} placeholder="OBSERVAÇÃO" />
                        
                        {/* Botão Salvar no modo de edição */}
                        <div style={{ display: 'flex', justifyContent: 'flex-end', marginTop: '10px' }}>
                             <button onClick={handleSaveEdit} style={{ backgroundColor: 'var(--cor-principal)', flexGrow: 0 }}>
                                Salvar
                            </button>
                        </div>
                    </div>
                ) : (
                    // 2. ÁREA DE VISUALIZAÇÃO (Cada campo em uma linha)
                    <div style={{ cursor: 'pointer', paddingBottom: '10px' }}>
                        
                        {/* NOME */}
                        <p style={{ fontWeight: 'bold', fontSize: '1.2em', margin: '0 0 10px 0' }}> 
                            NOME: {tarefa.nome}
                        </p>
                        
                        {/* DESCRIÇÃO */}
                        {tarefa.descricao && <p style={{ margin: '0 0 10px 0', fontSize: '0.9em' }}>
                            DESCRIÇÃO: {tarefa.descricao}
                        </p>}
                        
                        {/* OBSERVAÇÃO */}
                        {tarefa.observacoes && <p style={{ margin: '0 0 10px 0', fontSize: '0.9em' }}>
                            OBSERVAÇÃO: {tarefa.observacoes}
                        </p>}

                        {/* STATUS */}
                        <div style={{ display: 'flex', alignItems: 'center', margin: '0 0 10px 0' }}>
                            <span style={{ fontWeight: 'bold', fontSize: '0.9em', marginRight: '8px' }}>STATUS:</span>
                            <span style={{ padding: '4px 10px', borderRadius: '4px', color: 'white', backgroundColor: statusColor, fontSize: '0.8em' }}>
                                {tarefa.status}
                            </span>
                        </div>
                        
                        {/* DATAS (Linha dividida) */}
                        <div style={{ display: 'flex', justifyContent: 'space-between', fontSize: '0.75em', color: '#666' }}>
                            <span>
                                **CRIADO EM:** {formatarData(tarefa.dataCriacao)}
                            </span>
                            <span>
                                **ATUALIZADO EM:** {formatarData(tarefa.dataAtualizacao)}
                            </span>
                        </div>
                    </div>
                )}
            </div>

            {/* 3. BOTÕES DE AÇÃO (Linha única no rodapé, só aparece se não estiver editando) */}
            <div style={{ 
                justifyContent: isEditing ? 'flex-end' : 'space-between',
            }}>
                
                {!isEditing && (
                    <>
                        <button 
                            onClick={handleToggleStatus} 
                            style={{ backgroundColor: tarefa.status === 'CONCLUIDA' ? '#a29bfe' : 'var(--cor-sucesso)' }}
                        >
                            {tarefa.status === 'CONCLUIDA' ? 'Reabrir' : 'Concluir Tarefa'}
                        </button>
                        <button 
                            onClick={() => setIsEditing(true)} 
                            style={{ backgroundColor: '#636e72' }}
                        >
                            Editar Detalhes
                        </button>
                        <button 
                            onClick={handleDelete} 
                            style={{ backgroundColor: 'var(--cor-erro)' }}
                        >
                            Deletar
                        </button>
                    </>
                )}
            </div>
        </li>
    );
}

export default TarefaItem;