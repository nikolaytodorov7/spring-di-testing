package app.contextdep;

import app.classes.Message;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

@Component
public class BeanPostProcess implements BeanPostProcessor {
    public boolean messageBean = false;

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof Message)
            System.out.printf("postProcessBeforeInitialization() in %s for %s\n", getClass().getSimpleName(), beanName);

        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof Message) {
            System.out.printf("postProcessAfterInitialization() in %s for %s\n", getClass().getSimpleName(), beanName);
            messageBean = true;
            return bean;
        }

        messageBean = false;
        return bean;
    }
}
