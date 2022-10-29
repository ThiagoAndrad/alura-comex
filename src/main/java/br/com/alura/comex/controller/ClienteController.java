package br.com.alura.comex.controller;

import br.com.alura.comex.controller.dto.NovoClienteRequest;
import br.com.alura.comex.repository.ClienteRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;

@RestController
@RequestMapping("api/clientes")
class ClienteController {

    private final ClienteRepository clienteRepository;

    ClienteController(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @PostMapping
    ResponseEntity<Void> novoCliente(@RequestBody @Valid NovoClienteRequest novoClienteRequest,
                                     UriComponentsBuilder uriBuilder) {

        var cliente = novoClienteRequest.toCliente();

        clienteRepository.save(cliente);

        var location = uriBuilder.path("/clientes/{id}")
                .buildAndExpand(cliente.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }
}
