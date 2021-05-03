package ufpb.dcx.aula.perguntaserespostas.jwt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

import ufpb.dcx.aula.perguntaserespostas.jwt.filtros.FiltroDeJWT;

@SpringBootApplication
public class JwtApplication {

	@Bean
	public FilterRegistrationBean<FiltroDeJWT> filterJwt() {
		FilterRegistrationBean<FiltroDeJWT> filtroJwt = new FilterRegistrationBean<FiltroDeJWT>();
		filtroJwt.setFilter(new FiltroDeJWT());
		filtroJwt.addUrlPatterns("/perguntas/*");
		return filtroJwt;
	}

	public static void main(String[] args) {
		SpringApplication.run(JwtApplication.class, args);
	}

}
