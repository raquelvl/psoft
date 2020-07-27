# Entendendo um pouco mais de JPA e relacionamentos entre entidades

Já vimos que precisamos criar classes de modelo, conhecidas como entidades, para representar as classes do domínio da aplicação sendo desenvolvida. Se vamos vender bananas em navios, certamente teremos entidades para representar bananas e entidades para representar navios e conteiners... Estas classes são marcadas com a anotação @Entity. Lembremos também que estas entidades precisam obrigatoriamente ter um atributo que serve como identificador único, que anotamos com @Id.

Até o momento desenvolvemos aplicações simples em que as entidades não tem relacionamentos entre si. Mas no mundo real, qualquer aplicação, por mais simples que seja, vai requerer algum relacionamento entre entidades. Entender os relacionamentos entre as entidades e as operações realizadas pela aplicação é essencial para modelar o esquema de dados de aplicações que mantém dados em sistemas de dados relacionais. 

Em se tratando de JPA (Java Persistence API) contamos com um componente chamado EntityManager, que é parte da Java Persistence API. A função do EntityManager é implementar as interfaces e as regras de ciclo de vida definidas pela especificação JPA 2.0. É esta entidade então que vai lidar com as várias entidades @Entity gerenciando seu ciclo de vida e garantindo sua persistência no banco de dados como tabelas. Como desenvolvedor, o importante é reconhecer o tipo de relação existente entre as entidades para poder mapeá-las pela configuração mais adequada. 

Por exemplo, suponha que você tem uma API de comércio eletrônico. Dentre as entidades de sua aplicação estão Produto, CestaDeCompras e Estoque, cada uma com seus atributos específicos. Claramente há uma relação entre estas entidades. Uma cesta de compras tem um ou mais produtos e pertence a um único usuário. Um estoque contém uma coleção de produtos. Quando pensamos na cesta de compras, o relacionamento é de um para um, pois apenas uma cesta existe para cada usuário e apenas um usuário é dono de uma cesta de compras. Estes atributos cruzados precisam estar nas duas entidades? Por exemplo, na entidade CestaDeCompras, será preciso ter um atributo do tipo Usuario? Ou seria melhor manter um atributo do tipo CestaDecompras em usuário? Ou manter em ambas as classes? Essa decisão vai depender de como as operações são realizadas na aplicação. Se a aplicação sempre recupera primeiro o usuário, então apenas o usuário precisa definir que tem uma cesta de compras... Mas se é preciso acessar muitas vezes a cesta de compras e depois recuperar o usuário, então seria interessante ter também na cesta de compras a chave estrangeira de usuário. Veja que estamos falando de entidades, mas que representam tabelas de bancos de dados. Para entender melhor, vamos rever o conceito de chaves e chaves estrangeiras.

Você já sabe que toda entidade precisa ter um atributo que é marcado com @Id. Este atributo é a chave da tabela associada à entidade e significa que este atributo não pode ser duplicado na tabela, pois ele identifica cada elemento da tabela de forma única. Para estabelecer relações entre entidades, o que ocorre é que inserimos chaves estrangeiras (chaves/ids de outras entidades) em entidades relacionadas. Voltando para o caso da cesta de compras e do usuário. A relação estabelecida pode indicar, por exemplo, que na tabela USUARIO deve estar o id da cesta de compras a que ela pertence. Este id da cesta de compras no usuário é uma chave estramgeira e será este o atributo que estabelece o relacionamento entre as duas tabelas (USUARIO e CESTA_DE_COMPRAS). Assim, em USUARIO há uma coluna que corresponde à coluna que é a chave primária da tabela CESTA_DE_COMPRAS. Ao recuperar um usuário se torna fácil recuperar a cesta de compras do mesmo.

No momento de realizar este design não há certo ou errado, é um projeto. O que ocorre é que algumas configurações vão manter seu código mais simples que outras, ou manter sua aplicação mais eficiente em termos de desempenho. Esse tipo de decisão não é simples e pode até mudar ao longo do desenvolvimento da aplicação, pois aos poucos os próprios desenvolvedores vão tendo um conhecimento muito maior da aplicação e de seu uso. Até os desenvolvedores mais experientes passam muitas vezes por grandes desafios ao ter que identificar e projetar bons relacionamentos (eficientes) entre as entidades. 

Quando pensamos em produto e cesta de compras, já percebemos um outro tipo de relacionamento. Cada cesta de compras está relacionada com muitos produtos, mas cada produto só pode pertencer a uma cesta de compras (senão venderíamos o mesmo produto a mais de um usuário, o que não é recomendado). Neste caso a cesta de compras poderia ter uma lista de produtos mas o produto só deveria estar associado uma única cesta de compras. 

A qualidade do projeto de dados de uma aplicação, isto é, a definição desses relacionamentos entre entidades e como são mapeados/configurados, vai determinar em grande parte a complexidade da sua aplicação. Neste módulo vamos entender um pouco melhor sobre relacionamentos entre entidades e como configurá-los usando JPA. 

É possível relacionar duas entidades através de uma das seguintes anotações:

* @OneToOne: é um relacionamento um para um que define que só há uma entidade relacionada com a outra. Um exemplo desse tipo de relacionamento pode ocorrer entre a entidade Loja e a entidade endereço. Suponha que a aplicação comporta uma rede de lojas, cada uma com seu endereço. Ao configurar este relacionamento estamos dizendo que toda loja tem um endereço e todo endereço só pertence a uma loja.
* @OneToMany: só deve existir uma entidade da classe onde esta relação é definida, mas esta entidade está associada a muitas entidades da outra classe. Essa relação vai ocorrer sempre que uma classe tem uma coleção de tipos de outra classe. Esta é a associação entre uma cesta de compras e os produtos adicionados nela. Uma cesta de compras deve poder estar associada a muitos produtos, mas um produto específico do estoque só pode estar associado a uma cesta de compras (caso contrario estariamos vendendo o mesmo produto a usuários diferentes).
* @ManyToOne: é a relação inversa a @OneToMany, deve especificar a entidade que é a parte muitos. Por exemplo, na classe Produto, teriamos uma associação @ManyToOne com a classe CestaDeCompras.
* @ManyToMany: é uma associação de muitos para muitos. É usada nos casos em que uma entidade de um tipo A pode estar associada a muitas entidades do tipo B e cada entidade do tipo B também pode estar associada a várias entidades do tipo A. Imagine por exemplo, a relação entre livros e autores. Um livro pode ter muitos autores e um autor pode escrever muitos livros.

![Relações entre entidades](https://port.sas.ac.uk/pluginfile.php/233/mod_book/chapter/140/10%20Image%20D3.jpg)
> Fonte: https://port.sas.ac.uk/mod/book/view.php?id=75&chapterid=140

## Configurando relações

Nas associações OneToMany e ManyToOne vamos ter o conceito de **entidade proprietária** e entidade referenciada ou não proprietária. A entidade proprietária da relação é aquela entidade que possui a chave estrangeira da outra entidade. Em geral, a entidade que está do lado \*Many será a proprietária. Se pensarmos de forma lógica, só nela que poderemos ter uma chave estrangeira da outra, pois cada elemento da entidade proprietária tem associação com apenas *um* elemento da entidade referenciada. Vejamos um exemplo:

Vamos imaginar que estamos escrevendo a API para comércio eletrônico e precisamos modelar produtos (que representam produtos físicos em estoque que podem ser vendidos na loja) e a cesta de compras do usuário. Nos pedaços de código abaixo configuramos uma relação entre CestaDeCompras e Produto.

````java
@Entity
public class Produto {
  @Id
  private Long idProduto;
  
  ...
  
  @ManyToOne
  private CestaDeCompras cesta;
  ...
}
````
Esse primeiro trecho de código nos informa através da anotação @ManyToOne que aparece antes do atributo cesta que estamos configurando um relacionamento muitos para um que reflete a seguinte regra: muitos produtos podem estar associados à mesma cesta de compras, mas cada produto só pode estar associado a uma única cesta de compras. Faz sentido? Sim, é a regra de negócio comum: um usuário pode colocar quantos produtos desejar em sua cesta de compras e cada produto da loja não pode estar em mais de uma cesta de compras. Só com esta associação já temos uma relação definida, mesmo que nada seja configurado na cesta de compras. 

Com esta anotação, o que acontece no banco de dados é que na tabela PRODUTO vai existir uma chave estrangeira que será o ID da cesta de compras associada ao produto. Em termos de configuração o que ocorre é que a seguinte anotação é configurada pra nós na classe Protudo, mas fica transparente (isto é, não a vemos):

java```
  @ManyToOne
  @JoinColumn(name = "idCesta")
  private CestaDeCompras cesta;
  ...
```

Esta anotação @JoinColumn informa que na tabela PRODUTO existirá uma chave estrangeira que vem da tabela CESTA_DE_COMPRAS e que é a coluna identificada por *idCesta*. Em outras palavras, através da anotação @JoinColumn, informamos na classe Produto o nome da coluna da classe Cesta que deve servir de chave estrangeira. A entidade Produto será a entidade proprietária da relação, uma vez que é em sua tabela que está a chave estrangeira da associação com a classe Cesta. 

Dizemos que é uma relação **unidirecional**, pois só a entidade Produto sabe da existência dessa relação. Através desta relação conseguimos recuperar, apenas a partir da entidade produto, a cesta de compras relacionada. Isto significa que ao recuperar o ID de uma cesta de compras precisaremos de uma query extra para recuperar os produtos da cesta a partir da tabela de produtos. Para recuperar os produtos da cesta de compras teremos que pesquisar todos os produtos associados ao ID da cesta de interesse. Esta é uma query muito básica, já que existe uma chave estrangeira de idCesta na tabela de produtos. Para que isso seja realizado de forma simples é preciso acrescentar no repositório de Produto um método que retorne todos os produtos associados a um dado ID de cesta de compras. Ao usar o JpaRepository podemos facilmente definir na interface do DAO de Produto o seguinte método:

java```
	List<Produto> findByCestaIdCesta(Long id);
```

Este método informa que deve-se buscar o atributo cesta em Produto (que é do tipo CestaDeCompra) e usar o atributo idCesta dentro de CestaDeCompra para recuperar os registros desejados. Ele vai recuperar todos os produtos que estiverem associados ao id da cesta passado como parâmetro. 

Uma opção de relação unidirecional diferente teria sido escolher a classe CestaDeCompras para conehcer o relacionamento e manter a classe Produto sem o conhecimento do relacionamento. Nesse caso a configuração realizada não seria na classe Produto, mas sim na classe CestaDeCompras como abaixo.

java```
@Entity
public class CestaDeCompras {
  @Id
  private Long idCesta;
  
  ...
  
  @OneToMany
  private List<Produto> produtos;
  ...
  
  public void adicionaProduto(Produto produto){
  	produtos.add(produto);
  }
  
  public List<Produto> getProdutos() {
	return produtos;
  }
  ...
 }
```

Neste caso, estamos informando que podem haver vários produtos para cada cesta, mas cada produto está associado a apenas uma cesta. O id da cesta estará associado a vários ids de produtos (os produtos da cesta). Desta forma, não é possível ter uma chave estrangeira na tabela CestaDeCompras, como tínhamos em produto. Neste caso uma nova tabela de associação é criada, com o idCesta e idProduto. Quando recuperamos uma cesta de compras do banco de dados (via o DAO específico), poderemos chamar o método getProdutos() de CestaDeCompras. Neste momento a tabela de associação será usada para recuperar os produtos do banco, mas tudo isso é transparente pra nós. Mais adiante veremos configurações que poderemos usar para indicar se queremos já recuperar os produtos automaticamente quando acessamos a cesta, ou se a recuperação dos produtos do banco se dará apenas quando o método getProdutos() for chamado. Se a única forma de acesso de cestas e produtos for a partir do identificador da cesta, então essa relação unidirecional em que a cesta conhece a relação e sabe que tem associação com muitos produtos, é mais eficiente. Mas se o acesso da aplicação é encontrar um produto e dali identificar em que cesta de compras o produto está então nesse caso, a primeira configuração que usamos seria mais eficiente. Tudo depende de como é o acesso aos dados.

Voltando para a primeira configuração, em que apenas a classe Produto conhece a relação... Temos a chance de tornar esta relação **bidirecional** informando na entidade CestaDeCompras sobre a relação (igual como fizemos quando apenas a CestaDeCompras sabia da relação). O que ocorre aqui é que a entidade Produto *sabe*  que está associada a uma cesta de compras e a entidade CestaDeCompras *sabe* que está associada a muitos produtos. Nesse caso será possível acessar, a partir da cesta de compras, os produtos relacionados a ela (como explicamos anteriormente) e também será possível acessar a cesta de compras a partir dos produtos. A configuração da classe CestaDeCompras já foi apresentada anteriormente.

Quando a relação é bidirecional assim, a classe que tem a chave estrangeira é chamada de *proprietária* e a outra classe é geralmente chamada de classe referenciada, ou no caso em que a relação é de um para muitos (como esse) a classe pode ser chamada de classe * mãe*. Na configuração bidirecional, usamos a anotação @OneToMany no atributo List<Produto> produtos da classe CestaDeCompras (o código está masi acima). Como a cesta de compras não é a classe proprietária, esta anotação @OneToMany vai referenciar o mapeamento que já existe (que foi definido por @JoinColumn) na classe proprietária Produto. Automaticamente há uma comfiguração *@OneToMany(mappedBy = "cesta")* que é realizada para este relacionamento. Esta configuração (que o framework vai pôr pra nós automaticamente) indica que já existe na entidade proprietária uma coluna de assoação entre as entidades Produto e CestaDeCompras que se chama "cesta". 
  
A tabela CESTA_DE_COMPRAS continua apenas com as colunas ID_CESTA, e outras colunas relativas à cesta. Na tabela PRODUTO temos as colunas ID_PRODUTO, outras colunas de produto e a coluna ID_CESTA que é a chave estrangeira da tabela CESTA_DE_COMPRAS em PRODUTO. Esta coluna é a que permite o *join* (junção, união) entre a CESTA_DE_COMPRAS e PRODUTO. Como a chave estrangeira está na entidade Produto, dizemos que Produto é a entidade proprietária dessa relação. Até aqui nenhuma novidade em relação ao que já tínhamos. O que muda é o seguinte: ao estabelecer esta relação bidirecional, o que o JPA realiza no contexto do banco de dados é a criação de uma tabela extra: a tabela que associa produtos e cestas na perspectiva da cesta (cada cesta associada a muitos produtos). Não dá pra ter uma coluna de chave estrangeira na CESTA_DE_COMPRAS, porque são muitos produtos que podem estar associados a cada registro de cesta. Assim, cria-se automaticamente uma nova tabela de associação no banco, com 2 colunas, ambas chaves estrangeiras: uma de CestaDeCompras e uma de Produto. Além disso, é preciso adicionar na cesta métodos que adicionem produtos a ela e que recuperem produtos (assim como era preciso adicionar em Produto métodos para recuperar e configurar a cesta de compras). Com a relação bidirecional, vai ser possível acessar a cesta de compras a partir do objeto produto. E será possível a partir da cesta acessar a lista de produtos dela. Isso tudo sem precisar de queries extra ao banco de dados. Ao contrário, quando a relação era unidirecional, a lista de produtos não vinha na cesta de compras lida do banco de dados. Era preciso usar o ID da cesta e realizar nova query para recuperar todos os produtos associados ao ID_CESTA de interesse. O que é melhor? Manter a relação uni ou bidirecional? Não existe uma resposta correta, isso vai depender de como esses dados são acessados pela aplicação.

Em resumo: relacionamentos podem ser bidirecionais ou unidirecionais. Em uma relação unidirecional apenas um lado da relação conhece a relação. O outro lado não sabe que a relação existe. Sendo assim, usamos a anotação de relacionamento (@OneToOne, @OneToMany, etc.) em apenas uma entidade da relação, a entidade que fica ciente da relação. Ao contrário, na relação bidirecional ambas as entidades sabem da existência da relação. Nas relações bidirecionais podemos navegar nas entidades nas duas direções sem precisar de queries extra. Aproveitamos aqui para mencionar sobre o conceito de entidade proprietária e entidade referenciada/não proprietária e entidade mãe.

Projetar bem o esquema de dados de uma aplicação, projetar estes relacionamentos e a forma como os dados serão acessados é um desafio. Mesmo programadores bastante experientes podem passar por muitas dúvidas aqui. Então, não queira abraçar um mundo agora. Vá aos poucos brincando com os conceitos e com as configurações, e entendendo a consequência de cada configuração no banco de dados e no seu código. O que deve reger o projeto destes relacionamentos é a forma como os dados devem ser acessados na aplicação. Em se tratando de aplicações big data (grandes massas de dados) o problema pode ser ainda maior... Para entender melhor e fixar estes conceitos o ideal é brincar um pouco com eles. Para isso use algum exmeplo muito simples de um código que você já entende e configure relações uni e bidirecionais entre entidades nesse código. E veja como fica o banco de dados e como os objetos são acessados no código java.

## Configuração de Cascata (Cascade)

JPA permite operações em cascata que se propagam da entidade "mãe" para a "filha". Tipicamente, **a entidade mãe é a não proprietária**. No nosso exemplo, a entidade mãe é a CestaDeCompras, que tem a associação com a entidade filha Produto. As operações SELECT, INSERT, UPDATE, DELETE podem ser propagadas para a base de dados da entidade filha.

Os tipos de cascata JPA são PERSIST, MERGE, REFRESH, REMOVE, DETACH, ALL. Ao realizar estas operações na entidade mãe, a entidade filha também deve ser modificada para refletir as mudanças. Essa configuração é muito específica do tipo de relacionamento e não existe uma configuração default única. Para relacionamentos bidirecionais OneToOne, por exemplo, faz sentido o CascadeType.ALL uma vez que o ciclo de vida de uma entidade está associada ao ciclo de vida da outra. 

Em uma relação bidirecional como a que exemplificamos entre CestaDeCompras e Produto, se configurarmos na CestaDeCompras o CascadeType.ALL indicamos que todos os produtos associados à cesta sejam removidos da tabela de produtos, o que talvez não seja o que desejamos... No entando em uma relação semelhante entre Post e ComentariosDoPost provavelmente queremos que todos os comentários sejam removidos ao remover um post.

A dica é entender a relação e definir a melhor configuração para cada caso. Na dúvida faça testes simples que lhe permitam entender sua configuração e como as operações em cascata estão funcionando.

Uma configuração que anda perto da CascadeType.REMOVE é a orphanRemove = true. Esta opção marca a entidade filha a ser removida se ela não tiver mais referências a partir da entidade mãe. Por exemplo, no relacionamento entre Comentarios de um Post e o Post, inserir esta configuração indica que se o Post deixar de existir ou se fizermos um post.setComentarios(null) todos os comentários do post devem ser removidos, pois não existe mais nenhuma referência a estes e eles podem deixar de existir. No nosso exemplo de produto e cesta de compras, será que queremos que os produtos sejam removidos quando fizermos cesta.setProdutos(null)?

## Modo de busca (FETCH)

Uma outra configuração possível diz respeito à forma como os dados são recuperados do banco de dados. Essa é a configuração *FETCH*. Existem duas estratégias para esta configuração:

* A estratégia *EAGER* indica que em tempo de execução os dados devem ser recuperados em uma consulta de uma vez. Isto significa que se a estratégia EAGER for usada, o EntityManager vai recuperar os dados da entidade mãe e da entidade filha de uma só vez por uma consulta. 
* A estratégia *LAZY* indica que os dados serão obtidos de forma preguiçosa. isto significa que eles devem ser recuperados apenas quando forem acessados pela primeira vez. Neste caso, os dados são recuperados quando necessário através de subconsultas. O EntityManager recupera os dados da entidade mãe primeiro e depois os dados da entidade filha sob demanda. No caso do exemplo da relação entre cesta de compras e produto. Ao usar a estratégia EAGER ao recuperar uma cesta de compras, os produtos associados já são também recuperados. Já se a estratégia LAZY for usada, ao recuperar um produto apenas os atributos de produto específicos são recuperados. Apenas com uma chamada a, por exemplo, cesta.getProdutos() que os produtos associados à cesta de compras seriam recuperados do banco de dados.

## Sobre os repositórios

Alguns métodos adicionais podem ser úteis quando falamos de relacionamentos OneToMany. Por exemplo, recuperar a partir da tabela de produtos todos os produtos de uma dada cesta de compras (temos que conhecer o ID da cesta). Em se tratando da interface JPARepository, criamos novos métodos seguindo regras estritas para nomeação/assinatura do método novo. A consulta ao banco de dados será criada automaticamente ao seguir as regras para nomeação do método. 

O código abaixo para a interface ProdutosDAO adiciona à interface um novo metodo que retorna todos os produtos que estão associados à cesta de compras com o ID passado como parâmetro.

````java
@Repository
public interface ProdutosDAO<T, ID extends Serializable> extends JpaRepository<Produto, Long> {

	List<Comentario> findByCestaCestaId(Long id);
}
````
Este método irá recuperar todos os registros na tabela de produtos cujo atributo idCesta (que vem da associação cesta) seja igual ao ID passado. Para entender melhor: esse nome só funciona porque na classe Produto existe o atributo cesta definido como relação de muitos para um entre a classe Produto e a classe CestaDeCompras:

````java
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "idCesta")
  @JsonIgnore
  private CestaDeCompras cesta;
````

Nessa mesma configuração damos um nome à coluna que servirá de *join* para esta associação, o nome que demos foi idCesta. Então estamos dizendo que recuperamos a cesta de compras associada ao produto através do id da cesta. Em termos de banco de dados, o que acontece é que na tabela de PRODUTO vai haver uma coluna chamada ID_CESTA que é a chave estrangeira de CestaDeCompras na tabela PRODUTO. São essas configurações que usamos para gerar o nome do método e derivar a consulta ao banco automaticamente (sem precisar escrever uma @Query explícita). Se o atributo que chamamos cesta fosse chamado cestaDeCompras, então o nome do método na interface mudaria para findByCestaDeComprasIdCesta.

Mais detalhes de como gerar os nomes dos métodos para derivar as consutas podem ser vistos [aqui](https://www.baeldung.com/spring-data-derived-queries), [aqui](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#repositories.query-methods.query-creation) e [aqui](https://www.baeldung.com/spring-data-sorting).

## Documentação de referência

[Repositórios JPA](https://docs.spring.io/spring-data/jpa/docs/1.5.0.RELEASE/reference/html/index.html)

[Tutorial java sobre persistência no backend](https://docs.oracle.com/javaee/5/tutorial/doc/bnbrs.html)

[javadoc da JPA](https://docs.oracle.com/javaee/7/api/javax/persistence/package-summary.html)

[JPA mini book](http://enos.itcollege.ee/~jpoial/java/naited/JPA_Mini_Book.pdf)

[Artigos avançados relacionados a JPA da baeldung](https://github.com/eugenp/tutorials/tree/master/persistence-modules/java-jpa)

[Dicas para criação de queries a partir dos nomes dos métodos](https://www.baeldung.com/spring-data-derived-queries)

