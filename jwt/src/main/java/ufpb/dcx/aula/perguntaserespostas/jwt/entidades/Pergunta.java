package ufpb.dcx.aula.perguntaserespostas.jwt.entidades;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

@Entity
public class Pergunta {
	@Id
	@GeneratedValue
	private long id;
	private String texto;

	@ManyToMany
	@JoinTable(	name = "pergunta_palavra_chave", 
				joinColumns = @JoinColumn(name = "pergunta_id"), 
				inverseJoinColumns = @JoinColumn(name = "palavra_chave_id"))
	private List<PalavraChave> palavrasChave;

	private boolean apagada = false;

//	@OneToMany(mappedBy = "pergunta")
//	private List<Resposta> respostas;

	public Pergunta() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Pergunta(String texto) {
		super();
		this.texto = texto;
	}

	public Pergunta(String texto, List<PalavraChave> palavrasChave) {
		super();
		this.texto = texto;
		this.palavrasChave = palavrasChave;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public List<PalavraChave> getPalavrasChave() {
		return palavrasChave;
	}

	public void setPalavrasChave(List<PalavraChave> palavrasChave) {
		this.palavrasChave = palavrasChave;
	}

	public void adicionaPalavraChave(PalavraChave palavraChave) {
		palavrasChave.add(palavraChave);
	}

	public boolean isApagada() {
		return apagada;
	}

	public void setApagada(boolean apagada) {
		this.apagada = apagada;
	}

	public long getId() {
		return id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
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
		Pergunta other = (Pergunta) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Pergunta [id=" + id + ", texto=" + texto + ", palavrasChave=" + palavrasChave + ", apagada=" + apagada
				+ "]";
	}

}
