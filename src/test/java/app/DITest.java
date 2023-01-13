package app;

import app.classes.*;
import app.invalid.ConstructorInjectionTwoAutowired;
import app.invalid.ConstructorInjectionPrimitive;
import app.invalid.MessageServiceInvalid;
import jdk.jfr.Description;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.junit.jupiter.api.Assertions.*;

public class DITest {
    private static AnnotationConfigApplicationContext context;

    @BeforeAll
    public static void init() {
        context = new AnnotationConfigApplicationContext("app.classes");
    }

    @AfterAll
    public static void finish() {
        context.close();
    }

    @Test
    @Description("Tests if properties auto-wiring works")
    public void autoWiredProps() {
        AutoWiredInjectionTester tester = context.getBean(AutoWiredInjectionTester.class);
        PropertiesInjection propertiesInjection = context.getBean(PropertiesInjection.class);
        assertAll(
                () -> assertNotNull(tester),
                () -> assertNotNull(propertiesInjection));
    }

    @Test
    @Description("Tests if properties auto-wiring sets field")
    public void autoWiredPropsField() {
        PropertiesInjection propertiesInjection = context.getBean(PropertiesInjection.class);
        assertNotNull(propertiesInjection.getAutoWiredInjectionTesterClass());
    }

    @Test
    @Description("Tests if setter auto-wiring works")
    void autoWiredSetter() {
        AutoWiredInjectionTester tester = context.getBean(AutoWiredInjectionTester.class);
        SetterInjection setterInjection = context.getBean(SetterInjection.class);
        assertAll(
                () -> assertNotNull(tester),
                () -> assertNotNull(setterInjection));
    }

    @Test
    @Description("Tests if setter auto-wiring sets field")
    public void autoWiredSetterField() {
        SetterInjection setterInjection = context.getBean(SetterInjection.class);
        assertNotNull(setterInjection.getAutoWiredInjectionTesterClass());
    }

    @Test
    @Description("Tests if constructor auto-wiring works")
    void autoWiredConstructor() {
        AutoWiredInjectionTester tester = context.getBean(AutoWiredInjectionTester.class);
        ConstructorInjection constructorInjection = context.getBean(ConstructorInjection.class);
        assertAll(
                () -> assertNotNull(tester),
                () -> assertNotNull(constructorInjection));
    }

    @Test
    @Description("Tests if throws correct exception after having more than one @Autowired constructor")
    void autoWiredConstructorInvalidCount() {
        assertThrows(BeanCreationException.class, () -> new AnnotationConfigApplicationContext(ConstructorInjectionTwoAutowired.class));
    }

    @Test
    @Description("Tests if chooses correct @Autowired constructor (no params)")
    public void autoWiredConstructorPriorityNoParams() {
        ConstructorInjectionPriorityNoParams constructorInjectionPriorityNoParams = context.getBean(ConstructorInjectionPriorityNoParams.class);
        AutoWiredInjectionTester autoWiredInjectionTesterClass = constructorInjectionPriorityNoParams.getAutoWiredInjectionTesterClass();
        assertNull(autoWiredInjectionTesterClass);
    }

    @Test
    @Description("Tests if chooses correct @Autowired constructor (AutoWiredInjectionTesterClass param)")
    public void autoWiredConstructorPriorityParams() {
        ConstructorInjectionPriorityParams constructorInjectionPriorityParams = context.getBean(ConstructorInjectionPriorityParams.class);
        AutoWiredInjectionTester autoWiredInjectionTesterClass = constructorInjectionPriorityParams.getAutoWiredInjectionTesterClass();
        assertNotNull(autoWiredInjectionTesterClass);
    }

    @Test
    @Description("Tests if throws correct exception after having primitive in @Autowired constructor")
    void autoWiredConstructorPrimitive() {
        assertThrows(BeanCreationException.class, () -> new AnnotationConfigApplicationContext(ConstructorInjectionPrimitive.class));
    }

    @Test
    @Description("Tests if constructor auto-wiring sets field")
    public void autoWiredConstructorField() {
        ConstructorInjection constructorInjection = context.getBean(ConstructorInjection.class);
        assertNotNull(constructorInjection.getAutoWiredInjectionTesterClass());
    }

    @Test
    @Description("Tests if correct bean indication with qualifier works.")
    void qualifierCorrectBeans() {
        MessageService messageService = context.getBean(MessageService.class);
        assertNotNull(messageService);
    }

    @Test
    @Description("Tests if correct fields are set after bean indication with qualifier")
    void qualifierCorrectBeansFields() {
        MessageService messageService = context.getBean(MessageService.class);
        Message videoMessage = messageService.getVideoMessage();
        Message textMessage = messageService.getTextMessage();
        assertAll(
                () -> assertInstanceOf(Message.class, videoMessage),
                () -> assertInstanceOf(VideoMessage.class, videoMessage),
                () -> assertInstanceOf(Message.class, textMessage),
                () -> assertInstanceOf(TextMessage.class, textMessage));
    }

    @Test
    @Description("Tests if throws correct exception after not giving @Qualifier(..) annotation to fields with interface type")
    void qualifierExceptionMultipleBeans() {
        assertThrows(BeanCreationException.class, () -> new AnnotationConfigApplicationContext(MessageServiceInvalid.class));
    }

    @Test
    @Description("Tests context initialization with parameter class with @Configuration annotation")
    void configurationContextInitialization() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        assertNotNull(context);
        context.register(Config.class);
        context.refresh();
        assertNotNull(context);
        context.close();
    }

    @Test
    @Description("Tests if context initialization with parameter class with @Configuration annotation stores beans from Config class")
    void configurationContextInitializationReturnCorrect() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(Config.class);
        context.refresh();
        A aClass = context.getBean(A.class);
        assertNotNull(aClass);
        context.close();
    }

    @Test
    @Description("Tests context initialization with class parameters")
    void classesContextInitialization() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(A.class, B.class);
        assertNotNull(context);
        context.close();
    }

    @Test
    @Description("Tests if context initialization with class parameters stores beans")
    void classesContextInitializationReturnCorrect() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(A.class, B.class);
        assertNotNull(context);
        A aClass = context.getBean(A.class);
        assertNotNull(aClass);
        context.close();
    }

    @Test
    @Description("Tests if throws correct exception after not adding @Component annotation to class")
    void failedInjectionNoComponent() {
        assertThrows(NoSuchBeanDefinitionException.class, () -> context.getBean(FailedInjection.class));
    }
}
