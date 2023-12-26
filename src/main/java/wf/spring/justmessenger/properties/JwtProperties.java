package wf.spring.justmessenger.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("spring.security.jwt")
@Getter
@Setter
public class JwtProperties {

    private String secretKey = "secret_key_Ez5CCYmLFTc24474cMpI37ORHYsVGppo";

    private int expirationDays = 7;

    private String issuer = "spring-app";

}
