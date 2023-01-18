package app.configs;

import app.classes.A;
import app.classes.B;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_PROTOTYPE;

@Configuration
public class ConfigPrototype {

    @Bean
    @Scope(SCOPE_PROTOTYPE)
    public A classA() {
        return new A(classB());
    }

    @Bean
    @Scope(SCOPE_PROTOTYPE)
    public B classB() {
        return new B();
    }
}
