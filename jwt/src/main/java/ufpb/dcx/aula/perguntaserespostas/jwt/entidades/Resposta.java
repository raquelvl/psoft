package ufpb.dcx.aula.perguntaserespostas.jwt.entidades;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Resposta {
	@Id @GeneratedValue
	private long id;
	
	@ManyToOne @JoinColumn(name="id_pergunta")
	private Pergunta pergunta;
	
	private String texto;
	private boolean apagada = false;

	public Resposta() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Resposta(Pergunta pergunta, String texto) {
		super();
		this.pergunta = pergunta;
		this.texto = texto;
	}

	public Pergunta getPergunta() {
		return pergunta;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
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
		Resposta other = (Resposta) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Resposta [id=" + id + ", pergunta=" + pergunta.getId() + ", texto=" + texto + ", apagada=" + apagada + "]";
	}
	
	

}
