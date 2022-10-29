package br.com.alura.comex.controller.dto;

import br.com.alura.comex.entity.Cliente;
import org.springframework.data.domain.Page;

public class ClientesPaginadosResponse {
    private final String nome;
    private final String cpf;
    private final String telefone;
    private final String local;

    ClientesPaginadosResponse(Cliente cliente) {

        this.nome = cliente.getNome();
        this.cpf = cliente.getCpf();
        this.telefone = cliente.getTelefone();
        this.local = formataLocal(cliente);
    }

    public String getNome() {
        return nome;
    }

    public String getCpf() {
        return cpf;
    }

    public String getTelefone() {
        return telefone;
    }

    public String getLocal() {
        return local;
    }

    private String formataLocal(Cliente cliente) {
        var cidade = cliente.getCidade();
        var estado = cliente.getEstado();

        return cidade + "/" + estado;
    }


    public static Page<ClientesPaginadosResponse> from(Page<Cliente> clientesPaginados) {

        return clientesPaginados.map(ClientesPaginadosResponse::new);
    }
}
