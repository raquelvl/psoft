package projsoft.ufcg.entities;

import com.fasterxml.jackson.annotation.JsonCreator;

public class Greet {
	private String greet;

	@JsonCreator
	public Greet(String greet) {
		super();
		this.greet = greet;
	}

	public String getGreet() {
		return greet;
	}

	public void setGreet(String greet) {
		this.greet = greet;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((greet == null) ? 0 : greet.hashCode());
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
		Greet other = (Greet) obj;
		if (greet == null) {
			if (other.greet != null)
				return false;
		} else if (!greet.equals(other.greet))
			return false;
		return true;
	}

}
