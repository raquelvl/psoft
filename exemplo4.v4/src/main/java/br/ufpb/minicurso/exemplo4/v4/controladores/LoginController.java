package br.ufpb.minicurso.exemplo4.v4.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.ufpb.minicurso.exemplo4.v4.dtos.UsuarioLoginDTO;
import br.ufpb.minicurso.exemplo4.v4.servicos.JWTService;
import br.ufpb.minicurso.exemplo4.v4.servicos.LoginResponse;

@RestController
public class LoginController {
	
	@Autowired
	private JWTService jwtService;

	@PostMapping("/auth/login")
	public ResponseEntity<LoginResponse> autentica(@RequestBody UsuarioLoginDTO usuario) {
		return new ResponseEntity<LoginResponse>(jwtService.autentica(usuario), HttpStatus.OK);
	}

}
