package br.com.alura.comex.features.pedido.calculo_pedido;

import br.com.alura.comex.entity.Cliente;
import br.com.alura.comex.entity.TipoDesconto;
import br.com.alura.comex.features.pedido.calculo_item.DetalheItemPedidoCalculado;
import br.com.alura.comex.repository.PedidoRepository;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
class CalculadoraPedidoImpl implements CalculadoraPedido {

    private static final int NUMERO_MINIMO_DE_PEDIDOS_PARA_DESCONTO = 5;
    private final PedidoRepository pedidoRepository;

    CalculadoraPedidoImpl(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    @Override
    public DetalhePedidoCalculado calculo(List<DetalheItemPedidoCalculado> detalheItemPedidoCalculados, Cliente cliente) {

        var valorTotalDosItens = detalheItemPedidoCalculados.stream()
                .map(DetalheItemPedidoCalculado::getValorTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);


        var numeroDePedidosDoCliente = pedidoRepository.countPedidosByCliente(cliente);
        if (numeroDePedidosDoCliente >= NUMERO_MINIMO_DE_PEDIDOS_PARA_DESCONTO) {

            return aplicaDescontoFidelidade(valorTotalDosItens);
        }

        return new DetalhePedidoCalculado(valorTotalDosItens, TipoDesconto.NENHUM);
    }

    private DetalhePedidoCalculado aplicaDescontoFidelidade(BigDecimal valorTotalDosItens) {
        var valorTotalComDesconto = valorTotalDosItens.multiply(BigDecimal.valueOf(0.95));

        return new DetalhePedidoCalculado(valorTotalComDesconto, TipoDesconto.FIDELIDADE);
    }
}
