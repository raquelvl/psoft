package br.ufpb.minicurso.exemplo4.servicos;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufpb.minicurso.exemplo4.dtos.SaudacaoDTO;
import br.ufpb.minicurso.exemplo4.entidades.Saudacao;
import br.ufpb.minicurso.exemplo4.entidades.SaudacaoTemporalFactory;
import br.ufpb.minicurso.exemplo4.repositorios.SaudacoesRepository;

@Service
public class SaudacoesService {

	@Autowired
	private SaudacoesRepository<Saudacao, Integer> saudacoesDAO;
	@Autowired
	private UsuariosService usuarioService;
	@Autowired
	private JWTService jwtService;

	public SaudacaoDTO getSaudacao(String nome) {
		Saudacao saudacao = new Saudacao();
		saudacao.setNome(nome);
		return SaudacaoDTO.from(saudacao);
	}

	public SaudacaoDTO getSaudacaoTemporal(String nome) {
		return SaudacaoDTO.from(SaudacaoTemporalFactory.getSaudacaoTemporal(nome));
	}

	// os IDs gerados começam de 1
	public Saudacao setNovaSaudacao(Saudacao novaSaudacao, String autorizacao) {
		saudacoesDAO.save(novaSaudacao);
		String email = jwtService.getUsuarioId(autorizacao);
		usuarioService.adicionaSaudacao(email, novaSaudacao);
		return ultimaSaudacaoAlternativa();
	}

	private Saudacao ultimaSaudacaoAlternativa() {
		return saudacoesDAO.findById((int) saudacoesDAO.count()).get();
	}

	public List<Saudacao> getSaudacoesAlternativasDoUsuario(String autorizacao) {
		String email = jwtService.getUsuarioId(autorizacao);
		return usuarioService.getUsuario(email).getSaudacoes();
	}

	// os IDs gerados começam de 1
	public Optional<Saudacao> getNovaSaudacao(int id) {
		return saudacoesDAO.findById(id);
	}

	// os IDs gerados começam de 1
	public Saudacao getNovaSaudacao(String nome) {
		if (saudacoesDAO.count() == 0)
			throw new NoSuchElementException();

		Saudacao saudacao = ultimaSaudacaoAlternativa();
		saudacao.setNome(nome);
		return saudacao;

	}

	// os IDs gerados começam de 1
	public Saudacao updateNovaSaudacao(String nome, int id) {
		if (saudacoesDAO.count() == 0 || id <= 0 || id > (int) saudacoesDAO.count()
				|| saudacoesDAO.findById(id).isEmpty()) {
			throw new NoSuchElementException();
		}

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
