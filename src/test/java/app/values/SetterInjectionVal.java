package app.values;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SetterInjectionVal {
    public String surname;
    public int age;

    @Value("${age}")
    public void setMethodInject(int age) {
        this.age = age;
    }

    @Autowired
    public void setParamInject(@Value("${surname}") String surname) {
        this.surname = surname;
    }
}
