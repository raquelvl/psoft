package projsoft.ufcg;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

import projsoft.ufcg.filtros.TokenFilter;

@SpringBootApplication
public class DemojwtApplication {

	@Bean
	public FilterRegistrationBean<TokenFilter> filterJwt() {
		FilterRegistrationBean<TokenFilter> filterRB = new FilterRegistrationBean<TokenFilter>();
		filterRB.setFilter(new TokenFilter());
		filterRB.addUrlPatterns("/api/produtos", "/auth/usuarios/*");
		return filterRB;
	}

	public static void main(String[] args) {
		SpringApplication.run(DemojwtApplication.class, args);
	}

}
