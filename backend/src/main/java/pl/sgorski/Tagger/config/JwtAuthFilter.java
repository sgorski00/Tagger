package pl.sgorski.Tagger.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import pl.sgorski.Tagger.service.auth.JwtService;
import pl.sgorski.Tagger.service.auth.UserService;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final var authHeader = request.getHeader("Authorization");
        if(headerValueIsNotBearer(authHeader)) {
            filterChain.doFilter(request, response);
            return;
        }

        final var jwt = getToken(authHeader);
        final var email = jwtService.extractUsername(jwt);
        if(shouldAuthenticateUser(email)) {
            var user = userService.findByEmail(email);
            if(jwtService.isTokenValid(jwt, user)) {
                var authToken = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request, response);
    }

    private boolean shouldAuthenticateUser(String email) {
        return email != null && SecurityContextHolder.getContext().getAuthentication() == null;
    }

    private static String getToken(String header) {
        return header.substring(7);
    }

    private static boolean headerValueIsNotBearer(String header) {
        return header == null || !header.startsWith("Bearer ");
    }
}
