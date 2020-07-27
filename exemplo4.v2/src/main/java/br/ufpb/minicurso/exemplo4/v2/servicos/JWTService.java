package br.ufpb.minicurso.exemplo4.v2.servicos;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufpb.minicurso.exemplo4.v2.dtos.UsuarioLoginDTO;
import br.ufpb.minicurso.exemplo4.v2.entidades.Usuario;
import br.ufpb.minicurso.exemplo4.v2.filtros.TokenFilter;
import br.ufpb.minicurso.exemplo4.v2.repositorios.UsuariosRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JWTService {

	@Autowired
	private UsuariosRepository usuariosDAO;

	public LoginResponse autentica(UsuarioLoginDTO usuario) {
		String msgErro = "Usuario e/ou senha invalido(s). Login nao realizado";
		Optional<Usuario> optUsuario = usuariosDAO.findByEmail(usuario.getEmail());
		if (optUsuario.isPresent() && usuario.getSenha().equals(optUsuario.get().getSenha()))
			return new LoginResponse(geraToken(usuario));

		return new LoginResponse(msgErro);

	}

	private String geraToken(UsuarioLoginDTO usuario) {
		String token = Jwts.builder().setSubject(usuario.getEmail()).signWith(SignatureAlgorithm.HS512, TOKEN_KEY)
				.setExpiration(new Date(System.currentTimeMillis() + 3 * 60 * 1000)).compact();
		return token;
	}

	public static final String TOKEN_KEY = "ja pode sair?";

	public Optional<String> recuperaUsuarioId(String cabecalhoAutorizacao) {
		if (cabecalhoAutorizacao == null || 
			!cabecalhoAutorizacao.startsWith("Bearer ")) {
			throw new SecurityException();
		}

		// Extraindo apenas o token do cabecalho.
		String token = cabecalhoAutorizacao.substring(TokenFilter.TOKEN_INDEX);

		String subject = null;
		try {
			subject = Jwts.parser().setSigningKey(TOKEN_KEY).parseClaimsJws(token).getBody().getSubject();
		} catch (Exception e) {
			throw new SecurityException("Token invalido ou expirado!");
		}
		return Optional.of(subject);
	}
	
	public String getUsuarioId(String autorizacao) {
		Optional<String> emailUsuario = recuperaUsuarioId(autorizacao);
		if(emailUsuario.isEmpty())
			throw new SecurityException();
		return emailUsuario.get();
	}

}
