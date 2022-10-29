package br.com.alura.comex.features.pedido;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
@RequestMapping("api/pedidos")
class PedidoController {

    private final PedidoService pedidoService;

    PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @PostMapping
    @Transactional
    ResponseEntity<Void> novoPedido(@RequestBody @Valid NovoPedidoRequest novoPedidoRequest,
                                    UriComponentsBuilder uriBuilder) {

        var pedido = pedidoService.novoPedido(novoPedidoRequest);

        var location = uriBuilder.path("api/pedidos/{id}")
                .buildAndExpand(pedido.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }
}
