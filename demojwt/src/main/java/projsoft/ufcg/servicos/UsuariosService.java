package projsoft.ufcg.servicos;

import java.util.Optional;

import javax.servlet.ServletException;

import org.springframework.stereotype.Service;

import projsoft.ufcg.entidades.Usuario;
import projsoft.ufcg.repositories.UsuariosRepository;

@Service
public class UsuariosService {
	
	private UsuariosRepository<Usuario, String > usuariosDAO;

	public UsuariosService(UsuariosRepository<Usuario, String> usuariosDAO) {
		super();
		this.usuariosDAO = usuariosDAO;
	}
	
	public Usuario adicionaUsuario(Usuario usuario) {
		return this.usuariosDAO.save(usuario);
	}
	
	public Optional<Usuario> getUsuario(String email) {
		return this.usuariosDAO.findByEmail(email);
	}

	public Usuario removeUsuario(String email) throws ServletException {
		Optional<Usuario> usuario = usuariosDAO.findByEmail(email);
		if(usuario.isPresent()) {
			usuariosDAO.delete(usuario.get());
			return usuario.get();
		}
		throw new ServletException("Usuario nao encontrado");
	}

}
