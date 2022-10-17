package br.com.alura.comex.controller.dto;

import br.com.alura.comex.entity.Produto;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;

public record ProdutosPaginadosResponse(
        String nome,

        String descricao,

        BigDecimal precoUnitario,

        int quantidadeEmEstoque,

        Long idDaCategoria
) {

    public static Page<ProdutosPaginadosResponse> from(Page<Produto> pagesOfProducts) {

        return pagesOfProducts.map(produto -> new ProdutosPaginadosResponse(
                produto.getNome(),
                produto.getDescricao(),
                produto.getPrecoUnitario(),
                produto.getQuantidadeEstoque(),
                produto.getCategoria().getId()
        ));
    }
}
