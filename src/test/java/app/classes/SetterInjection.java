package app.classes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SetterInjection {
    private AutoWiredInjectionTester autoWiredInjectionTesterClass;

    @Autowired
    public void setAutoWiredInjectionTester(AutoWiredInjectionTester autoWiredInjectionTesterClass) {
        this.autoWiredInjectionTesterClass = autoWiredInjectionTesterClass;
    }

    public AutoWiredInjectionTester getAutoWiredInjectionTesterClass() {
        return autoWiredInjectionTesterClass;
    }
}
