package com.example.demo.repositorios;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.dtos.PerguntaDTO;
import com.example.demo.entidades.Pergunta;

public class RepositorioDePerguntas {
	private List<Pergunta> perguntas;
	static private int idPergunta = 1;

	public RepositorioDePerguntas() {
		super();
		perguntas = new ArrayList<Pergunta>();
	}

	public Pergunta adicionaPergunta(PerguntaDTO pergunta) {
		Pergunta novaPergunta = new Pergunta(idPergunta++, pergunta.getPergunta(), pergunta.getPalavrasChave());
		perguntas.add(novaPergunta);
		return novaPergunta;
	}

	public boolean existePergunta(int idPergunta) {
		for (Pergunta pergunta : perguntas) {
			if (pergunta.getId() == idPergunta)
				return true;
		}
		return false;
	}

	public Pergunta getPergunta(int idPergunta) {
		for (Pergunta pergunta : perguntas) {
			if (pergunta.getId() == idPergunta)
				return pergunta;
		}
		return null;
	}

	public List<Pergunta> getPerguntas() {
		return perguntas;
	}
	
	public List<Pergunta> getPerguntasPorPalavraChave(String palavrasChave) {
		if (palavrasChave.isEmpty())
			return perguntas;
		List<Pergunta> perguntasPorPalavraChave = new ArrayList<Pergunta>();
		for (Pergunta pergunta : perguntas) {
			String[] palavras = palavrasChave.split(",");
			for (int i = 0; i < palavras.length; i++) {
				if (pergunta.getPalavrasChave().contains(palavras[i].trim())) {
					perguntasPorPalavraChave.add(pergunta);
					break; // ja adicionou a pergunta
				}
			}
		}
		return perguntasPorPalavraChave;
	}

	public Pergunta removePergunta(int id) {
		Pergunta pergunta = getPergunta(id);
		pergunta.setApagada(true);
		return pergunta;
	}

}
