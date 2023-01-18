package app;

import app.classes.*;
import app.configs.*;
import app.contextdep.MyBean;
import app.contextdep.BeanFactoryPostProcess;
import app.contextdep.BeanLifeCycle;
import app.contextdep.BeanPostProcess;
import app.invalid.ConstructorInjectionPrimitive;
import app.invalid.ConstructorInjectionTwoAutowired;
import app.invalid.MessageServiceInvalid;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.scheduling.annotation.Async;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.*;

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
    @DisplayName("Combined injection")
    void combinedInjection() {
        CombinedInjection combinedInjection = context.getBean(CombinedInjection.class);
        assertNotNull(combinedInjection);
    }

    @Test
    @DisplayName("Properties auto-wiring")
    public void autoWiredProps() {
        PropertiesInjection propertiesInjection = context.getBean(PropertiesInjection.class);
        assertNotNull(propertiesInjection);
    }

    @Test
    @DisplayName("Properties auto-wiring field setting")
    public void autoWiredPropsField() {
        PropertiesInjection propertiesInjection = context.getBean(PropertiesInjection.class);
        assertNotNull(propertiesInjection.getAutoWiredInjectionTesterClass());
    }

    @Test
    @DisplayName("Static properties auto-wiring null")
    public void autoWiredStaticProps() {
        StaticPropertiesInjection propertiesInjection = context.getBean(StaticPropertiesInjection.class);
        assertNull(propertiesInjection.getAutoWiredInjectionTesterClass());
    }

    @Test
    @DisplayName("Static properties auto-wiring with ctor")
    public void autoWiredStaticPropsCtor() {
        StaticPropertyCtorInjection propertiesInjection = context.getBean(StaticPropertyCtorInjection.class);
        assertNotNull(propertiesInjection.getAutoWiredInjectionTesterClass());
    }

    @Test
    @DisplayName("Setter auto-wiring")
    void autoWiredSetter() {
        SetterInjection setterInjection = context.getBean(SetterInjection.class);
        assertNotNull(setterInjection);
    }

    @Test
    @DisplayName("Setter auto-wiring field setting")
    public void autoWiredSetterField() {
        SetterInjection setterInjection = context.getBean(SetterInjection.class);
        assertNotNull(setterInjection.getAutoWiredInjectionTesterClass());
    }

    @Test
    @DisplayName("Constructor auto-wiring")
    void autoWiredConstructor() {
        ConstructorInjection constructorInjection = context.getBean(ConstructorInjection.class);
        assertNotNull(constructorInjection);
    }

    @Test
    @DisplayName("Exception after having more than one @Autowired constructor")
    void autoWiredConstructorInvalidCount() {
        assertThrows(BeanCreationException.class, () -> new AnnotationConfigApplicationContext(ConstructorInjectionTwoAutowired.class));
    }

    @Test
    @DisplayName("Choose correct @Autowired constructor (no params)")
    public void autoWiredConstructorPriorityNoParams() {
        ConstructorInjectionPriorityNoParams constructorInjectionPriorityNoParams = context.getBean(ConstructorInjectionPriorityNoParams.class);
        AutoWiredInjectionTester autoWiredInjectionTesterClass = constructorInjectionPriorityNoParams.getAutoWiredInjectionTesterClass();
        assertNull(autoWiredInjectionTesterClass);
    }

    @Test
    @DisplayName("Choose correct @Autowired constructor (AutoWiredInjectionTesterClass param)")
    public void autoWiredConstructorPriorityParams() {
        ConstructorInjectionPriorityParams constructorInjectionPriorityParams = context.getBean(ConstructorInjectionPriorityParams.class);
        AutoWiredInjectionTester autoWiredInjectionTesterClass = constructorInjectionPriorityParams.getAutoWiredInjectionTesterClass();
        assertNotNull(autoWiredInjectionTesterClass);
    }

    @Test
    @DisplayName("Exception after having primitive in @Autowired constructor")
    void autoWiredConstructorPrimitive() {
        assertThrows(BeanCreationException.class, () -> new AnnotationConfigApplicationContext(ConstructorInjectionPrimitive.class));
    }

    @Test
    @DisplayName("Fields set after constructor auto-wiring")
    public void autoWiredConstructorField() {
        ConstructorInjection constructorInjection = context.getBean(ConstructorInjection.class);
        assertNotNull(constructorInjection.getAutoWiredInjectionTesterClass());
    }

    @Test
    @DisplayName("Bean indication with qualifier.")
    void qualifierCorrectBeans() {
        MessageService messageService = context.getBean(MessageService.class);
        assertNotNull(messageService);
    }

    @Test
    @DisplayName("Fields set after bean indication with qualifier")
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
    @DisplayName("Exception after not giving @Qualifier(..) annotation to fields with interface type")
    void qualifierExceptionMultipleBeans() {
        assertThrows(BeanCreationException.class, () -> new AnnotationConfigApplicationContext(MessageServiceInvalid.class));
    }

    @Test
    @DisplayName("Context initialization from class @Configuration")
    void configurationContextInitialization() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
        assertNotNull(context);
        context.close();
    }

    @Test
    @DisplayName("Context initialization from class with @Configuration getBean from Config class")
    void configurationContextInitializationReturnCorrect() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
        A aClass = context.getBean(A.class);
        assertNotNull(aClass);
        context.close();
    }

    @Test
    @DisplayName("Context initialization from two classes with @Configuration")
    void contextTwoConfigsInit() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Config.class, ConfigDoubleTwo.class);
        assertNotNull(context);
        context.close();
    }

    @Test
    @DisplayName("Context initialization from two classes with @Configuration getBean returns bean from secondly added Config class")
    void contextTwoConfigsDifReturns() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ConfigDoubleOne.class, ConfigDoubleTwo.class);
        int bean = context.getBean(int.class);
        assertEquals(bean, 11);
        context.close();
    }

    @Test
    @DisplayName("Context initialization with class parameters")
    void classesContextInitialization() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(A.class, B.class);
        assertNotNull(context);
        context.close();
    }

    @Test
    @DisplayName("Context initialization with class parameters storing beans")
    void classesContextInitializationReturnCorrect() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(A.class, B.class);
        assertNotNull(context);
        A aClass = context.getBean(A.class);
        assertNotNull(aClass);
        context.close();
    }

    @Test
    @DisplayName("Exception thrown after not adding @Component annotation to class")
    void failedInjectionNoComponent() {
        assertThrows(NoSuchBeanDefinitionException.class, () -> context.getBean(FailedInjection.class));
    }

    @Test
    @DisplayName("Values from properties file")
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

    @Test
    @DisplayName("Bean lifecycle 'InitializingBean'")
    void beanLifecycleInit() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(BeanLifeCycle.class);
        BeanLifeCycle bean = context.getBean(BeanLifeCycle.class);
        assertNotNull(bean);
        assertTrue(bean.initialized);
        context.close();
    }

    @Test
    @DisplayName("Bean lifecycle 'DisposableBean'")
    void beanLifecycleDisp() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(BeanLifeCycle.class);
        BeanLifeCycle bean = context.getBean(BeanLifeCycle.class);
        assertNotNull(bean);

        assertFalse(bean.destroyed);
        context.close();
        assertTrue(bean.destroyed);
    }

    @Test
    @DisplayName("Context shutdown hook")
    void contextShutdownHook() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(BeanLifeCycle.class);
        context.registerShutdownHook();
        BeanLifeCycle bean = context.getBean(BeanLifeCycle.class);
        assertFalse(bean.destroyed);
        context.close();
        assertTrue(bean.destroyed);
    }

    @Test
    @DisplayName("Bean post process bean instance init")
    void beanPostProcessMatch() {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(BeanPostProcess.class, TextMessage.class);
        BeanPostProcess bean = context.getBean(BeanPostProcess.class);
        assertTrue(bean.messageBean);
    }

    @Test
    @DisplayName("Bean post process bean instance init before new init")
    void beanPostProcess() {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(BeanPostProcess.class, TextMessage.class, A.class, B.class);
        BeanPostProcess bean = context.getBean(BeanPostProcess.class);
        assertFalse(bean.messageBean);
    }

    @Test
    @DisplayName("Bean factory post process bean property set from properties file")
    void beanFactoryPostProcess() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(BeanFactoryPostProcess.class);
        MyBean myBean = context.getBean(MyBean.class);
        assertEquals("Nikolay", myBean.getName());
        assertEquals("Todorov", myBean.getSurname());
        assertEquals(23, myBean.getAge());
    }

    @Test
    @DisplayName("Bean scope 'singleton' return same instance")
    void contextSingleton() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ConfigSingleton.class);
        A aClass = context.getBean(A.class);
        A aClass2 = context.getBean(A.class);
        assertEquals(aClass, aClass2);
    }

    @Test
    @DisplayName("Bean scope 'prototype' return different instances")
    void contextPrototype() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ConfigPrototype.class);
        A aClass = context.getBean(A.class);
        A aClass2 = context.getBean(A.class);
        assertNotEquals(aClass, aClass2);
    }

    @Test
    @DisplayName("Send event with publisher and receive with interface listener")
    void sendGetEventInterface() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(CustomEventPublisher.class, CustomEventListenerInterface.class);
        CustomEventPublisher publisher = context.getBean(CustomEventPublisher.class);
        String eventMessage = "Hello I am event";
        publisher.publishCustomEvent(eventMessage);
        CustomEventListenerInterface listener = context.getBean(CustomEventListenerInterface.class);
        assertEquals(eventMessage, listener.eventMessage);
    }

    @Test
    @DisplayName("Send event with publisher and receive with annotation listener")
    void sendGetEventAnnotation() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(CustomEventPublisher.class, CustomEventListenerAnnotation.class);
        CustomEventPublisher publisher = context.getBean(CustomEventPublisher.class);
        String eventMessage = "Hello I am event";
        publisher.publishCustomEvent(eventMessage);
        CustomEventListenerAnnotation listener = context.getBean(CustomEventListenerAnnotation.class);
        assertEquals(eventMessage, listener.eventMessage);
    }

    @Test
    @Async
    void asynchronizedEvent() throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(
                CustomEventPublisher.class, CustomEventListenerAnnotation.class, AsyncEventConfig.class);

        CustomEventPublisher publisher = context.getBean(CustomEventPublisher.class);
        CustomEventListenerAnnotation listener = context.getBean(CustomEventListenerAnnotation.class);

        String eventMessage = "Hello I am event";
        executorService.submit(() -> publisher.publishCustomEvent(eventMessage));
        assertEquals(eventMessage, listener.eventMessage);
    }

    @Test
    @DisplayName("ContextRefreshedEvent")
    void contextRefreshedEvent() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.registerBean(CustomEventPublisher.class);
        context.registerBean(B.class);
        context.registerBean(ContextRefreshedEventListener.class);
        context.refresh();

        ContextRefreshedEventListener listener = context.getBean(ContextRefreshedEventListener.class);
        assertTrue(listener.refreshed);
    }

    @Test
    @DisplayName("ContextStartedEvent")
    void contextStartedEvent() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.registerBean(CustomEventPublisher.class);
        context.registerBean(B.class);
        context.registerBean(ContextStartedEventListener.class);
        context.refresh();

        ContextStartedEventListener listener = context.getBean(ContextStartedEventListener.class);
        assertFalse(listener.refreshed);
        context.start();
        assertTrue(listener.refreshed);
    }

    @Test
    void chainEvents() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(
                CustomEventPublisher.class, ChainEventListener.class, CustomEventListener.class);
        CustomEventPublisher publisher = context.getBean(CustomEventPublisher.class);
        assertNotNull(publisher);
        String eventName = "ChainEvent";
        publisher.publishCustomEvent(eventName);
        ChainEventListener listener = context.getBean(ChainEventListener.class);
        assertEquals("Received chain event with message - " + eventName, listener.eventMessage);
    }
}
