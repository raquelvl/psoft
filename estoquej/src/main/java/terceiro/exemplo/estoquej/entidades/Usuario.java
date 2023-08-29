package terceiro.exemplo.estoquej.entidades;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Usuario {

	@Id
	private String email;

	private String nomeCompleto;

	private String senha;

	private Papel papel = Papel.REGULAR;

	public Usuario() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Usuario(String email, String nomeCompleto, String senha, Papel papel) {
		super();
		this.email = email;
		this.nomeCompleto = nomeCompleto;
		this.senha = senha;
		this.papel = papel;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNomeCompleto() {
		return nomeCompleto;
	}

	public void setNomeCompleto(String nomeCompleto) {
		this.nomeCompleto = nomeCompleto;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Papel getPapel() {
		return papel;
	}

	public void setPapel(Papel role) {
		this.papel = role;
	}

	@Override
	public String toString() {
		return "Usuario [email=" + email + ", nomeCompleto=" + nomeCompleto + "]";
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
		Usuario other = (Usuario) obj;
		return Objects.equals(email, other.email);
	}

}
