package br.ufpb.minicurso.exemplo4.v2.entidades;

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
	 * A anotação JoinColumn neste caso determina que na tabela SAUDACAO
	 * deve ser adicionada uma nova coluna, que será chamada usuario_id e
	 * que deve ser a chave estrangeira de usuario na tabela saudacao. Com 
	 * esta coluna o JPA saberá relacionar que usuario está associado com 
	 * cada saudação e assim poderá recuperar esta informação do banco de 
	 * dados. Esta anotação é a que define quem é a entidade proprietária
	 * da relação.
	 * Esta anotação é colocada pra nós automaticamente pelo framework. Faça
	 * o teste. Rode a aplicação com a anotação, use a aplicação, veja que
	 * esquema de banco de dados foi criado. Depois comente a anotação de
	 * JoinColumn e veja que exatamente o mesmo esquema de banco de dados foi
	 * criado. Por default, o framework vai usar o nome do atributo 
	 * da classe não proprietária, que nesse caso é usuario, e concatenar
	 * com "_" e o nome do atributo que é a chave primária dessa classe. Se
	 * você quer nomes diferentes tem que configurar a anotação. Caso queira
	 * exatamente esses nomes, pode até deixar sem anotar, mas lembre que 
	 * você precisa entender o que está ocorrendo.
	 */
	@ManyToOne
	@JoinColumn(name = "usuario_email")
	private Usuario usuario;

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
