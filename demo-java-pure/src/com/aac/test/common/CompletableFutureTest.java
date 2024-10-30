package com.aac.test.common;

import com.aac.test.utils.CommonUtils;

import java.io.IOException;
import java.util.concurrent.*;

public class CompletableFutureTest {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
//        testException();
//        testAsync();
//        testExceptionCatch();
//        testCreateNewStage();
//        testException2();

        System.out.println("==============");


        meituan_blog();

    }


    private static void meituan_blog() {
        /**
         * 美团的技术博客
         * https://tech.meituan.com/2022/05/12/principles-and-practices-of-completablefuture.html
         */
        ExecutorService executor = Executors.newFixedThreadPool(5);
        //1、使用runAsync或supplyAsync发起异步调用
        CompletableFuture<String> cf1 = CompletableFuture.supplyAsync(() -> {
            System.out.println("cf1");
            return "result1";
        }, executor);
        //2、CompletableFuture.completedFuture()直接创建一个已完成状态的CompletableFuture
        CompletableFuture<String> cf2 = CompletableFuture.completedFuture("result2");

        CommonUtils.sleepMs(1);

        System.out.println(1);
        //3、先初始化一个未完成的CompletableFuture，然后通过complete()、completeExceptionally()，完成该CompletableFuture
        CompletableFuture<String> cf = new CompletableFuture<>();
        cf.complete("success");

        // 多结果以及多结果的提取
        CompletableFuture<String> cf3 = CompletableFuture.completedFuture("cf3");
        CompletableFuture<String> cf4 = CompletableFuture.completedFuture("cf4");
        CompletableFuture<String> cf5 = CompletableFuture.completedFuture("cf5");

        CompletableFuture<Void> cf6 = CompletableFuture.allOf(cf3, cf4, cf5);
        CompletableFuture<String> result = cf6.thenApply(v -> {
            //这里的join并不会阻塞，因为传给thenApply的函数是在CF3、CF4、CF5全部完成时，才会执行 。
            String result3 = cf3.join();
            String result4 = cf4.join();
            String result5 = cf5.join();
            //根据result3、result4、result5组装最终result;
            return String.join(",", result3, result4, result5);
        });


        /**
         * 代码执行在哪个线程上？
         */
        ExecutorService threadPool1 = new ThreadPoolExecutor(10, 10, 0L, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(100));

        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> {
            System.out.println("supplyAsync 执行线程：" + Thread.currentThread().getName());
            //业务操作
            return "";
        }, threadPool1);

        //此时，如果future1中的业务操作已经执行完毕并返回，则该thenApply直接由当前main线程执行；否则，将会由执行以上业务操作的threadPool1中的线程执行。
        future1.thenApply(value -> {
            System.out.println("thenApply 执行线程：" + Thread.currentThread().getName());
            return value + "1";
        });

        //使用ForkJoinPool中的共用线程池CommonPool
        future1.thenApplyAsync(value -> {
            //do something
            return value + "1";
        });

        //使用指定线程池
        future1.thenApplyAsync(value -> {
            //do something
            return value + "1";
        }, threadPool1);


    }

    /**
     * 如 doGet 代码块所示，doGet方法第三行通过supplyAsync向threadPool1请求线程，并且内部子任务又向threadPool1请求线程。threadPool1大小为10，当同一时刻有10个请求到达，则threadPool1被打满，子任务请求线程时进入阻塞队列排队，但是父任务的完成又依赖于子任务，这时由于子任务得不到线程，父任务无法完成。主线程执行cf1.join()进入阻塞状态，并且永远无法恢复。
     * <p>
     * 为了修复该问题，需要将父任务与子任务做线程池隔离，两个任务请求不同的线程池，避免循环依赖导致的阻塞。
     * <p>
     * <p>
     * 4.2.3 异步RPC调用注意不要阻塞IO线程池
     * 服务异步化后很多步骤都会依赖于异步RPC调用的结果，这时需要特别注意一点，如果是使用基于NIO（比如Netty）的异步RPC，则返回结果是由IO线程负责设置的，即回调方法由IO线程触发，CompletableFuture同步回调（如thenApply、thenAccept等无Async后缀的方法）如果依赖的异步RPC调用的返回结果，那么这些同步回调将运行在IO线程上，而整个服务只有一个IO线程池，这时需要保证同步回调中不能有阻塞等耗时过长的逻辑，否则在这些逻辑执行完成前，IO线程将一直被占用，影响整个服务的响应。
     */
    public Object doGet() {
        ExecutorService threadPool1 = new ThreadPoolExecutor(10, 10, 0L, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(100));

        CompletableFuture<String> cf1 = CompletableFuture.supplyAsync(() -> {
            //do sth
            return CompletableFuture.supplyAsync(() -> {
                System.out.println("child");
                return "child";
            }, threadPool1).join();//子任务

        }, threadPool1);

        return cf1.join();
    }


    public CompletableFuture<String> getCancelTypeAsync(long orderId) {
        CompletableFuture<String> remarkResultFuture = CompletableFuture.completedFuture("");//业务方法，内部会发起异步rpc调用

        return remarkResultFuture
                .thenApply(result -> {
                    //这里增加了一个回调方法thenApply，如果发生异常thenApply内部会通过new CompletionException(throwable) 对异常进行包装
                    //这里是一些业务操作
                    return result.toUpperCase();
                })
                .exceptionally(err -> {
                    //通过exceptionally 捕获异常，这里的err已经被thenApply包装过，因此需要通过Throwable.getCause()提取异常
                    /**
                     * 上面代码中用到了一个自定义的工具类 extractRealException ，用于CompletableFuture的异常提取，在使用CompletableFuture做异步编程时，可以直接使用该工具类处理异常。实现代码如下：
                     */
                    CommonUtils.print("WmOrderRemarkService.getCancelTypeAsync Exception orderId={}", orderId, CommonUtils.extractRealException(err));
                    return "Error";
                });
    }

    private static void testException2() {
        CompletableFuture<String> completableFuture = new CompletableFuture<>();

        CompletableFuture<String> cf1 = completableFuture.thenCompose(v -> {
                    if (v != null) {
                        v = "new message 1";
//                        throw new RuntimeException("error 1");
                    }
                    return CompletableFuture.completedFuture(v.toUpperCase());
                })
                .handle((v, t) -> {
                    System.out.println("handle 1 " + v);
                    if (t != null) {
                        System.out.println("handle 1 " + t.getMessage());
                        return "handle 1";
                    }
                    return v;
                });
//                .exceptionally(e -> "exceptionally 1");

        CompletableFuture<String> cf2 = cf1.thenCompose(v -> {

                    System.out.println(v);
                    return CompletableFuture.completedFuture(v + " 2");
                })
                .exceptionally(e -> "exceptionally 2");


        completableFuture.complete("message 1");
    }

    private static void testCreateNewStage() throws InterruptedException, ExecutionException {
        CompletableFuture<String> completableFuture = new CompletableFuture<>();

        CompletableFuture<String> completableFuture2 = completableFuture.thenApplyAsync(v -> {
            CommonUtils.print("thenApply 1 " + v.toUpperCase());
//            throw new RuntimeException("error 1");
            return v.toUpperCase();
        }).exceptionally(e -> {
            CommonUtils.print("exceptionally 1 " + e.getMessage());
            return "exceptionally 1";
        });

        completableFuture2.thenApplyAsync(v -> {
            CommonUtils.print("thenApply 2 " + v.toUpperCase());
//            throw new RuntimeException("error 1");
            return v.toUpperCase() + "[2]";
        }).exceptionally(e -> {
            CommonUtils.print("exceptionally 2 " + e.getMessage());
            return "exceptionally 2";
        });

        completableFuture.complete("message 1");
//        completableFuture2.complete("message 2");
//        completableFuture2.complete("message 3");

        CommonUtils.print("completableFuture: " + completableFuture.get());
        CommonUtils.print("completableFuture: " + completableFuture.join());

        CommonUtils.print("completableFuture2: " + completableFuture2.get());
        CommonUtils.print("completableFuture2: " + completableFuture2.join());
    }


    /**
     * 当多次进行了 whenComplete 的时候，容易出现丢失 exception 的问题。
     * https://stackoverflow.com/questions/49230980/does-completionstage-always-wrap-exceptions-in-completionexception
     * 这里可以看出包装的是不合理的。因为存在第一次没有包装，第二次有的可能。
     */
    private static void testExceptionCatch() {
        CompletableFuture<Void> root = new CompletableFuture<>();

        CompletableFuture<Void> child = root.whenComplete((v, t) -> {
            System.out.println(t.getClass()); // class java.io.IOException
        });

        child.whenComplete((v, t) -> {
            System.out.println(t.getClass()); // class java.util.concurrent.CompletionException
        });

        root.completeExceptionally(new IOException("blow it up"));
    }

    private static void testAsync() {
        CompletionStage<Integer> currentStage = CompletableFuture.completedFuture(0);

        currentStage = currentStage.thenCompose(p -> {
            System.out.println(Thread.currentThread().getName() + " " + p);
            return CompletableFuture.completedFuture(p + 1);
        });

        ExecutorService executorService;

        executorService = Executors.newSingleThreadExecutor();
        executorService = ForkJoinPool.commonPool();

        CompletionStage<Integer> stage = currentStage.thenComposeAsync(p -> {
            CommonUtils.print(p);
            return CompletableFuture.completedFuture(p + 1);
        }, executorService);


        CompletionStage<CompletableFuture<Integer>> stage2 = stage.thenApplyAsync(p -> {

            CommonUtils.print(p);

            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            CommonUtils.print(p + " after sleep");
            return CompletableFuture.completedFuture(p + 1);
        }, executorService);

//        System.out.println("Final result: " + completableFutureCompletionStage.toCompletableFuture().join());
    }

    private static void testException() {
        CompletionStage<Integer> currentStage = CompletableFuture.completedFuture(0);

        currentStage = currentStage.thenCompose(p -> {
            System.out.println(Thread.currentThread().getName() + " " + p);
            return CompletableFuture.completedFuture(p + 1);
        });

        currentStage = currentStage.thenCompose(p -> {
            System.out.println(Thread.currentThread().getName() + " exception " + p);

            if (p < 999) {
                throw new RuntimeException("[" + p + "]");
            }

            return CompletableFuture.completedFuture(p + 1);
        });

        currentStage = currentStage.thenCompose(p -> {
            System.out.println(Thread.currentThread().getName() + " " + p);
            return CompletableFuture.completedFuture(p + 1);
        }).handle((result, throwable) -> {
            if (throwable != null) {
//                throwable.printStackTrace();
            }
            return -1;
        });

        System.out.println("=================== 1");

        currentStage = currentStage.thenApply(p -> p >= 3 ? 100 : 200);

        System.out.println("=================== 2");
        currentStage = currentStage.thenApply(p -> p == 100 ? p + 1 : p);
        currentStage = currentStage.thenApply(p -> p == 200 ? p + 2 : p);

        System.out.println("Final result: " + currentStage.toCompletableFuture().join());
    }

}
