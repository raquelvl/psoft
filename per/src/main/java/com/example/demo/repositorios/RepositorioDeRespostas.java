package com.example.demo.repositorios;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.entidades.Resposta;

public class RepositorioDeRespostas {

	private List<Resposta> respostas;
	static private int idResposta = 1;

	public RepositorioDeRespostas() {
		super();
		this.respostas = new ArrayList<Resposta>();
	}

	public Resposta adicionaResposta(int idPergunta, String resposta) {
		Resposta novaResposta = new Resposta(idPergunta, idResposta++, resposta);
		respostas.add(novaResposta);
		return novaResposta;
	}

	public List<Resposta> getRespostasDaPergunta(int idPergunta) {
		List<Resposta> asRespostas = new ArrayList<Resposta>();
		for (Resposta resposta : respostas) {
			if (resposta.getIdPergunta() == idPergunta)
				asRespostas.add(resposta);
		}
		return asRespostas;
	}

	public boolean existeResposta(int idResposta) {
		for (Resposta resposta : respostas) {
			if (resposta.getId() == idResposta)
				return true;
		}
		return false;
	}

	public Resposta editaResposta(int id, String respostaNova) {
		Resposta aResposta = getResposta(id);
		aResposta.setResposta(respostaNova);
		return aResposta;
	}
	
	public Resposta getResposta(int idResposta) {
		for (Resposta resposta : respostas) {
			if (resposta.getId() == idResposta) {
				return resposta;
			}
		}
		throw new IndexOutOfBoundsException();
	}

	public List<Resposta> removeRepostasDaPergunta(int id) {
		List<Resposta> respostasDaPergunta = getRespostasDaPergunta(id);
		for (Resposta resposta : respostasDaPergunta) {
			resposta.setApagada(true);
		}
		return respostasDaPergunta;
	}
}
