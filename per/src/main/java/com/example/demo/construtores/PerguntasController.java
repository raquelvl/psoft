package com.example.demo.construtores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import com.example.demo.dtos.ComentarioDTO;
import com.example.demo.dtos.PerguntaDTO;
import com.example.demo.dtos.RespostaDTO;
import com.example.demo.entidades.Comentario;
import com.example.demo.entidades.Pergunta;
import com.example.demo.entidades.Resposta;
import com.example.demo.servicos.PerguntasServico;

@RestController
public class PerguntasController {

	@Autowired
	private PerguntasServico perguntasServico;

	public PerguntasController(PerguntasServico perguntasServico) {
		super();
		this.perguntasServico = perguntasServico;
	}

	@PostMapping("/api/v1/perguntas")
	private ResponseEntity<Pergunta> adicionaPergunta(@RequestBody PerguntaDTO pergunta) {
		return new ResponseEntity<Pergunta>(perguntasServico.adicionaPergunta(pergunta), HttpStatus.CREATED);
	}

	@GetMapping("/api/v1/perguntas")
	private ResponseEntity<List<Pergunta>> getPerguntas(
			@RequestParam(value = "palavras-chave", defaultValue = "") String palavrasChave) {
		return new ResponseEntity<List<Pergunta>>(perguntasServico.getPerguntas(palavrasChave), HttpStatus.OK);
	}

	@GetMapping("/api/v1/perguntas/{id}")
	private ResponseEntity<Pergunta> getPerguntas(@PathVariable Integer idPergunta) {
		try {
			return new ResponseEntity<Pergunta>(perguntasServico.getPergunta(idPergunta), HttpStatus.OK);
		} catch (HttpClientErrorException cee) {
			return new ResponseEntity<Pergunta>(cee.getStatusCode());
		}
	}

	@PostMapping("/api/v1/perguntas/{id}/respostas")
	private ResponseEntity<Resposta> adicionaResposta(@PathVariable Integer id, @RequestBody RespostaDTO resposta) {
		try {
			return new ResponseEntity<Resposta>(perguntasServico.adicionaResposta(id, resposta), HttpStatus.CREATED);
		} catch (HttpClientErrorException cee) {
			return new ResponseEntity<Resposta>(cee.getStatusCode());
		}
	}

	@GetMapping("/api/v1/perguntas/{id}/respostas")
	private ResponseEntity<List<Resposta>> getRespostas(@PathVariable Integer id) {
		try {
			return new ResponseEntity<List<Resposta>>(perguntasServico.getRespostas(id), HttpStatus.OK);
		} catch (HttpClientErrorException cee) {
			return new ResponseEntity<List<Resposta>>(cee.getStatusCode());
		}
	}

	@PutMapping("/api/v1/perguntas/respostas/{id}")
	private ResponseEntity<Resposta> editaResposta(@PathVariable Integer id, @RequestBody RespostaDTO resposta) {
		try {
			return new ResponseEntity<Resposta>(perguntasServico.editaResposta(id, resposta), HttpStatus.OK);
		} catch (HttpClientErrorException cee) {
			return new ResponseEntity<Resposta>(cee.getStatusCode());
		}
	}

	@PostMapping("/api/v1/perguntas/{id}/comentarios")
	private ResponseEntity<Comentario> adicionaComentarioPergunta(@PathVariable Integer id,
			@RequestBody ComentarioDTO comentario) {
		try {
			return new ResponseEntity<Comentario>(
					perguntasServico.adicionaComentarioPergunta(id, comentario.getComentario()), HttpStatus.CREATED);
		} catch (HttpClientErrorException cee) {
			return new ResponseEntity<Comentario>(cee.getStatusCode());
		}
	}

	@PostMapping("/api/v1/perguntas/respostas/{id}/comentarios")
	private ResponseEntity<Comentario> adicionaComentarioResposta(@PathVariable Integer id,
			@RequestBody ComentarioDTO comentario) {
		try {
			return new ResponseEntity<Comentario>(
					perguntasServico.adicionaComentarioResposta(id, comentario.getComentario()), HttpStatus.CREATED);
		} catch (HttpClientErrorException cee) {
			return new ResponseEntity<Comentario>(cee.getStatusCode());
		}
	}

	@DeleteMapping("/api/v1/perguntas/{id}")
	private ResponseEntity<Pergunta> deletaPergunta(@PathVariable Integer id) {
		try {
			return new ResponseEntity<Pergunta>(perguntasServico.deletaPergunta(id), HttpStatus.CREATED);
		} catch (HttpClientErrorException cee) {
			return new ResponseEntity<Pergunta>(cee.getStatusCode());
		}
	}

}

/*
 * { "pergunta":"Dá pra fazer uma API sem BD só pra brincar?", "palavrasChave":
 * "API, BD" }
 * 
 * { "resposta" :
 * "sim, seu serviço não tem um DAO (repositório) ele pode ter a coleção em memória dentro dele mesmo (ou vc pode criar um gerente do recurso, mas usando colecoes em memória)."
 * }
 * 
 * { "resposta" :
 * "sim, mas testar é chato pois tem que adicionar os dados sempre que reinicia o serviço."
 * }
 * 
 * { "pergunta":"O que é o pom.xml?", "palavrasChave": "maven, dependencias" }
 * 
 * { "pergunta":"Como executar a API via terminal?", "palavrasChave":
 * "spring-boot, rodar" }
 * 
 * { "pergunta":"Tem como rodar a aplicação spring-boot pela IDE?",
 * "palavrasChave": "spring-boot, rodar, IDE" }
 * 
 * { "pergunta":"Qual a melhor IDE pra desenvolver com spring-boot?",
 * "palavrasChave": "spring-boot, IDE" }
 */