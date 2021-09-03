package primeiro.exemplo.estoquez.excecoes;

public class ProdutoInvalidoException extends RuntimeException {
	private String titulo;
	private String detalhes;

	public ProdutoInvalidoException(String titulo, String detalhes) {
		super();
		this.detalhes = detalhes;
		this.titulo = titulo;
	}

	public String getTitulo() {
		return titulo;
	}

	public String getDetalhes() {
		return detalhes;
	}

}
