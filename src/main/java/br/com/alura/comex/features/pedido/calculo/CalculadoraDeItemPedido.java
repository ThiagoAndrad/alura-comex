package br.com.alura.comex.features.pedido.calculo;

import br.com.alura.comex.entity.Produto;

public interface CalculadoraDeItemPedido {

    ItemPedidoCalculado calcula(int quantidade, Produto produto);
}
