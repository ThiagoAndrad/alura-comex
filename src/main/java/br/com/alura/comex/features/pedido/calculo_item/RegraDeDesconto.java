package br.com.alura.comex.features.pedido.calculo_item;

import java.math.BigDecimal;

public interface RegraDeDesconto {

    DescontoItemPedido aplica(int quantidade, BigDecimal valorTotal);
}
