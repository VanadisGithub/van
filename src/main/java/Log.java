import org.fusesource.jansi.Ansi;

/**
 * @program: vanadis
 * @description:
 * @author: 遥远
 * @create: 2020-04-19 00:52
 */
public class Log {

    public static void out(String msg, String level, Ansi.Color color) {
        System.out.println(Ansi.ansi().fg(color).bold().a(String.format("[%s] ", level)).reset().a(msg));
    }

    public static void info(String msg) {
        out(msg, "INFO", Ansi.Color.BLUE);
    }

    public static void suc(String msg) {
        out(msg, "SUCCESS", Ansi.Color.GREEN);
    }

    public static void err(String msg) {
        out(msg, "ERROR", Ansi.Color.RED);
    }

}
