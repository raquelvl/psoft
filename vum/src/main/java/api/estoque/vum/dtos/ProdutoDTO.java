package api.estoque.vum.dtos;

import api.estoque.vum.excecoes.ProdutoInvalidoException;
import lombok.Data;

@Data
public class ProdutoDTO {
	private String nome;

	private int quantidade;

	private double valorUnitario;

	private String descricao;

	public ProdutoDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public boolean validaProduto(ProdutoDTO produto) {
		if (nome == null || nome.isBlank() || nome.isEmpty())
			throw new ProdutoInvalidoException("Nome invalido.", "O nome do produto é um campo de texto obrigatorio.");

		if (quantidade <= 0)
			throw new ProdutoInvalidoException("Quantidade invalida.", "A quantidade do produto deve ser maior que zero.");

		if (valorUnitario <= 0)
			throw new ProdutoInvalidoException("Preco invalido.", "O preco da unidade do produto deve ser maior que zero.");

		if (descricao == null || descricao.isBlank() || descricao.isEmpty())
			throw new ProdutoInvalidoException("Descricao invalida.", "O produto deve ter uma descricao mais detalhada sobre ele.");

		return true;
	}

}
