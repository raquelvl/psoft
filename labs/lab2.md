# :wave: Laboratório - roteiro 2 - API com modelagem dos dados (relações JPA)

## 🤓 O que vamos aprender? 

* Aprender a escrever APIs com dados persistentes usando um esquema de dados relacional e modelagem de relacionamentos JPA.

### Tecnologias envolvidas:
* ORM - Mapeamento objeto relacional (Hibernate é a implementação por trás do que usaremos)
* JPA - interface unificada para facilitar mapeamento de objetos para registros de tabelas e definir relações entre entidades

Lembrete: use o [spring initizlizr](https://start.spring.io) para criar seu projeto spring dentro ou fora da IDE. Dessa vez marque as dependências "_Spring Web Starter_", "_H2 Database_" e "_Spring Data JPA_" na configuração do seu projeto (além de "_Lombok_" e "_DevTools_").

Neste segundo lab o design da API REST a ser desenvolvida será dado novamente, na verdade, é muito parecido com o primeiro. Continuaremos o desenvolvimento do primeiro lab no contexto de disciplinas. Mas agora vamos adicionar persistência, vamos iniciar todas as disciplinas de uma vez. Relembrando, por enquanto, no contexto da nossa API, uma **Disciplina** é uma classe que tem os seguintes atributos: **id:long**, **nome:String**, **notas:List<Double>** e **likes:int**. Para este lab vamos adicionar algo mais... É possível associar comentários a disciplinas. Assim, *comentarios:List\<Comentario>* passa a ser mais uma informação associada à disciplina. 

Um **Comentario** deve ter o seguinte estado: **id:long**, **dataDoComentario:LocalDate**, **texto:String**, **removido:boolean**, *disciplina:Disciplina*. Cada disciplina pode estar associada a muitos comentários, mas cada comentário está associado a apenas uma disciplina. Com essa nova funcionalidade vamos adicionar várias novas rotas na nossa API para o CRUD de comentários.

Também vamos começar a ter a noção de **Tag**. Uma tag é uma palavra (que pode ser simples ou composta) que os alunos que avaliam uma disciplina podem usar para caracterizar a disciplina. Uma disciplina então passa a estar associada a uma lista de tags que a representam, como, por exemplo: massante, muito teórica, rasgada, difícil, etc. A base de tags deve ser populada à medida que as disciplinas vão sendo tagueadas, assim, não deve ter na base de tags repetição de termos já usados. Cada tag pode estar associada a muitas disciplinas, e cada disciplina também pode estar associada a muitas tags. 

O objetivo desta API é permitir que alunos comentem e deem likes nas disciplinas do curso de Sistemas de Informação. 

### Povoando a base de disciplinas:
Temos um arquivo JSON [aulas/disciplinasSI.json](https://github.com/raquelvl/psoft/blob/master/aulas/disciplinasSI.json) já com os nomes de todas as disciplinas que devem ser criadas. A ideia é programar sua API para povoar o banco de dados com todas as disciplinas já existentes neste arquivo. [Neste documento](http://bit.ly/inicia-dados-json) encontra-se uma discussão sobre como ler dados de um json e adicionar ao banco de dados usando spring boot (você vai ter que entender e implementar o seu próprio). Lembre que a própria API deve se encarregar de gerar os identificadores únicos das disciplinas no banco (@GeneratedValues). Com isso, não precisaremos mais de uma rota na API para adicionar disciplinas. Outro lembrete: essa atividade envolve já o uso do banco, então você deve criar o repositório de Disciplinas e também o de comentários, marcar as classes que vão estar associadas ao banco como @Entity, e já deve ter configurado o banco em application.properties. (para testar você pode usar a rota GET /api/disciplinas que retornará todas as disciplinas inseridas no sistema).

### Use Spring Boot e java para desenvolver a seguinte API:

GET /api/disciplinas 
Retorna um JSON (apenas com campos id, nome) com todas as disciplinas inseridas no sistema e código 200. 

GET /api/disciplinas/{id}
Retorna um JSON que representa a disciplina completa (id, nome, nota média, número de likes e os comentários) cujo identificador único é id e código 200. Ou não retorna JSON e código 404 (not found) caso o id passado não tenha sido encontrado. 

PATCH /api/disciplinas/likes/{id}
Incrementa em um o número de likes da disciplina cujo identificador é id. 
Retorna a disciplina que foi atualizada (incluindo o id, nome e likes) e código 200. Ou não retorna JSON e código 404 (not found) caso o id passado não tenha sido encontrado.

PATCH /api/disciplinas/nota/{id}
Adiciona uma nova nota à lista de notas da disciplina de identificador id no sistema. No corpo da requisição HTTP deve estar um JSON com uma nova nota atribuída à disciplina. A nova nota da disciplina deve ser calculada como a média de todas as notas já recebidas, incluindo a nova nota passada nesta chamada. Se for a primeira nota sendo adicionada então esta nota é a que vai valer para a disciplina. 
Retorna a disciplina que foi atualizada (incluindo o id, nome e nota média) e código 200. Ou não retorna JSON e código 404 (not found) caso o id passado não tenha sido encontrado. 

POST /api/disciplinas/{id}/comentarios
Insere um novo comentário na disciplina de identificador id. No corpo da requisição HTTP deve estar um JSON com o novo comentário a ser adicionado na disciplina a ser atualizada no sistema. 
Retorna a disciplina que foi atualizada (incluindo o id, nome e os comentarios atualizados) e código 200. Ou não retorna JSON e código 404 (not found) caso o id passado não tenha sido encontrado.

GET /api/disciplinas/{id}/comentarios
Retorna todos os comentários associadas à disciplina de identificadir id e código de resposta 200. Ou não retorna JSON e código 404 (not found) caso o id passado não tenha sido encontrado. Aqui deve ser possível usar algum parrâmetro que filtre os comentarios que contiverem algum padrão (usar @RequestParameter) se o usuário desejar.

POST /api/disciplinas/{id}/tags
Insere uma nova tag associada à disciplina de identificador id. No corpo da requisição HTTP deve estar um JSON com a tag a ser adicionada na disciplina a ser atualizada no sistema. 
Retorna a disciplina que foi atualizada (incluindo o id, nome e as tags atualizadas) e código 200. Ou não retorna JSON e código 404 (not found) caso o id passado não tenha sido encontrado.

GET /api/disciplinas/ranking/notas
Retorna todas as disciplinas inseridas no sistema ordenadas pela nota (da maior para a menor) e código 200.

GET /api/disciplinas/ranking/likes
Retorna todas as disciplinas inseridas no sistema ordenadas pelo número de likes (da que tem mais likes para a que tem menos likes) e código 200.

GET /api/disciplinas/{id}/tags
Retorna todas as tags associadas a disciplina de código id e código 200. Ou não retorna JSON e código 404 (not found) caso o id passado não tenha sido encontrado.

GET /api/disciplinas/tags (?tag=str)
Retorna todas as disciplinas associadas à tag informada no parametro de busca (@RequestParameter) e código 200. Se nenhuma tag for informada não retorna nada.

Para todas as funcionalidades dessa API lembre de realizar o tratamento adequado de erros seguindo o que estudamos em sala (detalhes do problema - RFC 7807) e @RestControllerAdvice.

Seguem algumas dicas:

* Use o padrão DAO para acesso às bases de dados;
* Siga boas práticas de design, buscando desacoplamento utilize corretamente controladores, serviços e repositórios;
* Organize suas classes em packages com nomes significativos (xx.services, xx.controllers, xx.repositories, xx.entities, etc. - pode usar nomes em portugues também, mas mantenha a coerência, ou tudo em portugues ou tudo em ingles);
* Para ordenação aprenda a definir um novo método no repositório de disciplina seguindo o padrão de nomes dos métodos. Mais dicas [aqui](https://www.baeldung.com/spring-data-sorting).

**Não faça tudo de uma vez**. Desenvolva uma funcionalidade, teste, vá para a próxima… 🚀
