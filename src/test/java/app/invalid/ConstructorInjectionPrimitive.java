package app.invalid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ConstructorInjectionPrimitive {
    private int num;

    @Autowired
    public ConstructorInjectionPrimitive(int num) {
        this.num = num;
    }
}
