package br.com.alura.comex.security;

import br.com.alura.comex.entity.Usuario;
import br.com.alura.comex.exception.NotFoundException;
import br.com.alura.comex.repository.UsuarioRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

import static java.util.Objects.isNull;

class AuthTokenFilter extends OncePerRequestFilter {

    private final TokenService tokenService;

    private final UsuarioRepository usuarioRepository;

    AuthTokenFilter(TokenService tokenService, UsuarioRepository usuarioRepository) {
        this.tokenService = tokenService;
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {


        var token = extraiToken(request);

        if (tokenService.isTokenValido(token)) {
            autentica(token);
        }

        filterChain.doFilter(request, response);
    }

    private void autentica(String token) {
        var usuarioId = tokenService.getUsuarioId(token);
        var usuario = usuarioRepository.findById(usuarioId).orElseThrow(UnauthorizedException::new);
        var usuarioAutenticado = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(usuarioAutenticado);
    }

    private String extraiToken(HttpServletRequest request) {

        var tipoDoTokenComEspaco = "Bearer ";
        var tokenComTipo = request.getHeader("Authorization");

        if (isNull(tokenComTipo)) {
            throw new ForbiddenException();
        }

        if (tokenComTipo.isBlank() || !tokenComTipo.startsWith(tipoDoTokenComEspaco)) {

            throw new UnauthorizedException();
        }

        var token = tokenComTipo.replaceAll(tipoDoTokenComEspaco, "");

        return token;
    }
}
