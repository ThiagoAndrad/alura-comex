package br.com.alura.comex.features.pedido.calculo;

import br.com.alura.comex.entity.TipoDescontoItem;

import java.math.BigDecimal;

public class ItemPedidoCalculado {

    private final BigDecimal valorTotal;

    private final TipoDescontoItem tipo;

    public ItemPedidoCalculado(BigDecimal valorTotal, TipoDescontoItem tipo) {
        this.valorTotal = valorTotal;
        this.tipo = tipo;
    }

}
