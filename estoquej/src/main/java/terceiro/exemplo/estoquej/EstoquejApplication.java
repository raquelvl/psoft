package terceiro.exemplo.estoquej;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

import terceiro.exemplo.estoquej.filtros.FiltroDeTokensJWT;

@SpringBootApplication
public class EstoquejApplication {

	@Bean
	public FilterRegistrationBean<FiltroDeTokensJWT> filterJwt() {
		FilterRegistrationBean<FiltroDeTokensJWT> filterRB = new FilterRegistrationBean<FiltroDeTokensJWT>();
		filterRB.setFilter(new FiltroDeTokensJWT());
		filterRB.addUrlPatterns("/v1/api/produtos/*", "/auth/usuarios");
		return filterRB;
	}
	
	public static void main(String[] args) {
		SpringApplication.run(EstoquejApplication.class, args);
	}

}
