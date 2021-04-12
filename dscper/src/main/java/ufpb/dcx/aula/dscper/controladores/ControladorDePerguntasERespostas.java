package ufpb.dcx.aula.dscper.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import ufpb.dcx.aula.dscper.dtos.PerguntaDTO;
import ufpb.dcx.aula.dscper.dtos.RespostaDTO;
import ufpb.dcx.aula.dscper.entidades.Pergunta;
import ufpb.dcx.aula.dscper.entidades.Resposta;
import ufpb.dcx.aula.dscper.servicos.ServicoDePerguntasERespostas;


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
			return new ResponseEntity<Resposta>(HttpStatus.NOT_FOUND);
		}
	}
}
