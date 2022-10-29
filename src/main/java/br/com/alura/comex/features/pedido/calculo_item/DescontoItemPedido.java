package br.com.alura.comex.features.pedido.calculo_item;

import br.com.alura.comex.entity.TipoDescontoItem;

import java.math.BigDecimal;

record DescontoItemPedido(BigDecimal valorTotal, TipoDescontoItem tipo) {

}
