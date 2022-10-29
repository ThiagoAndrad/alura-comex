package br.com.alura.comex.features.pedido.calculo_item;

import org.springframework.stereotype.Component;

@Component
class RegraDeDescontoFactory {

    RegraDeDesconto create() {
        var semDesconto = new RegraSemDesconto();

        return new RegraParaMaisDeDezItens(semDesconto);
    }
}
