package api.estoque.vum.servicos;

import java.util.Collection;
import java.util.Optional;

import org.springframework.stereotype.Service;

import api.estoque.vum.dtos.ItemDeAtualizacaoDeProduto;
import api.estoque.vum.dtos.ProdutoDTO;
import api.estoque.vum.entidades.Produto;
import api.estoque.vum.excecoes.ProdutoJaExisteException;
import api.estoque.vum.excecoes.ProdutoNaoEncontradoException;
import api.estoque.vum.repositorios.Estoque;

@Service
public class ServicoDeEstoque {

	private Estoque estoque = new Estoque();

	public ServicoDeEstoque() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Produto adicionaProdutos(ProdutoDTO produto) {

		produto.validaProduto(produto);
		if (!estoque.recuperaPorNome(produto.getNome()).isEmpty()) {
			throw new ProdutoJaExisteException("Produto ja existe no estoque",
					"Este produto ja existe no estoque e pode apenas ser atualizado");
		}

		return estoque.adicionaProduto(Produto.criaProduto(produto));
	}

	public Produto atualizaProduto(long id, ItemDeAtualizacaoDeProduto itemDeAtualizacao) {
		if (estoque.recuperaPorId(id).isEmpty()) // produto nao existe
			throw new ProdutoNaoEncontradoException("Produto nao existe no estoque",
					"Este produto nao existe no estoque e precisa ser adicionado antes de ser atualizado");

		return estoque.atualizaItemDoProduto(id, itemDeAtualizacao);
	}

	public Collection<Produto> recuperaProdutos(Optional<String> padraoDeBusca) {
		return estoque.getProdutos(padraoDeBusca);
	}

	public Produto recuperaProduto(long id) {
		return estoque.getProduto(id);
	}

}
