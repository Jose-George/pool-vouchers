package br.com.pool.vouchers.api.entrypoint.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfiguration {
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI().info(new Info()
            .title("Vouchers - REST API")
            .description("""
                API REST do serviço de \
                Pool Vouchers
                """)
            .version("1.0.0")
            .license(new License()
                .name("Vouchers")
                .url("https://vouchers-challenge.com")));
    }
}
