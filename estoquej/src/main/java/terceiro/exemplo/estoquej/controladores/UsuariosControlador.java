package terceiro.exemplo.estoquej.controladores;

import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import terceiro.exemplo.estoquej.entidades.Usuario;
import terceiro.exemplo.estoquej.servicos.ServicoDeUsuarios;

@RestController
public class UsuariosControlador {
	@Autowired
	private ServicoDeUsuarios usuariosService;

	@PostMapping("v1/api/usuarios")
	public ResponseEntity<Usuario> cadastraUsuario(@RequestBody Usuario usuario) {
		return new ResponseEntity<Usuario>(this.usuariosService.adicionaUsuario(usuario), HttpStatus.OK);
	}

	@GetMapping("/auth/usuarios/{email}")
	public ResponseEntity<Usuario> recuperaUsuario(@PathVariable String email,
			@RequestHeader("Authorization") String header) {
		return new ResponseEntity<Usuario>(usuariosService.getUsuario(email, header), HttpStatus.OK);
	}

	@DeleteMapping("/auth/usuarios/{email}")
	public ResponseEntity<Usuario> removeUsuario(@PathVariable String email,
			@RequestHeader("Authorization") String header) {
		return new ResponseEntity<Usuario>(usuariosService.removeUsuario(email, header), HttpStatus.OK);
	}
}
