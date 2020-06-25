# Como implementar DAO com spring boot e JPA?

Nesta aula vamos ver um exemplo bem simples de como escrever uma API que expõe uma base de dados relacional usando spring boot e JPA (Java Persistence API). Suponha que você está desenvolvendo uma aplicação de comércio eletrônico e portanto precisa armazenar produtos em uma tabela para persistência. Cada produto deve ter um id único, um nome, uma descrição e um preço (double). Por enquanto a API deve ter apenas 3 rotas: 

* POST /v1/api/produtos (para adicionar um produto), 
* GET /v1/api/produtos (para recuperar todos os produtos já cadastrados) e 
* GET /v1/api/produtos/{id} (para recuperar um produto a partir do seu ID). 

O formato do JSON usado na comunicação (sempre no corpo da requisição ou da resposta HTTP) do POST /v1/api/produtos e do GET /v1/api/produtos/{id} tem os seguintes campos: 

```json
{
  "nome":"<nome>",
  "descricao":"<descricao>",
  "preco":<preco>
}
```

O campo *id* deve ser acrescido como o primeiro campo do JSON retornado para representar produtos da chamada GET /v1/api/produtos:

```json
{
  "id":<id>,
  "nome":"<nome>",
  "descricao":"<descricao>",
  "preco":"<preco>"
}
```

Seguindo uma abordagem bottom up de desenvolvimento, começaremos escrevendo as classes mais simples e independentes. Neste caso, a nossa entidade que será revertida para registros na tabela de produtos: Produto. Esta classe não deve ter relações de herança explícitas e deve ter um construtor default, um construtor que controi o objeto a partir dos seus campos e também precisa de gets e sets. E o mais importante: esta classe deve ser marcada com a anotação @Entity. Criamos um projeto base no [Spring Initializr](https://start.spring.io/) marcando as dependências Spring Web, Spring Data JPA e H2 Database (bd que usaremos). Abaixo segue o código da classe produto.

```java
package projsoft.ufcg.entidades;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Produto {

	@Id
	@GeneratedValue
	private long id;
	private String nome;
	private String descricao;
	private double preco;

	public Produto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Produto(long id, String nome, String descricao, double preco) {
		super();
		this.id = id;
		this.nome = nome;
		this.descricao = descricao;
		this.preco = preco;
	}

	// getters
  
  //setters

}
```

Note que Entity, Id e GeneratedValue são todos pertencentes ao pacote javax.persistence. Esse é o pacote da JPA. Outras anotações com mesmo nome existem em outros pacotes (ex. do Hibernate) então cuidado ao importar esses elementos de javax.persistence.

Seguindo o fluxo, vamos agora escrever a classe de acesso aos dados, o DAO. Na verdade, ao usar o Spring Data JPA, esta classe não é bem uma classe, iremos escrever uma interface que estende outra interface chamada [JPARepository](https://docs.spring.io/spring-data/jpa/docs/current/api/org/springframework/data/jpa/repository/JpaRepository.html). O DAO pode ser visto como a classe que representa o repositório de dados e que se comunica com ele para acessar os dados. Os métodos da JPARepository são métodos comuns para realizar CRUD (Create, Read, Update e Delete). Ao escrever esta interface precisamos informar o tipo de objeto que representa os registros deste repositório e o tipo da chave primária deste repositório. Segue abaixo o código desta interface. Perceba que todos os métodos foram herdados da interface "mãe" e, na verdade, nem precisamos definir novos métodos. Isso só precisará ser feito quando quisermos métodos mais específicos e falaremos disso mais adiante. Vejamos abaixo o código da interface ProdutosRepository:

```java
package projsoft.ufcg.daos;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface ProdutosRepository<T, ID extends Serializable> extends JpaRepository<Produto, Long> {

}
```

Não precisaremos criar uma implementação dessa interface. O Spring Data JPA vai fazer isso pra gente de forma transparente. 

Agora chegou o momento de escrever o serviço. É uma classe marcada com @Service e esta classe deve conhecer o DAO (isto é, o repositório) de Produtos. Apenas os serviços conhecem os DAOs e as entidades. Aos controladores fica restrito acesso apenas aos serviços. Por que isso é interessante? Em um ambiente real a API REST pública não deve ser modificada, pois isso quebraria diversos outros programas que chamam essa API, concordam? Claro que novos recursos podem ser adicionados à API e em casos de extrema necessidade ela vai ser modificada, mas é algo mais raro de ocorrer. Já as classes do modelo podem mudar muito com o passar do tempo, com a adição de novas funcionalidades e o refactoring natural que ocorre ao longo do tempo em software _vivo_. Ao separar completamente os controladores do modelo, será raro precisar mexer nos controladores, isso diminui o acoplamento e deixa o código muito mais fácil de manter, com menos propensão à inserção de erros durante a manutenção. Os refactorings também ficam restritos apenas às classes de serviço e entidades.

Voltemos ao desenvolvimento do nosso programa. A classe de serviço deve ter 3 responsabilidades por enquanto (equivalentes aos 3 recursos expostos pela API): 

* inserir um produto na base de dados de produto;
* recuperar uma lista de todos os produtos já cadastrados na base de dados;
* recuperar um produto da base de dados a partir de seu ID.

Vejamos abaixo o código desta classe:

```java
package projsoft.ufcg.servicos;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import projsoft.ufcg.daos.ProdutosRepository;
import projsoft.ufcg.entidades.Produto;

@Service
public class ProdutosService {

	@Autowired
	private ProdutosRepository<Produto, Long> produtosDAO;
	
	public Produto adicionaProduto(Produto produto) {
		return produtosDAO.save(produto);
	}
	
	public List<Produto> getProdutos() {
		return produtosDAO.findAll();
	}
	
	public Optional<Produto> getProduto(Long id) {
		return produtosDAO.findById(id);
	}
}
```

Agora vamos escrever o controlador de produtos, a classe marcada com @RestController que vai receber as requisições HTTP dos clientes da API e rotea-las para o serviço que saberá realizar as requisições. Abaixo segue o código do controlador de produtos.

```java
package projsoft.ufcg.controladores;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import projsoft.ufcg.entidades.Produto;
import projsoft.ufcg.servicos.ProdutosService;

@RestController
public class ProdutosController {

	private ProdutosService produtosService;

	//obrigatorio ter esse construtor, caso contrario chama um construtor
	//default e o serviço fica null
	public ProdutosController(ProdutosService produtosService) {
		super();
		this.produtosService = produtosService;
	}

	@PostMapping("/produtos")
	public ResponseEntity<Produto> adicionaProduto(@RequestBody Produto produto) {
		return new ResponseEntity<Produto>(produtosService.adicionaProduto(produto), HttpStatus.CREATED);
	}

	@GetMapping("/produtos/{id}")
	public ResponseEntity<Produto> getProduto(@PathVariable Long id) {
		Optional<Produto> produto = produtosService.getProduto(id);
		if (produto.isPresent())
			return new ResponseEntity<Produto>(produto.get(), HttpStatus.OK);
		return new ResponseEntity<Produto>(HttpStatus.NOT_FOUND);
	}

	@GetMapping("/produtos")
	public ResponseEntity<List<Produto>> getProdutos() {
		return new ResponseEntity<List<Produto>>(produtosService.getProdutos(), HttpStatus.OK);
	}

}
```

Finalmente, vamos olhar para o arquivo de configuração application.properties (que fica em resources). Este arquivo deve trazer informação para configurar/usar a base de dados.

````
# H2
spring.h2.console.enabled=true
spring.h2.console.path=/h2
#indicará o path para você acessar a interface do h2, em geral, vá ao browser e coloque localhost:8080/h2 com 8080 ou sua porta

#deixa que hibernate gerencia a criação das bases de dados - permite atualizações nas bases, mas nunca apaga tabelas ou colunas que não estejam em uso pela aplicação - existem outras configurações - essa é só simples e segura na fase de desenvolvimento!
spring.jpa.hibernate.ddl-auto=update

# Datasource
spring.datasource.url=jdbc:h2:file:./dados
spring.datasource.username=sa
spring.datasource.password=
spring.datasource.driver-class-name=org.h2.Driver

server.servlet.context-path=/v1
#diz ao spring que coloque /v1 antes de qualquer rota, assim se no controlador tem /api/produtos e esta configuração está habilitada, na verdade sua rota é /v1/api/produtos
````

Execute a aplicação (o código completo dessa API está [aqui](https://github.com/raquelvl/psoft/tree/master/demojpa)). Perceba que a base de dados foi gerada. Use a API adicionando produtos. Encerre a aplicação (API), em seguida coloque para rodar novamente e veja que todos os produtos inseridos anteriormente estão lá.

Este exemplo, apesar de bastante simples, serve de base para você escrever suas APIs com dados que persistem em bancos de dados relacionais de agora em diante. 

Uma possibilidade é se conectar ao BD para ver como estão as tabelas. Para isso você vai usar as credenciais indicadas na configuração do banco no arquivo application.properties para se conectar ao banco. Na configuração passada neste artigo o link para se conectar ao banco vai ser [http://localhost:8080/v1/api/h2](http://localhost:8080/v1/api/h2). Ao entrar na console do banco você vai poder ver todas as tabelas criadas, e se rodar o select * from <tabela> você consegue ver o conteúdo da tabela indicada.

É possível que a interface CrudRepository não tenha exatamente o método de acesso aos dados desejado. Neste caso, temos duas opções a seguir para adicionar o método e continuar sem implementar a interface explicitamente:

1. Inserir o método desejado na interface seguindo determinadas convenções para o nome do método. Isso informa para o JPA a query a ser realizada no banco pelo método novo sendo definido. Regras para nomear estes métodos extra podem ser encontradas na seção 4.5 e 5.3.2 [deste documento](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#repositories.query-methods.details).

2. Inserir o método sem seguir a convenção do nome do método e usar a anotação [@Query](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods.at-query) para indicar a consulta específica a ser realizada. Esta consulta é realizada usando a linguagem JPQL (Java Persistence Query Language) ou SQL pura ([veja exemplos e mais detalhes aqui].(https://www.baeldung.com/spring-data-jpa-query)).

Um outro exemplo de uso de Spring Data JPA continuando nossa API de saudações está aqui.

## Exercício

Use Spring boot e JPA para adicionar na API que acabamos de escrever o seguinte:

* POST /v1/api/usuarios (adiciona um usuario com email, nome e senha - o email é o login do usuario e deve ser um identificador único do sistema);
* GET /v1/api/usuarios/{email} - recupera um usuário com determinado login (email)

Para pensar: 
* que novas classes você vai precisar desenvolver?
* será que você pode deixar essa funcionalidade junto com ProdutosController e ProdutosService? 
	* Resposta: não é uma boa prática, são bases de dados (tabelas) diferentes e cada uma deveria idealmente ter não apenas seu DAO específico, mas também seus serviços e controladores. Isso mantém o código menos acoplado e mais fácil de manter.

