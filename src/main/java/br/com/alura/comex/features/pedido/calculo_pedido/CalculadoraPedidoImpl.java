package br.com.alura.comex.features.pedido.calculo_pedido;

import br.com.alura.comex.entity.Cliente;
import br.com.alura.comex.entity.ItemDePedido;
import br.com.alura.comex.entity.Pedido;
import br.com.alura.comex.entity.TipoDesconto;
import br.com.alura.comex.repository.PedidoRepository;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
class CalculadoraPedidoImpl implements CalculadoraPedido {

    private static final int NUMERO_MINIMO_DE_PEDIDOS_PARA_DESCONTO = 5;
    private final PedidoRepository pedidoRepository;

    private final  PedidoFactory pedidoFactory;

    CalculadoraPedidoImpl(PedidoRepository pedidoRepository, PedidoFactory pedidoFactory) {
        this.pedidoRepository = pedidoRepository;
        this.pedidoFactory = pedidoFactory;
    }

    @Override
    public Pedido calculo(List<ItemDePedido> itensDePedidos, Cliente cliente) {

        var valorTotalDosItens = itensDePedidos.stream()
                .map(ItemDePedido::getValorTotalItemComDesconto)
                .reduce(BigDecimal.ZERO, BigDecimal::add);


        var numeroDePedidosDoCliente = pedidoRepository.countPedidosByCliente(cliente);
        if (numeroDePedidosDoCliente >= NUMERO_MINIMO_DE_PEDIDOS_PARA_DESCONTO) {

            var desconto = valorTotalDosItens.multiply(BigDecimal.valueOf(0.05));

            return pedidoFactory.criaPedido(desconto, cliente, TipoDesconto.FIDELIDADE);
        }

        return pedidoFactory.criaPedido(BigDecimal.ZERO, cliente, TipoDesconto.NENHUM);
    }

}
