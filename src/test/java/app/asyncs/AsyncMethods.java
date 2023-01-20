package app.asyncs;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

import java.util.concurrent.Future;

@Component
public class AsyncMethods {

    @Async
    public void asyncMethodWithVoidReturnType() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Async
    public Future<String> asyncMethodWithReturnType(String str) {
        try {
            Thread.sleep(5000);
            return new AsyncResult<>(str);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Async
    public Future<String> asyncMethodException() throws IllegalStateException, InterruptedException {
        Thread.sleep(5000);

        throw new IllegalStateException();
    }

    @Async("threadPoolTaskExecutor")
    public Future<String> asyncMethodExceptionMyExecutor() throws IllegalStateException, InterruptedException {
        Thread.sleep(5000);

        throw new IllegalArgumentException();
    }
}
