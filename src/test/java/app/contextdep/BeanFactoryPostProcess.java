package app.contextdep;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.GenericBeanDefinition;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class BeanFactoryPostProcess implements BeanFactoryPostProcessor {
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        GenericBeanDefinition definition = new GenericBeanDefinition();
        definition.setBeanClass(MyBean.class);
        Properties properties = loadProperties();
        properties.forEach((k, v) -> definition.getPropertyValues().addPropertyValue(k.toString(), v));
        DefaultListableBeanFactory bf = (DefaultListableBeanFactory) beanFactory;
        bf.registerBeanDefinition("MyBean", definition);
    }

    private Properties loadProperties() {
        Properties properties = new Properties();
        String path = "src/test/resources/factory.properties"; //todo remove hardcode path
        try {
            properties.load(new FileInputStream(path));
        } catch (IOException e) {
            throw new RuntimeException(String.format("File with path '%s' does not exist", path));
        }

        return properties;
    }
}
