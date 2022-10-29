package br.com.alura.comex.features.categoria;

import br.com.alura.comex.repository.projection.PedidosPorCategoriaProjection;

import java.math.BigDecimal;
import java.util.List;

record PedidosPorCategoriaResponse(
        List<PedidoPorCategoriaResponse> pedidosPorCategoria
) {
    static PedidosPorCategoriaResponse from(List<PedidosPorCategoriaProjection> projections) {
        var pedidosPorCategoria = projections.stream()
                .map(projection -> new PedidoPorCategoriaResponse(
                        projection.getNomeCategoria(),
                        projection.getQuantidadeProdutosVendidos(),
                        projection.getValorTotal())
                ).toList();

        return new PedidosPorCategoriaResponse(pedidosPorCategoria);
    }

}

record PedidoPorCategoriaResponse(
        String nomeCategoria,
        int quantidadeProdutosVendidos,
        BigDecimal valorTotal
) {
}
