package br.com.alura.comex.features.pedido.calculo_item;

import br.com.alura.comex.entity.Produto;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
class CalculadoraDeItemPedidoImpl implements CalculadoraDeItemPedido {

    private final RegraDeDesconto regraDeDesconto;

    private final RegradDeDescontoFactory regraDeDescontoFactory;

    CalculadoraDeItemPedidoImpl(RegradDeDescontoFactory regraDeDescontoFactory) {
        this.regraDeDescontoFactory = regraDeDescontoFactory;
        this.regraDeDesconto = regraDeDescontoFactory.create();
    }

    @Override
    public DetalheItemPedidoCalculado calcula(int quantidade, Produto produto) {

        var valorTotal = produto.getPrecoUnitario()
                .multiply(BigDecimal.valueOf(quantidade));


        return regraDeDesconto.aplica(quantidade, valorTotal);
    }
}
