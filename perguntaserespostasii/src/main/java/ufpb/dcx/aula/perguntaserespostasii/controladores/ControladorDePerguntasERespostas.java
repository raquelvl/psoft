package ufpb.dcx.aula.perguntaserespostasii.controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import ufpb.dcx.aula.perguntaserespostasii.dtos.PerguntaDTO;
import ufpb.dcx.aula.perguntaserespostasii.dtos.RespostaDTO;
import ufpb.dcx.aula.perguntaserespostasii.entidades.Pergunta;
import ufpb.dcx.aula.perguntaserespostasii.entidades.Resposta;
import ufpb.dcx.aula.perguntaserespostasii.servicos.ServicoDePerguntasERespostas;

@RestController
public class ControladorDePerguntasERespostas {
	@Autowired
	private ServicoDePerguntasERespostas servicoDePerguntasERespostas;

	public ControladorDePerguntasERespostas(ServicoDePerguntasERespostas servicoDePerguntasERespostas) {
		super();
		this.servicoDePerguntasERespostas = servicoDePerguntasERespostas;
	}

	@PostMapping("/perguntas")
	public ResponseEntity<Pergunta> adicionaPergunta(@RequestBody PerguntaDTO pergunta) {
		return new ResponseEntity<Pergunta>(servicoDePerguntasERespostas.adicionaPergunta(pergunta),
				HttpStatus.CREATED);
	}

	@PostMapping("/perguntas/{id}/respostas")
	public ResponseEntity<Resposta> adicionaResposta(@PathVariable int id, @RequestBody RespostaDTO resposta) {
		try {
			return new ResponseEntity<Resposta>(servicoDePerguntasERespostas.adicionaResposta(id, resposta),
					HttpStatus.CREATED);
		} catch (HttpClientErrorException hce) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/perguntas")
	public ResponseEntity<List<Pergunta>> getPerguntas(
			@RequestParam(name = "palavra-chave", required = false, defaultValue = "") String palavraChave) {
		return new ResponseEntity<List<Pergunta>>(servicoDePerguntasERespostas.getPerguntas(palavraChave),
				HttpStatus.OK);
	}
	
	@GetMapping("/perguntas/{id}/respostas")
	public ResponseEntity<List<Resposta>> getRespostas(@PathVariable long id) {
		try {
			return new ResponseEntity<List<Resposta>>(servicoDePerguntasERespostas.getRespostas(id),
					HttpStatus.OK);
		} catch (HttpClientErrorException hce) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

}
