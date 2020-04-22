/**
 * @program: vanadis
 * @description:
 * @author: 遥远
 * @create: 2020-04-19 00:52
 */
public class Log {

    public static void info(String msg) {
        System.out.println(String.format("\u001b[34m[INFO]\u001b[37m:%s", msg));
    }

    public static void suc(String msg) {
        System.out.println(String.format("\u001b[34m[SUCCESS]\u001b[37m:%s", msg));
    }

    public static void err(String msg) {
        System.out.println(String.format("\u001b[34m[ERROR]\u001b[37m:%s", msg));
    }

}
