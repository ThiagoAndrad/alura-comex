package br.com.alura.comex.features.pedido;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("api/pedidos")
class PedidoController {

    private final PedidoService pedidoService;

    PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @PostMapping
    ResponseEntity<Void> novoPedido(@RequestBody @Valid NovoPedidoRequest novoPedidoRequest) {

        pedidoService.novoPedido(novoPedidoRequest);

        return null;
    }
}
