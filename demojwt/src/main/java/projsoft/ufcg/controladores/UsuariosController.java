package projsoft.ufcg.controladores;

import java.util.Optional;

import javax.servlet.ServletException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import projsoft.ufcg.entidades.Usuario;
import projsoft.ufcg.servicos.JWTService;
import projsoft.ufcg.servicos.UsuariosService;

@RestController
public class UsuariosController {

	private UsuariosService usuariosService;
	private JWTService jwtService;

	public UsuariosController(UsuariosService usuariosService, JWTService jwtService) {
		super();
		this.usuariosService = usuariosService;
		this.jwtService = jwtService;
	}

	@PostMapping("/api/usuarios")
	public ResponseEntity<Usuario> adicionaUsuario(@RequestBody Usuario usuario) {
		return new ResponseEntity<Usuario>(this.usuariosService.adicionaUsuario(usuario), HttpStatus.OK);
	}

	@GetMapping("/auth/usuarios/{email}")
	public ResponseEntity<Usuario> adicionaUsuario(@PathVariable String email) {
		Optional<Usuario> usuario = this.usuariosService.getUsuario(email);
		if (usuario.isPresent())
			return new ResponseEntity<Usuario>(usuario.get(), HttpStatus.OK);

		return new ResponseEntity<Usuario>(HttpStatus.NOT_FOUND);
	}

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
}
