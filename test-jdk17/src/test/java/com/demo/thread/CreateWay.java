package com.demo.thread;

import org.junit.jupiter.api.Test;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author : chenbo
 * @date : 2026-01-16
 */
public class CreateWay {

    /**
     * 推荐创建线程方式
     */
    @Test
    void bestWayCreate() throws InterruptedException {
        BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<>(2);
        /**
         * corePoolSize 核心线程数，即使线程处于空闲状态，也不会被销毁
         * maximumPoolSize 最大线程数
         *      1. 当线程数>=corePoolSize，且任务队列已满时。线程池会创建新线程来处理任务。
         *      2. 当线程数=maxPoolSize，且任务队列已满时，线程池会拒绝处理任务而抛出异常。因此任务队列需要满足使用场景。
         * keepAliveTime 空闲线程存活时间
         * unit 时间单位
         * workQueue 任务队列
         */
        ExecutorService threadPool = new ThreadPoolExecutor(1, 2, 1L, TimeUnit.SECONDS, workQueue);
        for (int i = 0; i < 5; i++) {
            Thread.sleep(100);
            threadPool.submit(() -> {
                System.out.println(Thread.currentThread().getName());
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
        }
        // 关键：优雅关闭
        threadPool.shutdown(); // 不再接受新任务
        if (!threadPool.awaitTermination(5, TimeUnit.SECONDS)) {
            threadPool.shutdownNow(); // 强制关闭（可选）
        }
    }


    // ***** 线程的基础创建方式

    static class Thread1 extends Thread {
        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName());
        }
    }

    static class Thread2 implements Runnable {
        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName());
        }
    }

    static class Thread3 implements Callable<String> {
        @Override
        public String call() {
            System.out.println(Thread.currentThread().getName());
            return "Thread3";
        }
    }

    @Test
    void baseWayCreate() throws ExecutionException, InterruptedException {
        System.out.println(Thread.currentThread().getName());
        new Thread1().start();
        new Thread(new Thread2()).start();
        Thread3 thread3 = new Thread3();
        FutureTask<String> futureTask = new FutureTask<>(thread3);
        new Thread(futureTask).start();
        System.out.println(Thread.currentThread().getName() + ":" + futureTask.get());
    }

    @Test
    void finishThread() throws InterruptedException {
        // 结束线程测试
        Thread thread1 = new Thread(() -> {
            Thread thread = Thread.currentThread();
            thread.interrupt();
            for (int i = 0; i < 50000; i++) {
                if (Thread.currentThread().isInterrupted()) {
                    System.out.println("线程已经结束，退出线程");
                    return;
                }
            }
            System.out.println("我是for下面的语句，我被执行说明线程没有真正结束");
        });
        thread1.start();
        thread1.join();
    }

    // ***** 预制线程池创建方式

    @Test
    void newSingleThreadExecutorTest() throws InterruptedException {
        // 创建一个单线程化线程池，它只能用一个工作线程来执行任务
        // 保证所有任务按照指定顺序(FIFO, LIFO, 优先级)执行。
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        for (int i = 0; i < 5; i++) {
            executorService.submit(() -> {
                System.out.println("newSingleThreadExecutor\t" + Thread.currentThread().getName());
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        // 等待子线程运行完成
        Thread.sleep(500);
    }

    @Test
    void newCachedThreadPoolTest() throws InterruptedException {
        // 缓存线程池，先查看线程池中有没有之前创建的线程，如果有则直接使用。否则就新创建一个新的线程加入线程池中
        // 常用于执行一些业务处理时间很短的任务。
        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i = 0; i < 5; i++) {
            try {
                // 模拟线程调用间隔时间，执行完的线程会释放并复用
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            executorService.submit(() -> {
                System.out.println("newCachedThreadPool\t" + Thread.currentThread().getName());
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        // 等待子线程运行完成
        Thread.sleep(500);
    }

    @Test
    void newFixedThreadPoolTest() throws InterruptedException {
        // 创建一个固定（指定）长度可重用的线程池，可以控制最大创建数，超过最大长度之后就会放入到队列进行等待。
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 25; i++) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            executorService.submit(() -> {
                System.out.println("newFixedThreadPool\t" + Thread.currentThread().getName());
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        // 等待子线程运行完成
        Thread.sleep(500);
    }
}
