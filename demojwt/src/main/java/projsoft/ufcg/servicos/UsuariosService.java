package projsoft.ufcg.servicos;

import java.util.Optional;

import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import projsoft.ufcg.entidades.Usuario;
import projsoft.ufcg.repositories.UsuariosRepository;

@Service
public class UsuariosService {

	@Autowired
	private UsuariosRepository<Usuario, String> usuariosDAO;
	@Autowired
	private JWTService jwtService;

	public Usuario adicionaUsuario(Usuario usuario) {
		return this.usuariosDAO.save(usuario);
	}

	public Usuario getUsuario(String email) {
		Optional<Usuario> optUsuario = usuariosDAO.findByEmail(email);
		if(optUsuario.isEmpty())
			throw new IllegalArgumentException();//usuario nao existe
		return optUsuario.get();
	}


	public Usuario removeUsuario(String email, String authHeader) throws ServletException {
		Usuario usuario = getUsuario(email);
		if (usuarioTemPermissao(authHeader, email)) {
			usuariosDAO.delete(usuario);
			return usuario;
		}
		throw new ServletException("Usuario nao tem permissao");
	}
	
	private boolean usuarioTemPermissao(String authorizationHeader, String email) throws ServletException {
		String subject = jwtService.getSujeitoDoToken(authorizationHeader);
		Optional<Usuario> optUsuario = usuariosDAO.findByEmail(subject);
		return optUsuario.isPresent() && optUsuario.get().getEmail().equals(email);
	}

	public boolean validaUsuarioSenha(Usuario usuario) {
		Optional<Usuario> optUsuario = usuariosDAO.findByEmail(usuario.getEmail());
		if (optUsuario.isPresent() && optUsuario.get().getSenha().equals(usuario.getSenha()))
			return true;
		return false;
	}

}
