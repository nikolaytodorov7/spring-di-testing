package app.classes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PropertiesInjection {
    @Autowired
    private AutoWiredInjectionTester autoWiredInjectionTesterClass;

    public AutoWiredInjectionTester getAutoWiredInjectionTesterClass() {
        return autoWiredInjectionTesterClass;
    }
}
