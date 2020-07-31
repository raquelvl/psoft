package br.ufpb.minicurso.exemplo4.entidades;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Saudacao {
	@Id @GeneratedValue
	private int id;
	private String nome;
	private String saudacao;
	
	public Saudacao() {
		this.saudacao = "Oi";
	}

	public Saudacao(String nome, String saudacao) {
		super();
		this.nome = nome;
		this.saudacao = saudacao;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setSaudacao(String saudacao) {
		this.saudacao = saudacao;
	}

	public String getNome() {
		return nome;
	}

	public String getSaudacao() {
		return saudacao;
	}

	@Override
	public String toString() {
		return "Saudacao [id=" + id + ", nome=" + nome + ", saudacao=" + saudacao + "]";
	}
	
	

}
