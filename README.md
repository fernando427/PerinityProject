# PerinityProject

O Projeto tem o objetivo de construir uma aplicação com as seguintes funcionalidades:

![image](https://github.com/fernando427/PerinityProject/assets/48501389/c1992a20-3d24-42e6-ac99-8d60985c1bbf)
![image](https://github.com/fernando427/PerinityProject/assets/48501389/107ad331-d8c7-4ff2-9409-09ee380b3a46)

## Estrutura do Projeto

![image](https://github.com/fernando427/PerinityProject/assets/48501389/1a0921f0-67b5-455f-a895-65f4a5f49aa0)

## Estrutura do Postman

![image](https://github.com/fernando427/PerinityProject/assets/48501389/c5810e8b-ff65-43bf-9111-8e56bdca2fd4)

## Para iniciar o projeto

1. O usuário pode escolher entre usar um postgres local ou subir um contâiner postgres pelo terminal com o seguinte código:

`
docker-compose up --build
`

Lembrando sempre de respeitar a configuração no `application.properties` caso resolva usar um postgres local.

```
spring.application.name=PerinityProject
spring.datasource.url=jdbc:postgresql://localhost:5433/perinity
spring.datasource.username=postgres
spring.datasource.password=postgres
spring.datasource.driver-class-name=org.postgresql.Driver

spring.jpa.hibernate.ddl-auto=create-drop
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
```

2. Startar o projeto pelo `PerinityProjectApplication`

OBS: Para facilitar ao usuário, já há alguns dados sendo incrementados automaticamente no banco de dados

```
@PostConstruct
    public void startDB() {
        if (pessoaRepository.count() == 0) {
            Pessoa pessoaA = new Pessoa(null, "Tomas", "TI", null);
            Pessoa pessoaB = new Pessoa(null, "Agostinho", "Segurança", null);

            pessoaRepository.saveAll(List.of(pessoaA, pessoaB));
        }

        if (tarefaRepository.count() == 0) {
            Tarefa tarefaA = new Tarefa(null, "Procurar requisitos", "Procurar todos os requisitos", LocalDate.of(2024, 10, 1), 5, "PENDENTE", "TI", null);
            Tarefa tarefaB = new Tarefa(null, "Atualizar banco de dados", "Realizar atualização de drivers", LocalDate.of(2024, 6, 5), 7, "PENDENTE", "Segurança", null);
            Tarefa tarefaC = new Tarefa(null, "Desenvolvimento de requisitos", "Desenvolver todos os requisitos", LocalDate.of(2024, 6, 10), 10, "PENDENTE", "TI", null);
            Tarefa tarefaD = new Tarefa(null, "Criação de testes", "Criar testes das funcionalidades", LocalDate.of(2024, 8, 1), 11, "PENDENTE", "Segurança", null);

            tarefaRepository.saveAll(List.of(tarefaA, tarefaB, tarefaC, tarefaD));
        }
    }
```

## Endpoints da API

### Adicionar Pessoa

- **URL:** `/pessoas`
- **Método:** `POST`
- **Descrição:** Adiciona uma nova pessoa.
- **Parâmetros:**
  - `nome` (string): O nome do pessoa.
  - `departamento` (string): O departamento do pessoa.
- **Exemplo de Solicitação:**
  ```json
  {
    "nome": "James",
    "departamento": "ADM"
  }
- **Resposta de Sucesso**
  ```json
  {
    "id": 3,
    "nome": "James",
    "departamento": "ADM",
    "tarefas": null
  }

### Alterar Pessoa

- **URL:** `/pessoas/{id}`
- **Método:** `PUT`
- **Descrição:** Altera uma pessoa.
- **Parâmetros:**
  - `nome` (string): O nome do pessoa.
  - `departamento` (string): O departamento do pessoa.
- **Exemplo de Solicitação:**
  ```json
  {
    "nome": "Eduardo",
    "departamento": "ADM"
  }
- **Resposta de Sucesso**
  ```json
  {
    "id": 3,
    "nome": "Eduardo",
    "departamento": "ADM",
    "tarefas": null
  }

### Deletar Pessoa

- **URL:** `/pessoas/{id}`
- **Método:** `DELETE`
- **Descrição:** Deleta uma pessoa.
- **Parâmetros:**
  - `nome` (string): O nome do pessoa.
  - `departamento` (string): O departamento do pessoa.
- **Resposta de Sucesso**
  `No Content`

### Adicionar Tarefa

- **URL:** `/tarefas`
- **Método:** `POST`
- **Descrição:** Adiciona uma nova tarefa.
- **Parâmetros:**
  - `nome` (string): O titulo da tarefa.
  - `descricao` (string): A descricao da tarefa.
  - `prazo` (LocalDate): O prazo da tarefa.
  - `duracaoH` (integer): A duração em horas da tarefa.
  - `status` (string): O status da tarefa.
  - `departamento` (string): O departamento da tarefa.
- **Exemplo de Solicitação:**
  ```json
  {
      "titulo": "Trabalho 1",
      "descricao": "Você vai lá e trabalha",
      "prazo": "2024-02-01",
      "duracaoH": 12,
      "status": "PENDENTE",
      "departamento": "ADM"
  }
- **Resposta de Sucesso**
  ```json
  {
    "id": 5,
    "titulo": "Trabalho 1",
    "descricao": "Você vai lá e trabalha",
    "prazo": "2024-02-01",
    "duracaoH": 12,
    "status": "PENDENTE",
    "departamento": "ADM"
  }

### Adicionar Tarefa

- **URL:** `/tarefas/{tarefaId}/alocar/{pessoaId}`
- **Método:** `PUT`
- **Descrição:** Aloca uma tarefa para uma pessoa.
- **Resposta de Sucesso**
  ```json
  {
    "id": 6,
    "titulo": "Trabalho 1",
    "descricao": "Você vai lá e trabalha",
    "prazo": "2024-02-01",
    "duracaoH": 12,
    "status": "PENDENTE",
    "departamento": "ADM"
  }

### Finalizar Tarefa

- **URL:** `/tarefas/finalizar/{id}`
- **Método:** `PUT`
- **Descrição:** Finaliza uma tarefa.
- **Resposta de Sucesso**
  ```json
  {
    "id": 2,
    "titulo": "Atualizar banco de dados",
    "descricao": "Realizar atualização de drivers",
    "prazo": "2024-06-05",
    "duracaoH": 7,
    "status": "FINALIZADA",
    "departamento": "Segurança"
  }

### Buscar Pessoas com Total de Horas Gastas

- **URL:** `/pessoas`
- **Método:** `GET`
- **Descrição:** Listar pessoas trazendo nome, departamento, total horas gastas nas tarefas.
- **Resposta de Sucesso**
  ```json
    {
        "nome": "Agostinho",
        "departamento": "Segurança",
        "totalHorasGastas": 0
    },
    {
        "nome": "James",
        "departamento": "ADM",
        "totalHorasGastas": 12
    },
    {
        "nome": "Eduardo",
        "departamento": "Bolsa",
        "totalHorasGastas": 18
    }

### Buscar Pessoas com Período de Tempo

- **URL:** `/pessoas/gastos?nome={nomeDaPessoa}&startDate={YYYY-MM-DD}&endDate={YYYY-MM-DD}`
- **Método:** `GET`
- **Descrição:** Buscar pessoas por nome e período, retorna média de horas gastas por tarefa. Colocar o tempo inicial e o final respectivamente nos parâmetros.
- **Resposta de Sucesso**
  ```json
    {
        "nome": "Eduardo",
        "periodo": "2024-01-01 to 2024-12-31",
        "mediaHorasGastas": 12.0
    }

### Buscar Pessoas com Período de Tempo

- **URL:** `/tarefas/pendentes`
- **Método:** `GET`
- **Descrição:** Listar 3 tarefas que estejam sem pessoa alocada com os prazos mais antigos.
- **Resposta de Sucesso**
  ```json
    {
        "id": 5,
        "titulo": "Trabalho 1",
        "descricao": "Você vai lá e trabalha",
        "prazo": "2024-02-01",
        "duracaoH": 12,
        "status": "PENDENTE",
        "departamento": "ADM"
    },
    {
        "id": 2,
        "titulo": "Atualizar banco de dados",
        "descricao": "Realizar atualização de drivers",
        "prazo": "2024-06-05",
        "duracaoH": 7,
        "status": "PENDENTE",
        "departamento": "Segurança"
    },
    {
        "id": 3,
        "titulo": "Desenvolvimento de requisitos",
        "descricao": "Desenvolver todos os requisitos",
        "prazo": "2024-06-10",
        "duracaoH": 10,
        "status": "PENDENTE",
        "departamento": "TI"
    }

### Buscar Departamentos

- **URL:** `/tarefas/pendentes`
- **Método:** `GET`
- **Descrição:** Listar departamento e quantidade de pessoas e tarefas.
- **Resposta de Sucesso**
  ```json
    {
        "nome": "TI",
        "qntPessoas": 0,
        "qntTarefas": 2
    },
    {
        "nome": "ADM",
        "qntPessoas": 1,
        "qntTarefas": 2
    },
    {
        "nome": "Segurança",
        "qntPessoas": 1,
        "qntTarefas": 2
    }
  
### **OBS: A partir daqui esses dois métodos foram incluindos como forma de facilitar a visualização de todas as pessoas e tarefas no banco de dados.**

### Buscar Todas as Pessoas

- **URL:** `/pessoas/todas`
- **Método:** `GET`
- **Descrição:** Listar todas as pessoas.
- **Resposta de Sucesso**
  ```json
    {
        "id": 2,
        "nome": "Agostinho",
        "departamento": "Segurança",
        "tarefas": []
    },
    {
        "id": 3,
        "nome": "Eduardo",
        "departamento": "ADM",
        "tarefas": [
            {
                "id": 6,
                "titulo": "Trabalho 1",
                "descricao": "Você vai lá e trabalha",
                "prazo": "2024-02-01",
                "duracaoH": 12,
                "status": "PENDENTE",
                "departamento": "ADM"
            }
        ]
    }

### Buscar Todas as Tarefas

- **URL:** `/tarefas/todas`
- **Método:** `GET`
- **Descrição:** Listar todas as tarefas.
- **Resposta de Sucesso**
  ```json
    {
        "id": 2,
        "titulo": "Atualizar banco de dados",
        "descricao": "Realizar atualização de drivers",
        "prazo": "2024-06-05",
        "duracaoH": 7,
        "status": "PENDENTE",
        "departamento": "Segurança"
    },
    {
        "id": 3,
        "titulo": "Desenvolvimento de requisitos",
        "descricao": "Desenvolver todos os requisitos",
        "prazo": "2024-06-10",
        "duracaoH": 10,
        "status": "PENDENTE",
        "departamento": "TI"
    },
    {
        "id": 1,
        "titulo": "Procurar requisitos",
        "descricao": "Procurar todos os requisitos",
        "prazo": "2024-10-01",
        "duracaoH": 5,
        "status": "FINALIZADA",
        "departamento": "TI"
    }
