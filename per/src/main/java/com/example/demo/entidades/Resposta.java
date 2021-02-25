package com.example.demo.entidades;

public class Resposta {
	private int idPergunta;
	private int id;
	private String resposta;
	private boolean apagada = false;

	public Resposta(int idPergunta, int id, String resposta) {
		super();
		this.idPergunta = idPergunta;
		this.id = id;
		this.resposta = resposta;
	}

	public String getResposta() {
		return resposta;
	}

	public void setResposta(String resposta) {
		this.resposta = resposta;
	}

	public int getIdPergunta() {
		return idPergunta;
	}

	public int getId() {
		return id;
	}

	public boolean isApagada() {
		return apagada;
	}

	public void setApagada(boolean apagada) {
		this.apagada = apagada;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + idPergunta;
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
		Resposta other = (Resposta) obj;
		if (id != other.id)
			return false;
		if (idPergunta != other.idPergunta)
			return false;
		return true;
	}

}
