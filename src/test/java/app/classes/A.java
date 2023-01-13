package app.classes;

import org.springframework.stereotype.Component;

@Component
public class A {
    private B classB;

    public A(B classB) {
        this.classB = classB;
    }
}
