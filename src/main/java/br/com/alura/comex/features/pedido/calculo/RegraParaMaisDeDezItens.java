package br.com.alura.comex.features.pedido.calculo;

import br.com.alura.comex.entity.TipoDescontoItem;

import java.math.BigDecimal;

class RegraParaMaisDeDezItens implements RegraDeDesconto {
    private final RegraSemDesconto proximaRegra;

    public RegraParaMaisDeDezItens(RegraSemDesconto semDesconto) {
        proximaRegra = semDesconto;
    }

    @Override
    public ItemPedidoCalculado aplica(int quantidade, BigDecimal valorTotal) {

        if (quantidade > 10) {
            var valorComDesconto = valorTotal.multiply(BigDecimal.valueOf(0.9));

            return new ItemPedidoCalculado(valorComDesconto, TipoDescontoItem.QUANTIDADE);
        }

        return proximaRegra.aplica(quantidade, valorTotal);
    }
}
