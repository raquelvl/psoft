# Criando uma API REST com Spring MVC

Neste curso vamos usar o spring boot como framework de desenvolvimento do backend. Isto significa que precisamos aprender como configurar nossa aplicação para usar os serviços do spring boot de forma correta. Neste artigo vamos aprender a criar um controlador REST através de um exemplo bem simples.

# Nossa aplicação

Antes de começar, vamos entender o que faz nossa aplicação. É uma aplicação sem propósito mesmo, só para apresentar alguns conceitos importantes em nossa primeira experiência com REST e com spring boot.

O código completo da API REST desenvolvida nesta aula está [aqui](https://github.com/raquelvl/psoft/tree/master/lab0).

Primeiro crie um projeto spring web seguindo orientações [desta outra aula](back_hello.md).

Construa uma API REST bem simples que te oferece as seguintes funcionalidades:

* GET /api/v1/greetings - pode receber um parâmetro opcional de nome (URI?nomeParam=valor) e retorna um JSON com dois campos: nome e saudação. O nome será o nome passado no parâmetro (se tiver sido passado) ou algum nome programado como default (para os casos que não é passado o parâmetro). A saudação depende do horário corrente no servidor. Entre 6h da manhã e meio dia, "Bom dia", entre meio dia e 6h da noite "Boa tarde" e depois das 6h da noite e antes das 6h da manhã "Boa noite". Retorna código 200.

* GET /api/v1/time - deve retornar um json com a hora atual no servidor e a time zone default dessa hora e o código 200.

* POST /api/v1/greetings/alternative - deve setar uma nova saudação alternativa. A nova saudação deve ser passada na requisição HTTP por um JSON no corpo da requisição. O servidor mantém apenas a última saudação alternativa configurada. Retorna JSON com a saudação alternativa configurada e código 200.

* GET /api/v1/greetings/alternative - pode receber um parâmetro opcional de nome (URI?nomeParam=valor) e retorna um JSON com dois campos: nome e saudação alternativa. O nome será o nome passado no parâmetro (se tiver sido passado) ou algum nome programado como default (para os casos que não é passado o parâmetro). A saudação alternativa é a saudação configurada na chamada ao POST /api/v1/greetings/alternative. Se nenhuma saudação alternativa foi configurada o servidor deve retornar uma saudação alternativa nula. Retorna código 200.

Para rodar seu projeto abra um terminal, entre no diretório raiz do projeto e execute o comando abaixo:
````
mvn spring-boot:run
````
Se o maven não estiver instalado de forma apropriada você também pode executar com o seguinte comando (que vem no próprio projeto criado no starter do Spring quando você escolhe maven como seu gerenciador de dependências):
````
./mvnw spring-boot:run
````

Você vai precisar criar um controlador Rest que será uma classe marcada com a anotação @RestController, um serviço que será acessado por este controlador. O serviço deve conhecer as regras de negócio para fazer o que foi solicitado, deixando o controlador menos "inteligente", sendo apenas um delegador. Também devem ser criadas classes que chamamos de entidades, são classes Java tradicionais, que irão representar as entidades que serão convertidas para/de arquivos JSON. Essas classes precisam de construtor e de métodos get e set para todos os atributos que devem ir no JSON.

# Data Transfer Objects

Aqui nesse momento vale a pena conversar sobre um outro padrão importante e simples de entender neste contexto: o DTO - data transfer objects. Vamos pensar na prática. O controlador expõe informações que vem dos dados. Dentro da aplicação OO o que ocorre é que ao acessar o banco de dados, um objeto (do tipo entidade) é recuperado. Pensando em um usuário, por exemplo, esse objeto recuperado pode conter, por exemplo, a senha do usuário, os papéis que ele exerce. Na troca de informações com o cliente alguns atributos dessas entidades não fazem sentido serem repassadas para o cliente (como por exemplo, a senha). Então dentro do sistema de backend costumamos escrever algumas classes de apoio, cujos objetos são chamados de data transfer objects. Esses objetos representam objetos de entidades, mas com o cuidado de manter apenas os atributos que são necessários na comunicação com o cliente. Esses objetos podem ser usados em métodos POST e PATCH (o cliente manda a parte da informação que ele detém - a informação necessária) e em métodos GET o serviço pode enviar DTOs que escondem certas informações da entidade/recurso. Obviamente, deve haver uma forma simples dentro da aplicação de acessar a entidade real a partir das informações do DTO e gerar um DTO a partir da entidade real.
