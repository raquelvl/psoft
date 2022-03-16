package api.estoque.vum.dtos;

import lombok.Data;

@Data
public class ItemDeAtualizacaoDeProduto {

	private String atributo;
	private String novoValor;

	public ItemDeAtualizacaoDeProduto() {
		super();
		// TODO Auto-generated constructor stub
	}

}
