package ufpb.dcx.aula.perguntaserespostasii.dtos;

import java.util.Arrays;
import java.util.List;

import lombok.Data;

@Data
public class PerguntaDTO {
	private String texto;
	private String palavrasChave;
	private String separador = ",";
	
	public List<String> extraiPalavrasChave() {
		return Arrays.asList(palavrasChave.split(separador));
	}
	
}
