package br.ufpb.minicurso.exemplo4.v4.entidades;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Usuario {

	@Id
	private String email;
	private String nome;
	private String senha;
	
	/*
	 * A configuração mappedBy vai indicar para o JPA que já existem dados
	 * que relacionam as duas entidades - neste caso Usuario e Saudacao.
	 * Como funciona? Indicamos o nome do atributo na classe proprietária
	 * que foi marcado com JoinColumn (nesse caso, o atributo usuario da
	 * classe Saudacao).
	 * Sem esta configuração, o JPA pensará que não existem dados que 
	 * possam ser usados para realizar esta associação e vai criar uma 
	 * tabela de join entre Saudacao e Usuario. Esta tabela vai ter duas 
	 * colunas, uma coluna com id de cada saudação salva e a outra com o 
	 * email do usuário que adicionou esta saudação. Você pode fazer o 
	 * teste rodando a aplicação com e sem a configuração do mappedBy 
	 * e ver como fica o banco de dados. Lembre de apagar os dados de uma
	 * execução para a outra.
	 */
	//@OneToMany(mappedBy = "usuario")
	/*
	 * Esta anotação JsonIgnore diz pra ignorar este atributo ao montar 
	 * o json de usuario. Lembremos que agora temos uma relação bidirecional
	 * então, sem esta configuração, ao montar um json de usuário o
	 * jackson vai tentar pegar as saudações do usuário, mas dentro de
	 * saudação tem o usuário de novo, e entraríamos em loop.
	 */
	@JsonIgnore
	@OneToMany
	private List<Saudacao> saudacoes;
	/* Quando um objeto usuário é recuperado do banco de dados, por default,
	 * a lista de suas saudações não é recuperada automaticamente. Isso só
	 * ocorre quando o metodo getSaudacoes de usuário é chamado. Esta é uma
	 * configuração de "fetch" e para coleções, o valor default é LAZY, 
	 * isto é, preguiçoso.
	 */

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










