package pl.sgorski.Tagger.service.auth;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import pl.sgorski.Tagger.exception.OAuth2MissingAttributeException;
import pl.sgorski.Tagger.model.CustomOAuth2User;
import pl.sgorski.Tagger.model.User;

@Log4j2
@Service
@RequiredArgsConstructor
public class OAuth2UserServiceImpl extends DefaultOAuth2UserService {

    private final UserService userService;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        log.debug("Loading user from OAuth2 provider: {}", userRequest.getClientRegistration().getRegistrationId());
        OAuth2User oAuth2User = super.loadUser(userRequest);
        log.debug("User logged: {}", oAuth2User);
        User user = getUserOrCreateWithOAuth2User(oAuth2User);
        log.debug("Mapped User: {}", user);
        return new CustomOAuth2User(user, oAuth2User.getAttributes());
    }

    private User getUserOrCreateWithOAuth2User(OAuth2User oAuth2User) {
        String email = getEmailOrThrow(oAuth2User);
        if(userService.existsByEmail(email)) {
            log.debug("User with email {} already exists, fetching from database.", email);
            return userService.findByEmail(email);
        } else {
            log.debug("Creating new user with email: {}", email);
            return userService.save(new User(oAuth2User));
        }
    }

    private String getEmailOrThrow(OAuth2User oAuth2User) {
        String email = oAuth2User.getAttribute("email");
        if(email == null || email.isEmpty()) {
            throw new OAuth2MissingAttributeException("email");
        }
        return email;
    }
}

