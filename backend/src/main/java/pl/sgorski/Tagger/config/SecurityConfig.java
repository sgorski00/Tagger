package pl.sgorski.Tagger.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import pl.sgorski.Tagger.service.auth.OAuth2UserServiceImpl;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

  private final OAuth2UserServiceImpl oAuth2UserService;
  private final OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler;
  private final OAuth2ExceptionAuthenticationEntryPoint oAuth2ExceptionAuthenticationEntryPoint;
  private final JwtAuthFilter jwtAuthFilter;

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    return http.authorizeHttpRequests(auth -> auth
        .requestMatchers("/api/history/**").authenticated()
        .requestMatchers("/api/tags/**", "/swagger-ui/**", "/graphql/**", "/login/**").permitAll()
        .anyRequest().denyAll()
      )
      .oauth2Login(oauth2 -> oauth2
        .userInfoEndpoint(userInfo -> userInfo
          .userService(oAuth2UserService)
        )
        .successHandler(oAuth2LoginSuccessHandler)
      )
      .exceptionHandling(exceptions -> exceptions
        .authenticationEntryPoint(oAuth2ExceptionAuthenticationEntryPoint))
      .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
      .sessionManagement(session -> session
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
      )
      .cors(cors -> cors.configure(http))
      .csrf(AbstractHttpConfigurer::disable)
      .build();
  }
}
