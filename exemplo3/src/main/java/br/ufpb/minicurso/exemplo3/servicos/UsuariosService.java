package br.ufpb.minicurso.exemplo3.servicos;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufpb.minicurso.exemplo3.dtos.UsuarioDTO;
import br.ufpb.minicurso.exemplo3.entidades.Usuario;
import br.ufpb.minicurso.exemplo3.excecoes.UsuarioInvalidoException;
import br.ufpb.minicurso.exemplo3.excecoes.UsuarioJaExisteException;
import br.ufpb.minicurso.exemplo3.repositorios.UsuariosRepository;

@Service
public class UsuariosService {

	@Autowired
	private UsuariosRepository usuariosDAO;
	@Autowired
	private JWTService jwtService;

	public UsuarioDTO criaUsuario(Usuario usuario) {
		if (usuariosDAO.findByEmail(usuario.getEmail()).isPresent())
			throw new UsuarioJaExisteException();
		if (!usuario.isValid())
			throw new UsuarioInvalidoException();
		usuariosDAO.save(usuario);
		return new UsuarioDTO(usuario);
	}

	public UsuarioDTO deletaUsuario(String cabecalhoDeAutorizacao) {
		//vai ler o token e recuperar o subject
		Optional<String> usuarioId = jwtService.recuperaUsuario(cabecalhoDeAutorizacao);
		
		//vai pegar o subject do token e ver se existe um usuário correspondente
		Usuario usuario = validaUsuario(usuarioId);
		
		//vai remover o usuario associado ao token
		usuariosDAO.delete(usuario);
		return new UsuarioDTO(usuario);
	}

	private Usuario validaUsuario(Optional<String> id) {
		if(id.isEmpty())
			throw new UsuarioInvalidoException();

		Optional<Usuario> usuario = usuariosDAO.findByEmail(id.get());
		if (usuario.isEmpty())
			throw new UsuarioInvalidoException();
		
		return usuario.get();

	}

}
