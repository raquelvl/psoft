package com.example.demo.entidades;

public class Pergunta {
	private int id;
	private String pergunta;
	private String palavrasChave;
	boolean apagada = false;

	public Pergunta(int id, String pergunta, String palavrasChave) {
		super();
		this.id = id;
		this.pergunta = pergunta;
		this.palavrasChave = palavrasChave;
	}

	public String getPalavrasChave() {
		return palavrasChave;
	}

	public void setPalavrasChave(String palavrasChave) {
		this.palavrasChave = palavrasChave;
	}

	public int getId() {
		return id;
	}

	public String getPergunta() {
		return pergunta;
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
		Pergunta other = (Pergunta) obj;
		if (id != other.id)
			return false;
		return true;
	}

}
