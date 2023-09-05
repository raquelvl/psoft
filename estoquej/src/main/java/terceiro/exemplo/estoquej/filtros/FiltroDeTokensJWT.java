package terceiro.exemplo.estoquej.filtros;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.jsonwebtoken.*;
import org.springframework.web.filter.GenericFilterBean;

import terceiro.exemplo.estoquej.servicos.ServicoJWT;

import static terceiro.exemplo.estoquej.servicos.ServicoJWT.TOKEN_KEY;

public class FiltroDeTokensJWT extends GenericFilterBean {

	public final static int TOKEN_INDEX = 7;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;

		String header = req.getHeader("Authorization");

		if (header == null || !header.startsWith("Bearer ")) {
			((HttpServletResponse) response).sendError(HttpServletResponse.SC_UNAUTHORIZED,
					"Token inexistente ou mal formatado!");
			return;
			// throw new ServletException("Token inexistente ou mal formatado!");
		}

		// Extraindo apenas o token do cabecalho (removendo o prefixo "Bearer ").
		String token = header.substring(TOKEN_INDEX);
		try {
			JwtParser parser = Jwts.parserBuilder().setSigningKey(TOKEN_KEY).build();
			parser.parseClaimsJws(token).getBody();

			//Jwts.parser().setSigningKey(TOKEN_KEY).parseClaimsJws(token).getBody();
		} catch (io.jsonwebtoken.security.SignatureException | ExpiredJwtException | MalformedJwtException | PrematureJwtException
				| UnsupportedJwtException | IllegalArgumentException e) {
			((HttpServletResponse) response).sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
			return;// a requisição nem precisa passar adiante, retornar já para o cliente pois não
					// pode prosseguir daqui pra frente
					// por falta de autorização
		}

		chain.doFilter(request, response);
	}

}
