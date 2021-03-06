package br.ufpb.minicurso.exemplo3.dtos;

import br.ufpb.minicurso.exemplo3.entidades.Saudacao;
import lombok.Data;

@Data
public class SaudacaoDTO {
	private String nome;
	private String saudacao;
	
	public static SaudacaoDTO from(Saudacao saudacao) {
		SaudacaoDTO saudacaoDTO = new SaudacaoDTO();
		saudacaoDTO.setNome(saudacao.getNome());
		saudacaoDTO.setSaudacao(saudacao.getSaudacao());
		return saudacaoDTO;
	}

}
