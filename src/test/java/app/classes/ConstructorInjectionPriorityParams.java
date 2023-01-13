package app.classes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ConstructorInjectionPriorityParams {
    private AutoWiredInjectionTester autoWiredInjectionTesterClass;

    public ConstructorInjectionPriorityParams() {
    }

    @Autowired
    public ConstructorInjectionPriorityParams(AutoWiredInjectionTester autoWiredInjectionTesterClass) {
        this.autoWiredInjectionTesterClass = autoWiredInjectionTesterClass;
    }

    public AutoWiredInjectionTester getAutoWiredInjectionTesterClass() {
        return autoWiredInjectionTesterClass;
    }
}
