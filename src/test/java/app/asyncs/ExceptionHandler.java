package app.asyncs;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Component
public class ExceptionHandler implements AsyncUncaughtExceptionHandler {
    Throwable throwable;

    @Override
    public void handleUncaughtException(Throwable ex, Method method, Object... params) {
        throwable = ex;
        System.out.println("Exception message - " + ex.getMessage());
        System.out.println("Method name - " + method.getName());
        for (Object param : params) {
            System.out.println("Parameter value - " + param);
        }
    }
}