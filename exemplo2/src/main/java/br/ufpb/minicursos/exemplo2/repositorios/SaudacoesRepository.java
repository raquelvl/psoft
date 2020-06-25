package br.ufpb.minicursos.exemplo2.repositorios;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.ufpb.minicursos.exemplo2.entidades.Saudacao;

@Repository
public interface SaudacoesRepository<T, ID extends Serializable> extends JpaRepository<Saudacao, Integer> {

}
