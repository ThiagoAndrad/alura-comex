package br.com.alura.comex.repository;

import br.com.alura.comex.entity.Pedido;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidoRepository extends CrudRepository<Pedido, Long> {
}
