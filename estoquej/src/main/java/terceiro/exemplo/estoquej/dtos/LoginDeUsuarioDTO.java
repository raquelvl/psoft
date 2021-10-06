package terceiro.exemplo.estoquej.dtos;

import java.util.Objects;

public class LoginDeUsuarioDTO {

	private String email;
	private String senha;

	public LoginDeUsuarioDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public LoginDeUsuarioDTO(String email, String senha) {
		super();
		this.email = email;
		this.senha = senha;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	@Override
	public int hashCode() {
		return Objects.hash(email);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LoginDeUsuarioDTO other = (LoginDeUsuarioDTO) obj;
		return Objects.equals(email, other.email);
	}

}
