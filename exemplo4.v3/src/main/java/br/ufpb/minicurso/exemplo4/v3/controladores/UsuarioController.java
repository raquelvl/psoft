package br.ufpb.minicurso.exemplo4.v3.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import br.ufpb.minicurso.exemplo4.v3.dtos.UsuarioDTO;
import br.ufpb.minicurso.exemplo4.v3.entidades.Usuario;
import br.ufpb.minicurso.exemplo4.v3.excecoes.UsuarioInvalidoException;
import br.ufpb.minicurso.exemplo4.v3.excecoes.UsuarioJaExisteException;
import br.ufpb.minicurso.exemplo4.v3.services.UsuariosService;

@RestController
public class UsuarioController {

	@Autowired
	private UsuariosService usuarioService;

	@PostMapping("/usuarios")
	public ResponseEntity<UsuarioDTO> criaUsuario(@RequestBody Usuario usuario) {
		try {
			return new ResponseEntity<UsuarioDTO>(usuarioService.criaUsuario(usuario), HttpStatus.CREATED);
		} catch (UsuarioJaExisteException uee) {
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		} catch (UsuarioInvalidoException uie) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
	}

	@DeleteMapping("/auth/usuarios")
	public ResponseEntity<UsuarioDTO> deletaUsuario(@RequestHeader("Authorization") String token) {
		try {
			return new ResponseEntity<>(usuarioService.deletaUsuario(token), HttpStatus.OK);
		} catch (UsuarioJaExisteException uee) {
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		} catch (UsuarioInvalidoException uie) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}

	}

}










