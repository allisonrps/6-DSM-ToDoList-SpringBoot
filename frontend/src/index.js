import React from 'react';
import ReactDOM from 'react-dom/client';
import App from './App'; // Importa o seu componente principal
import './index.css';

// 1. Cria a raiz da aplicação usando o novo Client API
const root = ReactDOM.createRoot(document.getElementById('root'));

// 2. Renderiza o componente principal (App) dentro do modo estrito
root.render(
  <React.StrictMode>
    <App />
  </React.StrictMode>
);

// Nota: O 'root' é o elemento HTML (geralmente uma div)
// que está no seu arquivo 'public/index.html'