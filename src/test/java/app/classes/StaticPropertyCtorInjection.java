package app.classes;

import org.springframework.stereotype.Component;

@Component
public class StaticPropertyCtorInjection {
    private static AutoWiredInjectionTester autoWiredInjectionTesterClass;

    public StaticPropertyCtorInjection(AutoWiredInjectionTester autoWiredInjectionTesterClass) {
        this.autoWiredInjectionTesterClass = autoWiredInjectionTesterClass;
    }

    public static AutoWiredInjectionTester getAutoWiredInjectionTesterClass() {
        return autoWiredInjectionTesterClass;
    }
}
