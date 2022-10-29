package br.com.alura.comex.features.pedido.calculo_item;

import br.com.alura.comex.entity.ItemDePedido;
import br.com.alura.comex.entity.Produto;
import org.springframework.stereotype.Component;

@Component
class CalculadoraDeItemPedidoImpl implements CalculadoraDeItemPedido {

    private final RegraDeDesconto regraDeDesconto;

    private final RegraDeDescontoFactory regraDeDescontoFactory;

    CalculadoraDeItemPedidoImpl(RegraDeDescontoFactory regraDeDescontoFactory) {
        this.regraDeDescontoFactory = regraDeDescontoFactory;
        this.regraDeDesconto = regraDeDescontoFactory.create();
    }

    @Override
    public ItemDePedido calcula(int quantidade, Produto produto) {

        var itemDePedido = new ItemDePedido(quantidade, produto);
        var valorTotalItem = itemDePedido.getValorTotalItem();

        var descontoItemPedido = regraDeDesconto.aplica(quantidade, valorTotalItem);

        itemDePedido.setDesconto(descontoItemPedido.valorTotal());
        itemDePedido.setTipoDesconto(descontoItemPedido.tipo());

        return itemDePedido;
    }
}
