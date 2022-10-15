package br.com.alura.comex.repository;

import br.com.alura.comex.entity.Produto;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdutoRepository extends CrudRepository<Produto, Long> {
}
