package br.ufpb.minicurso.exemplo3.servicos;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufpb.minicurso.exemplo3.dtos.UsuarioLoginDTO;
import br.ufpb.minicurso.exemplo3.entidades.Usuario;
import br.ufpb.minicurso.exemplo3.repositorios.UsuariosRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;

@Service
public class JWTService {

	@Autowired
	private UsuariosRepository usuariosDAO;

	public LoginResponse autentica(UsuarioLoginDTO usuario) {
		String msgErro = "Email e/ou senha invalido(s). Login nao realizado";
		Optional<Usuario> optUsuario = usuariosDAO.findByEmail(usuario.getEmail());
		if (optUsuario.isPresent() && usuario.getSenha().equals(optUsuario.get().getSenha()))
			return new LoginResponse(geraToken(usuario));

		return new LoginResponse(msgErro);

	}

	private String geraToken(UsuarioLoginDTO usuario) {
		String token = Jwts.builder().setSubject(usuario.getEmail()).signWith(SignatureAlgorithm.HS512, TOKEN_KEY)
				.setExpiration(new Date(System.currentTimeMillis() + 1 * 60 * 1000)).compact();
		return token;
	}

	public static final String TOKEN_KEY = "ja pode sair?";

	public Optional<String> recuperaUsuario(String cabecalhoAutorizacao) {
		if (cabecalhoAutorizacao == null || 
			!cabecalhoAutorizacao.startsWith("Bearer ")) {
			throw new SecurityException();
		}

		// Extraindo apenas o token do cabecalho.
		String token = cabecalhoAutorizacao.substring(br.ufpb.minicurso.exemplo3.filtros.TokenFilter.TOKEN_INDEX);

		String subject = null;
		try {
			subject = Jwts.parser().setSigningKey(TOKEN_KEY).parseClaimsJws(token).getBody().getSubject();
		} catch (SignatureException e) {
			throw new SecurityException("Token invalido ou expirado!");
		}
		return Optional.of(subject);
	}

}
