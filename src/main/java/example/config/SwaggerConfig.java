package example.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI depositServiceOpenApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("Deposit Service API")
                        .description("OpenAPI documentation for the Deposit Service application")
                        .version("v1")
                        .contact(new Contact().name("Deposit Service Team"))
                        .license(new License().name("Internal Use")));
    }
}
