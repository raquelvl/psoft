package projsoft.ufcg.controladores;

import java.util.Date;
import java.util.Optional;

import javax.servlet.ServletException;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import projsoft.ufcg.entidades.Usuario;
import projsoft.ufcg.servicos.UsuariosService;

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
