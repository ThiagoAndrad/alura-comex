package br.com.alura.comex.controller.dto;

import br.com.alura.comex.entity.Categoria;
import br.com.alura.comex.entity.Produto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

public record NovoProdutoRequest(

        @NotBlank
        @Size(min = 2)
        String nome,

        String descricao,

        @Positive
        BigDecimal precoUnitario,

        @PositiveOrZero
        int quantidadeEmEstoque,

        @Positive
        Long idDaCategoria
) {


    public Produto toProduto(Categoria categoria) {

        return new Produto(
                this.nome,
                this.descricao,
                this.precoUnitario,
                this.quantidadeEmEstoque,
                categoria
        );
    }
}
