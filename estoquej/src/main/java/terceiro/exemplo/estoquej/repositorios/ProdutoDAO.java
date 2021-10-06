package terceiro.exemplo.estoquej.repositorios;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import terceiro.exemplo.estoquej.entidades.Produto;

@Repository
public interface ProdutoDAO extends JpaRepository<Produto, Long> {
	public Optional<Produto> findByNome(String nome);
	public List<Produto> findByNomeContaining(String padrao); 
	public boolean existsByNome(String nome);
}
