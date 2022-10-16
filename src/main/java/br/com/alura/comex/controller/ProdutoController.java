package br.com.alura.comex.controller;

import br.com.alura.comex.controller.dto.NovoProdutoRequest;
import br.com.alura.comex.repository.CategoriaRepository;
import br.com.alura.comex.repository.ProdutoRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/produtos")
class ProdutoController {

    private final CategoriaRepository categoriaRepository;
    private final ProdutoRepository produtoRepository;

    ProdutoController(CategoriaRepository categoriaRepository, ProdutoRepository produtoRepository) {
        this.categoriaRepository = categoriaRepository;
        this.produtoRepository = produtoRepository;
    }

    @PostMapping
    ResponseEntity<Void> novoProduto(@RequestBody @Valid NovoProdutoRequest novoProdutoRequest,
                                     UriComponentsBuilder uriBuilder) {

        var categoriaId = novoProdutoRequest.idDaCategoria();

        var categoriaOptional = categoriaRepository.findById(categoriaId);

        if (categoriaOptional.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        var newProduto = novoProdutoRequest.toProduto(categoriaOptional.get());

        var produto = produtoRepository.save(newProduto);

        var location = uriBuilder.path("/produtos/{id}")
                .buildAndExpand(produto.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }
}
