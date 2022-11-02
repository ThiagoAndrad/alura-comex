package br.com.alura.comex.features.autenticacao;

import br.com.alura.comex.security.TokenService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
class AutenticacaoController {

    private final AuthenticationManager authManager;

    private final TokenService tokenService;

    AutenticacaoController(AuthenticationManager authManager, TokenService tokenService) {
        this.authManager = authManager;
        this.tokenService = tokenService;
    }

    @PostMapping
    ResponseEntity<?> autentica(@RequestBody @Valid LoginRequest loginRequest) {

        var usernamePasswordAuthenticationToken = loginRequest.toUsernamePasswordAuthenticationToken();

        var authenticate = authManager.authenticate(usernamePasswordAuthenticationToken);

        var token = tokenService.gerarToken(authenticate);

        var response = new TokenResponse(token, "Bearer");

        return ResponseEntity.ok(response);
    }
}
