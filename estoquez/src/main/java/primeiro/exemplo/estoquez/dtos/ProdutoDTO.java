package primeiro.exemplo.estoquez.dtos;

import java.util.Objects;

import primeiro.exemplo.estoquez.excecoes.ProdutoInvalidoException;

public class ProdutoDTO {
	private String nome;

	private int quantidade;

	private double valorUnitario;

	private String descricao;

	public ProdutoDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ProdutoDTO(String nome, int quantidade, double valorUnitario, String descricao) {
		super();
		this.nome = nome;
		this.quantidade = quantidade;
		this.valorUnitario = valorUnitario;
		this.descricao = descricao;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}

	public double getPrecoDaUnidade() {
		return valorUnitario;
	}

	public void setValorUnitario(double valorUnitario) {
		this.valorUnitario = valorUnitario;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	@Override
	public int hashCode() {
		return Objects.hash(nome);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProdutoDTO other = (ProdutoDTO) obj;
		return Objects.equals(nome, other.nome);
	}

	@Override
	public String toString() {
		return "ProdutoDTO [nome=" + nome + ", quantidade=" + quantidade + ", valorUnitario=" + valorUnitario
				+ ", descricao=" + descricao + "]";
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
