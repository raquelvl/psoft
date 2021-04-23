package ufpb.dcx.aula.perguntaserespostasii.servicos;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import ufpb.dcx.aula.perguntaserespostasii.daos.PalavraChaveDAO;
import ufpb.dcx.aula.perguntaserespostasii.daos.PerguntaDAO;
import ufpb.dcx.aula.perguntaserespostasii.daos.RespostaDAO;
import ufpb.dcx.aula.perguntaserespostasii.dtos.PerguntaDTO;
import ufpb.dcx.aula.perguntaserespostasii.dtos.RespostaDTO;
import ufpb.dcx.aula.perguntaserespostasii.entidades.PalavraChave;
import ufpb.dcx.aula.perguntaserespostasii.entidades.Pergunta;
import ufpb.dcx.aula.perguntaserespostasii.entidades.Resposta;

@Service
public class ServicoDePerguntasERespostas {
	@Autowired
	private PerguntaDAO repositorioDePerguntas;

	@Autowired
	private RespostaDAO repositorioDeRespostas;

	@Autowired
	private PalavraChaveDAO repositorioDePalavrasChave;

	public ServicoDePerguntasERespostas(PerguntaDAO repositorioDePerguntas, RespostaDAO repositorioDeRespostas,
			PalavraChaveDAO repositorioDePalavrasChave) {
		super();
		this.repositorioDePerguntas = repositorioDePerguntas;
		this.repositorioDeRespostas = repositorioDeRespostas;
		this.repositorioDePalavrasChave = repositorioDePalavrasChave;
	}

	public Pergunta adicionaPergunta(PerguntaDTO pergunta) {
		Pergunta aPergunta = new Pergunta(pergunta.getTexto());
		Pergunta perguntaSalva = repositorioDePerguntas.save(aPergunta);
		List<PalavraChave> asPalavrasChave = new ArrayList<>();

		List<String> palavrasChave = pergunta.extraiPalavrasChave();
		for (String palavra : palavrasChave) {

			if (!repositorioDePalavrasChave.existsById(palavra.trim())) {
				// umaPalavraChave.adicionaPergunta(perguntaSalva);
				repositorioDePalavrasChave.save(new PalavraChave(palavra.trim()));
			}

			// System.out.println("=========> palavraChave recuperada = " +
			// palavraChaveOpt.get());
			asPalavrasChave.add(new PalavraChave(palavra.trim()));
		}

		perguntaSalva.setPalavrasChave(asPalavrasChave);
		perguntaSalva = repositorioDePerguntas.save(aPergunta);
		return perguntaSalva;
	}

	public Resposta adicionaResposta(long id, RespostaDTO resposta) {
		if (repositorioDePerguntas.findById(id).isPresent()) {
			Resposta umaResposta = new Resposta(repositorioDePerguntas.findById(id).get(), resposta.getTexto());
			repositorioDeRespostas.save(umaResposta);
			return umaResposta;
		}

		throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "Pergunta nao existe.");
	}

	public List<Pergunta> getPerguntas(String palavraChave) {
		if (palavraChave.equals(""))
			return repositorioDePerguntas.findAll();

		Optional<PalavraChave> aPalavraChave = repositorioDePalavrasChave.findById(palavraChave);
		System.out.println("=========> palavraChave recuperada = " + aPalavraChave.get());
		if (aPalavraChave.isPresent()) {
			return aPalavraChave.get().getPerguntas();
		}
		throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "Palavra-chave nao encontrada: " + palavraChave);

	}

	public List<Resposta> getRespostas(long id) {
		if (repositorioDePerguntas.findById(id).isEmpty()) {
			throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
		}

		return repositorioDeRespostas.findByPergunta(repositorioDePerguntas.findById(id).get());
	}

}
