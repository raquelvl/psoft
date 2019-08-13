package projsoft.ufcg.services;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Service;

import projsoft.ufcg.entities.Greeting;

@Service
public class GreetingService {
	
	private Greeting novaSaudacao;

	public String getSaudacao(String nome) {
		String saudacao = null;
		LocalTime hora = LocalTime.now();

		if (hora.isBefore(LocalTime.NOON) && hora.isAfter(LocalTime.MIDNIGHT.plusHours(6)))
			saudacao = "Bom dia";
		else if (hora.isAfter(LocalTime.MIDNIGHT.minusHours(6)))
			saudacao = "Boa noite";
		else
			saudacao = "Boa tarde";
		return saudacao + ", " + nome;
	}

	public String getHoraNoServidor() {
		return LocalTime.now().format(DateTimeFormatter.ofPattern("H:mm:ss")) + " em ServerLand";
	}

	public Greeting setNovaSaudacao(Greeting novaSaudacao2) {
		this.novaSaudacao = novaSaudacao2;
		return this.novaSaudacao;
	}

	public String getNovaSaudacao() {
		return novaSaudacao.toString();
	}

}
