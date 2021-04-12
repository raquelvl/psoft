package ufpb.dcx.aula.dscper.daos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ufpb.dcx.aula.dscper.entidades.Resposta;

@Repository
public interface RespostaDAO extends JpaRepository<Resposta, Long> {

}
