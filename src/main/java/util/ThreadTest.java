package util;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @program: van
 * @description:
 * @author: 遥远
 * @create: 2020-04-27 19:44
 */
public class ThreadTest {

    private static final int POOL_SIZE = 16;

    private static final ThreadFactory SHOP_COPY_FACTORY = new ThreadFactoryBuilder().setNameFormat("Thread-%d").build();
    private static final ThreadPoolExecutor EXECUTOR = new ThreadPoolExecutor(POOL_SIZE, POOL_SIZE, 0L, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<>(30), SHOP_COPY_FACTORY);

    public static void main(String[] args) {
        for (int i = 0; i < 70; i++) {
            EXECUTOR.execute(SHOP_COPY_FACTORY.newThread(() -> System.out.println("is run")));
        }
    }
}
