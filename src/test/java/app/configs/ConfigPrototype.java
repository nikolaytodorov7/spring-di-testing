package app.configs;

import app.classes.A;
import app.classes.B;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class ConfigPrototype {

    @Bean
    @Scope(value = "prototype")
    public A classA() {
        return new A(classB());
    }

    @Bean
    @Scope(value = "prototype")
    public B classB() {
        return new B();
    }
}
