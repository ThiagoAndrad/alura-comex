package br.com.alura.comex.controller.dto;

import br.com.alura.comex.entity.Categoria;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public record NovaCategoriaRequest(
        @Size(min = 2)
        @NotBlank
        String nome
) {
    public Categoria toCategoriaAtiva() {

        var categoria = new Categoria();
        categoria.setNome(this.nome);
        categoria.ativa();

        return categoria;
    }
}
