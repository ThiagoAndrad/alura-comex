package br.com.alura.comex.features.cliente;

import br.com.alura.comex.entity.Cliente;
import br.com.alura.comex.entity.Estado;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import static java.util.Objects.isNull;

public record NovoClienteRequest(
        @NotBlank
        @Size(min = 2)
        String nome,

        @NotBlank
        @CPF
        String cpf,

        @NotBlank
        String telefone,

        @NotBlank
        String rua,

        @NotBlank
        String numero,

        String complemento,

        @NotBlank
        String bairro,

        @NotBlank
        String cidade,

        @NotNull
        Estado estado
) {
    public Cliente toCliente() {
        var cliente = new Cliente();
        cliente.setNome(this.nome);
        cliente.setCpf(somenteDigitos(this.cpf));
        cliente.setTelefone(somenteDigitos(this.telefone));
        cliente.setRua(this.rua);
        cliente.setNumero(this.numero);
        cliente.setComplemento(this.complemento);
        cliente.setBairro(this.bairro);
        cliente.setCidade(this.cidade);
        cliente.setEstado(this.estado);

        return cliente;
    }
    private String somenteDigitos(String digitos) {
        if (isNull(digitos)) {
            return digitos;
        }

        return digitos.replaceAll("\\D", "");
    }
}
