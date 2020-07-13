# Exemplo de autorização usando JWT

Para usar o JWT teremos que ter uma aplicação preparada para cadastrar usuários e realizar a autenticação dos mesmos. Neste momento, por questão de simplicidade, não vamos nos preocupar em criptografar nada. 

Suponha que já temos a gerência de usuários em nossa API:

* POST /api/v1/usuarios (adiciona um usuario com email, nome e senha - o email é o login do usuario e deve ser um identificador único do sistema);
* GET /api/v1/usuarios/{email} - recupera um usuário com determinado login (email);
* POST /api/v1/login - realiza o login de um usuário cujas credenciais (email, senha) são passados no JSON no corpo da requisição HTTP.

O que precisamos fazer agora é:

1. gerar o JWT do usuário quando ele se autenticar e enviar o token na resposta HTTP do login do usuário (se o login for bem sucedido, claro!);
2. configurar as rotas privadas da aplicação (consideraremos a aplicação de produtos que desenvolvemos quando estávamos aprendendo sobre DAO+JPA). O cadastro e a recuperação de novos produtos só pode ser realizado por usuários autenticados (isto é, usuários com token válido);
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

Vamos iniciar pelo ponto **1**. A geração do token deve ser realizada no momento em que o usuário se autentica no sistema, isto é, no momento que o usuário se loga. Para se autenticar o usuário deve informar suas credenciais; em geral email ou outro identificador único do usuário e senha. 

Sugerimos um controlador específico para o login. Esse controlador vai responder apenas pela rota de login. Conforme já comentamos antes, o controlador não deve ser responsável por muita inteligência. Toda a lógica da aplicação deve ser deixada para os serviços e entidades. Assim, a única coisa que o controlador faz é solicitar ao serviço de JWT que realize a autenticação do usuário. Neste caso optamos por separar o serviço que lida com o recurso "usuarios" do serviço que lida com a autenticação/autorização, sendo este último o responsável por entender sobre os Java Web Tokens. A cadeia formada é a seguinte: o controlador de login conhece apenas o serviço JWT e este conhece o serviço de usuários. Isso é necessário para que o serviço JWT possa verificar se o usuário que tenta se logar realmente existe e se sua senha está correta.

O método de login (dentro do @RestController de login) apenas delega para o serviço JWT a autenticação do usuário e geração do token. Abaixo um exemplo de código do controlador responsável pelo login.

```java
@RestController
@RequestMapping("/auth")
public class LoginController {

	@Autowired
	private JWTService jwtService;

	@PostMapping("/login")
	public ResponseEntity<RespostaDeLogin> autentica(@RequestBody Usuario usuario) throws ServletException {
		return new ResponseEntity<RespostaDeLogin>(jwtService.autentica(usuario), HttpStatus.OK);
	}

}
```

O serviço JWT que é chamado pelo controlador de login, por sua vez, terá que chamar o serviço de usuários para validar o usuário e a senha. Validar usuário e senha significam verificar se o usuário existe na base de dados de usuários e se a senha passada bate com a senha do usuário nesta base de dados. Isso é feito invocando o serviço UsuariosService.

Em seguida, com usuário e senha validados, o serviço JWT gera o token.

Vejamos abaixo o código do serviço responsável pela autenticação dos usuários e geração do token.

```java
@Service
public class JWTService {
	@Autowired
	private UsuariosService usuariosService;
	private final String TOKEN_KEY = "login do batman";

	public RespostaDeLogin autentica(Usuario usuario) {

		if (!usuariosService.validaUsuarioSenha(usuario)) {
			return new RespostaDeLogin("Usuario ou senha invalidos. Nao foi realizado o login.");
		}

		String token = geraToken(usuario.getEmail());
		return new RespostaDeLogin(token);
	}

	private String geraToken(String email) {
		return Jwts.builder().setSubject(email)
				.signWith(SignatureAlgorithm.HS512, TOKEN_KEY)
				.setExpiration(new Date(System.currentTimeMillis() + 3 * 60 * 1000)).compact();// 3 min
	}
...
}
```
Para gerar o JWT nós invocamos o método builder() da classe JWTs. A classe JWTs é uma fábrica de tokens, o que facilita a criação dos tokens sem estar acoplado a uma implementação específica. No momento da criação do token estamos adicionando ao token a declaração registrada pública "sub" que é o identificador único do usuário (neste caso, o email). Perceba também que o token gerado é assinado e criptografado. No futuro, quando esse token for lido, será possível identificar que usuário está por trás do token. Neste código "new Date(System.currentTimeMillis() + 3 * 60 * 1000)" é o tempo de expiração do token, que nesse caso é de 3 minutos.

A classe RespostaDeLogin é uma classe simples com uma string (token) pública, criada apenas para deixar o código mais limpo. Um objeto dessa classe será "convertido" em um JSON que terá a chave "token" e o o valor do token gerado (ou uma mensagem de erro caso o login não tenha sido bem sucedido). Esse JSON vai retornar no corpo da resposta HTTP do pedido de login. 

Uma discussão interessante aqui diz respeito ao código de resposta HTTP para quando o login não é bem sucedido. Não existe um código claro que indica exatamente isso e não há um consenso na comunidade. É comum que um login mal sucedido por conta de email/senha erradas retornem comdigo 200 (OK) já que a operação foi realizada com sucesso. Nesse caso retornamos uma mensagem de erro que indique que o login foi executado, porém não realizado por conta do email ou senha estarem incorretos.

## Criação de filtro para verificação de tokens

Para garantir que algumas rotas só podem ser acessadas por usuáiros autorizados, um dos passos é criar um filtro e configurar estas rotas. Um filtro é um objeto usado para interceptar requisições e respostas HTTP da API. Usando o filtro, podemos executar operações em dois momentos difetentes nesse fluxo de comunicação entre cliente servidor: (i) antes de enviar a solicitação ao controlador e (ii) antes de enviar uma resposta ao cliente.

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

O método doFilter é chamado pelo container toda vez que um par de requisição/resposta HTTP é passado pela cadeia devido à chegada de uma requisição do cliente por um recurso. O FilterChain transmitido para esse método permite que o Filter transmita a solicitação e a resposta para a próxima entidade na cadeia (chamando chain.doFilter() no fim do método). 

Na nossa implementação do método doFilter() a primeira etapa é examinar a requisição e extrair o cabeçalho de interesse (no nosso caso o Authorization). Para fazer o parsing do token recebido no authorization header nós removemos o prefixo "Bearer " e por isso recuperamos a substring que começa no índice 7. Para fazer o parsing do token temos que usar a mesma chave usada para gerar o token (o que não deve ser problema já que todo esse código pertence à mesma organização). Fazemos a análise desejada com os dados extraídos do header e em seguida invocamos a próxima entidade na cadeia usando o objeto FilterChain (chain.doFilter()). A análise que realizamos é o *parsing* do token recuperado. Se o token não for válido setamos a resposta para estar associada ao código HTTP UNAUTHORIZED (401) e retornamos. Nesse caso a requisição nem precisa chegar no controlador porque o usuário não tem autorização para acessar a rota desejada. Uma resposta já é enviada para o cliente indicando o erro de token expirado ou inválido e o codigo HTTP 401.

Se não ocorrer erro no parsing do token (isto é, o usuário tem um token válido) um dos próximos componentes da cadeia a receber esta requisição será o controlador que serve a URI da requisição.

### Configuração do filtro

Já sabemos como criar um filtro para olhar os tokens das requisições, mas isso ainda não é suficiente. Precisamos configurar o filtro como um componente conhecido como @Bean e indicar que rotas devem invocar o filtro (só as rotas que de fato requerem token para acesso). Um bean é um objeto que é criado, gerenciado e destruído pelo container do spring, o framework é totalmente responsável por este objeto, criando e injetando suas propriedades (injeção de dependência).

A configuração dos beans deve ocorrer dentro de uma classe marcada com @Configuration. Já temos uma classe dessas, apesar de estar transparente pra gente. É a classe da aplicação marcada com @SpringBootApplication. Ao anotar uma classe com @SpringBootApplication estamos na verdade anotando a classe com 3 anotações distintas: @EnableAutoConfiguration (ativa o mecanismo de auto-configuração do Spring boot - é isso que tornou o Spring Boot tão popular), @ComponentScan (habilita o *scan* de componentes @Component no pacote e sub-pacotes onde a aplicação está localizada e @Configuration (permite o registro de beans - como por exemplo filtros como esse nosso, filtros para logging, etc. e classes adicionais de configuração. No código abaixo adicionamos na aplicação principal (main) a definição do bean.

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

Quando queremos um filtro que seja aplicável apenas a algumas URLs da API, então definimos um FilterRegistrationBean. Esse bean é um filtro e podemos configurar através do método addUrlPatterns as URLs às quais o filtro deve ser aplicado. Neste caso configuramos duas URLs ("/api/produtos", "/auth/usuarios"), quaisquer outras URLs da API não irão invocar o filtro. Dessa forma, quando estas URLs indicadas na configuração do filtro forem acessadas, o filtro será executado automaticamente, realizando a validação do token. Tokens válidos permitem a continuação do pedido, que deve em seguida chegar no controlador. Tokens inválidos já devem ser respondidos para o usuário com um código de resposta 401/Unauthorized.


### Recuperando o ID do usuário através do JWT

O filtro que criamos verifica se o token é válido. Se quisermos algo além disso, será realizado no controlador. Por exemplo, é possível que certas rotas só possam ser acessadas por um usuário específico. Por exemplo, só o próprio usuário deveria poder deletar sua própria conta; se um usuário inseriu um comentário usando uma API, só este usuário deveria poder editar e deletar este comentário.

Uma forma simples de realizar essa identificação do usuário é recuperar o token no controlador, e fazer o *parsing* do token para recuperar o usuário. Note que quando fomos criar o token nós usamos o seguinte comando:

```java
Jwts.builder().setSubject(email)
```

Então, ao fazer parsing do token podemos recuperar o subject:

```java
subject = Jwts.parser().setSigningKey(TOKEN_KEY).parseClaimsJws(token).getBody().getSubject();
```
No @RestController temo acesso à requisição HTTP e, consequentemente, seu cabeçalho. A anotação @RequestHeader("Authorization") nos dá acesso ao conteúdo do cabeçalho de autorização da requisição HTTP onde está o token. Vejamos abaixo um método do controlador que recupera o cabeçalho de autoriação da requisição HTTP e passa para o serviço de parsing JWT (chamado aqui de jwtService):

```java
@RestController
public class UsuariosController {

	@Autowired
	private UsuariosService usuariosService;

	...

	@DeleteMapping("/auth/usuarios/{email}")
	public ResponseEntity<Usuario> removeUsuario(@PathVariable String email,
			@RequestHeader("Authorization") String header) {
		try {
			return new ResponseEntity<Usuario>(usuariosService.removeUsuario(email, header), HttpStatus.OK);

		} catch (IllegalArgumentException iae) {
			return new ResponseEntity<Usuario>(HttpStatus.NOT_FOUND);
		} catch (ServletException e) {
			return new ResponseEntity<Usuario>(HttpStatus.FORBIDDEN);
		}
	}
}
```

Na assinatura do método no controlador nós recuperamos o cabeçalho de interesse (authorization) através da anotação @RequestHeader("Authorization"). A identificação do cabeçalho é passado pela String recebida pela anotação @RequestHeader. Esta é a forma de recuperar qualquer cabeçalho, bastando mudar o nome do cabeçalho de interesse. Este método no controlador recebe também o email do usuário a ser removido (isso poderia ser diferente, ao solicitar a deleção o usuário poderia nem passar email e sua própria conta seria deletada. A primeira opção permite mais tarde mudanças no código para que tenhamos um usuário com papel de _admin_ que pode remover qualquer conta usando a mesma rota). Como sempre, o controlador delega para o serviço (nesse caso o serviço de usuários) a responsabilidade de realizar a ação necessária. 

Vejamos abaixo o serviço de usuário. Veja o método removeUsuario neste serviço:

```java
@Service
public class UsuariosService {

	@Autowired
	private UsuariosRepository<Usuario, String> usuariosDAO;
	@Autowired
	private JWTService jwtService;

	public Usuario adicionaUsuario(Usuario usuario) {
		return this.usuariosDAO.save(usuario);
	}

	public Usuario getUsuario(String email) {
		Optional<Usuario> optUsuario = usuariosDAO.findByEmail(email);
		if(optUsuario.isEmpty())
			throw new IllegalArgumentException();//usuario nao existe
		return optUsuario.get();
	}


	public Usuario removeUsuario(String email, String authHeader) throws ServletException {
		Usuario usuario = getUsuario(email);
		if (usuarioTemPermissao(authHeader, email)) {
			usuariosDAO.delete(usuario);
			return usuario;
		}
		throw new ServletException("Usuario nao tem permissao");
	}
	
	private boolean usuarioTemPermissao(String authorizationHeader, String email) throws ServletException {
		String subject = jwtService.getSujeitoDoToken(authorizationHeader);
		Optional<Usuario> optUsuario = usuariosDAO.findByEmail(subject);
		return optUsuario.isPresent() && optUsuario.get().getEmail().equals(email);
	}

	public boolean validaUsuarioSenha(Usuario usuario) {
		Optional<Usuario> optUsuario = usuariosDAO.findByEmail(usuario.getEmail());
		if (optUsuario.isPresent() && optUsuario.get().getSenha().equals(usuario.getSenha()))
			return true;
		return false;
	}

}
```

Continuando: esse método realiza 2 passos. 

* O primeiro passo é verificar se o usuário com o e-mail informado na URI existe. Se o usuário não existe o serviço vai lançar uma exceção que vai subir para o controlador (nesse caso foi escolhida a exceção IllegalArgumentException). No controlador essa exceção deve gerar uma resposta HTTP que é retornada para o cliente da API com código 404 - not found. 
* O segundo passo só ocorre se o usuário existir. Neste caso o passo é recuperar o _subject_ (declaração sub) do token e verificar se o usuário do subject tem um token válido e tem permissão para deletar a conta do e-mail passado. Neste exemplo, se o usuário por trás do token for o mesmo usuário a ter sua conta removida, então o usuário tem autorização para tal. Nesse código o serviço de usuários acessa o serviço de JWT (que chamamos de JWTService). Veja código do método usuarioTemPermissao(...). Neste método um método do serviço JWT é invocado: getSujeitoDoToken. Veja abaixo o código do serviço de JWT. O JWTService é especialista em ler/analisar os JWTs e recuperar o subject do token, além de ter acesso à base de dados de usuários. Ao executar o método getSujeitoDoToken é possível que a exceção ServletException seja lançada, com mensagens que indicam os erros. Uma possibilidade é lançar esta exceção para o caso do token não existir ou não iniciar com "Bearer " (que é o tipo de mecanismo de segurança associado ao JWT). Esta exceção chegará primeiro no serviço de usuários, que não a irá tratar, e ela será então relançada para o controlador. No controlador, esta exceção será entendida como token inválido e então retornamos uma resposta HTTP com status FORBIDDEN (403), pois esta rota requer autenticação do usuário e autorização. Esse tipo de erro, na verdade, será capturado já pelo filtro, se ele estiver configurado para esta rota no addUrlPatterns do filtro, mas não custa sermos cautelosos... Dado que o token é válido e não expirado, será possível usar o parser do Jwts para recuperar o usuário autenticado. Voltando ao método removeUsuario do serviço de usuários. Após recuperar o usuário por trás do token, este método verifica se este usuário tem permissão para realizar esta operação. Caso este usuário não tenha a autorização para deletar a conta do usuário indicado no e-mail da URI (são usuários diferentes), então uma exceção ServletException("Usuario nao tem permissao") será lançada para o controlador. No controlador, esta exceção é traduzida para uma resposta HTTP com código 401 - UNAUTHORIZED.

```java
@Service
public class JWTService {
	@Autowired
	private UsuariosService usuariosService;
	private final String TOKEN_KEY = "login do batman";

	public RespostaDeLogin autentica(Usuario usuario) {

		if (!usuariosService.validaUsuarioSenha(usuario)) {
			return new RespostaDeLogin("Usuario ou senha invalidos. Nao foi realizado o login.");
		}

		String token = geraToken(usuario.getEmail());
		return new RespostaDeLogin(token);
	}

	private String geraToken(String email) {
		return Jwts.builder().setSubject(email)
				.signWith(SignatureAlgorithm.HS512, TOKEN_KEY)
				.setExpiration(new Date(System.currentTimeMillis() + 3 * 60 * 1000)).compact();// 3 min
	}

	public String getSujeitoDoToken(String authorizationHeader) throws ServletException {
		if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
			throw new ServletException("Token inexistente ou mal formatado!");
		}

		// Extraindo apenas o token do cabecalho.
		String token = authorizationHeader.substring(TokenFilter.TOKEN_INDEX);

		String subject = null;
		try {
			subject = Jwts.parser().setSigningKey(TOKEN_KEY).parseClaimsJws(token).getBody().getSubject();
		} catch (SignatureException e) {
			throw new ServletException("Token invalido ou expirado!");
		}
		return subject;
	}

}
```

Para os que querem praticar mais, uma boa ideia é pensar em um papel para o usuário. Pode ser algo bem simples como um boolean para indicar quem é admin ou algo mais elaborado, com vários papéis diferentes em uma Enum, por exemplo. Ao associar papéis (do inglês "roles") aos usuário dá pra fazer uma análise mais fina sobre o que cada usuário pode realizar. Por exemplo, poderíamos deixar usuários com papel de administrador remover outros usuários que não são administradores, ou deixar que apenas usuários com papel de "gerente" pudessem adicionar/remover produtos.
