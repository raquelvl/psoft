package ufpb.dcx.aula.perguntaserespostas.jwt.controladores;

import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ufpb.dcx.aula.perguntaserespostas.jwt.dtos.RespostaDeLogin;
import ufpb.dcx.aula.perguntaserespostas.jwt.entidades.Usuario;
import ufpb.dcx.aula.perguntaserespostas.jwt.servicos.ServicoJWT;

@RestController
@RequestMapping("/auth")
public class ControladorDeLogin {

	@Autowired
	private ServicoJWT servicoJwt;

//	public LoginController(ServicoJWT servicoJwt) {
//		super();
//		this.servicoJwt = servicoJwt;
//	}

	@PostMapping("/login")
	public ResponseEntity<RespostaDeLogin> autentica(@RequestBody Usuario usuario) throws ServletException {
		return new ResponseEntity<RespostaDeLogin>(servicoJwt.autentica(usuario), HttpStatus.OK);
	}

}
