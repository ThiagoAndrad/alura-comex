package br.com.alura.comex.security;

import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static java.util.Objects.isNull;

public class AuthTokenFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {


        var token = extraiToken(request);

        //TODO validar token

        filterChain.doFilter(request, response);
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
