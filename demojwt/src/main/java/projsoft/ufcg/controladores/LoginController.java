package projsoft.ufcg.controladores;

import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import projsoft.ufcg.entidades.Usuario;
import projsoft.ufcg.servicos.JWTService;
import projsoft.ufcg.servicos.RespostaDeLogin;

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
