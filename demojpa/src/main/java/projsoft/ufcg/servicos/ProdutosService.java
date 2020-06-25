package projsoft.ufcg.servicos;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import projsoft.ufcg.daos.ProdutosRepository;
import projsoft.ufcg.entidades.Produto;

@Service
public class ProdutosService {

	@Autowired
	private ProdutosRepository<Produto, Long> produtosDAO;

	public Produto adicionaProduto(Produto produto) {
		return produtosDAO.save(produto);
	}
	
	public List<Produto> getProdutos() {
		return produtosDAO.findAll();
	}
	
	public Optional<Produto> getProduto(Long id) {
		return produtosDAO.findById(id);
	}
}
