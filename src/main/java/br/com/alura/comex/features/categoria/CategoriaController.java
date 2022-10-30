package br.com.alura.comex.features.categoria;

import br.com.alura.comex.repository.CategoriaRepository;
import br.com.alura.comex.repository.PedidoRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/categorias")
class CategoriaController {

    private final CategoriaRepository categoriaRepository;
    private final PedidoRepository pedidoRepository;

    CategoriaController(CategoriaRepository categoriaRepository, PedidoRepository pedidoRepository) {
        this.categoriaRepository = categoriaRepository;
        this.pedidoRepository = pedidoRepository;
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

    @GetMapping("/pedidos")
    @Cacheable(value = "pedidos_por_categoria")
    public ResponseEntity<PedidosPorCategoriaResponse> listaPedidosPorCategoria() {

        var pedidosPorCategoriaProjection = pedidoRepository.findGroupByCateoria();

        var pedidosPorCategoriaResponse = PedidosPorCategoriaResponse.from(pedidosPorCategoriaProjection);

        return ResponseEntity.ok().body(pedidosPorCategoriaResponse);
    }

}
