package ufpb.dcx.aula.perguntaserespostasii.daos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ufpb.dcx.aula.perguntaserespostasii.entidades.Pergunta;
import ufpb.dcx.aula.perguntaserespostasii.entidades.Resposta;

@Repository
public interface RespostaDAO extends JpaRepository<Resposta, Long> {
	List<Resposta> findByPergunta(Pergunta pergunta);
}
