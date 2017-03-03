package shared.util;

import java.util.concurrent.*;

/**
 * Created by Steven's on 2017/3/3.
 */
public class AsynHelper {
    private final static ExecutorService Executor = Executors.newFixedThreadPool(100);

    public static <T> Future<T> submit(final Callable<T> task){
        Future<T> future = Executor.submit(new Callable<T>() {
            public T call() throws Exception {
                return task.call();
            }
        });
        return future;
    }

    public static int getExecuteQueueSize(){
        ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) Executor;
        return threadPoolExecutor.getQueue().size();
    }
}
