package com.rachidy.sassgestionstockapp.properties;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

@Getter
@Configuration
@ConfigurationProperties(prefix = "app.jwt")
@Validated
public class JwtProperties {
    @NotNull  private String privateKeyPath;
    @NotNull private String publicKeyPath;
    @NotNull  private Long expirationDate;
}
