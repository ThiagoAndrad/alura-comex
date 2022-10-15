package br.com.alura.comex.repository;

import br.com.alura.comex.entity.Cliente;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends CrudRepository<Cliente, Long> {
}
