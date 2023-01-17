package app.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigDoubleTwo {

    @Bean
    public int num() {
        return 11;
    }
}
