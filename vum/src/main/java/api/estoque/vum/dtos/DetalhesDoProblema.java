package api.estoque.vum.dtos;

import lombok.Data;

//@Getter @Setter @NoArgsConstructor
@Data
public class DetalhesDoProblema {
	private int status;
	private String type;
	private String title;
	private String detail;

	public DetalhesDoProblema() {
		super();
		// TODO Auto-generated constructor stub
	}

}
