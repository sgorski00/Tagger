package pl.sgorski.Tagger.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("security.jwt")
@Data
public class JwtProperties {
    private String secretKey;
    private long expirationTime;
}
