package app.classes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ConstructorInjectionPriorityNoParams {
    private AutoWiredInjectionTester autoWiredInjectionTesterClass;

    @Autowired
    public ConstructorInjectionPriorityNoParams() {
    }

    public ConstructorInjectionPriorityNoParams(AutoWiredInjectionTester autoWiredInjectionTesterClass) {
        this.autoWiredInjectionTesterClass = autoWiredInjectionTesterClass;
    }

    public AutoWiredInjectionTester getAutoWiredInjectionTesterClass() {
        return autoWiredInjectionTesterClass;
    }
}