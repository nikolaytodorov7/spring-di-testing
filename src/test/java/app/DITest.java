package app;

import app.classes.*;
import app.configs.*;
import app.invalid.ConstructorInjectionPrimitive;
import app.invalid.ConstructorInjectionTwoAutowired;
import app.invalid.MessageServiceInvalid;
import jdk.jfr.Description;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

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
    void xmlTest() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext();
        context.setConfigLocation("app/xml/config.xml");
        context.refresh();

        A aClass = context.getBean(A.class);
        assertNotNull(aClass);
    }

    @Test
    @Description("Combined injection field -> constructor -> setter")
    void combinedInjection() {
        CombinedInjection combinedInjection = context.getBean(CombinedInjection.class);
        assertNotNull(combinedInjection);
    }

    @Test
    @Description("Properties auto-wiring")
    public void autoWiredProps() {
        PropertiesInjection propertiesInjection = context.getBean(PropertiesInjection.class);
        assertNotNull(propertiesInjection);
    }

    @Test
    @Description("Properties auto-wiring field setting")
    public void autoWiredPropsField() {
        PropertiesInjection propertiesInjection = context.getBean(PropertiesInjection.class);
        assertNotNull(propertiesInjection.getAutoWiredInjectionTesterClass());
    }

    @Test
    @Description("Static properties auto-wiring null")
    public void autoWiredStaticProps() {
        StaticPropertiesInjection propertiesInjection = context.getBean(StaticPropertiesInjection.class);
        assertNull(propertiesInjection.getAutoWiredInjectionTesterClass());
    }

    @Test
    @Description("Static properties auto-wiring setter")
    public void autoWiredStaticPropsSet() {
        StaticPropertiesInjection propertiesInjection = context.getBean(StaticPropertiesInjection.class);
        propertiesInjection.setAutoWiredInjectionTesterClass(new AutoWiredInjectionTester());
        assertNotNull(propertiesInjection.getAutoWiredInjectionTesterClass());
    }

    @Test
    @Description("Setter auto-wiring")
    void autoWiredSetter() {
        SetterInjection setterInjection = context.getBean(SetterInjection.class);
        assertNotNull(setterInjection);
    }

    @Test
    @Description("Setter auto-wiring field setting")
    public void autoWiredSetterField() {
        SetterInjection setterInjection = context.getBean(SetterInjection.class);
        assertNotNull(setterInjection.getAutoWiredInjectionTesterClass());
    }

    @Test
    @Description("Constructor auto-wiring")
    void autoWiredConstructor() {
        ConstructorInjection constructorInjection = context.getBean(ConstructorInjection.class);
        assertNotNull(constructorInjection);
    }

    @Test
    @Description("Exception after having more than one @Autowired constructor")
    void autoWiredConstructorInvalidCount() {
        assertThrows(BeanCreationException.class, () -> new AnnotationConfigApplicationContext(ConstructorInjectionTwoAutowired.class));
    }

    @Test
    @Description("Choose correct @Autowired constructor (no params)")
    public void autoWiredConstructorPriorityNoParams() {
        ConstructorInjectionPriorityNoParams constructorInjectionPriorityNoParams = context.getBean(ConstructorInjectionPriorityNoParams.class);
        AutoWiredInjectionTester autoWiredInjectionTesterClass = constructorInjectionPriorityNoParams.getAutoWiredInjectionTesterClass();
        assertNull(autoWiredInjectionTesterClass);
    }

    @Test
    @Description("Choose correct @Autowired constructor (AutoWiredInjectionTesterClass param)")
    public void autoWiredConstructorPriorityParams() {
        ConstructorInjectionPriorityParams constructorInjectionPriorityParams = context.getBean(ConstructorInjectionPriorityParams.class);
        AutoWiredInjectionTester autoWiredInjectionTesterClass = constructorInjectionPriorityParams.getAutoWiredInjectionTesterClass();
        assertNotNull(autoWiredInjectionTesterClass);
    }

    @Test
    @Description("Exception after having primitive in @Autowired constructor")
    void autoWiredConstructorPrimitive() {
        assertThrows(BeanCreationException.class, () -> new AnnotationConfigApplicationContext(ConstructorInjectionPrimitive.class));
    }

    @Test
    @Description("Fields set after constructor auto-wiring")
    public void autoWiredConstructorField() {
        ConstructorInjection constructorInjection = context.getBean(ConstructorInjection.class);
        assertNotNull(constructorInjection.getAutoWiredInjectionTesterClass());
    }

    @Test
    @Description("Bean indication with qualifier.")
    void qualifierCorrectBeans() {
        MessageService messageService = context.getBean(MessageService.class);
        assertNotNull(messageService);
    }

    @Test
    @Description("Fields set after bean indication with qualifier")
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
    @Description("Exception after not giving @Qualifier(..) annotation to fields with interface type")
    void qualifierExceptionMultipleBeans() {
        assertThrows(BeanCreationException.class, () -> new AnnotationConfigApplicationContext(MessageServiceInvalid.class));
    }

    @Test
    @Description("Context initialization from class @Configuration")
    void configurationContextInitialization() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
        assertNotNull(context);
        context.close();
    }

    @Test
    @Description("Context initialization from class with @Configuration getBean from Config class")
    void configurationContextInitializationReturnCorrect() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
        A aClass = context.getBean(A.class);
        assertNotNull(aClass);
        context.close();
    }

    @Test
    @Description("Context initialization from two classes with @Configuration")
    void contextTwoConfigsInit() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Config.class, ConfigDoubleTwo.class);
        assertNotNull(context);
        context.close();
    }

    @Test
    @Description("Context initialization from two classes with @Configuration getBean returns bean from secondly added Config class")
    void contextTwoConfigsDifReturns() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ConfigDoubleOne.class, ConfigDoubleTwo.class);
        Integer bean = context.getBean(int.class);
        assertEquals(bean, 11);
        context.close();
    }

    @Test
    @Description("Configuration with 'singleton' return same instance")
    void contextSingletonConfig() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ConfigSingleton.class);
        A aClass = context.getBean(A.class);
        A aClass2 = context.getBean(A.class);
        assertEquals(aClass, aClass2);
    }

    @Test
    @Description("Configuration with 'prototype' return different instances")
    void contextPrototypeConfig() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ConfigPrototype.class);
        A aClass = context.getBean(A.class);
        A aClass2 = context.getBean(A.class);
        assertNotEquals(aClass, aClass2);
    }

    @Test
    @Description("Context initialization with class parameters")
    void classesContextInitialization() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(A.class, B.class);
        assertNotNull(context);
        context.close();
    }

    @Test
    @Description("Context initialization with class parameters storing beans")
    void classesContextInitializationReturnCorrect() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(A.class, B.class);
        assertNotNull(context);
        A aClass = context.getBean(A.class);
        assertNotNull(aClass);
        context.close();
    }

    @Test
    @Description("Exception thrown after not adding @Component annotation to class")
    void failedInjectionNoComponent() {
        assertThrows(NoSuchBeanDefinitionException.class, () -> context.getBean(FailedInjection.class));
    }

    @Test
    @Description("Values from properties file")
    void propertyValues() throws IOException {
        Properties properties = new Properties();
        properties.load(new FileInputStream("src/test/java/app/props.properties"));
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        properties.forEach((k, v) -> {
            try {
                context.registerBean(Class.forName(k.toString()), v);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        });

        context.refresh();
        Integer bean = context.getBean(Integer.class);
        assertEquals(bean, 13);
        context.close();
    }
}
