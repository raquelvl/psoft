package br.ufpb.minicurso.exemplo4.v2.excecoes;

public class UsuarioInvalidoException extends IllegalArgumentException {

	public UsuarioInvalidoException(String s) {
		super(s);
	}

	public UsuarioInvalidoException() {
		super();
		// TODO Auto-generated constructor stub
	}

}
