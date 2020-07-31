package br.ufpb.minicurso.exemplo4.entidades;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;

@Entity
public class Usuario {

	@Id
	private String email;
	private String nome;
	private String senha;
	
	/*
	 * Rode esta aplicação com a configuração @JoinTable e veja o BD
	 * Depois comente esta configuração, apague os dados e rode novamente
	 * Você verá que o framework coloca pra nós exatamente esta configuração
	 * Ela diz que uma tabela de associação deve ser criada, esta tabela
	 * vai se chamar usuario_saudacoes (este nome é formado pegando o nome
	 * da classe proprietária, que é usuario e concatenando com "_" e o nome
	 * do atributo que referencia a classe não proprietária, nesse caso, 
	 * saudacoes. A primeira coluna é composta pelo nome da classe 
	 * proprietária concatenado com "_" e o nome da chave primária dessa
	 * classe, que é email. Para a segunda coluna, que corresponde à classe 
	 * nao proprietária, mais uma vez usamos o nome do atributo referente
	 * a esta classe, que é saudações, concatenado com "_" e o nome do 
	 * atributo que é a chave primária desta classe não proprietária, que
	 * nesse caso é id. 
	 */
	@OneToMany
	@JoinTable(name = "usuario_saudacoes",
				joinColumns = @JoinColumn(name = "usuario_email"),
				inverseJoinColumns = @JoinColumn(name = "saudacoes_id"))
	private List<Saudacao> saudacoes;

	public Usuario() {
		super();
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
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
		Usuario other = (Usuario) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		return true;
	}

	public boolean isValid() {
		return !email.isBlank() && !nome.isBlank() && !senha.isBlank();
	}
	
	public void adicionaSaudacao(Saudacao saudacao) {
		saudacoes.add(saudacao);
	}
	
	public List<Saudacao> getSaudacoes() {
		return saudacoes;
	}

}










