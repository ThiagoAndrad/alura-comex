package br.com.alura.comex.features.pedido.calculo;

import br.com.alura.comex.features.pedido.ItemPedidoCalculado;

import java.math.BigDecimal;

public interface RegraDeDesconto {

    ItemPedidoCalculado aplica(int quantidade, BigDecimal valorTotal);
}
