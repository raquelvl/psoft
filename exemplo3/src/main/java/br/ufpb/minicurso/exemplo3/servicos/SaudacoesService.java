package br.ufpb.minicurso.exemplo3.servicos;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufpb.minicurso.exemplo3.dtos.SaudacaoDTO;
import br.ufpb.minicurso.exemplo3.entidades.Saudacao;
import br.ufpb.minicurso.exemplo3.entidades.SaudacaoTemporalFactory;
import br.ufpb.minicurso.exemplo3.repositorios.SaudacoesRepository;

@Service
public class SaudacoesService {

	@Autowired
	private SaudacoesRepository<Saudacao, Integer> saudacoesDAO;

	public SaudacaoDTO getSaudacao(String nome) {
		Saudacao saudacao = new Saudacao();
		saudacao.setNome(nome);
		return SaudacaoDTO.from(saudacao);
	}

	public SaudacaoDTO getSaudacaoTemporal(String nome) {
		return SaudacaoDTO.from(SaudacaoTemporalFactory.getSaudacaoTemporal(nome));
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

	public List<Saudacao> getSaudacoesAlternativas() {
		return saudacoesDAO.findAll();
	}

	public List<Saudacao> getSaudacoesAlternativas(String expressao) {
		return saudacoesDAO.findBySaudacaoContaining(expressao);
	}
	
	public List<Saudacao> getSaudacoesAlternativasComQuery(String expressao) {
		return saudacoesDAO.searchBySaudacaoContaining(expressao);
	}
	

}
