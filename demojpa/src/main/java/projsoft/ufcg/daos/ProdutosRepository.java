package projsoft.ufcg.daos;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import projsoft.ufcg.entidades.Produto;

@Repository
public interface ProdutosRepository<T, ID extends Serializable> extends JpaRepository<Produto, Long> {

}
