# :wave: Laboratório - roteiro 3 - API com autenticação e autorização Bearer Tokens JWT

## 🤓 O que vamos aprender? 

Aprender a escrever APIs REST com java/spring boot com rotas privadas usando Bearer Tokens do tipo JWT.

### Tecnologias envolvidas:
* JWT - Json Web Token (é a lib que lida com bearer tokens desse tipo) - tokens de autorização de acesso a dados
* ORM - Mapeamento objeto relacional (Hibernate é a implementação por trás do que usaremos)
* JPA - interface unificada para facilitar mapeamento de objetos para registros de tabelas e definir relações entre entidades

Lembrete: ao usar o [spring initizlizr](https://start.spring.io) para criar seu projeto spring marque as dependências:
* "_Spring Web Starter_",
* "_H2 Database_" e
* "_Spring Data JPA_"
* lombok e devtools opcional

Na configuração do maven (pom.xml) você também deve inserir a dependência do JWT:

```xml
    <dependency>
	<groupId>io.jsonwebtoken</groupId>
	<artifactId>jjwt</artifactId>
	<version>0.9.1</version>
    </dependency>
```

Neste terceiro lab iremos inserir um novo recurso ao código que já vínhamos desenvolvendo no lab 2: usuários. Agora vamos adicionar usuários e, consequentemente, precisamos lidar com autenticação (via login) e autorização usando JWT. Até agora, a versão da API ainda é muito reduzida, pois a idéia de usuário ainda não tinha sido introduzida. Com a introdução do usuário vamos precisar modificar algumas entidades e estabelecer novas relações JPA. Seguem exemplos:
* A entidade usuário precisa ser criada, e com isso várias outras classes são demandadas, como por exemplo DTOs para esta entidade, DAO, serviço
* É preciso agora ter algumas operações da API que lidam com o CRUD desse novo recurso que é a entidade Usuário, e assim precisamos de controlador e serviço
* Até o lab 2 quando um like era dado apenas incrementava o contador de likes da disciplina, mas agora vamos precisar de uma tabela separada para os likes, pois um like associa um usuário a uma disciplina
* Quando um comentário era feito o novo comentário não estava associado a usuários

As funcionalidades programadas no lab 2 devem continuar existindo, com as mudanças necessárias para manter a API segura.

### Projeto de segurança
Para cada operação da sua API decida que tipo de proteção ela deve ter. Em termos de proteção vamos considerar 4 possibilidades
* rotas públicas - não requerem usuário autenticado/logado
* rotas privadas genéricas - requerem autenticação (tem que ver um token válido no authorization header) mas não precisa checar quem é o usuário logado (usuário representado pelo token)
* rotas privadas dependentes do usuário - precisa checar quem é o usuário logado e a depender disso autoriza ou não a operação (ex. deleção de conteúdo criado pelo usuário)
* rotas privadas dependentes de papel do usuário - precisa checar se o usuário logado tem um determinado papel. Por exemplo, um usuário com papel de admin poderia deletar contas de usuários (além do próprio usuário dono da conta).

Então o primeiro passo é decidir que tipo de proteção as operações que já existem devem ter.

### Inserindo usuários
* Crie a classe usuário (pode usar email como id). Tenha também um atributo que indica o papel do usuário (ou uma lista de papéis, de acordo com seu projeto)
* Crie o DAO de usuário
* Crie o controlador de usuário para fazer CRUD de usuário, assim como os DTOs necessários e o serviço.
* Configure os relacionamentos entre usuário e as classes que já existem
	* Ex. cada comentário deve estar associado a um unico usuário, mas cada usuário pode ter muitos comentários
 	* Os likes devem passar a ser um recurso que associa disciplinas a usuários (será uma relação de muitos para muitos pois cada usuário pode dar muitos likes e cada disciplina também pode ter muitos likes)
 
### Inserindo autenticacao com JWT
* Crie um controlador de login com a rota POST para login
* Crie o serviço JWT que sabe gerar tokens JWT. Lembre de manter a claim sub que informa o id do usuário e a claim exp que traz a data de expiração do token

### Inserindo autorização
Para cada operação da API que você projetou para ser protegida, implemente esta proteção. A proteção mais genérica é resolvida pelo filtro, mas as outras dependentes do usuário e do papel requerem que o controlador receba o token. O serviço JWT deve realizar todo o parsing necessários ao esquema de segurança que vc polanejar. Então, nesta etapa você deve precisar:
* Configure a aplicação indicando que rotas serão privadas e configure um filtro para analisar token JWT de rotas privadas
* Implemente o filtro que sabe avaliar Bearer token do tipo JWT (o filtro que será chamado toda vez que chegar uma requisição para rotas não públicas)
* modificar alguns métodos nos controladores para receber o authorization header
* modificar serviços para receber authorization header e chamar o serviço JWT para realizar o que for necessário
* adicionar métodos no serviço JWT para receber o authorization header e saber extrair dele as informações que forem necessárias (em geral será o sub e a partir do id do usuário recupera o usuário para tomar as decisões de autorização)

Detalhes sobre novas rotas a serem desenvolvidas (além de atualizar as que já existe com o projeot de segurança para elas):

**<MÉTODO HTTP> /api/usuarios**<br>
Adiciona um usuario com email, nome e senha (outros atributos podem ser inseridos se desejar). O email é o identificador único do usuario. Retorna um JSON que representa o usuário inserido (claro que sem a senha) e código <código de resposta HTTP>. Ou não retorna JSON e <código de resposta HTTP> caso o identificador de usuário passado já exista na base de dados.

**<MÉTODO HTTP> /api/login** <br>
Recebe email e senha de um usuário, verifica na base de dados de usuários se esse usuário existe, e se a senha está correta para realizar a autenticação. Se o usuário for autenticado este recurso deve gerar um JWT que deve ser retornado para o cliente. Retorna um JSON que representa o usuário inserido (claro que sem a senha) e código <código de resposta HTTP>.
* Informações adicionais sobre essa funcionalidade: o JWT gerado deve carregar a informação de subject (email do usuário), o tempo de expiração do token deve ser determinado por cada desenvolvedor (que deve saber justificar sua decisão). 

**<MÉTODO HTTP>  /auth/usuarios/{email}**  <br>
Remove o usuário cujo identificador é passado ({email}). É preciso garantir que o usuário requisitando este recurso é o mesmo usuário do {email} passado (esta identificação é feita através do token passado no authorization header da requisição HTTP). Só o próprio usuário ou um usuário com papel de admin pode remover sua conta. Retorna informação do usuário removido (em um JSON no corpo da resposta) e código <código de resposta HTTP>. 
* Detalhes: Esta ação só pode ser realizada pelo próprio usuário dono da conta ou um usuário com papel de admin, assim é preciso receber um JWT na requisição e recuperar credenciais do usuário. Retornar código HTTP adequado para as possíveis possibilidades de erro (ex. requisição sem JWT, com JWT inválido, ou com JWT de usuário inexistente).

**<MÉTODO HTTP>  /auth/usuarios/{email}**  <br>
Recupera informações do usuário cujo identificador é passado ({email}). É preciso garantir que o usuário requisitando este recurso é o mesmo usuário do {email} passado (esta identificação é feita através do token passado no authorization header da requisição HTTP). Só o próprio usuário pode receber informações sobre sua conta. Retorna informação do usuário (em um JSON no corpo da resposta) e código <código de resposta HTTP>. 

**Reavaliando as rotas anteriores**

Agora que temos o conceito de usuários, é importante que eles estejam associados a suas ações na API. Então vamos realizar as seguintes configurações/mudanças no código:

1. Apenas usuários cadastrados podem dar likes nas disciplinas, e é preciso associar cada like ao respectivo usuário. Pense assim: se a gente for desenvolver o frontend é preciso ter essa informação pra quando for mostrar a disciplina o frontend saber se o símbolo de loke fica marcado ou não para o usuário logado.

2. Apenas usuarios cadastrados podem comentar as disciplinas e os comentários devem ficar associados aos usuários que os escreverm. Apenas o dono de um comentário pode apagar o comentário.

3. Apenas usuários cadastrados podem dar notas às disciplinas, mas não é preciso associar cada nota ao respectivo usuário. 

Seguem algumas dicas:

* Use o padrão DAO para acesso às bases de dados;
* Siga boas práticas de design, buscando desacoplamento utilize corretamente controladores, serviços e repositórios;
* Organize suas classes em packages com nomes significativos (xx.services, xx.controllers, xx.repositories, xx.entities, etc. - pode usar nomes em portugues também, mas mantenha a coerência, ou tudo em portugues ou tudo em ingles);
* Para ordenação aprenda a definir um novo método no repositório de disciplina seguindo o padrão de nomes do método. Mais dicas [aqui](https://www.baeldung.com/spring-data-sorting).
* Use o que aprendemos sobre relacionamentos JPA para relacionar entidades.

Execute a sua aplicação no terminal, dentro do diretório raiz do seu projeto com o seguinte comando: 
$ ./mvnw spring-boot:run

Use Curl ou Postman ou Insomnia para testar sua API. 

**Não faça tudo de uma vez**. Desenvolva uma funcionalidade, teste, vá para a próxima… 🚀
