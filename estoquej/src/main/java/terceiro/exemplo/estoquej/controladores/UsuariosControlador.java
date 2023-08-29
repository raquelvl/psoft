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

import terceiro.exemplo.estoquej.dtos.UsuarioDTO;
import terceiro.exemplo.estoquej.entidades.Usuario;
import terceiro.exemplo.estoquej.servicos.ServicoDeUsuarios;

@RestController
public class UsuariosControlador {
	@Autowired
	private ServicoDeUsuarios usuariosService;

	@PostMapping("v1/api/usuarios")
	public ResponseEntity<UsuarioDTO> cadastraUsuario(@RequestBody Usuario usuario) {
		return new ResponseEntity<>(this.usuariosService.adicionaUsuario(usuario), HttpStatus.OK);
	}

	@GetMapping("/auth/usuarios/{email}")
	public ResponseEntity<UsuarioDTO> recuperaUsuario(@PathVariable String email,
													  @RequestHeader("Authorization") String header) {
		return new ResponseEntity<>(usuariosService.recuperaUsuario(email, header), HttpStatus.OK);
	}

	@DeleteMapping("/auth/usuarios/{email}")
	public ResponseEntity<UsuarioDTO> removeUsuario(@PathVariable String email,
			@RequestHeader("Authorization") String header) {
		return new ResponseEntity<>(usuariosService.removeUsuario(email, header), HttpStatus.OK);
	}
}
