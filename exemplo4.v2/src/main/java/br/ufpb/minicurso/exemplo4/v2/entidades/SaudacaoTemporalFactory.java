package br.ufpb.minicurso.exemplo4.v2.entidades;


import java.time.LocalTime;

public class SaudacaoTemporalFactory {
	
	public static Saudacao getSaudacaoTemporal(String nome) {
		if(manha())
			return new Saudacao(nome, "Bom dia");
		if(tarde())
			return new Saudacao(nome, "Boa tarde");
		else
			return new Saudacao(nome, "Boa noite");
	}

	private static boolean manha() {
		LocalTime now = LocalTime.now();
		if(now.isAfter(LocalTime.NOON.minusHours(6)) && now.isBefore(LocalTime.NOON))
			return true;
		return false;
	}

	private static boolean tarde() {
		LocalTime now = LocalTime.now();
		if(now.isAfter(LocalTime.NOON) && now.isBefore(LocalTime.NOON.plusHours(6)))
			return true;
		return false;
	}

}
