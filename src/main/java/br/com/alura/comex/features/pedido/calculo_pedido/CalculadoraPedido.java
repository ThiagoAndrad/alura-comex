package br.com.alura.comex.features.pedido.calculo_pedido;

import br.com.alura.comex.entity.Cliente;
import br.com.alura.comex.features.pedido.calculo_item.DetalheItemPedidoCalculado;

import java.util.List;

public interface CalculadoraPedido {

    DetalhePedidoCalculado calculo(List<DetalheItemPedidoCalculado> detalheItemPedidoCalculados, Cliente cliente);

}
