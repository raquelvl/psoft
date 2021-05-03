package ufpb.dcx.aula.perguntaserespostas.jwt.servicos;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import ufpb.dcx.aula.perguntaserespostas.jwt.dtos.RespostaDeLogin;
import ufpb.dcx.aula.perguntaserespostas.jwt.entidades.Usuario;
import ufpb.dcx.aula.perguntaserespostas.jwt.filtros.FiltroDeJWT;

@Service
public class ServicoJWT {
	@Autowired
	private ServicoDeUsuario servicoDeUsuarios;
	public static final String TOKEN_KEY = "wdsjfhkwbfdgwuierhweij";

	public RespostaDeLogin autentica(Usuario usuario) {

		if (!servicoDeUsuarios.validaUsuarioSenha(usuario)) {
			return new RespostaDeLogin("Usuario ou senha invalidos. Nao foi realizado o login.");
		}

		String token = geraToken(usuario.getEmail());
		return new RespostaDeLogin(token);
	}

	private String geraToken(String email) {
		return Jwts.builder().setHeaderParam("typ", "JWT").setSubject(email)
				.signWith(SignatureAlgorithm.HS512, TOKEN_KEY)
				.setExpiration(new Date(System.currentTimeMillis() + 3 * 60 * 1000)).compact();// 3 min
	}

	public String getSujeitoDoToken(String authorizationHeader) {
		if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
			throw new SecurityException("Token inexistente ou mal formatado!");
		}

		// Extraindo apenas o token do cabecalho.
		String token = authorizationHeader.substring(FiltroDeJWT.TOKEN_INDEX);

		String subject = null;
		try {
			subject = Jwts.parser().setSigningKey(TOKEN_KEY).parseClaimsJws(token).getBody().getSubject();
		} catch (SignatureException e) {
			throw new SecurityException("Token invalido ou expirado!");
		}
		return subject;
	}
}
