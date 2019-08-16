# Criando aplicações Web usando spring boot e eclipse

1. Certifique-se de que tem Java instalado (SDK)
2. Certifique-se de que tem o apache maven instalado (usaremos para gerenciar dependências)
3. Certifique-se de que tem o eclipse (ou outra IDE de sua preferência instalado)

Eu particularmente não acho necessario instalar o pacote spring para o eclipse. A maneira mais fácil (e menos sujeita a erros) de criar um novo projeto de backend Web com spring é usar o starter fornecido pelo spring: [https://start.spring.io/](https://start.spring.io/).

Nesse site vc precisa dar um nome ao pacote do seu projeto, ao projeto em si, escolhar linguagem Java e que o resultado deve estar em um jar. Aí também você já pode escolher algumas dependências para o seu projeto. Como vamos escrever *aplicações REST simples* temos sempre que marcar a opção *Spring Web Starter*. 

Thymeleaf deve ser escolhido caso você vá desenvolver backend que retorna páginas HTML. Não será o foco desta disciplina, aqui vamos desenvolver backend como APIs REST que se comunicam usando HTTP e JSON como representação dos dados.

JPA deve ser uma dependência marcada quando começarmos a trabalhar com bancos de dados.

Uma vez criado o projeto spring, salve em seu disco. Vai surgir um arquivo zip na pasta de downloads default, faça o unzip antes de continuar. 

Vá no eclipse, Import > Maven > existing maven project. A imagem abaixo ilustra esse import.

![Importando o projeto spring criado no eclipse](imagens/importaMavenProject.png)

Vá clicando em next e informando o que é solicitado. Em _root directory_ informe o caminho do diretório do projeto dezipado e deplois clique em Finish/terminar. Pronto, seu projeto spring já está criado.

Construa uma API REST bem simples que te oferece as seguintes funcionalidades:

* GET /api/v1/greetings - pode receber um parâmetro opcional de nome (URI?nomeParam=valor) e retorna um JSON com dois campos: nome e saudação. O nome será o nome passado no parâmetro (se tiver sido passado) ou algum nome programado como default (para os casos que não é passado o parâmetro). A saudação depende do horário corrente no servidor. Entre 6h da manhã e meio dia, "Bom dia", entre meio dia e 6h da noite "Boa tarde" e depois das 6h da noite e antes das 6h da manhã "Boa noite". Retorna código 200.

* GET /api/v1/time - deve retornar um json com a hora atual no servidor e a time zone default dessa hora e o código 200.

* POST /api/v1/greetings/alternative - deve setar uma nova saudação alternativa. A nova saudação deve ser passada na requisição HTTP por um JSON no corpo da requisição. O servidor mantém apenas a última saudação alternativa configurada. Retorna JSON com a saudação alternativa configurada e código 200.

* GET /api/v1/greetings/alternative - pode receber um parâmetro opcional de nome (URI?nomeParam=valor) e retorna um JSON com dois campos: nome e saudação alternativa. O nome será o nome passado no parâmetro (se tiver sido passado) ou algum nome programado como default (para os casos que não é passado o parâmetro). A saudação alternativa é a saudação configurada na chamada ao POST /api/v1/greetings/alternative. Se nenhuma saudação alternativa foi configurada o servidor deve retornar uma saudação alternativa nula. Retorna código 200.

Para rodar seu projeto abra um terminal, entre no diretório raiz do projeto e execute o comando abaixo:
````
mvn spring-boot:run
````
