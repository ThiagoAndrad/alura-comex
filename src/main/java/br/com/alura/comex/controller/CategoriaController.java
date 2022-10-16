package br.com.alura.comex.controller;

import br.com.alura.comex.controller.dto.NovaCategoriaRequest;
import br.com.alura.comex.repository.CategoriaRepository;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/categorias")
class CategoriaController {

    private final CategoriaRepository categoriaRepository;

    CategoriaController(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Void> novaCategoria(@RequestBody @Valid NovaCategoriaRequest novaCategoriaRequest,
                                       UriComponentsBuilder uriBuilder) {

        var novaCategoria = novaCategoriaRequest.toCategoriaAtiva();

        var categoria = categoriaRepository.save(novaCategoria);

        var location = uriBuilder.path("/categoria/{id}")
                .buildAndExpand(categoria.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

}
