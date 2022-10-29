package br.com.alura.comex.repository;

import br.com.alura.comex.entity.ItemDePedido;
import org.springframework.data.repository.CrudRepository;

public interface ItemPedidoRepository extends CrudRepository<ItemDePedido, Long> {
}
