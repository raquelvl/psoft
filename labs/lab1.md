# Laboratório - roteiro 1 - nossa primeira API REST

**Lembrete**: Ao criar seu projeto spring/java, selecione as dependência "DevTools", "Lombok" e "Spring Web" na criação do seu projeto. Lembre de selecionar maven como gerenciador de dependências e de empacotar em jar.

Neste primeiro lab o design da API REST a ser desenvolvida será dado, exceto os [métodos HTTP](https://developer.mozilla.org/pt-BR/docs/Web/HTTP/Methods) a serem usados e os [códigos de retorno HTTP](https://developer.mozilla.org/en-US/docs/Web/HTTP/Status). Com o tempo, o ideal é que cada um pense seu próprio *design* em termos de que recursos são expostos e quais as rotas para estes recursos. 

Imagine que estamos criando o embrião de um sistema que é uma rede social de alunos para avaliar e falar sobre disciplinas de seu curso específico, por exemplo, computação. 

No contexto da API abaixo uma Disciplina é uma entidade que tem os seguintes atributos: **id:int**, **nome:String**, **likes:int** e **notas:List<double>**. A nota da disciplina vem de uma média de notas atribuídas à disciplina pelos alunos. As disciplinas do sistema não podem ter nomes repetidos. 

Pensando apenas no recurso /disciplinas, use spring boot/MVC e java para desenvolver a seguinte API (não usaremos banco de dados ainda, assim não haverá persistência de dados):

**\<METODO HTTP\> /v1/api/disciplinas**

Adiciona a disciplina no sistema. A própria API deve se encarregar de gerar os identificadores únicos das disciplinas. No corpo da requisição HTTP deve estar um JSON com as informações de nome da disciplina a ser adicionada no sistema.

Retorna a disciplina que foi adicionada (incluindo o id gerado pelo sistema) e o \<código de resposta HTTP\> (ver [https://developer.mozilla.org/en-US/docs/Web/HTTP/Status](https://developer.mozilla.org/en-US/docs/Web/HTTP/Status)).


**\<METODO HTTP\> /v1/api/disciplinas (id numerico, nome, likes, nota)**

Retorna um JSON com todas as disciplinas já inseridas no sistema e o \<código de resposta HTTP\> (ver [https://developer.mozilla.org/en-US/docs/Web/HTTP/Status](https://developer.mozilla.org/en-US/docs/Web/HTTP/Status)). As disciplinas retornadas devem conter o id, nome, número de likes, a nota da disciplina (média de todas as notas da disciplina).


**\<METODO HTTP\> /v1/api/disciplinas/{id}**

Retorna um JSON que representa a disciplina cujo identificador único é id e o \<código de resposta HTTP\> (ver [https://developer.mozilla.org/en-US/docs/Web/HTTP/Status](https://developer.mozilla.org/en-US/docs/Web/HTTP/Status)). A disciplina retornada deve ter id, nome, número de likes e nota média. Pense em todas as possibilidades de erro e programe-se para elas com seus devidos códigos de resposta HTTP.


**\<METODO HTTP\> /v1/api/disciplinas/{id}/nome**

Atualiza o nome da disciplina de identificador id no sistema. No corpo da requisição HTTP deve estar um JSON com o novo nome da disciplina a ser atualizado no sistema.

Retorna a disciplina que foi atualizada (incluindo o id, novo nome, número de likes e nota média) e o \<código de resposta HTTP\> (ver [https://developer.mozilla.org/en-US/docs/Web/HTTP/Status](https://developer.mozilla.org/en-US/docs/Web/HTTP/Status)). Pense em todas as possibilidades de erro e programe-se para elas com seus devidos códigos.


**\<METODO HTTP\> /v1/api/disciplinas/{id}/nota**

Adiciona uma nota à disciplina de identificador id no sistema. No corpo da requisição HTTP deve estar um JSON com a nova nota da disciplina a ser adicionada no sistema.

Retorna a disciplina que foi atualizada (incluindo o id, nome, número de likes e a nova nota média) e o \<código de resposta HTTP\> (ver [https://developer.mozilla.org/en-US/docs/Web/HTTP/Status](https://developer.mozilla.org/en-US/docs/Web/HTTP/Status)). Pense em todas as possibilidades de erro e programe-se para elas com seus devidos códigos.

**\<METODO HTTP\> /v1/api/disciplinas/{id}/like**

Adiciona um like à disciplina de identificador id no sistema. Retorna a disciplina que foi atualizada (incluindo o id, nome, novo número de likes e a nota média) e o \<código de resposta HTTP\> (ver [https://developer.mozilla.org/en-US/docs/Web/HTTP/Status](https://developer.mozilla.org/en-US/docs/Web/HTTP/Status)). Pense em todas as possibilidades de erro e programe-se para elas com seus devidos códigos.

**\<METODO HTTP\> /v1/api/disciplinas/{id}**

Remove a disciplina de identificador id do sistema e retorna a disciplina que foi removida (um JSON) e o \<código de resposta HTTP\> (ver [https://developer.mozilla.org/en-US/docs/Web/HTTP/Status](https://developer.mozilla.org/en-US/docs/Web/HTTP/Status)). Pense em todas as possibilidades de erro e programe-se para elas com seus devidos códigos.


**\<METODO HTTP\> /v1/api/disciplinas/ranking**

Retorna todas as disciplinas inseridas no sistema ordenadas pela nota média (da maior para a menor) e o \<código de resposta HTTP\> (ver [https://developer.mozilla.org/en-US/docs/Web/HTTP/Status](https://developer.mozilla.org/en-US/docs/Web/HTTP/Status)).


**Seguem algumas dicas...**

O desenvolvimento é um ciclo. Não desenvolva todas as funcionalidades de uma só vez. Siga os passos:

1. Desenvolva uma funcionalidade
2. Teste a funcionalidade usando postman ou outra ferramenta de sua preferência
3. Vá para a próxima funcionalidade volte para o passo 1
4. Lembrem que o controlador conhece o serviço e o serviço conhece o repositório de disciplinas, temos essas camadas sempre!
5. Lembrem que cada método do controlador tem apenas 1 linha de código
6. Neste lab não estamos usando persistência ainda

Você irá desenvolver as seguintes classes:

Classe que será o controlador do recurso /disciplinas e será marcada com @RestController
Classe de serviço (@Service) que oferece serviços ao controlador para gerenciar a coleção de disciplinas da API - o repositório das disciplinas aqui será o próprio serviço.
Classe que representa a Disciplina. 
Outras classes auxiliares para transferência, por exemplo, classe um DTO de disciplina só com nome (para quando a disciplina for ser adicionada), uma com id, nome, número de likes e nota média...

Execute a sua aplicação na ide ou no terminal, dentro do diretório raiz do seu projeto com o seguinte comando (caso não esteja usando o DvTools)

$ ./mvnw spring-boot:run

