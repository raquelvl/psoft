package terceiro.exemplo.estoquej.servicos;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import terceiro.exemplo.estoquej.dtos.LoginDeUsuarioDTO;
import terceiro.exemplo.estoquej.entidades.Usuario;
import terceiro.exemplo.estoquej.excecoes.OperacaoNaoAutorizadaException;
import terceiro.exemplo.estoquej.repositorios.UsuarioDAO;

@Service
public class ServicoDeUsuarios {

	@Autowired
	private UsuarioDAO usuariosDAO;
	@Autowired
	private ServicoJWT jwtService;

	public Usuario adicionaUsuario(Usuario usuario) {
		return this.usuariosDAO.save(usuario);
	}

	public Usuario getUsuario(String email, String authHeader) {
		Optional<Usuario> optUsuario = usuariosDAO.findByEmail(email);
		if (!optUsuario.isEmpty() && usuarioTemPermissao(authHeader, email)) {
			return optUsuario.get();
		}
		throw new OperacaoNaoAutorizadaException("Usuario nao tem permissao",
				"A operacao requerida nao pode ser realizada por este usuario: " + jwtService.getSujeitoDoToken(authHeader) + ".");
	}

	public Usuario removeUsuario(String email, String authHeader) {
		Usuario usuario = getUsuario(email, authHeader);
		if (usuarioTemPermissao(authHeader, email)) {
			usuariosDAO.delete(usuario);
		}
		return usuario;
	}

	private boolean usuarioTemPermissao(String authorizationHeader, String email) {
		String subject = jwtService.getSujeitoDoToken(authorizationHeader);
		Optional<Usuario> optUsuario = usuariosDAO.findByEmail(subject);
		return optUsuario.isPresent() && optUsuario.get().getEmail().equals(email);
	}

	public boolean validaUsuarioSenha(LoginDeUsuarioDTO usuario) {
		Optional<Usuario> optUsuario = usuariosDAO.findByEmail(usuario.getEmail());
		if (optUsuario.isPresent() && optUsuario.get().getSenha().equals(usuario.getSenha()))
			return true;
		return false;
	}

}
