package ufpb.dcx.aula.dscper.servicos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import ufpb.dcx.aula.dscper.daos.PerguntaDAO;
import ufpb.dcx.aula.dscper.daos.RespostaDAO;
import ufpb.dcx.aula.dscper.dtos.PerguntaDTO;
import ufpb.dcx.aula.dscper.dtos.RespostaDTO;
import ufpb.dcx.aula.dscper.entidades.Pergunta;
import ufpb.dcx.aula.dscper.entidades.Resposta;

@Service
public class ServicoDePerguntasERespostas {
	@Autowired
	private PerguntaDAO repositorioDePerguntas;
	@Autowired
	private RespostaDAO repositorioDeRespostas;

	public Pergunta adicionaPergunta(PerguntaDTO pergunta) {
		Pergunta umaPergunta = new Pergunta(pergunta.getTexto(), pergunta.getPalavrasChave());
		repositorioDePerguntas.save(umaPergunta);
		return umaPergunta;
	}
	
	public Resposta adicionaResposta(long id, RespostaDTO resposta) {
		if(repositorioDePerguntas.findById(id).isEmpty())
			throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
		Resposta umaResposta = new Resposta(id, resposta.getTexto());
		repositorioDeRespostas.save(umaResposta);
		return umaResposta;
	}

}
