package br.com.alura.comex.features.pedido;

import br.com.alura.comex.entity.*;
import br.com.alura.comex.exception.NotFoundException;
import br.com.alura.comex.exception.UnprocessableEntityException;
import br.com.alura.comex.features.pedido.calculo_item.CalculadoraDeItemPedido;
import br.com.alura.comex.features.pedido.calculo_pedido.CalculadoraPedido;
import br.com.alura.comex.repository.ClienteRepository;
import br.com.alura.comex.repository.ItemPedidoRepository;
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
    private final CalculadoraDeItemPedido calculadoraDeItemPedido;
    private final CalculadoraPedido calculadoraPedido;
    private final ItemPedidoRepository itemPedidoRepository;


    PedidoService(PedidoRepository pedidoRepository,
                  ClienteRepository clienteRepository,
                  ProdutoRepository produtoRepository,
                  CalculadoraDeItemPedido calculadoraDeItemPedido,
                  CalculadoraPedido calculadoraPedido, ItemPedidoRepository itemPedidoRepository) {

        this.pedidoRepository = pedidoRepository;
        this.clienteRepository = clienteRepository;
        this.produtoRepository = produtoRepository;
        this.calculadoraDeItemPedido = calculadoraDeItemPedido;
        this.calculadoraPedido = calculadoraPedido;
        this.itemPedidoRepository = itemPedidoRepository;
    }

    Pedido novoPedido(NovoPedidoRequest novoPedidoRequest) {
        var cliente = findCliente(novoPedidoRequest);

        var itensDePedidos = calculaItensDoPedido(novoPedidoRequest);

        var pedido = calculadoraPedido.calculo(itensDePedidos, cliente);

        pedidoRepository.save(pedido);

        itensDePedidos.forEach(itemDePedido -> itemDePedido.setPedido(pedido));

        itemPedidoRepository.saveAll(itensDePedidos);

        return pedido;
    }

    private List<ItemDePedido> calculaItensDoPedido(NovoPedidoRequest novoPedidoRequest) {
        List<ItemDePedido> itensDePedidos = new ArrayList<>();
        for (ProdutoPedidoRequest produtoRequest : novoPedidoRequest.produtos()) {

            var produto = findProduto(produtoRequest);

            var quantidade = produtoRequest.quantidade();
            validaQuantidade(quantidade, produto);

            var itemPedido = calculadoraDeItemPedido.calcula(quantidade, produto);

            itensDePedidos.add(itemPedido);
        }

        return itensDePedidos;
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
