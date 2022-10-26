package br.com.alura.comex.repository.projection;

import java.math.BigDecimal;

public interface PedidosPorCategoriaProjection {

    String getNomeCategoria();
    int getQuantidadeProdutosVendidos();
    BigDecimal getValorTotal();
}
