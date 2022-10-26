package br.com.alura.comex.repository;

import br.com.alura.comex.entity.Pedido;
import br.com.alura.comex.repository.projection.PedidosPorCategoriaProjection;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PedidoRepository extends CrudRepository<Pedido, Long> {

    @Query(value = """
                SELECT 
                    c.nome as nomeCategoria, 
                    SUM(ip.quantidade) as quantidadeProdutosVendidos, 
                    SUM(ip.preco_unitario * ip.quantidade) as valorTotal 
                FROM produtos p
                    JOIN itens_pedido ip on p.id = ip.produto_id
                    JOIN categorias c on c.id = p.categoria_id
                GROUP BY categoria_id
            """, nativeQuery = true)
    List<PedidosPorCategoriaProjection> findGroupByCateoria();
}
