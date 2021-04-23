package ufpb.dcx.aula.perguntaserespostasii.daos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ufpb.dcx.aula.perguntaserespostasii.entidades.Pergunta;

@Repository
public interface PerguntaDAO extends JpaRepository<Pergunta, Long> {

}
