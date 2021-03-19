package com.example.demo.repositorios;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.entidades.Comentario;
import com.example.demo.entidades.TipoDeComentario;

public class RepositorioDeComentarios {

	private List<Comentario> comentarios;
	static private int idComentario = 1;

	public RepositorioDeComentarios() {
		super();
		this.comentarios = new ArrayList<Comentario>();
	}

	public Comentario adicionaComentario(int idEstrangeiro, String texto, TipoDeComentario tipo) {
		Comentario comentario = new Comentario(idEstrangeiro, idComentario++, tipo, texto);
		comentarios.add(comentario);
		return comentario;
	}

	public List<Comentario> getComentarios(int idEstrangeiro, TipoDeComentario tipo) {
		List<Comentario> comentariosDaPergunta = new ArrayList<>();
		for (Comentario comentario : comentarios) {
			if (comentario.getTipo() == tipo && comentario.getIdEstrangeiro() == idEstrangeiro)
				comentariosDaPergunta.add(comentario);
		}
		return comentariosDaPergunta;

	}

	public int removeComentariosDaPergunta(int id) {
		int numComentariosApagados = 0;
		for (Comentario comentario : comentarios) {
			if (comentario.getTipo() == TipoDeComentario.COMENTARIO_DE_PERGUNTA
					&& comentario.getIdEstrangeiro() == id) {
				comentario.setApagado(true);
				numComentariosApagados++;
			}
		}
		return numComentariosApagados;

	}

}
