package app.contextdep;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

@Component
public class BeanLifeCycle implements InitializingBean, DisposableBean {
    public boolean initialized = false;
    public boolean destroyed = false;

    public void afterPropertiesSet() {
        System.out.println("Initialized....");
        initialized = true;
    }

    public void destroy() {
        System.out.println("Context destroyed...");
        destroyed = true;
    }
}
