package test;

/**
 * @program: van
 * @description:
 * @author: 遥远
 * @create: 2020-05-06 20:25
 */
public class ObjectTest {

    public static void main(String[] args) throws InterruptedException {
        A a = new A();
        new Thread(() -> {
            try {
                a.test();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        Thread thread = new Thread(() -> {
            synchronized (a) {
                try {
                    a.notifyAll();
                    a.wait(1000);
                    System.out.println(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.join();
        thread.start();
    }

    static class A {
        synchronized void test() throws InterruptedException {
            System.out.println(0);
            this.wait(5000);
            System.out.println(1);
        }
    }

}
