package app.values;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:factory.properties")
public class Config {
    @Bean
    public EmptyClass emptyClass() {
        return new EmptyClass();
    }
}
