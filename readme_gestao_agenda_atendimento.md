# 🧠 Gestão de Agenda de Atendimento Psicológico

Este projeto é um sistema desktop em Java para gerenciamento de atendimentos psicológicos, com funcionalidades de agendamento, aceitação de pedidos, cancelamento e visualização de consultas — tudo persistido em arquivos CSV.

O sistema foi desenvolvido com arquitetura MVC, interface Swing e lógica de negócio robusta para garantir o não conflito de horários.

---

## 🎯 Objetivo

O objetivo principal é oferecer uma solução leve e funcional para controle de agendas entre **psicólogos** e **clientes**, com foco na **organização de consultas** e **gestão de pedidos de agendamento**.

---

## 🛠️ Tecnologias Utilizadas

- **Java 8+**
- **Swing (Java GUI)**
- **Arquitetura MVC**
- **Persistência em Arquivos CSV**
- **Collections e Streams API**

---

## 🗂️ Estrutura do Projeto

```
GestaoAgendaAtendimento/
│
├── src/
│   ├── model/             # Classes de domínio: Cliente, Psicologo, Consulta, PedidoAgendamento, etc.
│   ├── controller/        # Controladores para interação entre view e service
│   ├── service/           # Lógica de negócio e validações
│   ├── repository/        # Persistência e manipulação dos arquivos CSV
│   └── view/              # Telas Swing (Login, Cadastro, Menu, Agendamento, etc.)
│
├── data/                  # Arquivos CSV utilizados como banco de dados
│
├── README.md              # Documentação do projeto
└── ...
```

---

## ✅ Funcionalidades

### 👥 Clientes
- Cadastro e login
- Solicitação de agendamento com psicólogo
- Visualização de histórico de consultas

### 🧑‍⚕️ Psicólogos
- Cadastro e login
- Definição de carga horária de atendimento (ex: "Seg a Sab 08h-18h")
- Visualização e aceitação de pedidos pendentes
- Justificativa em caso de recusa
- Acompanhamento da agenda

### 🗓️ Agendamentos
- Geração automática de horários disponíveis com base no horário de trabalho
- Intervalos de 30min + 5min para anotações
- Evita sobreposição de pedidos e consultas
- Cancelamento de consultas pelo psicólogo

---

## ▶️ Como Executar

1. Clone o repositório:
```bash
git clone https://github.com/LuizHelio-Fim/GestaoAgendaAtendimento.git
```

2. Abra no seu IDE Java (Eclipse, IntelliJ, VS Code...)
3. Execute a classe `App.java` (ou `Main.java`) para iniciar o sistema via console (ou interface gráfica, se configurada)
4. Certifique-se de que a pasta `data/` exista com os arquivos CSV iniciais:
   - `clientes.csv`
   - `psicologos.csv`
   - `consultas.csv`
   - `Pedidos_agendamento.csv`

---

## 🧪 Testes e Validações

- Horários de agendamento são verificados contra conflitos automaticamente
- CPF dos usuários é único e validado na criação
- Apenas horários dentro da carga de trabalho definida aparecem para agendamento
- Persistência funcional: dados mantidos após reinício do sistema

---

## 📌 Próximas melhorias (TO-DO)

- Criptografia de senha dos usuários
- Exportação de relatórios (PDF ou CSV)
- Notificações por email (via API externa)
- Validação de CPF pela API da Receita
- Interface gráfica final com visual moderno (Swing ou JavaFX)

---

## 🧑‍💼 Autor

**Luiz Hélio**  
Estudante de Engenharia de Software — FAESA  
[LinkedIn](https://www.linkedin.com/in/luiz-helio/) | [GitHub](https://github.com/LuizHelio-Fim)

---

## 📝 Licença

Este projeto está licenciado sob a **MIT License** — sinta-se livre para usar, modificar e compartilhar com os devidos créditos.

