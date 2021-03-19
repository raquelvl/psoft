package com.example.demo.entidades;

public class Comentario {
	private int idPergunta;
	private int id;
	private TipoDeComentario tipo;
	private String comentario;
	private boolean apagado;

	public Comentario(int idEstrangeiro, int id, TipoDeComentario tipo, String comentario) {
		super();
		this.idPergunta = idEstrangeiro;
		this.id = id;
		this.tipo = tipo;
		this.comentario = comentario;
		this.apagado = false;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public int getIdEstrangeiro() {
		return idPergunta;
	}

	public int getId() {
		return id;
	}

	public TipoDeComentario getTipo() {
		return tipo;
	}

	public boolean isApagado() {
		return apagado;
	}

	public void setApagado(boolean apagado) {
		this.apagado = apagado;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + idPergunta;
		result = prime * result + ((tipo == null) ? 0 : tipo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Comentario other = (Comentario) obj;
		if (id != other.id)
			return false;
		if (idPergunta != other.idPergunta)
			return false;
		if (tipo == null) {
			if (other.tipo != null)
				return false;
		} else if (!tipo.equals(other.tipo))
			return false;
		return true;
	}

}
