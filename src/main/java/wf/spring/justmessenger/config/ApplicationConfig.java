package wf.spring.justmessenger.config;

import jakarta.validation.Validator;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import wf.spring.justmessenger.properties.JwtProperties;

@Configuration
@EnableConfigurationProperties({JwtProperties.class})
public class ApplicationConfig {

    @Bean
    public Validator defaultValidator() {
        return new LocalValidatorFactoryBean();
    }

}
