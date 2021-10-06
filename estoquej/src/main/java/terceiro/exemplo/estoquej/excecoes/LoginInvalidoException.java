package terceiro.exemplo.estoquej.excecoes;

public class LoginInvalidoException extends RuntimeException {
	private String titulo;
	private String detalhes;

	public LoginInvalidoException(String titulo, String detalhes) {
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
