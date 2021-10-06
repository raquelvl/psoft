package terceiro.exemplo.estoquej.controladores;

import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import terceiro.exemplo.estoquej.dtos.LoginDeUsuarioDTO;
import terceiro.exemplo.estoquej.dtos.RespostaDeLogin;
import terceiro.exemplo.estoquej.servicos.ServicoJWT;

@RestController
public class LoginControlador {
	@Autowired
	private ServicoJWT servicoJwt;

	@PostMapping("auth/login")
	public ResponseEntity<RespostaDeLogin> autentica(@RequestBody LoginDeUsuarioDTO usuario) throws ServletException {
		return new ResponseEntity<RespostaDeLogin>(servicoJwt.autentica(usuario), HttpStatus.OK);
	}

}
