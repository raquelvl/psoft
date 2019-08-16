package projsoft.ufcg.services;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.GregorianCalendar;

import org.springframework.stereotype.Service;

import projsoft.ufcg.entities.Greeting;
import projsoft.ufcg.entities.ServerTimeInfo;

@Service
public class GreetingService {

	private Greet novaSaudacao = new Greet("");

	public Greeting getSaudacao(String nome) {
		String saudacao = null;
		LocalTime hora = LocalTime.now();

		if (hora.isBefore(LocalTime.NOON) && hora.isAfter(LocalTime.MIDNIGHT.plusHours(6)))
			saudacao = "Bom dia";
		else if (hora.isAfter(LocalTime.MIDNIGHT.minusHours(6)))
			saudacao = "Boa noite";
		else
			saudacao = "Boa tarde";
		return new Greeting(nome, saudacao);
	}

	public ServerTimeInfo getInfoTempoNoServidor() {
		return new ServerTimeInfo(LocalTime.now().format(DateTimeFormatter.ofPattern("H:mm:ss")),
				new GregorianCalendar().getTimeZone().getDisplayName());
	}

	public Greet setNovaSaudacao(Greet novaSaudacao2) {
		this.novaSaudacao = novaSaudacao2;
		return this.novaSaudacao;
	}

	public Greeting getNovaSaudacao(String nome) {
		return new Greeting(novaSaudacao.getGreet(), nome);
	}

}
