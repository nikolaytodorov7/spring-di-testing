package app.classes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StaticPropertiesInjection {
    @Autowired
    private static AutoWiredInjectionTester autoWiredInjectionTesterClass;

    public AutoWiredInjectionTester getAutoWiredInjectionTesterClass() {
        return autoWiredInjectionTesterClass;
    }

    public void setAutoWiredInjectionTesterClass(AutoWiredInjectionTester autoWiredInjectionTesterClass) {
        StaticPropertiesInjection.autoWiredInjectionTesterClass = autoWiredInjectionTesterClass;
    }
}
