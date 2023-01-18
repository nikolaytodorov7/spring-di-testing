package app.contextdep;

import app.classes.Message;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;


@Component
public class BeanPostProcess implements BeanPostProcessor {
    public boolean messageBean = false;

    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof Message)
            System.out.printf("postProcessBeforeInitialization() in %s for %s\n", getClass().getSimpleName(), beanName);

        return bean;
    }

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
