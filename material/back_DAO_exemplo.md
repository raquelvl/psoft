# Como implementar DAO com spring boot e JPA?

Nesta aula vamos ver um exemplo bem simples de como escrever uma API que expõe uma base de dados relacional usando spring boot e JPA (Java Persistence API). Suponha que você está desenvolvendo uma aplicação de comércio eletrônico e portanto precisa armazenas produtos em uma tabela para persistência. Cada produto deve ter um id único, um nome, uma descrição e um preço (double). Por enquanto a API deve ter apenas 2 rotas: 

* POST /api/v1/produtos (para adicionar um prodito), 
* GET /api/v1/produtos (para recuperar todos os produtos já cadastrados) e 
* GET /api/v1/produtos/{i} (para recuperar um produto a partir do seu ID). 

O formato do JSON usado na comunicação (sempre no corpo da requisição ou da resposta HTTP) do POST /api/v1/produtos e do GET /api/v1/produtos/{i} tem os seguintes campos: 

```json
{
  "nome":"<nome>",
  "descricao":"<descricao>",
  "preco":"<preco>"
}
```
O campo *id* deve ser acrescido como o primeiro campo do JSON usado para representar produtos da chamada GET /api/v1/produtos:

```json
{
  "id":"<id>",
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

Seguindo o fluxo, vamos agora escrever a classe de acesso aos dados, o DAO. Na verdade, ao usar o Spring boot, esta classe não é bem uma classe, iremos escrever uma interface que estende outra interface chamada [JPARepository](https://docs.spring.io/spring-data/jpa/docs/current/api/org/springframework/data/jpa/repository/JpaRepository.html). O DAO pode ser visto como a classe que representa o repositório de dados. Os métodos da JPARepository são métodos comuns para realizar CRUD (Create, Read, Update e Delete). Ao escrever esta interface precisamos informar o tipo de objeto que representa registros deste repositório e o tipo da chave primária deste repositório. Segue abaixo o código desta interface. Perceba que todos os métodos foram herdados da interface "mãe" e, na verdade, nem precisamos definir novos métodos. Isso só precisará ser feito quando quisermos métodos mais específicos e falaremos disso mais adiante. Vejamos abaixo o código da interface ProdutosRepository:

```java
package projsoft.ufcg.daos;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface ProdutosRepository<T, ID extends Serializable> extends JpaRepository<Produto, Long> {

}
```

Não precisaremos criar uma implementação dessa interface. O Spring boot vai fazer isso pra gente de forma transparente.

Agora chegou o momento de escrever o serviço. É uma classe marcada com @Service e esta classe deve conhecer o DAO (isto é, o repositório) de Produtos. Esta classe deve ter 3 responsabilidades por enquanto: 

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

	private ProdutosRepository<Produto, Long> produtosDAO;
	
	//obrigatorio ter esse construtor, caso contrario chama um construtor
    //default e o DAO fica null
	public ProdutosService(ProdutosRepository<Produto, Long> produtosDAO) {
		super();
		this.produtosDAO = produtosDAO;
	}

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

server.servlet.context-path=/api/v1
#diz ao spring que coloque /api antes de qualquer url, ou seja, se voce quiser utilizar as rotas /products, precisará adicionar /api =>  /api/v1/products e assim por diante
````

Execute a aplicação (o códico completo dessa API está [aqui](https://github.com/raquelvl/psoft/tree/master/demojpa)). Perceba que a base de dados foi gerada. Use a API adicionando produtos. Encerre a aplicação (API), em seguida coloque para rodar novamente e veja que todos os produtos inseridos anteriormente estão lá.

Este exemplo, apesar de bastante simples, serve de base para você escrever suas APIs com dados que persistem em bancos de dados relacionais de agora em diante. 

## Exercício

Use Spring boot e JPA para adicionar na API que acabamos de escrever o seguinte:

* POST /api/v1/usuarios (adiciona um usuario com email, nome e senha - o email é o login do usuario e deve ser um identificador único do sistema);
* GET /api/v1/usuarios/{email} - recupera um usuário com determinado login (email)

Para pensar: 
* que novas classes você vai precisar desenvolver?
* será que você pode deixar essa funcionalidade junto com ProdutosController e ProdutosService? 
	* Resposta: não é uma boa prática, são bases de dados (tabelas) diferentes e cada uma deveria idealmente ter não apenas seu DAO específico, mas também seus serviços e controladores. Isso mantém o código menos acoplado e mais fácil de manter.

