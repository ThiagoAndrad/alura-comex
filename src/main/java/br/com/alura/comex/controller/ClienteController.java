package br.com.alura.comex.controller;

import br.com.alura.comex.controller.dto.ClientesPaginadosResponse;
import br.com.alura.comex.controller.dto.NovoClienteRequest;
import br.com.alura.comex.repository.ClienteRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;

import static org.springframework.data.domain.Sort.Direction.ASC;

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

    @GetMapping
    ResponseEntity<Page<ClientesPaginadosResponse>> listaClientes(@PageableDefault(sort = "nome", direction = ASC, size = 5) Pageable pageable) {

        var clientesPaginados = clienteRepository.findAll(pageable);

        var clientesPaginadosResponse = ClientesPaginadosResponse.from(clientesPaginados);

        return ResponseEntity.ok(clientesPaginadosResponse);
    }
}
