# Entendendo um pouco mais de JPA e relacionamentos entre entidades

Já vimos que precisamos criar classes de modelo, conhecidas como entidades, para representar as classes do domínio da aplicação sendo desenvolvida. Se vamos vender bananas em navios, certamente teremos entidades para representar bananas e entidades para representar navios e conteiners... Estas classes são marcadas com a anotação @Entity. Lembremos também que estas entidades precisam obrigatoriamente ter um atributo que serve como identificador único, que anotamos com @Id.

Até o momento desenvolvemos aplicações simples em que as entidades não tem relacionamentos entre si. Mas no mundo real, qualquer aplicação, por mais simples que seja, vai requerer algum relacionamento entre entidades. Entender os relacionamentos entre as entidades e as operações realizadas pela aplicação é essencial para modelar o esquema de dados de aplicações que mantém dados em sistemas de dados relacionais. 

Em se tratando de JPA (Java Persistence API) contamos com um componente chamado EntityManager, que é parte da Java Persistence API. A função do EntityManager é implementar as interfaces e as regras de ciclo de vida definidas pela especificação JPA 2.0. É esta entidade então que vai lidar com as várias entidades @Entity gerenciando seu ciclo de vida e garantindo sua persistência no banco de dados como tabelas. Como desenvolvedor, o importante é reconhecer o tipo de relação existente entre as entidades para poder mapeá-las pela configuração mais adequada. 

Por exemplo, suponha que você tem uma API de comércio eletrônico. Dentre as entidades de sua aplicação estão Produto, Cesta e Estoque, cada uma com seus atributos específicos. Claramente há uma relação entre estas entidades. Uma cesta de compras tem um ou mais produtos e pertence a um único usuário. Um estoque contém uma coleção de produtos. Quando pensamos na cesta de compras, o relacionamento é de um para um, pois apenas uma cesta existe para cada usuário e apenas um usuário é dono de uma cesta de compras. Estes atributos cruzados precisam estar nas duas entidades? Por exemplo, na entidade Cesta, será preciso ter um atributo do tipo Usuario? Ou seria melhor manter um atributo do tipo CestaDecompras em usuário? Ou manter em ambas as classes? Essa decisão vai depender de como as operações são realizadas na aplicação. Se a aplicação sempre recupera primeiro o usuário, então apenas o usuário precisa definir que tem uma cesta de compras... Mas se é preciso acessar muitas vezes a cesta de compras e depois recuperar o usuário, então seria interessante ter também na cesta de compras a chave estrangeira de usuário. Veja que estamos falando de entidades, mas que representam tabelas de bancos de dados. Para entender melhor, vamos rever o conceito de chaves e chaves estrangeiras.

Você já sabe que toda entidade precisa ter um atributo que é marcado com @Id. Este atributo é a chave da tabela associada à entidade e significa que este atributo não pode ser duplicado na tabela, pois ele identifica cada elemento da tabela de forma única. Para estabelecer relações entre entidades, o que ocorre é que inserimos chaves estrangeiras (chaves/ids de outras entidades) em entidades relacionadas. Voltando para o caso da cesta de compras e do usuário. A relação estabelecida pode indicar, por exemplo, que na tabela USUARIO deve estar o id da cesta de compras a que ela pertence. Este id da cesta de compras no usuário é uma chave estramgeira e será este o atributo que estabelece o relacionamento entre as duas tabelas (USUARIO e CESTA). Assim, em USUARIO há uma coluna que corresponde à coluna que é a chave primária da tabela CESTA. Ao recuperar um usuário se torna fácil recuperar a cesta de compras do mesmo.

No momento de realizar este design não há certo ou errado, é um projeto. O que ocorre é que algumas configurações vão manter seu código mais simples que outras, ou manter sua aplicação mais eficiente em termos de desempenho. Esse tipo de decisão não é simples e pode até mudar ao longo do desenvolvimento da aplicação, pois aos poucos os próprios desenvolvedores vão tendo um conhecimento muito maior da aplicação e de seu uso. Até os desenvolvedores mais experientes passam muitas vezes por grandes desafios ao ter que identificar e projetar bons relacionamentos (eficientes) entre as entidades. 

Quando pensamos em produto e cesta de compras, já percebemos um outro tipo de relacionamento. Cada cesta de compras está relacionada com muitos produtos, mas cada produto só pode pertencer a uma cesta de compras (senão venderíamos o mesmo produto a mais de um usuário, o que não é recomendado). Neste caso a cesta de compras poderia ter uma lista de produtos mas o produto só deveria estar associado uma única cesta de compras. 

A qualidade do projeto de dados de uma aplicação, isto é, a definição desses relacionamentos entre entidades e como são mapeados/configurados, vai determinar em grande parte a complexidade da sua aplicação. Neste módulo vamos entender um pouco melhor sobre relacionamentos entre entidades e como configurá-los usando JPA. 

É possível relacionar duas entidades através de uma das seguintes anotações:

* @OneToOne: é um relacionamento um para um que define que só há uma entidade relacionada com a outra. Um exemplo desse tipo de relacionamento pode ocorrer entre a entidade Loja e a entidade endereço. Suponha que a aplicação comporta uma rede de lojas, cada uma com seu endereço. Ao configurar este relacionamento estamos dizendo que toda loja tem um endereço e todo endereço só pertence a uma loja.
* @OneToMany: uma entidade da classe onde esta relação é definida está associada a muitas entidades da outra classe. Essa relação vai ocorrer sempre que uma classe tem uma coleção de tipos de outra classe. Esta é a associação entre uma cesta de compras e os produtos adicionados nela. Uma cesta de compras deve poder estar associada a muitos produtos, mas um produto específico do estoque só pode estar associado a uma cesta de compras (caso contrario estariamos vendendo o mesmo produto a usuários diferentes).
* @ManyToOne: é a relação inversa a @OneToMany, deve ser especificada na entidade que é a parte muitos. Existem muitas entidades da classe onde esta relação é definida associada a uma única entidade da outra classe. Por exemplo, na classe Produto, teriamos uma associação @ManyToOne com a classe Cesta, já que um produto só pode estar associado a uma única cesta.
* @ManyToMany: é uma associação de muitos para muitos. É usada nos casos em que uma entidade de um tipo A pode estar associada a muitas entidades do tipo B e cada entidade do tipo B também pode estar associada a várias entidades do tipo A. Imagine por exemplo, a relação entre livros e autores. Um livro pode ter muitos autores e um autor pode escrever muitos livros.

![Relações entre entidades](https://port.sas.ac.uk/pluginfile.php/233/mod_book/chapter/140/10%20Image%20D3.jpg)
> Fonte: https://port.sas.ac.uk/mod/book/view.php?id=75&chapterid=140

## Configurando relações

Vamos imaginar que estamos escrevendo a API para comércio eletrônico e precisamos modelar produtos (que representam produtos físicos em estoque que podem ser vendidos na loja) e a cesta de compras do usuário. Nos pedaços de código abaixo configuramos uma relação entre Cesta e Produto.

````java
@Entity
public class Produto {
  @Id
  private Long idProduto;
  
  ...
  
  @ManyToOne
  @JoinColumn(name = "cesta_id")
  private Cesta cesta;
  ...
}
````
Esse primeiro trecho de código nos informa através da anotação @ManyToOne que aparece antes do atributo cesta que estamos configurando um relacionamento muitos para um que reflete a seguinte regra: muitos produtos podem estar associados à mesma cesta de compras, mas cada produto só pode estar associado a uma única cesta de compras. Faz sentido? Sim, é a regra de negócio comum: um usuário pode colocar quantos produtos desejar em sua cesta de compras e cada produto da loja não pode estar em mais de uma cesta de compras. Só com esta associação já temos uma relação definida, mesmo que nada seja configurado na cesta de compras. 

A anotação JoinColumn serve para instruir o JPA sobre como gerar dados que permitam o reconhecimento desta relação. Com esta anotação, estamos dizendo que na tabela PRODUTO deve existir uma nova coluna, que vai se chamar cesta_id e que é chave estrangeira de Cesta. Esta chave estrangeira vem da tabela CESTA e é a coluna identificada por *id*. Com esta nova coluna na tabela PRODUTO é simples recuperar a cesta de compras onde está um produto. Através desta coluna o JPA estará habilitado a formar as consultas adequadas para preencher os dados de produto.getCesta() e através do id da cesta recuperar na tabela CESTA a cesta onde está o produto. 

É bom entender o que a anotação JoinColumn faz, mas ela poderia ser omitida, mantendo apenas a anotação @ManyToOne no atributo cesta. Neste caso o framework colocaria de forma trnasparente pra nós esta anotação (@JoinColumn(name = "cesta_id")). Por default, usa-se como nome da coluna o <nome do atributo "\_" nome do id da entidade do atributo>. Assim, na classe Cesta deve existir um atributo marcado com @Id que se chama id e é portanto a chave primária de Cesta. Você pode escolher o nome que quiser para a coluna que carrega a chave estrangeira da entidade não proprietária, mas é interessante usar estes valores default porque fica tudo padronizado.

Nas associações OneToMany e ManyToOne vamos ter o conceito de **entidade proprietária** e entidade não proprietária. A entidade proprietária da relação é aquela entidade que possui a chave estrangeira da outra entidade. Em geral, a entidade que está do lado \*Many será a proprietária. Se pensarmos de forma lógica, só nela que poderemos ter uma chave estrangeira da outra, pois cada elemento da entidade proprietária tem associação com apenas *um* elemento da entidade referenciada. Neste exemplo, a entidade proprietária é claramente a entidade Produto.

Esta é uma relação **unidirecional**, pois só a entidade Produto sabe da existência dessa relação. Através desta relação conseguimos recuperar, apenas a partir da entidade produto, a cesta de compras relacionada. Isto significa que ao recuperar uma cesta de compras precisaremos de uma query extra para recuperar os produtos da cesta a partir da tabela de produtos. Para recuperar os produtos da cesta de compras teremos que pesquisar todos os produtos associados ao ID da cesta de interesse. Esta é uma query muito básica, já que existe uma chave estrangeira (cesta_id) na tabela de produtos. Para que isso seja realizado de forma simples é preciso acrescentar no repositório de Produto um método que retorne todos os produtos associados a um dado ID de cesta de compras. Ao usar o JpaRepository podemos facilmente definir na interface do DAO de Produto o seguinte método:

````java
  List<Produto> findByCestaId(Long id);
````

Este método informa que deve-se buscar o atributo cesta em Produto (que é do tipo CestaDeCompra) e usar o atributo id dentro de Cesta para recuperar os registros desejados. Ele vai recuperar todos os produtos que estiverem associados ao id da cesta passado como parâmetro. 

Uma relação muitos para um bem parecido com esta que configuramos, porém em outro contexto, pode ser vista neste [repositório](https://github.com/raquelvl/psoft/tree/master/exemplo4.v2).

Uma opção de relação unidirecional diferente teria sido escolher a classe Cesta para conhecer o relacionamento e manter a classe Produto sem o conhecimento do relacionamento. Nesse caso a configuração realizada não seria na classe Produto, mas sim na classe Cesta como abaixo.

````java
@Entity
public class Cesta {
  @Id
  private Long id;
  
  ...
  
  @OneToMany
  @JoinTable(name = "cesta_produtos",
	     joinColumns = @JoinColumn(name = "cesta_id"),
	     inverseJoinColumns = @JoinColumn(name = "produtos_id"))
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
````

Neste caso, estamos informando que podem haver vários produtos para cada cesta, mas cada produto está associado a apenas uma cesta. O id da cesta estará associado a vários ids de produtos (os produtos da cesta). Desta forma, não é possível ter uma chave estrangeira na tabela CESTA, como tínhamos em produto. Neste caso uma nova tabela de associação é criada, com o id da cesta e o id do produto. Esta configuração é realizada através da anotação @JoinTable. Esta anotação com a configuração associada diz que uma tabela de associação deve ser criada, esta tabela vai se chamar cesta_produtos (este nome é formado pegando o nome daonde a anotação é feita, que é cesta e concatenando com "\_" e o nome do atributo que referencia a outra classe do relacionamento, nesse caso, produtos). Dentro da configuração indicamos as colunas. A primeira coluna define a classe que será a proprietária da relação (joinColumn) e a outra a classe não proprietária (inverseJoinColumn). Os nomes das colunas também são formados seguindao algumas regras (por default): nome da classe proprietária concatenado com "\_" e o nome da chave primária dessa classe, que é id; e para a segunda coluna, que corresponde à classe nao proprietária, mais uma vez usamos o nome do atributo referente a esta classe, que é produtos, concatenado com "\_" e o nome do atributo que é a chave primária desta classe não proprietária, que nesse caso é id.  

Mais uma vez, se não indicarmos esta configuração explicitamente, o framework vai pôr pra nós. Mas, é importante saber que isto está ocorrendo e o que significa esta configuração. Seguir a padronização de nomenclatura é bom para manter coerência de nomes em toda a aplicação e até entre desenvolvedores diferentes programando.

Quando recuperamos uma cesta de compras do banco de dados (via o DAO específico), poderemos chamar o método getProdutos() de Cesta. Neste momento a tabela de associação será usada para recuperar os produtos do banco, mas tudo isso é transparente pra nós, feito pelo hibernate/JPA. Mais adiante veremos configurações que poderemos usar para indicar se queremos já recuperar os produtos automaticamente quando acessamos a cesta, ou se a recuperação dos produtos do banco se dará apenas quando o método getProdutos() for chamado. Se a única forma de acesso de cestas e produtos for a partir do identificador da cesta, então essa relação unidirecional em que a cesta conhece a relação e sabe que tem associação com muitos produtos, é mais eficiente. Mas se o acesso da aplicação é encontrar um produto e dali identificar em que cesta de compras o produto está então nesse caso, a primeira configuração que usamos seria mais eficiente. Tudo depende de como é o acesso aos dados.

Voltando para a primeira configuração, em que apenas a classe Produto conhece a relação... A coluna cesta_id na tabela PRODUTO seria suficiente para se recuperar os produtos de uma cesta. No entanto, até o momento, configuramos uma relação **unidirecional**, em qeu só uma entidade sabe que a relação existe. Até o momento, a entidade Cesta não sabe que tem uma relação com Produto. Assim, a cesta de compras não o método getProdutos(), pois ela desconhece a relação. 

Temos a chance de tornar esta relação **bidirecional** informando na entidade Cesta sobre a relação (igual como fizemos quando apenas a Cesta sabia da relação). O que ocorre aqui é que a entidade Produto *sabe*  que está associada a uma cesta de compras e a entidade Cesta *sabe* que está associada a muitos produtos. Nesse caso será possível acessar, a partir da cesta de compras, os produtos relacionados a ela (como explicamos anteriormente) e também será possível acessar a cesta de compras a partir dos produtos. A configuração da classe Cesta já foi apresentada anteriormente.

Quando a relação é bidirecional assim, a classe que tem a chave estrangeira é chamada de **proprietária** e a outra classe é geralmente chamada de classe **mãe**. Na configuração bidirecional, usamos a anotação @OneToMany no atributo List<Produto> produtos da classe Cesta (o código está masi acima). Como a cesta de compras não é a classe proprietária, esta anotação @OneToMany vai referenciar o mapeamento que já existe (que foi definido por @JoinColumn) na classe proprietária Produto. Automaticamente há uma comfiguração *@OneToMany(mappedBy = "cesta")* que é realizada para este relacionamento. Esta configuração (que o framework vai pôr pra nós automaticamente) indica que já existe na entidade proprietária uma coluna de assoação entre as entidades Produto e Cesta que se chama "cesta". 
  
A tabela CESTA continua apenas com as colunas ID_CESTA, e outras colunas relativas à cesta. Na tabela PRODUTO temos as colunas ID_PRODUTO, outras colunas de produto e a coluna ID_CESTA que é a chave estrangeira da tabela CESTA em PRODUTO. Esta coluna é a que permite o *join* (junção, união) entre a CESTA e PRODUTO. Como a chave estrangeira está na entidade Produto, dizemos que Produto é a entidade proprietária dessa relação. Até aqui nenhuma novidade em relação ao que já tínhamos. O que muda é o seguinte: ao estabelecer esta relação bidirecional, o que o JPA realiza no contexto do banco de dados é a criação de uma tabela extra: a tabela que associa produtos e cestas na perspectiva da cesta (cada cesta associada a muitos produtos). Não dá pra ter uma coluna de chave estrangeira na CESTA, porque são muitos produtos que podem estar associados a cada registro de cesta. Assim, cria-se automaticamente uma nova tabela de associação no banco, com 2 colunas, ambas chaves estrangeiras: uma de Cesta e uma de Produto. Além disso, é preciso adicionar na cesta métodos que adicionem produtos a ela e que recuperem produtos (assim como era preciso adicionar em Produto métodos para recuperar e configurar a cesta de compras). Com a relação bidirecional, vai ser possível acessar a cesta de compras a partir do objeto produto. E será possível a partir da cesta acessar a lista de produtos dela. Isso tudo sem precisar de queries extra ao banco de dados. Ao contrário, quando a relação era unidirecional, a lista de produtos não vinha na cesta de compras lida do banco de dados. Era preciso usar o ID da cesta e realizar nova query para recuperar todos os produtos associados ao ID_CESTA de interesse. O que é melhor? Manter a relação uni ou bidirecional? Não existe uma resposta correta, isso vai depender de como esses dados são acessados pela aplicação.

Em resumo: relacionamentos podem ser bidirecionais ou unidirecionais. Em uma relação unidirecional apenas um lado da relação conhece a relação. O outro lado não sabe que a relação existe. Sendo assim, usamos a anotação de relacionamento (@OneToOne, @OneToMany, etc.) em apenas uma entidade da relação, a entidade que fica ciente da relação. Ao contrário, na relação bidirecional ambas as entidades sabem da existência da relação. Nas relações bidirecionais podemos navegar nas entidades nas duas direções sem precisar de queries extra. Aproveitamos aqui para mencionar sobre o conceito de entidade proprietária e entidade referenciada/não proprietária e entidade mãe.

Projetar bem o esquema de dados de uma aplicação, projetar estes relacionamentos e a forma como os dados serão acessados é um desafio. Mesmo programadores bastante experientes podem passar por muitas dúvidas aqui. Então, não queira abraçar um mundo agora. Vá aos poucos brincando com os conceitos e com as configurações, e entendendo a consequência de cada configuração no banco de dados e no seu código. O que deve reger o projeto destes relacionamentos é a forma como os dados devem ser acessados na aplicação. Em se tratando de aplicações big data (grandes massas de dados) o problema pode ser ainda maior... Para entender melhor e fixar estes conceitos o ideal é brincar um pouco com eles. Para isso use algum exmeplo muito simples de um código que você já entende e configure relações uni e bidirecionais entre entidades nesse código. E veja como fica o banco de dados e como os objetos são acessados no código java.

É importante lembrar aqui que essas configurações que realizamos usando JPA vão criar tabelas nos bancos de dados com os nomes que colocarmos nas entidades e em cada tabela existirão as colunas com os nomes e tipos que definirmos nas entidades. Por exemplo, se existe uma entidade Produto e dentro dela um atributo idProduto do tipo long, então na tabela PRODUTO que será criada/acessada vamos ter uma coluna ID_PRODUTO do tipo long. Isso é muito cômodo para quando o esquema de dados não existe ainda e o banco de dados não existe ainda e o desenvolvedor tem a liberdade de projetar como achar mais adequado. Nem sempre é essa a situação. Para bancos de dados legados será necessário adaptar o código às bases de dados existentes e outras configurações serão necessárias (ex. @Table e @Column). Como estamos aqui na introdução a este mundo já tão complexo, não entraremos nesses detalhes.

## Configuração de Cascata (Cascade)

Para entender operações em cascata vamos usar um exemplo novo, mas conhecido, uma rede social, que tem sua timeline com suas postagens. Se você cria uma nova postagem, esta postagem vai ser adicionada à sua timeline. Temos aqui um relacionamento @ManyToOne em Postagem, que será a classe proprietária e na tabela POSTAGEM vamos encontrar uma chave estrangeira do id da timeline relacionada. Até aqui nenhuma novidade... Imagine seu código, que você tem um objeto timeline e que vai chamar neste objeto o método adiciona postagem passando o objeto novaPostagem como parâmetro. Até aqui você está trabalhando com os objetos timeline e novaPostagem em memória. Mas em algum momento você vai chamar o DAO (data access object) da timeline, digamos que chama timelinesDAO e chamar um save para salvar a timeline alterada com a adição da nova postagem em banco de dados. Mas, se você tentar salvar a nova timeline em banco antes de salvar a postagem, vai ocorrer um erro, pois seu banco irá tentar referenciar um registro que não existe. A postagem nunca foi salva em banco. Na sua console o erro vai ser mais ou menos assim:

````java
hibernate.TransientObjectException: object references an unsaved transient instance - save the transient instance before flushing: br.ufpb.minicurso.rede.entidades.Postagem
````
Isto significa que você está tentando referenciar uma instância transiente, que ainda não foi armazenada no disco. Para resolver este problema você tem que dar o save da Postagem criada antes de associá-la à Timeline. Essa é a maneira mais intuitiva de resolver o problema e deixa na mão do desenvolvedor lembrar disso e decidir quando cada entidade será salva em banco. Mas tem uma outra opção, que usa a configuração de operações em cascata (do inglês, _cascade_). Não vamos entrar em muitos detalhes de como configurar cada situação específica, queremos apenas dar uma noção do que é isso e de quando pensar sobre isso.

As operações em cascata podem se propagar da entidade mãe para a filha. A entidade mãe está do lado One e é em geral a entidade não proprietária. No nosso exemplo, a entidade mãe é a Timeline, que tem a associação com a entidade filha Postagem. As operações SELECT, INSERT, UPDATE, DELETE do banco de dados podem ser propagadas para a base de dados da entidade filha. Lembre que em timeline tem um método adicionaPostagem(Postagem novaPostagem). Se configuramos que a Timeline deve cascatear operações de PERSIST, por exemplo, isso equivale a dizer que quando salvarmos a Timeline a nova postagem será também salva, evitando este problema.

Esta configuração é chamada em JPA de *cascade* (cascata) e se for usada deve estar como uma opção dentro do relacionamento (ex. @OneToMany(cascade = CascadeType.ALL)). Os tipos de cascata JPA são PERSIST, MERGE, REFRESH, REMOVE, DETACH, ALL. Estes tipos indicam o tipo de operação que, ao ser realizada na entidade mãe, deve se propagar para a entidade filha. Por exemplo, PERSIST é o save da entidade mãe, se o método sabe de CestasDAO for chanmado e o cascateamento estiver para PERSIST, então o produto também será salvo. O ALL indica que qualquer operação na classe mãe deve ser automaticamente refletida na classe filha, isto é, nas tabelas associadas a elas. Essa configuração é muito específica do tipo de relacionamento e não existe uma configuração default única. 

Em uma relação bidirecional como a que exemplificamos entre Postagem e Timeline, se configurarmos na Timeline o CascadeType.ALL indicamos que todas as postagens associadas à timeline sejam removidas da tabela de postagens quando a Timeline for excluída (o que deve ocorrer quando o usuário for excluído). Para relacionamentos bidirecionais OneToOne, por exemplo, faz sentido o CascadeType.ALL quando o ciclo de vida de uma entidade está associada ao ciclo de vida da outra. Mas é preciso entender bem o relacionamento e só com o tempo você vai ficar seguro(a) com estas configurações. Você pode ver mais sobre essa configuração [aqui](https://www.codeflow.site/pt/article/jpa-cascade-types), [aqui](https://www.baeldung.com/jpa-cascade-types) ou na própria [documentação da JPA](https://javaee.github.io/javaee-spec/javadocs/javax/persistence/CascadeType.html). 

A dica é entender a relação e definir a melhor configuração para cada caso. Na dúvida faça testes simples que lhe permitam entender sua configuração e como as operações em cascata estão funcionando. Enquanto estamos ainda aprendendo, testar várias configurações e ver o que ocorre é a melhor dica.

Uma configuração que anda perto da CascadeType.REMOVE é a orphanRemove = true. Esta opção marca a entidade filha a ser removida se ela não tiver mais referências a partir da entidade mãe. Por exemplo, no relacionamento entre Comentarios de uma Postagem e a Postagem, inserir esta configuração indica que se a Postagem deixar de existir ou se fizermos um postagem.setComentarios(null) todos os comentários da postagem devem ser removidos, pois não existe mais nenhuma referência a estes e eles podem deixar de existir. No nosso exemplo original de produto e cesta de compras, será que queremos que os produtos sejam removidos quando fizermos cesta.setProdutos(null)? Acredito que não... São ciclos de vida independentes.

Em uma aplicação real, em especial quando o conteúdo do banco de dados é construído interativamente pelos usuários, como em uma rede social, por exemplo, é comum que as remoções sejam apenas lógicas. Isto significa que existe uma variável booleana, por exemplo, associada a Comentario, por exemplo, que vai ser Então quando removemos um comentário de uma postagem, esse comentário deixa de existir quando é solicitado 

## Modo de busca (FETCH)

Uma outra configuração possível diz respeito à forma como os dados são recuperados do banco de dados. Esta configuração vem já como opção da definição do relacionamento, ex. @ManyToOne(fetch = FetchType.LAZY). Essa é a configuração *FETCH*. Existem duas estratégias para esta configuração:

* A estratégia *EAGER* indica que em tempo de execução os dados devem ser recuperados em uma consulta de uma vez. Isto significa que se a estratégia EAGER for usada, o EntityManager vai recuperar os dados da entidade em questão e da entidade relacionada de uma só vez por uma consulta de forma automática pra nós. Ao usar a estratégia EAGER ao recuperar uma cesta de compras, os produtos associados já são também recuperados e quando fazemos cesta.getProdutos() nenhuma consulta precisa ser feita ao banco. Parece uma boa opção em se tratando de cesta de compras. Mas será que seria uma boa opção se estivéssemos tratando de universidade federal e seus alunos? 
* A estratégia *LAZY* indica que os dados serão obtidos de forma preguiçosa. Isto significa que eles devem ser recuperados apenas quando forem acessados pela primeira vez (ao chamar o método get específico, por exemplo, getProdutos() no caso de cesta de compras e seus produtos). Neste caso, os dados são recuperados quando necessário através de subconsultas após a chamada do método get. O EntityManager recupera os dados da entidade mãe primeiro e depois os dados da entidade filha sob demanda. Se a estratégia LAZY for usada, ao recuperar um produto apenas os atributos de produto específicos são recuperados. Apenas com uma chamada a cesta.getProdutos() que os produtos associados à cesta de compras seriam recuperados do banco de dados. 

Por default, quando a entidade relacionada é na verdade uma coleção, então o tipo de recuperação será LAZY. Por exemplo, se não configuramos nada em Cesta, a coleção de produtos relacionada não será recuperada ao acessar a cesta do banco de dados. Claro que para o programador isso fica transparente. O programador não vai ter mais trabalho se escolher LAZY por conta das consultas extra ao banco. Quem faz tudo isso é o JPA pra nós. Esta é uma questão de pensar o que é mais eficiente para a aplicação. Em uma aplicação em que a entidade relacionada raramente será acessada, ou que seja muito caro acessar e nem sempre é acessada, LAZY é melhor. EAGER já se torna mais atrativo quando temos certeza que ao acessar uma entidade teremos necessariamente que acessar a entidade relacionada. 

## Sobre os repositórios

É importante chamar atenção aqui para alguns métodos que adicionais podem ser úteis quando falamos de relacionamentos OneToMany e que não estão definidos por default por JpaRepository. Por exemplo, recuperar a partir da tabela de produtos todos os produtos de uma dada cesta de compras, como fizemos anteriormente (temos que conhecer o ID da cesta). Em se tratando da interface JPARepository, criamos novos métodos seguindo regras estritas para nomeação/assinatura do método novo. A consulta ao banco de dados será criada automaticamente ao seguir as regras para nomeação do método. 

Podemos brincar bastante gerando novos métodos no DAO (JpaRepository) só seguindo essas regras. Por exemplo, podemos recuperar uma coleção de objetos de forma ordenada, ou recuperar só os n primeiros, etc. Mais detalhes de como gerar os nomes dos métodos para derivar as consutas podem ser vistos [aqui](https://www.baeldung.com/spring-data-derived-queries), [aqui](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#repositories.query-methods.query-creation) e [aqui](https://www.baeldung.com/spring-data-sorting).

Para entender melhor tudo que foi discutido aqui, brinque com o código simples e veja como configurações diferentes vão funcionar. Para ajduar nessa brincadeira, temos exemplos de APIs muito simples que usam JPA e seus relacionamentos:
* [exemplo 4](https://github.com/raquelvl/psoft/tree/master/exemplo4) - configuração de relação unidirecional em Usuario (cada usuário pode ter muitas saudações, e uma tabela de associação entre usuário e saudação deve ser criada).
* [exemplo 4.v2](https://github.com/raquelvl/psoft/tree/master/exemplo4.v2) - configuração de relação unidirecional em Saudacao (cada saudação pertence a um único usuário, então na tabela de saudação deve ficar uma chave estrangeira de usuário. Saudação passa a ser a classe proprietária da relação)
* [exemplo 4.v3](https://github.com/raquelvl/psoft/tree/master/exemplo4.v3) - configuração de relação bidirecional entre Saudacao e Usuario.

Você também pode pegar o código da [demo de JPA](https://github.com/raquelvl/psoft/tree/master/demojpa) e adicionar a cesta de compras em um relacionamento com produto. Nesse caso deve pensar na API, como isso altera a API.

Este documento é uma breve introdução sobre como cofiguramos relacionamentos JPA mas está longe de ser completo ou exaustivo. É apenas um ponto de partida. À medida que você for praticando, isto é, programando, você provavelmente vai sentir necessidade de entender mais coisas. Entendimento/Estudo adicional deve vir sob demanda, à medida do que você for precisando, pois já entendeu a base. Abaixo estão várias documentações sobre este assundo e na Internet vai ter muito mais.

## Documentação de referência

[Repositórios JPA](https://docs.spring.io/spring-data/jpa/docs/1.5.0.RELEASE/reference/html/index.html)

[Tutorial java sobre persistência no backend](https://docs.oracle.com/javaee/5/tutorial/doc/bnbrs.html)

[javadoc da JPA](https://docs.oracle.com/javaee/7/api/javax/persistence/package-summary.html)

[JPA mini book](http://enos.itcollege.ee/~jpoial/java/naited/JPA_Mini_Book.pdf)

[Artigos avançados relacionados a JPA da baeldung](https://github.com/eugenp/tutorials/tree/master/persistence-modules/java-jpa)

[Dicas para criação de queries a partir dos nomes dos métodos](https://www.baeldung.com/spring-data-derived-queries)

[Curso da AlgaWorks só para aprender JPA](https://www.youtube.com/watch?v=MGWJbaYdy-Y&list=PLZTjHbp2Y7812axMiHkbXTYt9IDCSYgQz)

