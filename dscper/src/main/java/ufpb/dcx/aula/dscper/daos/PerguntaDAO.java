package ufpb.dcx.aula.dscper.daos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ufpb.dcx.aula.dscper.entidades.Pergunta;

@Repository
public interface PerguntaDAO extends JpaRepository<Pergunta, Long> {

}
