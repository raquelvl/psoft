package br.ufpb.minicursos.exemplo2.servicos;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufpb.minicursos.exemplo2.entidades.Saudacao;
import br.ufpb.minicursos.exemplo2.entidades.SaudacaoTemporalFactory;
import br.ufpb.minicursos.exemplo2.repositorios.SaudacoesRepository;

@Service
public class SaudacoesService {

	@Autowired
	private SaudacoesRepository<Saudacao, Integer> saudacoesDAO;

	public Saudacao getSaudacao(String nome) {
		Saudacao saudacao = new Saudacao();
		saudacao.setNome(nome);
		return saudacao;
	}

	public Saudacao getSaudacaoTemporal(String nome) {
		return SaudacaoTemporalFactory.getSaudacaoTemporal(nome);
	}

	// IMPORTANTE: falar de Optional
	// os IDs gerados começam de 1
	public Saudacao setNovaSaudacao(Saudacao novaSaudacao) {
		saudacoesDAO.save(novaSaudacao);
		return saudacoesDAO.findById((int) saudacoesDAO.count()).get();
	}

	// os IDs gerados começam de 1
	public Optional<Saudacao> getNovaSaudacao(int id) {
		return saudacoesDAO.findById(id);
	}

	// os IDs gerados começam de 1
	public Saudacao getNovaSaudacao(String nome) {
		if (saudacoesDAO.count() == 0)
			throw new NoSuchElementException();

		Saudacao saudacao = saudacoesDAO.findById((int) saudacoesDAO.count()).get();
		saudacao.setNome(nome);
		return saudacao;

	}

	// os IDs gerados começam de 1
	public Saudacao updateNovaSaudacao(String nome, int id) {
		if (saudacoesDAO.count() == 0 || id <= 0 || id > (int) saudacoesDAO.count()) {

		}

		if (saudacoesDAO.findById(id).isEmpty())
			throw new NoSuchElementException();

		Saudacao saudacao = saudacoesDAO.findById(id).get();
		saudacao.setNome(nome);
		saudacoesDAO.save(saudacao);
		return saudacoesDAO.findById(id).get();
	}

}
