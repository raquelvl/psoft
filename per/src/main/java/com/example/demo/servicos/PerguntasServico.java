package com.example.demo.servicos;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import com.example.demo.dtos.PerguntaDTO;
import com.example.demo.dtos.RespostaDTO;
import com.example.demo.entidades.Comentario;
import com.example.demo.entidades.Pergunta;
import com.example.demo.entidades.Resposta;
import com.example.demo.entidades.TipoDeComentario;
import com.example.demo.repositorios.RepositorioDeComentarios;
import com.example.demo.repositorios.RepositorioDePerguntas;
import com.example.demo.repositorios.RepositorioDeRespostas;

@Service
public class PerguntasServico {

	private RepositorioDePerguntas repositorioDePerguntas;
	private RepositorioDeRespostas repositorioDeRespostas;
	private RepositorioDeComentarios repositorioDeComentarios;

	public PerguntasServico() {
		super();
		this.repositorioDePerguntas = new RepositorioDePerguntas();
		this.repositorioDeRespostas = new RepositorioDeRespostas();
		this.repositorioDeComentarios = new RepositorioDeComentarios();
	}

	public Pergunta adicionaPergunta(PerguntaDTO pergunta) {
		return repositorioDePerguntas.adicionaPergunta(pergunta);
	}

	public List<Pergunta> getPerguntas(String palavrasChave) {
		return repositorioDePerguntas.getPerguntasPorPalavraChave(palavrasChave);
	}

	public Resposta adicionaResposta(int idPergunta, RespostaDTO resposta) {
		if (!repositorioDePerguntas.existePergunta(idPergunta)) {
			throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "Pergunta nao encontrada");
		}
		return repositorioDeRespostas.adicionaResposta(idPergunta, resposta.getResposta());

	}

	public List<Resposta> getRespostas(int idPergunta) {
		if (!repositorioDePerguntas.existePergunta(idPergunta)) {
			throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "Pergunta nao encontrada");
		}
		return repositorioDeRespostas.getRespostasDaPergunta(idPergunta);
	}

	public Resposta editaResposta(int idResposta, RespostaDTO resposta) {
		if (!repositorioDeRespostas.existeResposta(idResposta)) {
			throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "Resposta nao encontrada");
		}
		return repositorioDeRespostas.editaResposta(idResposta, resposta.getResposta());
	}

	public Pergunta getPergunta(Integer idPergunta) {
		if (!repositorioDePerguntas.existePergunta(idPergunta)) {
			throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "Pergunta nao encontrada");
		}
		return repositorioDePerguntas.getPergunta(idPergunta);
	}

	public Comentario adicionaComentarioPergunta(int idPergunta, String textoComentario) {
		return repositorioDeComentarios.adicionaComentario(idPergunta, textoComentario, 
														TipoDeComentario.COMENTARIO_DE_PERGUNTA);
	}
	
	public Comentario adicionaComentarioResposta(int idResposta, String textoComentario) {
		return repositorioDeComentarios.adicionaComentario(idResposta, textoComentario, 
														TipoDeComentario.COMENTARIO_DE_RESPOSTA);
	}

	public Pergunta deletaPergunta(int id) {
		repositorioDePerguntas.removePergunta(id);
		repositorioDeRespostas.removeRepostasDaPergunta(id);
		repositorioDeComentarios.removeComentariosDaPergunta(id);
		return null;
	}

}
