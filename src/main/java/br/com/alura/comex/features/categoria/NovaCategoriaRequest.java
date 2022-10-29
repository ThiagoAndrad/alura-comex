package br.com.alura.comex.features.categoria;

import br.com.alura.comex.entity.Categoria;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

record NovaCategoriaRequest(
        @Size(min = 2)
        @NotBlank
        String nome
) {
    Categoria toCategoriaAtiva() {

        var categoria = new Categoria();
        categoria.setNome(this.nome);
        categoria.ativa();

        return categoria;
    }
}
