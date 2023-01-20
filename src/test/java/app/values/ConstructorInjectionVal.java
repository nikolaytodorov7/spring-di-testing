package app.values;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ConstructorInjectionVal {
    public String name;

    public ConstructorInjectionVal(@Value("${name}")String name) {
        this.name = name;
    }
}
