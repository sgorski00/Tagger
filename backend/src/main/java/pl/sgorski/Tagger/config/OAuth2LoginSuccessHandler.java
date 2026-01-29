package pl.sgorski.Tagger.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import pl.sgorski.Tagger.model.CustomOAuth2User;
import pl.sgorski.Tagger.service.auth.JwtService;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

    @Value("${tagger.frontend.url}")
    private String frontendUrl;
    private final JwtService jwtService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        var oAuth2User = (CustomOAuth2User) authentication.getPrincipal();
        var user = oAuth2User.user();
        var jwt = jwtService.generateToken(user);

        var redirectUrl = String.format("%s/oauth2/success?token=%s", frontendUrl, jwt);
        response.sendRedirect(redirectUrl);
    }
}
