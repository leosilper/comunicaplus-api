package br.com.fiap.comunicaplus_api_main.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import br.com.fiap.comunicaplus_api_main.model.User;
import br.com.fiap.comunicaplus_api_main.service.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AuthFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        
        System.out.println("=== FILTRO DE AUTENTICAÇÃO ===");

        var header = request.getHeader("Authorization");

        if (header == null) {
            System.out.println("→ Sem header Authorization");
            filterChain.doFilter(request, response);
            return;
        }

        if (!header.startsWith("Bearer ")) {
            System.out.println("→ Token não começa com 'Bearer '");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write("""
                { "message": "Token deve começar com Bearer" }
            """);
            return;
        }

        var token = header.replace("Bearer ", "").trim();

        try {
            User user = tokenService.getUserFromToken(token);

            if (user != null) {
                var authentication = new UsernamePasswordAuthenticationToken(
                    user, null, user.getAuthorities()
                );
                SecurityContextHolder.getContext().setAuthentication(authentication);
                System.out.println("→ Usuário autenticado: " + user.getUsername());
            }

            filterChain.doFilter(request, response);

        } catch (Exception e) {
            System.err.println("→ Erro ao validar token: " + e.getMessage());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write("""
                { "message": "Token inválido ou expirado" }
            """);
        }
    }
}
