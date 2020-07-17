# Laboratório - roteiro 3


### Objetivos:
* Inserir alguma segurança em nossa API usando json web tokens (jwt)

### Tecnologias envolvidas:
* ORM - Mapeamento objeto relacional (Hibernate é a impementação por trás do que usaremos)
* JPA - interface unificada para facilitar mapeamento de objetos para registros de tabelas
* JWT - tokens para autorização de acesso a dados

Lembrete: use o [spring initizlizr](https://start.spring.io) para criar seu projeto spring. Desas vez marque as dependências "_Spring Web Starter_", "_H2 Database_" e "_Spring Data JPA_" na configuração do seu projeto. Você também pode inserir a dependência do Lombok caso queira usar suas facilidades para o desenvolvimento dos DTOs (data transfer objects).

Faça o unzip do arquivo zip criado para o seu workspace. Na sua IDE de preferência (eclipse, IntelliJ, etc.) importe o projeto criado como um projeto maven já existente. Agora você já pode desenvolver sua aplicação. Lembre de organizar tudo em pacotes específicos. Antes de continuar é preciso adicionar no pom.xml a dependência do JWT:

```xml
    <dependency>
	<groupId>io.jsonwebtoken</groupId>
	<artifactId>jjwt</artifactId>
	<version>0.9.1</version>
    </dependency>
```

Neste terceiro lab iremos inserir um novo recurso ao código que já vínhamos desenvolvendo no lab 2. Agora vamos adicionar usuários, autenticação (via login), e autorização com JWT. 

Relembrando, por enquanto, no contexto da nossa API, uma **Disciplina** é uma classe que tem os seguintes atributos: **id:long**, **nome:String**, **nota:double**, **comentarios:String** e **likes:int**. O objetivo desta API é permitir que alunos comentem e deem likes nas disciplinas do curso de Ciência da Computação. 

Até agora, a versão da API ainda é muito reduzida, pois a idéia é ir aprendendo com problemas simples e solidificando conceitos. Quando um like é dado apenas incrementa o contador de likes da disciplina e quando um comentário é feito o novo comentário fica concatenado com os comentários anteriores com um newline entre eles. Neste terceiro roteiro de laboratório vamos continuar a partir do código do lab2. 

Use Spring Boot e java para desenvolver a seguinte API (por facilidade, vamos marcar o que já foi feito no lab 2):

POST /usuarios 
Adiciona um usuario com email, nome e senha. O email é o identificador único do usuario. 

POST /auth/login 
Recebe email e senha de um usuário, verifica na base de dados de usuários se esse usuário existe, e se a senha está correta (autenticação). Se o usuário for autenticado gerar um JWT que deve ser retornado para o cliente. 

* Informações adicionais sobre essa funcionalidade: o JWT gerado deve carregar a informação de subject (email do usuário), o tempo de expiração do token deve ser determinado por cada desenvolvedor (que deve saber justificar sua decisão). 


DELETE /auth/usuarios/ 
Remove o usuário de quem está requisitando a deleção (esta identificação é feita através do token passado no authorization header da requisição HTTP). Retorna informação do usuário removido (em um JSON no corpo da resposta) e código 200. Esta ação só pode ser realizada pelo próprio usuário que quer se remover, assim é preciso receber um JWT na requisição e recuperar credenciais do usuário. Retornar código HTTP adequado para as possíveis possibilidades de erro (ex. requisição sem JWT, com JWT inválido, ou com JWT de usuário inexistente).

GET /api/disciplinas - funcionalidade já implementada

Retorna um JSON (com campos id, nome) com todas as disciplinas inseridas no sistema e código 200. 

GET /api/disciplinas/{id} - funcionalidade já implementada

Retorna um JSON que representa a disciplina completa (id, nome, nota, likes e comentarios) cujo identificador único é id e código 200. Ou não retorna JSON e código 404 (not found) caso o id passado não tenha sido encontrado. 

PUT /api/disciplinas/likes/{id} - funcionalidade já parcialmente implementada

**Esta funcionalidade só está autorizada para usuários previamente autenticados. Esta autorização deve ser realizada via avaliação de tokens JWT.** Para usuários autorizados, esta função incrementa em um o número de likes da disciplina cujo identificador é id. 
Retorna a disciplina que foi atualizada (incluindo o id, nome e likes) e código 200. Ou não retorna JSON e código 404 (not found) caso o id passado não tenha sido encontrado e código 401 Unauthorized caso o cliente não tenha autorização.

PUT /api/disciplinas/nota/{id} - funcionalidade já parcialmente implementada

**Esta funcionalidade só está autorizada para usuários previamente autenticados. Esta autorização deve ser realizada via avaliação de tokens JWT.** Para usuários autorizados, esta função atualiza a nota da disciplina de identificador id no sistema. No corpo da requisição HTTP deve estar um JSON com uma nova nota atribuída à disciplina. A nova nota da disciplina é a média aritmética da nota anterior da disciplina e da nova nota passada nesta chamada. Se for a primeira nota sendo adicionada então esta nota é a que vai valer para a disciplina. 
Retorna a disciplina que foi atualizada (incluindo o id, nome e nota) e código 200. Ou não retorna JSON e código 404 (not found) caso o id passado não tenha sido encontrado e código 401 Unauthorized caso o cliente não tenha autorização. 

PUT /api/disciplinas/comentarios/{id} - funcionalidade já parcialmente implementada

**Esta funcionalidade só está autorizada para usuários previamente autenticados. Esta autorização deve ser realizada via avaliação de tokens JWT.** Para usuários autorizados, esta função insere um novo comentário na disciplina de identificador id. No corpo da requisição HTTP deve estar um JSON com o novo comentário (chave "comentario") a ser adicionado na disciplina a ser atualizada no sistema. 
Retorna a disciplina que foi atualizada (incluindo o id, nome e os comentarios atualizados) e código 200. Ou não retorna JSON e código 404 (not found) caso o id passado não tenha sido encontradoe código 401 Unauthorized caso o cliente não tenha autorização.

GET /api/disciplinas/ranking/notas - funcionalidade já implementada

Retorna todas as disciplinas inseridas no sistema ordenadas pela nota (da maior para a menor) e código 200.

GET /api/disciplinas/ranking/likes - funcionalidade já implementada

Retorna todas as disciplinas inseridas no sistema ordenadas pelo número de likes (da que tem mais likes para a que tem menos likes) e código 200.

Seguem algumas dicas:

* Use o padrão DAO para acesso às bases de dados;
* Use DTOs para a comunicação entre cliente e servidor e Lombok para agilizar o desenvolvimento;
* Use JWT para autenticação e autorização;
* Siga boas práticas de design, buscando desacoplamento utilize corretamente controladores, serviços e repositórios;
* Organize suas classes em packages com nomes significativos (xx.services, xx.controllers, xx.repositories, xx.entities, etc. - pode usar nomes em portugues também, mas mantenha a coerência, ou tudo em portugues ou tudo em ingles);
* Para ordenação aprenda a definir um novo método no repositório de disciplina seguindo o padrão de nomes do método. Mais dicas [aqui](https://www.baeldung.com/spring-data-sorting).

Execute a sua aplicação no terminal, dentro do diretório raiz do seu projeto com o seguinte comando: 
$ ./mvnw spring-boot:run

Use Curl ou Postman para testar sua API. 

**Não faça tudo de uma vez**. Desenvolva uma funcionalidade, teste, vá para a próxima…
