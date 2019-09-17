package projsoft.ufcg.controladores;

import java.util.Optional;

import javax.servlet.ServletException;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import projsoft.ufcg.entidades.Usuario;
import projsoft.ufcg.servicos.JWTService;
import projsoft.ufcg.servicos.UsuariosService;

@RestController
@RequestMapping("/auth")
public class LoginController {

	private UsuariosService usuariosService;
	private JWTService jwtService;

	public LoginController(UsuariosService usuariosService, JWTService jwtService) {
		super();
		this.usuariosService = usuariosService;
		this.jwtService = jwtService;
	}

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

	private void verificaSenha(Usuario usuario, Optional<Usuario> authUsuario) throws ServletException {
		if (!authUsuario.get().getSenha().equals(usuario.getSenha())) {
			throw new ServletException("Senha invalida!");
		}
	}

	private class LoginResponse {
		public String token;

		public LoginResponse(String token) {
			this.token = token;
		}
	}

}
