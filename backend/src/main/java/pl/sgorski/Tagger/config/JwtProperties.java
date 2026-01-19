package pl.sgorski.Tagger.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("security.jwt")
public record JwtProperties(String secretKey, long expirationTime) {
}
