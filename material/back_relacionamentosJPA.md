# Entendendo um pouco mais de JPA e relacionamentos entre entidades

Já vimos que precisamos criar classes de modelo, conhecidas como entidades, para representar as classes do domínio da aplicação sendo desenvolvida. Se vamos vender bananas em navios, certamente teremos entidades para representar bananas e entidades para representar navios... Estas classes são marcadas com a anotação @Entity. Estas entidades precisam obrigatoriamente ter um atributo que serve como identificador único, que anotamos com @Id.

Em uma API certamente teremos várias classes de entidade. Em se tratando de JPA (Java Persistence API) contamos com um componente chamado EntityManager, que é parte da Java Persistence API. A função do EntityManager é implementar as interfaces  e as regras de ciclo de vida definidas pela especificação JPA 2.0. É esta entidade então que vai lidar com as várias entidades @Entity gerenciando seu ciclo de vida e garantindo sua persistência no banco de dados como tabelas.

Neste módulo vamos entender um pouco melhor sobre relacionamentos entre entidades. É possível relacionar duas entidades através de uma das seguintes anotações:

* @OneToOne: é um relacionamento um para um que define que só há uma entidade relacionada com a outra. Um exemplo desse tipo de relacionamento pode ocorrer entre a entidade Loja e a entidade endereço. Suponha que a aplicação comporta uma rede de lojas, cada uma com seu endereço. Ao configrar este relacionamento estamos dizendo que toda loja tem um endereço e todo endereço só pertence a uma loja.
* @OneToMany: só deve existir uma entidade da classe onde esta relação é definida, mas esta entidade está associada a muitas entidades da outra classe. Essa relação vai ocorrer sempre que uma classe tem uma coleção de tipos de outra classe. Esta é a associação entre uma cesta de compras e os produtos adicionados nela. Uma cesta de compras deve poder estar associada a muitos produtos, mas um produto específico do estoque só pode estar associado a uma cesta de compras (caso contrario estariamos vendendo o mesmo produto a usuários diferentes).
* @ManyToOne: é a relação inversa a @OneToMany, deve especificar a entidade que é a parte muitos. Por exemplo, na classe Produto, teriamos uma associação @ManyToOne com a classe CestaDeCompras.
* @ManyToMany: é uma associação de muitos para muitos. É usada nos casos em que uma entidade de um tipo A pode estar associada a muitas entidades do tipo B e cada entidade do tipo B também pode estar associada a várias entidades do tipo A. Imagine por exemplo, a relação entre livros e autores. Um livro pode ter muitos autores e um autor pode escrever muitos livros.

Na associação entre entidades vai sempre existir a entidade proprietária e a entidade não proprietária. A entidade proprietária da relação é aquela entidade que possui a chave estrangeira da outra entidade. Vejamos um exemplo:

Este conceito de entidade proprietária é importante para entender a configuração de CASCADE na relação.

Ao definir uma relação entre entidades devemos primeiro decidir se esta relação é unidirecional ou bidirecional.

Uma outra configuração possível diz respeito à forma como os dados são recuperados do banco de dados. Essa é a configuração FETCH. Existem duas estratégias para esta configuração. A estratégia do EAGER indica que em tempo de execução os dados devem ser recuperados em uma consulta. Isto significa que se a estratégia EAGER for usada, o EntityManager vai recuperar os dados da entidade mãe (proprietária) e da entidade filha (não proprietária) de uma só vez por uma consulta. A outra estratégia é a LAZY, ou preguiçosa. Ao usar esta estratégia dados serão obtidos de forma preguiçdevem ser buscados preguiçosamente quando forem acessados ​​pela primeira vez (busque quando necessário como subconsultas). O EntityManager recupera os dados da entidade pai primeiro e depois os dados da entidade filho sob demanda.
Se a estratégia do EAGER usou o EntityManager, os resultados serão obtidos em uma consulta (pai e filho)osa.


EAGER strategy is a requirement on the persistence provider runtime that data must be eagerly fetched ( fetch in one query ) .
If EAGER strategy used EntityManager fetch results in one query (parent and childs).
FetchType.LAZY : The LAZY strategy is a hint to the persistence provider runtime that data should be fetched lazily when it is first accessed( fetch when needed as sub-queries). EntityManager retrieves parent entity data first then retrieves child entity data on demand.
Outras configurações 
