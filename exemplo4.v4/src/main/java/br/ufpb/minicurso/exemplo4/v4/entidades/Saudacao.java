package br.ufpb.minicurso.exemplo4.v4.entidades;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Saudacao {
	@Id
	@GeneratedValue
	private int id;
	private String nome;
	private String saudacao;

	/*
	 * Com a anotação JoinColumn definimos quem é a entidade que é
	 * a proprietária na relação. Em relações @ManyToOne a entidade 
	 * do lado "Many" sempre vai ser a proprietária. Vamos entender 
	 * vendo este exemplo: existem muitas saudações para cada usuário,
	 * então Saudacao é a entidade do lado "Muitos". Se olharmos para um
	 * usuário encontramos várias saudações relacionadas. Mas se olharmos
	 * para uma saudação, temos apenas *um* usuário relacionado. Por conta
	 * disso, esta é a única entidade que poderia ser a proprietária 
	 * da relação, isto é, carregar a chave estrangeira da outra entidade.
	 * A outra entidade que não é proprietária é muitas vezes chamada de
	 * entidade mãe.
	 * 
	 */
	@ManyToOne
	@JoinColumn(name = "usuario_email")
	private Usuario usuario;
	/* Por default, quando uma saudação é recuperada do banco de dados,
	 * o usuário associado já é recuperado também. Esta é a configuração 
	 * fetch. No caso em que a entidade relacionada não vem em uma 
	 * coleção, a configuração fetch default é EAGER (ansioso).
	 */

	public Saudacao() {
		this.saudacao = "Oi";
	}

	public Saudacao(String nome, String saudacao) {
		super();
		this.nome = nome;
		this.saudacao = saudacao;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setSaudacao(String saudacao) {
		this.saudacao = saudacao;
	}

	public String getNome() {
		return nome;
	}

	public String getSaudacao() {
		return saudacao;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

}
