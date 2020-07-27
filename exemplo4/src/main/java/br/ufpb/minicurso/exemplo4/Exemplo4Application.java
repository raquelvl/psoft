package br.ufpb.minicurso.exemplo4;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

import br.ufpb.minicurso.exemplo4.filtros.TokenFilter;

@SpringBootApplication
public class Exemplo4Application {
	
	@Bean
	public FilterRegistrationBean<TokenFilter> filterJwt() {
		FilterRegistrationBean<TokenFilter> filterRB = new FilterRegistrationBean<TokenFilter>();
		filterRB.setFilter(new TokenFilter());
		filterRB.addUrlPatterns("/saudacoes/alternativa/*", "/auth/usuarios");
		return filterRB;
	}

	public static void main(String[] args) {
		SpringApplication.run(Exemplo4Application.class, args);
	}

}
