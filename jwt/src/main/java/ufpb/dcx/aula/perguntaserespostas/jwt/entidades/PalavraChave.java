package ufpb.dcx.aula.perguntaserespostas.jwt.entidades;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class PalavraChave {

	@Id
	private String palavraChave;

	@ManyToMany(mappedBy = "palavrasChave")
	@JsonIgnore
	private List<Pergunta> perguntas;

	public PalavraChave() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PalavraChave(String palavraChave) {
		super();
		this.palavraChave = palavraChave;
	}

	public String getPalavraChave() {
		return palavraChave;
	}

	public void setPalavraChave(String palavraChave) {
		this.palavraChave = palavraChave;
	}

	public List<Pergunta> getPerguntas() {
		return perguntas;
	}

	public void setPerguntas(List<Pergunta> perguntas) {
		this.perguntas = perguntas;
	}
	
	public void adicionaPergunta(Pergunta pergunta) {
		perguntas.add(pergunta);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((palavraChave == null) ? 0 : palavraChave.hashCode());
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
		PalavraChave other = (PalavraChave) obj;
		if (palavraChave == null) {
			if (other.palavraChave != null)
				return false;
		} else if (!palavraChave.equals(other.palavraChave))
			return false;
		return true;
	}

	
}
