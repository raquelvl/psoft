# API e backend

(Atenção, revisão)

O backend expõe serviços geralmente na forma de uma API (_Application Programming Interface_). A API vai definir que "funções" podem ser chamadas naquela aplicação, que informações de entrada são necessárias para chamar cada função e como são estruturadas as respostas de cada função dessas. Isso tudo precisa ser bem definido porque o cliente da API é outro software, que precisa ser programado para usar a API. 

A API dá acesso aos dados/serviços de forma controlada, expondo apenas o que deve ser exposto e com o cuidado de verificar se o usuário que requisita cada função tem autorização para o fazê-lo. Organizar o backend como uma (ou mais) API(s) permite diferentes “frontends” para a mesma aplicação backend. Além disso também permite criar aplicações a partir de outras aplicações, desde que elas também definam APIs (APIs que são clientes de outras APIs). A API padroniza o acesso aos dados/serviços de forma totalmente controlada e projetada.

As APIs podem ser bastante simples ou incrivelmente complexas, e existem diferentes maneiras de programar uma API. De forma bastante prática, podemos definir uma API como uma coleção de métodos claramente especificados para comunicação entre diferentes componentes de um software. Claro que ao definir métodos precisamos definir sua visibilidade, seus parâmetros de entrada e seu tipo de retorno. Pensar na API que queremos expor é a parte mais importante do desenvolvimento do backend e será alvo de estudos neste curso.

## API REST

Neste curso iremos focar no desenvolvimento de APIs REST - _Representational State Transfer_. A adoção de APIs REST é universalmente bem aceita e simplifica o entendimento da API uma vez que REST usa tecnologias/padrões já bem consolidados na Web para a comunicação entre cliente e servidor. Exemplos de tecnologias usadas: HTTP para comunicação entre processos, JSON ou XML para representar dados.

A API REST é baseada em URIs (_Uniform Resource Identifier_). Cada função oferecida está associada a uma URI bem definida. As respostas desta API vem em geral em um formato de dados específico chamado JSON (mas também pode ser HTML ou XML). 

Uma API REST segue alguns princípios, um dos mais importantes é que a interação cliente/API é _stateless_,isto é, sem estado. Isto significa que cada requisição traz a informação necessária para que o servidor possa entendê-la e respondê-la. O estado das sessões fica sendo mantido apenas nos clientes, o que é bom para escalabilidade dos servidores. Por outro lado, esse tipo de interação usa mais mais rede já que certos dados precisam estar presentes em todas as requisições (como por exemplo, token de acesso do usuário). Para minimizar este problema alguns requisições podem usar cache no cliente. Estas requisições são  "cacheáveis". Poder ser mantida em cache no cliente pode não ser opção para as requisições que tem conteúdo muito dinâmico e requerem sempre a busca no servidor para receber dados atuais ou personalizados (requisições não "cacheáveis"). Usar cache melhora desempenho, mas pode prejudicar confiabilidade (dados velhos).

Em se tratando de API REST, um **recurso** é uma abstração sobre um determinado tipo de informação que a API gerencia. Por exemplo, no contexto de comércio eletrônico, podemos ter recursos tais como produtos, clientes, vendas, etc. Em REST todo recurso deve possuir uma identificação únicapara diferenciar qual dos recursos deve ser manipulado em uma determinada solicitação. Essa identificação do recurso é feita por URI (Uniform Resource Identifier). Em se tratando de uma API REST que segue uma arquitetura monolítica, teremos um *endpoint* associado a esta API, que é a URL usada para acessar os recursos expostos por esta API. Aplicações de microsserviços terão muitos endpoints associados à mesma API.

O design de uma API REST consiste em pensar nos recursos que serão expostos (em termos de URIs) e de seus parâmetros de entrada e informações de saída. Existem regras de boas práticas para que este design seja realizado de forma elegante. A parte mais importante (ou uma das mais importantes) do trabalho de um desenvolvedor de backend é projetar boas APIs. Uma API que seja fácil de entender, usar e manter é sempre desejável. 

Vejamos com um exemplo simples um pouco do que é pensar no design de uma API: imagine que você está desenvolvendo uma API para um restaurante italiano que quer começar a fazer entregar. Um dos pratos a serem entregues é de parmegiana (que pode ser de filé, frango ou peixe), e que também inclui tamanhos variados. Existem várias formas de programar isso. Por exemplo, podemos pensar nas URIs GET /parmegiana-file-grande, GET /parmegiana-file-media, … e GET /parmegiana-peixe-pequena. Outra forma de pensar na API seria ter um recurso GET /parmegianas e passar informações específicas sobre o pedido na forma de dados em JSON no corpo da requisição. Qual dessas formas parece ser a mais simples e a mais indicada?

## Boas práticas para design de APIs REST

O trabalho de um desenvolvedor de backend começa ao projetar a API que será desenvolvida. O projeto de APIs deve ser muito bem pensado, em termos dos caminhos (chamados rotas) a serem criados, dos verbos HTTP usados. Quanto mais bem pensado é o projeto, mais chances de sua API ser popular. 

Seguem algumas regras importantes ao projetar uma API Web (que pode ser uma API REST):
* Use URIs legíveis, de fácil dedução (por humanos). Por que? Porque isso vai facilitar que os programadores de aplicações clientes de sua API usem a API de forma correta mesmo sem precisar estudar muito sua API.
* Utilize o mesmo padrão de URI na identificação dos recursos. Por que? Porque aos poucos, os programadores de aplicações clientes de sua API vão entender até mesmo como acessar recursos recém desenvolvidos por você sem necessidade de levar muito tempo para entender as novidades.
* Evite URI’s que contenham a operação a ser realizada em um recurso (/produto/excluir, /produto/adicionar). Na verdade devemos *evitar a todo custo* usar verbos nas rotas das APIs. Lembre que o protocolo usado para comunicação é o HTTP que já informa a ação a ser realizada com um verbo (GET, POST, PUT, DELETE...). O verbo que irá na requisição que vai chegar na sua aplicação é que vai indicar a ação a ser realizada. Portanto, usar verbos em sua URI seria redundante e em termos de estilo de projeto, errado!
* Evite adicionar na URI o formato desejado da representação do recurso. Por exemplo, na URI constar a palavra json para informar que a resposta será um json, ou que a entrada é um json. Por que? Por isso engessa sua API. Se um dia você quiser mudar de JSON para um outro formato que acha mais adequado terá que mudar o caminho na API, quebrando todas as aplicações clientes que acessam essa rota.
* Por fim, pense bem antes de definir as URIs (ou rotas) que compoem sua API e evite alterações nas URI’s. Por que? Acredito que a essa altura você já sabe responder esta pergunta.

Ao definir a sua API assegure-se de estar usando o protocolo HTTP da forma correta. Os métodos do protocolo devem definir ações a serem executadas nos recursos expostos por sua API. Na tabela abaixo resumimos como usar esses verbos de forma adequada.

Método | URI | Utilização
------------ | ------------- | -------------
GET | /clientes | Recuperar dados de todos os clientes
GET | /clientes/{id} | Recuperar dados de um cliente específico
POST | /clientes | Criar um novo cliente
PUT | /clientes/{id} | Atualizar dados de um determinado cliente
DELETE | /clientes/{id} | Excluir um determinado cliente

Note que muitas URIs são exatamente iguais e o que vai diferenciar o que será realizado no servidor é exatamente o verbo HTTP associado à requisição HTTP que chega na API.

A requisição HTTP que chega no backend vai conter o verbo de ação, a URI requerida e todos os campos de uma requisição HTTP normal, como por exemplo, headers e o corpo da requisição. A requisição HTTP PUT /clientes/{id} provavelmente levará um JSON (ou um XML) no corpo da mensagem com as informações do cliente a serem atualizadas.

A resposta que a API deve retornar pra o cliente é uma representação do recurso solicitado. Será uma resposta HTTP que deve conter, por exemplo, um código de resposta que indica se o processamento no servidor ocorreu com sucesso ou se falhou. Note que o desenvolvedor da API deve portanto programar a resposta a ser dada ao cliente seguindo também o bom uso do protocolo HTTP. Na tabela abaixo vemos um resumo das principais famílias de códigos de status HTTP retornados.

Família | Descrição 
------------ | -------------
2xx | Em geral indica sucesso (código 200 - OK, 201 - Created)
4xx | Indica que ocorreu um erro em geral que a requisição que chegou não está bem formada. É um erro de quem gerou a requisição.
5xx | Indica que ocorreu um erro interno no servidor ao processar a requisição, é um erro no backend.

É importante que um programador de backend entenda os *status codes*  HTTP para retornar códigos de forma correta para o cliente. Veja mais detalhes sobre isso [nessa documentação da mozilla](https://developer.mozilla.org/pt-BR/docs/Web/HTTP/Status).

Você pode ver mais detalhes sobre essas boas práticas [nesse artigo do blog da caelum](https://blog.caelum.com.br/rest-principios-e-boas-praticas/).

### Um exemplo de API REST

A Câmara dos Deputados oferece um serviço para qualquer pessoa interessada obter dados da Câmara. Trata-se de uma API REST chamada [Dados abertos](https://dadosabertos.camara.leg.br/swagger/api.html). Ao abrir a documentação da API neste link é  possível conhecer as chamadas aceitas pela API, que tipo de dado deve vir no corpo das mensagens, como são as respostas de cada chamada, dentre outras informações. 
