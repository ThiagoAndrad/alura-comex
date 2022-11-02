package br.com.alura.comex.features.autenticacao;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import javax.validation.constraints.NotBlank;

record LoginRequest(

        @NotBlank
        String email,

        @NotBlank
        String senha
) {
        UsernamePasswordAuthenticationToken toUsernamePasswordAuthenticationToken() {
                return new UsernamePasswordAuthenticationToken(email, senha);
        }
}
