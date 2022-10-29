package br.com.alura.comex.features.pedido.calculo_pedido;

import br.com.alura.comex.entity.Cliente;
import br.com.alura.comex.entity.Pedido;
import br.com.alura.comex.entity.TipoDesconto;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
class PedidoFactory {

    Pedido criaPedido(BigDecimal desconto, Cliente cliente, TipoDesconto tipoDesconto) {

        var pedido = new Pedido();
        pedido.setCliente(cliente);
        pedido.setDesconto(desconto);
        pedido.setTipoDesconto(tipoDesconto);

        return pedido;
    }
}
