package segundo.exemplo.estoqued.entidades;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import segundo.exemplo.estoqued.dtos.ProdutoDTO;

@Entity
public class Produto {
	@Id @GeneratedValue
	private Long id;

	private String nome;

	private int quantidade;

	private double valorUnitario;

	private String descricao;

	public Produto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Produto(String nome, int quantidade, double valorUnitario, String descricao) {
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

	public Long getId() {
		return id;
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
		return new Produto(produto.getNome(), produto.getQuantidade(), produto.getPrecoDaUnidade(),
				produto.getDescricao());
	}

	@Override
	public String toString() {
		return "Produto [id=" + id + ", nome=" + nome + ", quantidade=" + quantidade + ", valorUnitario="
				+ valorUnitario + ", descricao=" + descricao + "]";
	}
	
	

}
