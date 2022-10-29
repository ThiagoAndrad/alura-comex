package br.com.alura.comex.features.pedido.calculo_pedido;

import br.com.alura.comex.entity.TipoDesconto;

import java.math.BigDecimal;

public class DetalhePedidoCalculado {

    private final BigDecimal valorTotal;

    private final TipoDesconto tipoDesconto;

    public DetalhePedidoCalculado(BigDecimal valorTotal, TipoDesconto tipoDesconto) {
        this.valorTotal = valorTotal;
        this.tipoDesconto = tipoDesconto;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public TipoDesconto getTipoDesconto() {
        return tipoDesconto;
    }
}
