package test;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @program: van
 * @description:
 * @author: 遥远
 * @create: 2020-05-06 20:25
 */
@Slf4j
public class ThreadTest {

    public static void main(String[] args) throws InterruptedException {

        ReentrantLock lock = new ReentrantLock();

        ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat("yy-%d").build();

        ExecutorService executorService = new ThreadPoolExecutor(10, 10, 0, TimeUnit.SECONDS, new LinkedBlockingQueue<>(10), threadFactory, new ThreadPoolExecutor.AbortPolicy());

        for (int i = 0; i < 10; i++) {
            final int finalI = i;
            executorService.execute(() -> {
                long start = System.currentTimeMillis();
                log.info(String.valueOf(finalI));
                test(lock, finalI);
                System.out.println(System.currentTimeMillis() - start);
            });
        }
    }

    @SneakyThrows
    static void test(ReentrantLock lock, Integer i) {
        System.out.println(i + "开始");
        lock.lock();
        try {
            System.out.println(i + "开始了");
            Thread.sleep(3000);
            System.out.println(i);
        } finally {
            lock.unlock();
        }
    }

    static class A {
        synchronized void test() throws InterruptedException {
            System.out.println(0);
            this.wait(5000);
            System.out.println(1);
        }
    }

}
