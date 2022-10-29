package br.com.alura.comex.features.pedido.calculo_item;

import br.com.alura.comex.entity.TipoDescontoItem;

import java.math.BigDecimal;

class RegraSemDesconto implements RegraDeDesconto {
    @Override
    public DetalheItemPedidoCalculado aplica(int quantidade, BigDecimal valorTotal) {
        return new DetalheItemPedidoCalculado(BigDecimal.ZERO, TipoDescontoItem.NENHUM);
    }
}
