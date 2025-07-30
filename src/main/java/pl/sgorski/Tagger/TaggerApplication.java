package pl.sgorski.Tagger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import pl.sgorski.Tagger.config.JwtProperties;

@SpringBootApplication
@EnableConfigurationProperties(JwtProperties.class)
public class TaggerApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaggerApplication.class, args);
	}

}
