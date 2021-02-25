package com.example.demo.dtos;

public class PerguntaDTO {
	private String pergunta;
	private String palavrasChave;

	public PerguntaDTO(String pergunta, String palavrasChave) {
		super();
		this.pergunta = pergunta;
		this.palavrasChave = palavrasChave;
	}

	public String getPergunta() {
		return pergunta;
	}

	public String getPalavrasChave() {
		return palavrasChave;
	}

}
