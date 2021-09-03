package primeiro.exemplo.estoquez.dtos;

public class DetalhesDoProblema {
	private int status;
	private String type;
	private String title;
	private String detail;

	public DetalhesDoProblema() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DetalhesDoProblema(int status, String type, String title, String detail) {
		super();
		this.status = status;
		this.type = type;
		this.title = title;
		this.detail = detail;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	
}
