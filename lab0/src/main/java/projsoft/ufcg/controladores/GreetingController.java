package projsoft.ufcg.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import projsoft.ufcg.entities.Greet;
import projsoft.ufcg.entities.Greeting;
import projsoft.ufcg.entities.ServerTimeInfo;
import projsoft.ufcg.services.GreetingService;

@RestController
public class GreetingController {

	@Autowired
	private GreetingService greetingService;

	@RequestMapping("/v1/greetings")
	public ResponseEntity<Greeting> getSaudacao(@RequestParam(value = "nome", defaultValue = "Ser Humano") String nome) {
		return new ResponseEntity<Greeting>(greetingService.getSaudacao(nome), HttpStatus.OK);
	}
	
	@RequestMapping("/v1/time")
	public ResponseEntity<ServerTimeInfo> getHoraNoServidor() {
		return new ResponseEntity<ServerTimeInfo>(greetingService.getInfoTempoNoServidor(), HttpStatus.OK);
	}
	
	@PostMapping("/v1/greetings/alternative")
	public ResponseEntity<Greet> setNovaSaudacao(@RequestBody Greet novaSaudacao) {
		System.out.println(novaSaudacao);
		return new ResponseEntity<Greet>(greetingService.setNovaSaudacao(novaSaudacao), HttpStatus.CREATED);
	}
	
	@RequestMapping("/v1/greetings/alternative")
	public ResponseEntity<Greeting> getNovaSaudacao(@RequestParam(value = "nome", defaultValue = "Ser Humano") String nome) {
		return new ResponseEntity<Greeting>(greetingService.getNovaSaudacao(nome), HttpStatus.OK);
	}	
	
}
