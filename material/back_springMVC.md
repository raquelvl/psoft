# Criando uma API REST com Spring MVC

Neste curso vamos usar o spring boot como framework de desenvolvimento do backend. Isto significa que precisamos aprender como configurar nossa aplicação para usar os serviços do spring boot de forma correta. Neste artigo vamos aprender a criar um controlador REST através de um exemplo bem simples.

# Nossa aplicação

Antes de começar, vamos entender o que faz nossa aplicação. É uma aplicação sem propósito mesmo, só para apresentar alguns conceitos importantes em nossa primeira experiência com REST e com spring boot.

O código completo da API REST desenvolvida nesta aula está [aqui](https://github.com/raquelvl/projsw.github.io/tree/master/lab0).

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
