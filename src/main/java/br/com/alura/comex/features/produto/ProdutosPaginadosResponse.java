package br.com.alura.comex.features.produto;

import br.com.alura.comex.entity.Produto;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;

record ProdutosPaginadosResponse(
        String nome,

        String descricao,

        BigDecimal precoUnitario,

        int quantidadeEmEstoque,

        Long idDaCategoria
) {

    static Page<ProdutosPaginadosResponse> from(Page<Produto> pagesOfProducts) {

        return pagesOfProducts.map(produto -> new ProdutosPaginadosResponse(
                produto.getNome(),
                produto.getDescricao(),
                produto.getPrecoUnitario(),
                produto.getQuantidadeEstoque(),
                produto.getCategoria().getId()
        ));
    }
}
