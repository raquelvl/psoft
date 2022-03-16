package api.estoque.vum.entidades;

import java.util.Objects;

import api.estoque.vum.dtos.ProdutoDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Produto {
	private static long proximoId = 0;
	private Long id;

	private String nome;

	private int quantidade;

	private double valorUnitario;

	private String descricao;

	public Produto(String nome, int quantidade, double valorUnitario, String descricao) {
		super();
		this.id = proximoId++;
		this.nome = nome;
		this.quantidade = quantidade;
		this.valorUnitario = valorUnitario;
		this.descricao = descricao;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Produto other = (Produto) obj;
		return Objects.equals(id, other.id);
	}

	public static Produto criaProduto(ProdutoDTO produto) {
		return new Produto(produto.getNome(), produto.getQuantidade(), produto.getValorUnitario(),
				produto.getDescricao());
	}

	@Override
	public String toString() {
		return "Produto [id=" + id + ", nome=" + nome + ", quantidade=" + quantidade + ", valorUnitario="
				+ valorUnitario + ", descricao=" + descricao + "]";
	}

}
