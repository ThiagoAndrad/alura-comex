package br.com.alura.comex.features.pedido;

import br.com.alura.comex.entity.*;
import br.com.alura.comex.exception.NotFoundException;
import br.com.alura.comex.exception.UnprocessableEntityException;
import br.com.alura.comex.features.pedido.calculo_item.CalculadoraDeItemPedido;
import br.com.alura.comex.features.pedido.calculo_item.DetalheItemPedidoCalculado;
import br.com.alura.comex.features.pedido.calculo_pedido.CalculadoraPedido;
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

    private  final CalculadoraPedido calculadoraPedido;

    PedidoService(PedidoRepository pedidoRepository,
                  ClienteRepository clienteRepository,
                  ProdutoRepository produtoRepository,
                  CalculadoraDeItemPedido calculadoraDePedido,
                  CalculadoraPedido calculadoraPedido) {

        this.pedidoRepository = pedidoRepository;
        this.clienteRepository = clienteRepository;
        this.produtoRepository = produtoRepository;
        this.calculadoraDePedido = calculadoraDePedido;
        this.calculadoraPedido = calculadoraPedido;
    }

    void novoPedido(NovoPedidoRequest novoPedidoRequest) {
        var cliente = findCliente(novoPedidoRequest);

        List<DetalheItemPedidoCalculado> detalheItensPedidosCalculados = calculaItensDoPedido(novoPedidoRequest);

        var pedido = constroiPedido(cliente, detalheItensPedidosCalculados);
    }

    private List<DetalheItemPedidoCalculado> calculaItensDoPedido(NovoPedidoRequest novoPedidoRequest) {
        List<DetalheItemPedidoCalculado> detalheItensPedidosCalculados = new ArrayList<>();
        for (ProdutoPedidoRequest produtoRequest : novoPedidoRequest.produtos()) {

            var produto = findProduto(produtoRequest);

            var quantidade = produtoRequest.quantidade();
            validaQuantidade(quantidade, produto);

            var itemPedidoCalculado = calculadoraDePedido.calcula(quantidade, produto);

            detalheItensPedidosCalculados.add(itemPedidoCalculado);
        }
        return detalheItensPedidosCalculados;
    }

    private Pedido constroiPedido(Cliente cliente, List<DetalheItemPedidoCalculado> detalheItensPedidosCalculados) {
        var detalhePedidoCalculado = calculadoraPedido.calculo(detalheItensPedidosCalculados, cliente);
        var pedido = new Pedido();
        pedido.setCliente(cliente);
        pedido.setDesconto(detalhePedidoCalculado.getValorTotal());
        pedido.setTipoDesconto(detalhePedidoCalculado.getTipoDesconto());

        return pedido;
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
