package br.com.alura.comex.repository;

import br.com.alura.comex.entity.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaRepository extends CrudRepository<Categoria, Long> {

}
