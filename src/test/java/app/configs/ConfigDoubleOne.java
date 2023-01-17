package app.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigDoubleOne {

    @Bean
    public int num() {
        return 12;
    }
}
