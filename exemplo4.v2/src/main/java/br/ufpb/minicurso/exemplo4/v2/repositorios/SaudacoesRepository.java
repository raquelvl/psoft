package br.ufpb.minicurso.exemplo4.v2.repositorios;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.ufpb.minicurso.exemplo4.v2.entidades.Saudacao;

@Repository
public interface SaudacoesRepository<T, ID extends Serializable> extends JpaRepository<Saudacao, Integer> {

	List<Saudacao> findBySaudacaoContaining(String substring);
	
	@Query("SELECT s FROM Saudacao s WHERE s.saudacao LIKE %:expressao%")
	List<Saudacao> searchBySaudacaoContaining(@Param("expressao") String expressao);
	
	List<Saudacao> findByUsuarioEmail(String email);

}



































