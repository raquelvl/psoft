package api.estoque.vum.repositorios;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import api.estoque.vum.dtos.ItemDeAtualizacaoDeProduto;
import api.estoque.vum.entidades.Produto;
import api.estoque.vum.excecoes.CampoInvalidoException;
import api.estoque.vum.excecoes.NovoValorInvalidoException;
import api.estoque.vum.excecoes.ProdutoNaoEncontradoException;

public class Estoque {

	private Map<String, Produto> produtosPorNome = new HashMap<>();
	private Map<Long, Produto> produtosPorId = new HashMap<>();

	public Estoque() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Optional<Produto> recuperaPorNome(String nome) {
		return Optional.ofNullable(produtosPorNome.get(nome));
	}

	public Optional<Produto> recuperaPorId(long id) {
		return Optional.ofNullable(produtosPorId.get(id));
	}

	public Produto adicionaProduto(Produto produto) {
		produtosPorNome.put(produto.getNome(), produto);
		produtosPorId.put(produto.getId(), produto);
		return produto;
	}

	public Produto atualizaItemDoProduto(long id, ItemDeAtualizacaoDeProduto itemDeAtualizacao) {
		Produto produto = recuperaPorId(id).get();
		switch (itemDeAtualizacao.getAtributo()) {
		case "nome":
			if (produtoComEsteNomeJaExiste(itemDeAtualizacao.getNovoValor()))
				throw new NovoValorInvalidoException("Produto deve ter nome unico.",
						"Um produto com o novo nome a ser atualizado ja existe no estoque. Produtos devem ter nomes unicos.");

			produto.setNome(itemDeAtualizacao.getNovoValor());
			break;

		case "quantidade":
			int novaQuantidade;
			try {
				novaQuantidade = Integer.parseInt(itemDeAtualizacao.getNovoValor());
			} catch (NumberFormatException nfe) {
				throw new NovoValorInvalidoException("Quantidade invalida.",
						"A quantidade do produto deve ser um numero inteiro maior que zero.");
			}
			if (novaQuantidade <= 0)
				throw new NovoValorInvalidoException("Quantidade invalida.",
						"A quantidade do produto deve ser um numero inteiro maior que zero.");

			produto.setQuantidade(novaQuantidade);
			break;

		case "valorUnitario":
			double novoValor;
			try {
				novoValor = Double.parseDouble(itemDeAtualizacao.getNovoValor());
			} catch (NumberFormatException nfe) {
				throw new NovoValorInvalidoException("Preco invalido.",
						"O preco do produto deve ser um numero real maior que zero.");
			}
			if (novoValor <= 0)
				throw new NovoValorInvalidoException("Preco invalido.",
						"O preco do produto deve ser um numero real maior que zero.");

			produto.setValorUnitario(novoValor);
			break;

		case "descricao":
			if (itemDeAtualizacao.getNovoValor() == null || itemDeAtualizacao.getNovoValor().isEmpty()
					|| itemDeAtualizacao.getNovoValor().isBlank())
				throw new NovoValorInvalidoException("Produto deve ter descricao unica.",
						"Produtos devem ter descricoes vlidas, nao pode ser nula ou em branco.");

			produto.setDescricao(itemDeAtualizacao.getNovoValor());
			break;

		default:
			throw new CampoInvalidoException("Campo invalido.",
					"O campo do item de atualizacao nao existe. Os campos para atualizacao sao nome, quantidade, descricao e valorUnitario.");
		}

		return produto;
	}

	public boolean produtoComEsteNomeJaExiste(String nome) {
		return recuperaPorNome(nome).isPresent();
	}

	public String toString() {
		String saida = "";

		Iterator<Produto> it = produtosPorId.values().iterator();

		while (it.hasNext()) {
			saida = saida.concat(it.next().toString() + System.lineSeparator());
		}

		return saida;
	}

	public Collection<Produto> getProdutos(Optional<String> padraoDeBusca) {

		if (padraoDeBusca.isEmpty())
			return produtosPorId.values();

		Set<String> nomes = produtosPorNome.keySet();

		Collection<Produto> produtos = new ArrayList<>();
		for (String nome : nomes) {
			if (nome.contains(padraoDeBusca.get())) {
				produtos.add(produtosPorNome.get(nome));
			}
		}
		return produtos;
	}

	public Produto getProduto(long id) {
		if (!produtosPorId.containsKey(id))
			throw new ProdutoNaoEncontradoException("Produto nao existe.",
					"Um produto com este id nao foi encontrado no estoque.");
		return produtosPorId.get(id);
	}

}
