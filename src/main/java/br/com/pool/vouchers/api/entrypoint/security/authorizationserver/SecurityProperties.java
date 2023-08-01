package br.com.pool.vouchers.api.entrypoint.security.authorizationserver;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

@Component
@Getter
@Setter
@Validated
@ConfigurationProperties("voucher.auth")
public class SecurityProperties {

    @NotBlank
    private String providerUrl;

}
