package projsoft.ufcg.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import projsoft.ufcg.entities.Greeting;
import projsoft.ufcg.services.GreetingService;

@RestController
public class GreetingController {

	@Autowired
	private GreetingService greetingService;

	@RequestMapping("/timegreetings")
	public ResponseEntity<String> getSaudacao(@RequestParam(value = "nome", defaultValue = "Ser Humano") String nome) {
		return new ResponseEntity<String>(greetingService.getSaudacao(nome), HttpStatus.OK);
	}
	
	@RequestMapping("/timenow")
	public ResponseEntity<String> getHoraNoServidor() {
		return new ResponseEntity<String>(greetingService.getHoraNoServidor(), HttpStatus.OK);
	}
	
	@PostMapping("/newgreetings")
	public ResponseEntity<Greeting> setNovaSaudacao(@RequestBody Greeting novaSaudacao) {
		return new ResponseEntity<Greeting>(greetingService.setNovaSaudacao(novaSaudacao), HttpStatus.CREATED);
	}
	
	@RequestMapping("/newgreetings")
	public ResponseEntity<String> getNovaSaudacao() {
		return new ResponseEntity<String>(greetingService.getNovaSaudacao(), HttpStatus.OK);
	}	
	
}
