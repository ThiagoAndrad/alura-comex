package br.com.alura.comex.features.pedido.calculo;

import br.com.alura.comex.entity.TipoDescontoItem;
import br.com.alura.comex.features.pedido.ItemPedidoCalculado;

import java.math.BigDecimal;

class RegraSemDesconto implements RegraDeDesconto {
    @Override
    public ItemPedidoCalculado aplica(int quantidade, BigDecimal valorTotal) {
        return new ItemPedidoCalculado(BigDecimal.ZERO, TipoDescontoItem.NENHUM);
    }
}
