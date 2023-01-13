package app.invalid;

import app.classes.AutoWiredInjectionTester;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ConstructorInjectionTwoAutowired {
    private AutoWiredInjectionTester autoWiredInjectionTesterClass;

    @Autowired
    public ConstructorInjectionTwoAutowired(AutoWiredInjectionTester autoWiredInjectionTesterClass) {
        this.autoWiredInjectionTesterClass = autoWiredInjectionTesterClass;
    }

    @Autowired
    public ConstructorInjectionTwoAutowired() {
    }

    public AutoWiredInjectionTester getAutoWiredInjectionTesterClass() {
        return autoWiredInjectionTesterClass;
    }
}
