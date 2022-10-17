package br.com.alura.comex.controller;

import br.com.alura.comex.controller.dto.NovoProdutoRequest;
import br.com.alura.comex.controller.dto.ProdutosPaginadosResponse;
import br.com.alura.comex.repository.CategoriaRepository;
import br.com.alura.comex.repository.ProdutoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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

    @GetMapping
    ResponseEntity<Page<ProdutosPaginadosResponse>> listaProdutos(
            @PageableDefault(sort = "nome", direction = Sort.Direction.ASC) Pageable pageable) {

        var pagesOfProducts = produtoRepository.findAll(pageable);

        var produtosPaginadosResponse = ProdutosPaginadosResponse.from(pagesOfProducts);

        return ResponseEntity.ok(produtosPaginadosResponse);
    }

}
