package ufpb.dcx.aula.perguntaserespostas.jwt.repositorios;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import ufpb.dcx.aula.perguntaserespostas.jwt.entidades.Usuario;

public interface UsuarioDAO extends JpaRepository<Usuario, String> {
	Optional<Usuario> findByEmail(String email);
}
