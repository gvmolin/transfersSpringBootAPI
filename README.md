# Transfer API

Este projeto é uma API desenvolvida com **Spring Boot** para gerenciar transferências bancárias entre usuários. Ele permite a criação de usuários, o agendamento de transferências e a execução de batch para atualização dos valores das transferências agendadas.

## Funcionalidades

- **Cadastro de Usuários**
- **Transferências Bancárias**
- **Agendamento de Transferências**
- **Batch de Atualização de Transferências**

## Tecnologias Utilizadas

- **Spring Boot 3.4.1**: Framework principal para desenvolvimento da API.
- **Spring Data JPA**: Para persistência de dados no banco de dados.
- **Spring Web**: Para criação de endpoints RESTful.
- **H2 Database**: Banco de dados em memória para desenvolvimento.
- **ModelMapper**: Para mapeamento de objetos entre diferentes camadas da aplicação.
- **Lombok**: Para reduzir a verbosidade do código com a geração automática de getters, setters e construtores.
- **Kafka**: Para gerenciar filas.
- **Maven**: Gerenciador de dependências e build.

## Como Executar o Projeto

1. Clone o repositório:

   ```bash
   git clone https://github.com/gvmolin/transfersSpringBootAPI
   ```

2. Entre no diretório do projeto:

   ```bash
   cd transfersApi
   ```

3. Inicie o kafka dentro de um container Docker:

   ```bash
   docker-compose up -d
   ```

4. Compile o projeto e execute a aplicação:

   ```bash
   mvn spring-boot:run
   ```

A API estará disponível em `http://localhost:8080`.

## Progresso geral

### Feito
- Estrutura inicial ( Spring + JPA + Spring Web + H2 + Lombok)
- Estrutura de usuários (Models, DTOs, Repository, Controller, Service, etc)
- Validação inicial de usuários
- Estrutura de transferências
- Formulário de criação de transferências (Models, DTOs, Repository, Controller, Service, etc)
- Rota para conferir saldo e taxas de transferência, dentro a estrutura de transferências
- Validação inicial de transferências
- Batch com tempo programável para a realização de transferências agendadadas
- Regras de negócio facilmente editáveis aplicadas aos calculos de taxa por período
- Implementação de filas e mensageria focado em escalar a organização de eventos de criação de transferências e atualizaçao das contas.

### A fazer
- Banco de dados em disco rígido
- Melhorias nas validações de dados
- Classe de retorno padrão
- Sistema de paginação por query
- Sistema de filtragem por query
- Sistema de ordenação por query
- Sistema de validação
- Precisão do motor de calculos
- Sistema de autenticação
- Registro de eventos
- Sistema mais robusto para criação das queries.
- Tabela de eventos
