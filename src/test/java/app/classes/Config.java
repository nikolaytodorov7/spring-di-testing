package app.classes;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

    @Bean
    public A classA() {
        return new A(classB());
    }

    @Bean
    public B classB() {
        return new B();
    }
}