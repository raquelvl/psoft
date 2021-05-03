package ufpb.dcx.aula.perguntaserespostas.jwt.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ufpb.dcx.aula.perguntaserespostas.jwt.entidades.Pergunta;

@Repository
public interface PerguntaDAO extends JpaRepository<Pergunta, Long> {

}
