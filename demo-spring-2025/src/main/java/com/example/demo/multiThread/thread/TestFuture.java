package com.example.demo.multiThread.thread;


import com.example.demo.common.Response;
import com.google.common.base.Throwables;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;


import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class TestFuture {

    static ThreadPoolExecutor THREAD_POOL_EXECUTOR = new ThreadPoolExecutor(3, 3,
            1, TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(100),
            new ThreadFactory() {
                private static final AtomicInteger poolNumber = new AtomicInteger();

                @Override
                public Thread newThread(Runnable r) {
                    Thread t = new Thread(r, "pbr-pool-" + poolNumber.incrementAndGet());
                    if (t.isDaemon()) {
                        t.setDaemon(false);
                    }
                    if (t.getPriority() != Thread.NORM_PRIORITY) {
                        t.setPriority(Thread.NORM_PRIORITY);
                    }
                    return t;
                }
            },
            new ThreadPoolExecutor.AbortPolicy()
    );


    public static void main(String[] args) {

        System.out.println("================================================");

//        method1();
//        method2();
//        guavaDaemon();
//        listeningDecorators();


        daemon();


        System.out.println("================================================");

    }

    /**
     * https://stackoverflow.com/questions/20057497/program-does-not-terminate-immediately-when-all-executorservice-tasks-are-done
     * <p>
     * Executors.newCachedThreadPool() uses Executors.defaultThreadFactory() for its ThreadFactory. defaultThreadFactory's javadocs say that "each new thread is created as a non-daemon thread" (emphasis added). So, the threads created for the newCachedThreadPool are non-daemon. That means that they'll prevent the JVM from exiting naturally (by "naturally" I mean that you can still call System.exit(1) or kill the program to cause the JVM to halt).
     * <p>
     * The reason the app finishes at all is that each thread created within the newCachedThreadPool times out and closes itself after some time of inactivity. When the last one of them closes itself, if your application doesn't have any non-daemon threads left, it'll quit.
     * <p>
     * You can (and should) close the ExecutorService down manually via shutdown or shutdownNow.
     * <p>
     * See also the JavaDoc for Thread, which talks about daemon-ness.
     */
    private static void daemon() {

        THREAD_POOL_EXECUTOR.execute(() -> {
            System.out.println("supplyAsync");
        });

        sleep(1);

        // 有2种方式结束这个代码。
        // 1
//        System.exit(0);

        // 2.
//        THREAD_POOL_EXECUTOR.shutdown();

        /*
        所以这里有个值得注意的地方：ForkJoinPool 有特殊的处理。
        private ForkJoinPool(byte forCommonPoolOnly)
        可以看出 用的这个 DefaultCommonPoolForkJoinWorkerThreadFactory

        最终发现这个 java.lang.Thread.setDaemon super.setDaemon(true);

        这样就解释了线程是否会随着主线程而结束。
        */
    }

    private static void listeningDecorators() {
        ExecutorService executorService = Executors.newCachedThreadPool();
        ListeningExecutorService listeningExecutorService = MoreExecutors.listeningDecorator(executorService);

        ListenableFuture<String> future1 = listeningExecutorService.submit(() -> "Hello");
        ListenableFuture<String> future2 = listeningExecutorService.submit(() -> "World");

        String greeting = null;
        try {
            greeting = Futures.allAsList(future1, future2).get().stream().collect(Collectors.joining(" "));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        System.out.println(greeting);
    }

    /**
     * https://www.baeldung.com/thread-pool-java-and-guava
     */
    private static void guavaDaemon() {
        ExecutorService executorService1 = Executors.newFixedThreadPool(5);

        /**
         * 添加下面的这段代码以后，等于添加了监听器，防止守护线程没法结束。
         */
        ExecutorService executorService2 = MoreExecutors.getExitingExecutorService((ThreadPoolExecutor) executorService1, 10, TimeUnit.SECONDS);

        executorService1.submit(() -> {
            long i = 0;
            while (true) {
                sleep(1);
                System.out.println(i++);
            }
        });
    }

    private static void method1() {

        CompletableFuture<Response> responseCompletableFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println("supplyAsync");
            return Response.builder().message("Hello").build();
        }, THREAD_POOL_EXECUTOR);

        // THREAD_POOL_EXECUTOR


        try {

            System.out.println(responseCompletableFuture.get());

            THREAD_POOL_EXECUTOR.shutdown();

        } catch (Exception e) {
            System.out.println(Throwables.getStackTraceAsString(e));
        }
    }

    private static void method2() {

        CompletableFuture<Response> responseCompletableFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println("supplyAsync");
            return Response.builder().message("Hello").build();
        }, Executors.newWorkStealingPool());

        // THREAD_POOL_EXECUTOR

        CompletableFuture<Void> voidCompletableFuture = responseCompletableFuture.whenComplete((response, throwable) -> {
            System.out.println("whenComplete " + response);
            if (throwable != null) {
                throwable.printStackTrace();
            }
        }).thenAccept(x -> System.out.println("thenAccept: " + x));

        try {
            Void join = voidCompletableFuture.join();

            System.out.println(responseCompletableFuture.join());

        } catch (Exception e) {
            System.out.println(Throwables.getStackTraceAsString(e));
        }
    }

    public static void sleep(int time) {
        try {
            TimeUnit.SECONDS.sleep(time);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }

}
