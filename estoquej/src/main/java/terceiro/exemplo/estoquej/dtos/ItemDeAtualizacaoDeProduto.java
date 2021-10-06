package terceiro.exemplo.estoquej.dtos;

public class ItemDeAtualizacaoDeProduto {

	private String atributo;
	private String novoValor;

	public ItemDeAtualizacaoDeProduto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ItemDeAtualizacaoDeProduto(String atributo, String novoValor) {
		super();
		this.atributo = atributo;
		this.novoValor = novoValor;
	}

	public String getAtributo() {
		return atributo;
	}

	public void setAtributo(String atributo) {
		this.atributo = atributo;
	}

	public String getNovoValor() {
		return novoValor;
	}

	public void setNovoValor(String novoValor) {
		this.novoValor = novoValor;
	}

}
