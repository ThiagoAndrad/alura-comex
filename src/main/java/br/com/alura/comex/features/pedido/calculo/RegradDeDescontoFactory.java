package br.com.alura.comex.features.pedido.calculo;

import org.springframework.stereotype.Component;

@Component
class RegradDeDescontoFactory {

    RegraDeDesconto create() {
        var semDesconto = new RegraSemDesconto();

        return new RegraParaMaisDeDezItens(semDesconto);
    }
}
