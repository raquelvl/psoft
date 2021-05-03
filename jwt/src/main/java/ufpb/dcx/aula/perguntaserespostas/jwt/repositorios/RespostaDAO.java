package ufpb.dcx.aula.perguntaserespostas.jwt.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ufpb.dcx.aula.perguntaserespostas.jwt.entidades.Pergunta;
import ufpb.dcx.aula.perguntaserespostas.jwt.entidades.Resposta;

@Repository
public interface RespostaDAO extends JpaRepository<Resposta, Long> {
	List<Resposta> findByPergunta(Pergunta pergunta);
}
