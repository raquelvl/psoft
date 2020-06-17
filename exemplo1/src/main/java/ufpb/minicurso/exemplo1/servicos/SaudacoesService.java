package ufpb.minicurso.exemplo1.servicos;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import ufpb.minicurso.exemplo1.entidades.Saudacao;
import ufpb.minicurso.exemplo1.entidades.SaudacaoTemporalFactory;

@Service
public class SaudacoesService {

	private List<Saudacao> saudacoesAlternativas = new ArrayList<>();

	public Saudacao getSaudacao(String nome) {
		Saudacao saudacao = new Saudacao();
		saudacao.setNome(nome);
		return saudacao;
	}

	public Saudacao getSaudacaoTemporal(String nome) {
		return SaudacaoTemporalFactory.getSaudacaoTemporal(nome);
	}

	// agora alguém vai precisar manter estado dessa saudacao alternativa...
	// Por enquanto vai ficar no serviço
	public Saudacao setNovaSaudacao(Saudacao novaSaudacao) {
		saudacoesAlternativas.add(novaSaudacao);
		return saudacoesAlternativas.get(saudacoesAlternativas.size() - 1);
	}

	public Saudacao getNovaSaudacao(String nome) {
		return getNovaSaudacao(nome, saudacoesAlternativas.size() - 1);
	}

	public Saudacao getNovaSaudacao(String nome, int id) {
		if (saudacoesAlternativas.isEmpty() || id < 0 || id >= saudacoesAlternativas.size()) {
			throw new ArrayIndexOutOfBoundsException();
		}

		saudacoesAlternativas.get(id).setNome(nome);
		return saudacoesAlternativas.get(id);
	}

}
