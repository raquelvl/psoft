# Laboratório - roteiro 1

**Lembrete**: use o site [https://start.spring.io](https://start.spring.io) para criar seu projeto spring. Selecione a dependência "Spring Web" na configuração do seu projeto.

Faça o unzip do arquivo zip gerado para o seu workspace. Na sua IDE de preferência (eclipse, IntelliJ, etc.) importe o projeto criado como um **projeto maven já existente**. Agora você já pode desenvolver sua primeira aplicação. Lembre de organizar os pacotes de controladores, serviços e entidades abaixo do pacote raíz.

Neste primeiro lab o design da API REST a ser desenvolvida será dado, exceto os [métodos HTTP](https://developer.mozilla.org/pt-BR/docs/Web/HTTP/Methods) a serem usados e os [códigos de retorno HTTP](https://developer.mozilla.org/en-US/docs/Web/HTTP/Status). Com o tempo, o ideal é que cada um pense seu próprio *design* em termos de que recursos são exposts e quais as rotas para estes recursos. 

Imagine que estamos criando o embrião de um sistema que é uma rede social de alunos para avaliar disciplinas de seu curso específico, por exemplo, computação. 

No contexto da API abaixo uma Disciplina é uma classe que tem os seguintes atributos: **id:int**, **nome:String** e **nota:double**.

Use spring boot/MVC e java para desenvolver a seguinte API (não usaremos banco de dados ainda, assim não haverá persistência de dados):

**\<METODO HTTP\> /v1/api/disciplinas**

Adiciona a disciplina no sistema. A própria API deve se encarregar de gerar os identificadores únicos das disciplinas. No corpo da requisição HTTP deve estar um JSON com as informações de nome e nota da disciplina a ser adicionada no sistema.

Retorna a disciplina que foi adicionada (incluindo o id). Um  e \<código de resposta HTTP\> (ver [https://developer.mozilla.org/en-US/docs/Web/HTTP/Status](https://developer.mozilla.org/en-US/docs/Web/HTTP/Status)).


**GET /v1/api/disciplinas (id numerico, nome, nota)**

Retorna um JSON com todas as disciplinas já inseridas no sistema e código 200.


**GET /v1/api/disciplinas/{id}**

Retorna um JSON que representa a disciplina cujo identificador único é id e código 200. Ou retorna JSON de disciplina nula e código 404 (not found) caso o id passado não tenha sido encontrado.


**PUT /v1/api/disciplinas/{id}/nome**

Atualiza o nome da disciplina de identificador id no sistema. No corpo da requisição HTTP deve estar um JSON com o novo nome da disciplina a ser atualizado no sistema.

Retorna a disciplina que foi atualizada (incluindo o id, nome e nota) e código 200. Ou não retorna JSON e código 404 (not found) caso o id passado não tenha sido encontrado.


**PUT /v1/api/disciplinas/{id}/nota**

Atualiza a nota da disciplina de identificador id no sistema. No corpo da requisição HTTP deve estar um JSON com a nova nota da disciplina a ser atualizada no sistema.

Retorna a disciplina que foi atualizada (incluindo o id, nome e nota) e código 200. Ou não retorna JSON e código 404 (not found) caso o id passado não tenha sido encontrado.


**DELETE /v1/api/disciplinas/{id}**

Remove a disciplina de identificador id do sistema e retorna a disciplina que foi removida (um JSON) e código 200. Ou não retorna JSON e retorna código 404 (para disciplina que não foi encontrada).


**GET /v1/api/disciplinas/ranking**

Retorna todas as disciplinas inseridas no sistema ordenadas pela nota (da maior para a menor) e código 200.


**Seguem algumas dicas...**

O desenvolvimento é um ciclo. Não desenvolva todas as funcionalidades de uma só vez. Siga os passos:

1. Desenvolva uma funcionalidade
2. Teste a funcionalidade usando postman ou outra ferramenta de sua preferência
3. Vá para a próxima funcionalidade volte para o passo 1

Você irá desenvolver as seguintes classes:

Classe que será o controlador do recurso /disciplinas e será marcada com @RestController
Classe de serviço (@Service) que oferece serviços ao controlador para gerenciar a coleção de disciplinas da API - o repositório das disciplinas aqui será o próprio serviço.
Classe que representa a Disciplina. 
Outras classes auxiliares para transferência, por exemplo, classe um DTO de disciplina sem o id (para quando a disciplina for ser adicionada)...

Execute a sua aplicação no terminal, dentro do diretório raiz do seu projeto com o seguinte comando (caso não esteja usando o DvTools)

$ ./mvnw spring-boot:run
