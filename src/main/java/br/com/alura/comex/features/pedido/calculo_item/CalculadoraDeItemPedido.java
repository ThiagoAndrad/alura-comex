package br.com.alura.comex.features.pedido.calculo_item;

import br.com.alura.comex.entity.Produto;

public interface CalculadoraDeItemPedido {

    DetalheItemPedidoCalculado calcula(int quantidade, Produto produto);
}
