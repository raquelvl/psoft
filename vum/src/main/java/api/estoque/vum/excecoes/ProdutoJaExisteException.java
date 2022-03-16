package api.estoque.vum.excecoes;

public class ProdutoJaExisteException extends RuntimeException {
	private String titulo;
	private String detalhes;

	public ProdutoJaExisteException(String titulo, String detalhes) {
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
