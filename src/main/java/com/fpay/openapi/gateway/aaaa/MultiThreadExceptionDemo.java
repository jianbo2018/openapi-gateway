package com.fpay.openapi.gateway.aaaa;

import java.util.concurrent.*;

/**
 * @Author jianbo
 * @Date 2021/6/24 10:48 下午
 * @Version 1.0
 * @Description <br/>
 */
public class MultiThreadExceptionDemo {
    public static void main(String[] args) throws Exception {
        catchOtherThreadsThrowableInCurrentThread();
        anotherWay();
    }

    static void catchOtherThreadsThrowableInCurrentThread() {
        DemoContext currentContext = DemoContext.getCurrentContext();
        ExecutorService service = Executors.newFixedThreadPool(1);
        service.execute(() -> {
            try {
                int i = 1 / 0;
            } catch (Throwable e) {
                currentContext.put("error", e);
            }
        });
        service.execute((() -> {
            System.out.println("no exception catch");
        }));

        service.shutdown();


        Object error = currentContext.get("error");
        System.out.println(error == null ? "null!!" : ((Throwable) error).getMessage());
    }

    static void anotherWay() throws Exception {
        DemoContext currentContext = DemoContext.getCurrentContext();
        Thread.UncaughtExceptionHandler exceptionHandler = new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                synchronized (this) {
                    currentContext.put("error", e);
                    System.out.printf("t.name : [%s], error message : [%s]%n", t.getName(), e.getMessage());
                }
            }
        };
        ThreadFactory threadFactory = r -> {
            Thread thread = new Thread(r);
            thread.setUncaughtExceptionHandler(exceptionHandler);
            return thread;
        };
        ExecutorService executorService = Executors.newFixedThreadPool(1, threadFactory);
        executorService.execute(() -> {
            int i = 10 / 0;
        });
        executorService.shutdown();
        System.out.println("成功在外部线程捕获：" + ((Throwable) currentContext.get("error")).getMessage());
    }

    public static class DemoThreadPool extends ThreadPoolExecutor {
        public DemoThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory) {
            super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory);
        }
    }

    static class DemoContext extends ConcurrentHashMap<String, Object> {
        static Class<?> clazz = DemoContext.class;
        static final ThreadLocal<DemoContext> threadLocal = new ThreadLocal<DemoContext>() {
            @Override
            protected DemoContext initialValue() {
                try {
                    return (DemoContext) clazz.newInstance();
                } catch (InstantiationException | IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        };


        static DemoContext getCurrentContext() {
            return threadLocal.get();
        }
    }


}
