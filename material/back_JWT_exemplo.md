# Exemplo de autorização usando JWT

Para usar o JWT teremos que ter uma aplicação preparada para cadastrar usuários e realizar a autenticação dos mesmos. Neste momento, por questão de simplicidade, não vamos nos preocupar em criptografar nada. 

Suponha que já temos a gerência de usuários em nossa API:

* POST /api/v1/usuarios (adiciona um usuario com email, nome e senha - o email é o login do usuario e deve ser um identificador único do sistema);
* GET /api/v1/usuarios/{email} - recupera um usuário com determinado login (email);
* POST /api/v1/login - realiza o login de um usuário cujas credenciais (email, senha) são passados no JSON no corpo da requisição HTTP.

O que precisamos fazer agora é:

1. gerar o JWT do usuário quando ele se autenticar e enviar o token na resposta HTTP do login do usuário (se o login for bem sucedido, claro!);
2. configurar as rotas privadas da aplicação (consideraremos a aplicação de produtos que desenvolvemos quando estávamos aprendendo sobre DAO+JPA). O cadastro e a recuperação de novos produtos só pode ser realizado por usuários autenticados;
3. quando as requisições para rotas seguras chegarem, é preciso recuperar e avaliar o token recebido (se é um token válido e não expirado) para então passar a requisição para um controlador;
4. no controlador (e nos serviços associados), é possível que chegem requisições a rotas que requerem uma identificação mais fina do usuário. Por exemplo, só quem deveria poder deletar uma conta de usuário deveria ser o próprio usuário dono da conta. Em casos como este é preciso recuperar a identidade do usuário através do token passado para poder deletar a conta apropriada.

### Dependência

Antes de prosseguir é importante lembrar que teremos uma nova dependência em nosso projeto: a dependência da API de JWT. Para configurar esta dependência adicionamos no pom.xm (assumindo que usamos maven) as seguintes linhas dentro de <dependencies></dependencies>:

````
	<!-- https://mvnrepository.com/artifact/io.jsonwebtoken/jjwt -->
	<dependency>
		<groupId>io.jsonwebtoken</groupId>
		<artifactId>jjwt</artifactId>
		<version>0.9.1</version>
	</dependency>
````

### Gerando JWTs

Vamos iniciar pelo ponto 1. O token deve ser gerado no momento em que o usuário faz login na API. Abaixo um exemplo de código do controlador responsável pelo login.

```java
@RestController
@RequestMapping("/auth")
public class LoginController {

	private final String TOKEN_KEY = "login do batman";

	private UsuariosService usuariosService;

	public LoginController(UsuariosService usuariosService) {
		super();
		this.usuariosService = usuariosService;
	}

	@PostMapping("/login")
	public LoginResponse authenticate(@RequestBody Usuario usuario) throws ServletException {

		// Recupera o usuario
		Optional<Usuario> authUsuario = usuariosService.getUsuario(usuario.getEmail());

		// verificacoes
		if (authUsuario.isEmpty()) {
			throw new ServletException("Usuario nao encontrado!");
		}

		if (!authUsuario.get().getSenha().equals(usuario.getSenha())) {
			throw new ServletException("Senha invalida!");
		}

		String token = Jwts.builder().setSubject(authUsuario.get().getEmail()).signWith(SignatureAlgorithm.HS512, TOKEN_KEY)
				.setExpiration(new Date(System.currentTimeMillis() + 1 * 60 * 1000)).compact();

		return new LoginResponse(token);

	}

	private class LoginResponse {
		public String token;

		public LoginResponse(String token) {
			this.token = token;
		}
	}

}
```

Para gerar o JWT vamos invocar o método builder() da classe JWTs. A classe JWTs é uma fábrica de tokens, o que facilita a criação dos tokens sem estar acoplado a uma implementação específica. No momento da criação do token estamos adicionando ao token a declaração registrada "sub" que é o identificador único do usuário. Perceba também que o token gerado é assinado e criptografado.

Temos uma inner classe usada para representar uma resposta de login que contém basicamente o token. Um objeto dessa classe será "convertido" em um JSON que terá a cghave "token" e o o valor do token gerado. Esse JSON vai retornar no corpo da resposta HTTP do login.

## Criação de filtro para verificação de tokens

Um filtro é um objeto usado para interceptar requisições e respostas HTTP da API. Usando o filtro, podemos executar operações em dois momentos difetentes nesse fluxo de comunicação entre cliente servidor: (i) antes de enviar a solicitação ao controlador e (ii) antes de enviar uma resposta ao cliente.

O filtro que precisamos agora deve operar interceptando a requisição HTTP antes de ser entregue ao controlador. A requisição interceptada deve ser avaliada da seguinte forma: recuperar o conteúdo do cabeçalho de autorização (*Authorization header*) e analisar o token para garantir que é um token válido.

Em Spring, criamos um filtro implementando a interface [Filter](https://docs.oracle.com/javaee/7/api/javax/servlet/Filter.html?is-external=true). Para facilitar ainda mais, vamos na verdade estender uma classe de filtro genérica que já implementa um Filter pra gente: GenericFilterBean. No nosso filtro (ver código abaixo) estamos sobrescrevendo o método doFilter e reusando os demais métodos implementados da interface Filter (destroy e init).

```java
package projsoft.ufcg.filtros;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.filter.GenericFilterBean;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;

public class TokenFilter extends GenericFilterBean {

	public final static int TOKEN_INDEX = 7;
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;

		String header = req.getHeader("Authorization");

		if (header == null || !header.startsWith("Bearer ")) {
			throw new ServletException("Token inexistente ou mal formatado!");
		}

		// Extraindo apenas o token do cabecalho.
		String token = header.substring(TOKEN_INDEX);

		try {
			Jwts.parser().setSigningKey("login do batman").parseClaimsJws(token).getBody();
		} catch(SignatureException | ExpiredJwtException | MalformedJwtException | PrematureJwtException | UnsupportedJwtException | IllegalArgumentException e) {
			
			//aqui optamos por tratar todas as exceções que podem ser lançadas da mesma forma e simplesmente
			//repassar a mensagem de erro
			//se quiser enviar mensagens em portugues mais personalizadas teria que capturar exceção
			//por exceção
           		((HttpServletResponse) response).sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
            		
			return;//a requisição nem precisa passar adiante, retornar já para o cliente pois não pode prosseguir 
			//daqui pra frente por falta de autorização
        }

		chain.doFilter(request, response);
	}

}
```

O método doFilter é chamado pelo container toda vez que um par de requisição/resposta HTTP é passado pela cadeia devido à chegada de uma requisição do cliente por um recurso. O FilterChain transmitido para esse método permite que o Filter transmita a solicitação e a resposta para a próxima entidade na cadeia (chamando chain.doFilter()). 

Na nossa implementação do método doFilter() a primeira etapa é examinar a requisição e extrair o cabeçalho de interesse (no nosso caso o Authorization). Para fazer o parsing do token recebido no authorization header nós removemos o prefixo "Bearer " e por isso recuperamos a substring que começa no índice 7. Para fazer o parsing do token temos que usar a mesma chave usada para gerar o token (o que não deve ser problema já que todo esse código pertence à mesma organização). Fazemos a análise desejada com os dados extraídos do header e em seguida invocamos a próxima entidade na cadeia usando o objeto FilterChain (chain.doFilter()). A análise que realizamos é o *parsing* do token recuperado. Se o token não for válido setamos a resposta para estar associada ao código HTTP UNAUTHORIZED (401) e retornamos. Nesse caso a requisição nem precisa chegar no controlador porque o usuário não tem autorização para acessar a rota desejada. Uma resposta já é enviada para o cliente indicando o erro de token expirado ou inválido e o codigo HTTP 401.

Se não ocorrer erro no parsing do token (isto é, o usuário tem um token válido) um dos próximos componentes da cadeia a receber esta requisição será o controlador que serve a URI da requisição.

### Configuração do filtro

Já sabemos como criar um filtro para olhar os tokens das requisições, mas isso ainda não é suficiente. Precisamos configurar o filtro como um componente conhecido como @Bean e indicar que rotas devem invocar o filtro (só as rotas que de fato requerem token para acesso). Um bean é um objeto que é criado, gerenciado e destruído pelo container do spring, o framework é totalmente responsável por este objeto, criando, injetando suas propriedades (injeção de dependência).

A configuração dos beans deve ocorrer dentro de uma classe marcada com @Configuration. Já temos uma classe dessas, apesar de estar transparente pra gente. É a classe da aplicação marcada com @SpringBootApplication. Ao anotar uma classe com @SpringBootApplication estamos na verdade anotando a classe com 3 anotações distintas: @EnableAutoConfiguration (ativa o mecanismo de auto-configuração do Spring boot), @ComponentScan (habilita o *scan* de componentes @Component no pacote e sub-pacotes onde a aplicação está localizada e @Configuration (permite o registro de beans - como por exemplo filtros como esse nosso, filtros para logging, etc. e classes adicionais de configuração. No código abaixo adicionamos na aplicação principal (main) a definição do bean.

```java
@SpringBootApplication
public class DemojwtApplication {

	@Bean
	public FilterRegistrationBean<TokenFilter> filterJwt() {
		FilterRegistrationBean<TokenFilter> filterRB = new FilterRegistrationBean<TokenFilter>();
		filterRB.setFilter(new TokenFilter());
		filterRB.addUrlPatterns("/api/produtos", "/auth/usuarios");
		return filterRB;
	}

	public static void main(String[] args) {
		SpringApplication.run(DemojwtApplication.class, args);
	}

}
```

Quando queremos um filtro que seja aplicável apenas a algumas URLs da API, então definimos um FilterRegistrationBean. Esse bean é um filtro e podemos configurar através do método addUrlPatterns as URLs às quais o filtro deve ser aplicado. Neste caso configuramos duas URLs, quaisquer outras URLs da API não irá invocar o filtro.

### Realizando login dos usuários

Agora que temos todas as ferramentas para analisar JWTs, precisamos gerar os tokens. A geração do token deve ser realizada no momento em que o usuário se autentica no sistema, isto é, no momento que o usuário se loga. Para se autenticar o usuário deve informar suas credenciais; em geral email ou outro identificador único do usuário e senha. 

Sugerimos um controlador específico para o login. Esse controlador vai responder apenas pela rota de login. Para tal ele deverá conhecer dois serviços: 

1. o serviço que acessa a base de dados dos usuários. Isso é necessário para que o controlador possa verificar se o usuário que tenta se logar realmente existe e se sua senha está correta;
2. o serviço JWT que é responsável por gerar tokens e analisar tokens.

Um método de login (dentro do @RestController de login) que funciona é como o apresentado abaixo:

```java
@PostMapping("/login")
public LoginResponse authenticate(@RequestBody Usuario usuario) throws ServletException {

	// Recupera o usuario
	Optional<Usuario> authUsuario = usuariosService.getUsuario(usuario.getEmail());

	// verificacoes
	if (authUsuario.isEmpty()) {
		throw new ServletException("Usuario nao encontrado!");
	}

	verificaSenha(usuario, authUsuario);

	String token = jwtService.geraToken(authUsuario.get().getEmail());

	return new LoginResponse(token);

}
```

Este método recupera as credenciais do usuário no corpo da requisição HTTP, verifica se o usuário existe e se a senha passada bate com a senha do usuário na base de dados. Isso é feito usando os serviços do UsuariosService. Em seguida, se o usuário existe e a senha está correta, o método solicita ao JWTService a criação do token. Abaixo está o método do JWTService que gera o token:

```java
public String geraToken(String email) {
	return Jwts.builder().setSubject(email)
	.signWith(SignatureAlgorithm.HS512, TOKEN_KEY)
	.setExpiration(new Date(System.currentTimeMillis() + 1 * 60 * 1000)).compact();
}
```

Neste código "new Date(System.currentTimeMillis() + 1 * 60 * 1000)" é o tempo de expiração do token.

### Recuperando o ID do usuário através do JWT

O filtro que criamos verifica se o token é válido. Se quisermos algo além disso, será realizado no controlador. Por exemplo, é possível que certas rotas só possam ser acessadas por um usuário específico. Por exemplo, só o próprio usuário deveria poder deletar sua própria conta; se um usuário inseriu um comentário usando uma API, só este usuário deveria poder editar e deletar este comentário.

Uma forma simples de realizar essa identificação do usuário é recuperar o token no controlador, e fazer o *parsing* do token para recuperar o usuário. Note que quando fomos criar o token nós usamos o seguinte comando:

```java
Jwts.builder().setSubject(authUsuario.get().getEmail())
```

Então, ao fazer parsing do token podemos recuperar o subject:

```java
subject = Jwts.parser().setSigningKey("login do batman").parseClaimsJws(token).getBody().getSubject();
```
No @RestController podemos ter acesso à requisição HTTP e, consequentemente, seu cabeçalho. Vejamos abaixo um método do controlador que recupera o cabeçalho de autoriação da requisição HTTP e passa para o serviço de parsing JWT (chamado aqui de jwtService):

```java
@DeleteMapping("/auth/usuarios/{email}")
	public ResponseEntity<Usuario> removeUsuario(@PathVariable String email, @RequestHeader("Authorization") String header) {
		if(usuariosService.getUsuario(email).isEmpty())
			return new ResponseEntity<Usuario>(HttpStatus.NOT_FOUND);
		try {
			if(jwtService.usuarioTemPermissao(header, email)) {
				return new ResponseEntity<Usuario>(usuariosService.removeUsuario(email), HttpStatus.OK);
			}
		} catch (ServletException e) {
			//usuario esta com token invalido ou vencido
			return new ResponseEntity<Usuario>(HttpStatus.FORBIDDEN);
		}
		//usuario nao tem permissao
		return new ResponseEntity<Usuario>(HttpStatus.UNAUTHORIZED);
	}
```

Na assinatura do método recuperamos o cabeçalho de interesse através da anotação @RequestHeader("Authorization"). Esse cabeçalho é passado para o método através do parâmetro de entrada header do tipo String que é definido logo em seguida à anotação. Este método recebe também o email do usuário a ser removido (isso poderia ser diferente, ao solicitar a deleção o usuário poderia nem passar email e sua própria conta seria deletada. A primeira opção permite mais tarde que a gente tenha um admin que pode remover qualquer conta usando a mesma rota).

Continuando: esse método realiza 2 passos. 

* O primeiro passo é verificar se o usuário com o e-mail informado na URI existe. Se o usuário não existe uma resposta HTTP é retornada com código 404 - not found. 
* O segundo passo é recuperar o subject do token e verificar se o usuário do subject tem um token válido e tem permissão para deletar a conta do e-mail passado. Nesse código usamos um serviço que chamamos de JWTService cujo código está abaixo. Este serviço é especialista em ler/analisar os JWTs e recuperar o subject do token, além de ter acesso à base de dados de usuários. Se o token passado não for válido então retornamos uma resposta HTTP com status FORBIDDEN (403), pois esta rota requer autenticação do usuário e autorização. Se a rota em questão estiver configurada no addUrlPatterns do filtro então os tokens inválidos serão capturados lá. Caso contrário será capturado aqui e o acesso ao recurso será negado. Outra possibilidade é o token ser válido mas o usuário autenticado não ter a autorização para deletar a conta do usuário indicado no e-mail da URI. Neste caso retornamos uma resposta HTTP com código 401 - UNAUTHORIZED.

```java
package psoft.ufcg.services;

import java.util.Optional;

import javax.servlet.ServletException;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;
import psoft.ufcg.model.Usuario;

@Service
public class JWTService {
	private UsuariosService usuariosService;

	public JWTService(UsuariosService usuariosService) {
		super();
		this.usuariosService = usuariosService;
	}

	public boolean usuarioExiste(String authorizationHeader) throws ServletException {
		String subject = getSujeitoDoToken(authorizationHeader);

		return usuariosService.getUsuario(subject).isPresent();
	}
	
	public boolean usuarioTemPermissao(String authorizationHeader, String email) throws ServletException {
		String subject = getSujeitoDoToken(authorizationHeader);

		Optional<Usuario> optUsuario = usuariosService.getUsuario(subject);
		return optUsuario.isPresent() && optUsuario.get().getEmail().equals(email);
	}

	private String getSujeitoDoToken(String authorizationHeader) throws ServletException {
		if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
			throw new ServletException("Token inexistente ou mal formatado!");
		}

		// Extraindo apenas o token do cabecalho.
		String token = authorizationHeader.substring(projsoft.ufcg.filtros.TokenFilter.TOKEN_INDEX);

		String subject = null;
		try {
			subject = Jwts.parser().setSigningKey("login do batman").parseClaimsJws(token).getBody().getSubject();
		} catch (SignatureException e) {
			throw new ServletException("Token invalido ou expirado!");
		}
		return subject;
	}

}
```

Nosso próximo assunto relacionado à autorização será associar papéis (do inglês "roles") aos usuário e fazer uma análise mais fina sobre o que cada usuário pode realizar. Por exemplo, poderíamos deixar usuários com papel de administrador remover outros usuários que não são administradores, ou deixar que apenas usuários com papel de "configurador" pudessem adicionar/remover produtos.
