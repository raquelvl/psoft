# Padrão MVC 

Praticamente todas as aplicações Web da atualidade seguem um padrão de projeto chamado **MVC - Model, View, Controller**. Considere que o backend é a API. Em se tratando de uma aplicação Web monolítica, que é nosso foco aqui, o cliente dessa API é o frontend. 

Esse padrão é importante porque mantém desacoplados os códigos do frontend (que é a parte View do padrão) e da API/backend que é a parte Model. Este padrão define claramente as responsabilidades do backend e do frontend:
* A View (frontend) é responsável pela interação com o usuário, por obter através dessa interação o que o usuário deseja e apresentar para o usuário os resultados de suas solicitações;
* O Model (backend) é responsável pelas regras de negócio, por garantir que as interações entre cliente e servidor seguem a lógica que o negócio por trás da aplicação define. Por exemplo, certas aplicações não permitem senhas menores que 8 caracteres e ainda tem restrições sobre os tipos de caracteres que devem estar nas senhas... Isso é uma regra de negócio. Outros sites não tem restrições quanto a isso e até permitem interações com usuários criados automaticamente. As operações que podem ser realizadas por cada tipo de usuário, as regras que definem essas autorizações, tudo é codificado no backend.

Agora precisamos de uma cola e permite a comunicação eficiente entre View e Model. Esta cola é o controlador. O controlador  orquestra a comunicação entre estas partes. O controlador é desenvolvido pelos desenvolvedores do backend e executa junto da API. O controlador recebe as requisições dos usuários e sabe exatamente para que serviço dentro do servidor delegar esta requisição.

Em resumo: 
* O que é o modelo? É quem sabe fazer o que precisa ser feito (backend - regras de negócio e dados)
* O que é a visão? É quem apresenta informações para o usuário (frontend) - muitas views são possíveis para um mesmo Model
* O que é o controlador? É quem roteia os pedidos dos usuários (View) para quem sabe fazer (services dentro do Model)

De forma simplificada, quando uma requisição chega na API ela vai ser processada primeiramente por um controlador, que vai identificar o recurso sendo solicitado, a ação (verbo HTTP), as informações passadas na requisição e vai repassar para o serviço adequado dentro do backend, que irá de fato processar o pedido. Apenas o controlador lida com requisições e respostas HTTP. Os outros componentes do backend (em OO) são de uma aplicação OO normal, objetos que se relacionam para realizar as ações necessárias.

Este padrão de projeto facilita muito a manutenção do código no lado do servidor de backend, pois para cada operação do backend existe um caminho muito específico, então fica fácil identificar onde alterações precisam ser realizadas. Para adicionar novas funcionalidades na API também fica simples, pois será necessário instrumentar o controlador para a nova funcionalidade e informar que serviço (possivelmente código novo) deve ser chamado para executá-la.

Tudo isso vai ficar muito mais claro quando começarmos a meter a mão na massa... Como criar APIs REST que seguem o padrão MVC usando Spring? Esse é o conteúdo [desse outro artigo](back_springMVC).
