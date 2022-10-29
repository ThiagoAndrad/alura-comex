package br.com.alura.comex.features.pedido;

import br.com.alura.comex.entity.Cliente;
import br.com.alura.comex.entity.ItemDePedido;
import br.com.alura.comex.entity.Produto;
import br.com.alura.comex.exception.NotFoundException;
import br.com.alura.comex.exception.UnprocessableEntityException;
import br.com.alura.comex.features.pedido.calculo.CalculadoraDeItemPedido;
import br.com.alura.comex.features.pedido.calculo.ItemPedidoCalculado;
import br.com.alura.comex.repository.ClienteRepository;
import br.com.alura.comex.repository.PedidoRepository;
import br.com.alura.comex.repository.ProdutoRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;

@Service
class PedidoService {

    private final PedidoRepository pedidoRepository;

    private final ClienteRepository clienteRepository;

    private final ProdutoRepository produtoRepository;

    private final CalculadoraDeItemPedido calculadoraDePedido;

    PedidoService(PedidoRepository pedidoRepository,
                  ClienteRepository clienteRepository,
                  ProdutoRepository produtoRepository,
                  CalculadoraDeItemPedido calculadoraDePedido) {

        this.pedidoRepository = pedidoRepository;
        this.clienteRepository = clienteRepository;
        this.produtoRepository = produtoRepository;
        this.calculadoraDePedido = calculadoraDePedido;
    }

    void novoPedido(NovoPedidoRequest novoPedidoRequest) {
        var cliente = findCliente(novoPedidoRequest);

        List<ItemPedidoCalculado> itensPedidosCaluculados = new ArrayList<>();
        for (ProdutoPedidoRequest produtoRequest : novoPedidoRequest.produtos()) {

            var produto = findProduto(produtoRequest);

            var quantidade = produtoRequest.quantidade();
            validaQuantidade(quantidade, produto);

            var itemPedidoCalculado = calculadoraDePedido.calcula(quantidade, produto);
        }
    }

    private static void validaQuantidade(int quantidade, Produto produto) {
        if (quantidade > produto.getQuantidadeEstoque()) {
            var message = format("Sem quantidade suficiente em esqtoque para o produto com id: %s", produto.getId());
            throw new UnprocessableEntityException(message);
        }
    }

    private Produto findProduto(ProdutoPedidoRequest produtoRequest) {
        var produtoId = produtoRequest.produtoId();
        return produtoRepository.findById(produtoId)
                .orElseThrow(() -> new NotFoundException(format("Produto com id: %s nao encontrado", produtoId)));
    }

    private Cliente findCliente(NovoPedidoRequest novoPedidoRequest) {
        var clienteId = novoPedidoRequest.clienteId();
        return clienteRepository.findById(clienteId)
                .orElseThrow(() -> new NotFoundException(format("Cliente com id: %s nao encontrado", clienteId)));
    }

}
