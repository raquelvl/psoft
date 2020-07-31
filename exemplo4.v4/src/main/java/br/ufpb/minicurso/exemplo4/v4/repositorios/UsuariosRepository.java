package br.ufpb.minicurso.exemplo4.v4.repositorios;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.ufpb.minicurso.exemplo4.v4.entidades.Usuario;

@Repository
public interface UsuariosRepository extends JpaRepository<Usuario, String> {

	Optional<Usuario> findByEmail(String email);
}
