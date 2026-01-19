package pl.sgorski.Tagger.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import pl.sgorski.Tagger.dto.JwtResponse;
import pl.sgorski.Tagger.model.CustomOAuth2User;
import pl.sgorski.Tagger.model.User;
import pl.sgorski.Tagger.service.auth.JwtService;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtService jwtService;
    private final ObjectMapper objectMapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();
        User user = oAuth2User.user();
        String jwtToken = jwtService.generateToken(user);
        JwtResponse jwt = JwtResponse.builder()
                .token(jwtToken)
                .build();

        response.setContentType("application/json");
        response.setStatus(200);
        response.getWriter().write(objectMapper.writeValueAsString(jwt));
        response.getWriter().flush();
    }
}
