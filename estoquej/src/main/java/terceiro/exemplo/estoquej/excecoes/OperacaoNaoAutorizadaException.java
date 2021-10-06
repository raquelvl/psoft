package terceiro.exemplo.estoquej.excecoes;

public class OperacaoNaoAutorizadaException extends RuntimeException {
	private String titulo;
	private String detalhes;

	public OperacaoNaoAutorizadaException(String titulo, String detalhes) {
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
