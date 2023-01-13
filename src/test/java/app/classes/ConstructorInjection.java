package app.classes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ConstructorInjection {
    private AutoWiredInjectionTester autoWiredInjectionTesterClass;

    @Autowired
    public ConstructorInjection(AutoWiredInjectionTester autoWiredInjectionTesterClass) {
        this.autoWiredInjectionTesterClass = autoWiredInjectionTesterClass;
    }

    public AutoWiredInjectionTester getAutoWiredInjectionTesterClass() {
        return autoWiredInjectionTesterClass;
    }
}

