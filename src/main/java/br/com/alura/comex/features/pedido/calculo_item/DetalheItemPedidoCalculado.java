package br.com.alura.comex.features.pedido.calculo_item;

import br.com.alura.comex.entity.TipoDescontoItem;

import java.math.BigDecimal;

public class DetalheItemPedidoCalculado {

    private final BigDecimal valorTotal;

    private final TipoDescontoItem tipo;

    public DetalheItemPedidoCalculado(BigDecimal valorTotal, TipoDescontoItem tipo) {
        this.valorTotal = valorTotal;
        this.tipo = tipo;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public TipoDescontoItem getTipo() {
        return tipo;
    }
}
