package app.configs;

import app.classes.A;
import app.classes.B;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class ConfigSingleton {

    @Bean
    @Scope(value = "singleton")
    public A classA() {
        return new A(classB());
    }

    @Bean
    @Scope(value = "singleton")
    public B classB() {
        return new B();
    }
}
