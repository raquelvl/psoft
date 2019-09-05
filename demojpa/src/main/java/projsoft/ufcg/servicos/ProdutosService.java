package projsoft.ufcg.servicos;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import projsoft.ufcg.daos.ProdutosRepository;
import projsoft.ufcg.entidades.Produto;

@Service
public class ProdutosService {

	private ProdutosRepository<Produto, Long> produtosDAO;
	
	//obrigatorio ter esse construtor, caso contrario chama um construtor
    //default e o DAO fica null
	public ProdutosService(ProdutosRepository<Produto, Long> produtosDAO) {
		super();
		this.produtosDAO = produtosDAO;
	}

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
