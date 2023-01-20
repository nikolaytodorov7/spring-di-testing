package app.values;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ValueAnnotationTest {
    @Value("5")
    public int number;

    @Value("${name}")
    public String name;

    @Value("${6:4}")
    public int defaultNum;

    @Value("#{systemProperties['prop'] ?: 'defaultVal'}")
    public String propValDefault;

    @Value("#{systemProperties['prop']}")
    public String propVal;

    @Value("#{emptyClass.val}")
    public String emptyClassVal;
}
