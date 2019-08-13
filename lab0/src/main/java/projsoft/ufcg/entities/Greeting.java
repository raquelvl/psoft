package projsoft.ufcg.entities;

import java.io.Serializable;

import lombok.Data;

@Data
public class Greeting implements Serializable {
	private String greet;
	private String nome;
	
	public Greeting(String novaSaudacao, String nome) {
		this.greet = novaSaudacao;
		this.nome = nome;
	}

	@Override
	public String toString() {
		return greet + ", " + nome + "!";
	}
}
