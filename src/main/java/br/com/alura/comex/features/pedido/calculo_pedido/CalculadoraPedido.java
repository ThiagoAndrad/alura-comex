package br.com.alura.comex.features.pedido.calculo_pedido;

import br.com.alura.comex.entity.Cliente;
import br.com.alura.comex.entity.ItemDePedido;
import br.com.alura.comex.entity.Pedido;

import java.util.List;

public interface CalculadoraPedido {

    Pedido calculo(List<ItemDePedido> detalheItemPedidoCalculados, Cliente cliente);

}
