package segundo.exemplo.estoqued.excecoes;

public class CampoInvalidoException extends RuntimeException {
	private String titulo;
	private String detalhes;

	public CampoInvalidoException(String titulo, String detalhes) {
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
