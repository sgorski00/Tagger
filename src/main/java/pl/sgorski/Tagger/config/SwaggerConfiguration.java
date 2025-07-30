package pl.sgorski.Tagger.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition
public class SwaggerConfiguration {

    @Bean
    public OpenAPI customOpenApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("Tagger API")
                        .version("1.0.4")
                        .description("The Tagger API is AI based application, which allows you to generate tags, titles and descriptions for products.")
                        .contact(new Contact()
                                .email("sebastiangorski00@gmail.com")
                                .name("Sebastian Górski")
                        )
                );
    }
}
