**Disciplina: Projeto de software<br>
Professores: Dalton Serey e Raquel Lopes<br>
Semestre: 2019.2**

# Laboratório 2: persistência de dados, autenticação e autorização
### Data para terminar:

### Objetivos:
* Aprender a escrever APIs com dados persistentes usando um esquema de dados relacional 

### Tecnologias envolvidas:
* ORM - Mapeamento objeto relacional (Hibernate é a impementação por trás do que usaremos)
* JPA - interface unificada para facilitar mapeamento de objetos para registros de tabelas
* JWT - tokans para autorização de acesso a dados

Lembrete: use o [spring initizlizr](https://start.spring.io) para criar seu projeto spring. Desas vez marque as dependências "_Spring Web Starter_", "_H2 Database_" e "_Spring Data JPA_" na configuração do seu projeto.

Faça o unzip do arquivo zip criado para o seu workspace. Na sua IDE de preferência (eclipse, IntelliJ, etc.) importe o projeto criado como um projeto maven. Agora você já pode desenvolver sua aplicação.

Neste segundo lab o design da API REST a ser desenvolvida será dado novamente, na verdade, é muito parecido com o primeiro. Continuaremos o desenvolvimento do primeiro lab no contexto de disciplinas. Mas agora vamos adicionar persistência, vamos iniciar todas as disciplinas de uma vez e vamos adicionar um pouco de segurança. Relembrando, por enquanto, no contexto da nossa API, uma **Disciplina** é uma classe que tem os seguintes atributos: **id:long**, **nome:String** e **nota:double**.

Temos um arquivo [JSON](./disciplinas.json) já com os nomes de todas as disciplinas que devem ser criadas. A ideia é programar sua API para povoar o banco de dados com todas as disciplinas já existentes. Neste documento estão as dicas de como fazer isso usando spring boot.

Use spring boot e java para desenvolver a seguinte API:

POST /v1/api/disciplinas 
Adiciona a disciplina no sistema. A própria API deve se encarregar de gerar os identificadores únicos das disciplinas. No corpo da requisição HTTP deve estar um JSON com as informações de nome e nota da disciplina a ser adicionada no sistema. 
Retorna a disciplina que foi adicionada (incluindo o id) e código 200.

POST /api/v1/usuarios (adiciona um usuario com email, nome e senha - o email é o login do usuario e deve ser um identificador único do sistema);
GET /api/v1/usuarios/{email} - recupera um usuário com determinado login (email)

GET /v1/api/disciplinas (id numerico, nome, nota)
Retorna um JSON com todas as disciplinas já inseridas no sistema e código 200.

GET /v1/api/disciplinas/:id
Retorna um JSON que representa a disciplina cujo identificador único é id e código 200. Ou não retorna JSON e código 404 (not found) caso o id passado não tenha sido encontrado.

PUT /v1/api/disciplinas/{id}/nome 
Atualiza o nome da disciplina de identificador id no sistema. No corpo da requisição HTTP deve estar um JSON com o novo nome da disciplina a ser atualizado no sistema. 
Retorna a disciplina que foi atualizada (incluindo o id, nome e nota) e código 200. Ou não retorna JSON e código 404 (not found) caso o id passado não tenha sido encontrado.

PUT /v1/api/disciplinas/{id}/nota 
Atualiza a nota da disciplina de identificador id no sistema. No corpo da requisição HTTP deve estar um JSON com a nova nota da disciplina a ser atualizada no sistema. 
Retorna a disciplina que foi atualizada (incluindo o id, nome e nota) e código 200. Ou não retorna JSON e código 404 (not found) caso o id passado não tenha sido encontrado.

DELETE /v1/api/disciplinas/{id}
Remove a disciplina de identificador id do sistema e retorna a disciplina que foi removida (um JSON) e código 200. Ou não retorna JSON e retorna código 404 (para disciplina que não foi encontrada).

GET /v1/api/disciplinas/ranking
Retorna todas as disciplinas inseridas no sistema ordenadas pela nota (da maior para a menor) e código 200.

Seguem algumas dicas...

Você irá desenvolver as seguintes classes:
Classe que será o controlador do recurso /disciplinas e será marcada com @RestController 
Classe de serviço (@Service) que oferece serviços ao controlador para gerenciar a coleção de disciplinas da API - o repositório das disciplinas pode ser um serviço aqui
Classe que representa a Disciplina 
Outras classes auxiliares para transferência, por exemplo, classe um DTO de disciplina sem o id (para quando a disciplina for ser adicionada)...

Execute a sua aplicação no termina, dentro do diretório raiz do seu projeto com o seguinte comando: 
$ ./mvnw spring-boot:run

Use Curl ou Postman para testar sua API. Desenvolve uma funcionalidade, teste, vá para a próxima…

