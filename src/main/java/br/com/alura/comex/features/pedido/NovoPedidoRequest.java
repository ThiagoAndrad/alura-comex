package br.com.alura.comex.features.pedido;

import java.util.List;

record NovoPedidoRequest(
        Long clienteId,
        List<ProdutoPedidoRequest> produtos
) {
}

record ProdutoPedidoRequest(
        Long produtoId,
        int quantidade
) {

}
