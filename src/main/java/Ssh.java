import java.io.IOException;

/**
 * @program: van
 * @description:
 * @author: 遥远
 * @create: 2020-04-28 13:51
 */
public class Ssh {

    public static void main(String[] args) throws IOException {
        args = "/Users/yaoyuan/bin/iterm2_login.sh 22 root@172.16.10.251 abc123".split(" ");
        Process pro = Runtime.getRuntime().exec(args);
        pro.destroy();
        System.exit(0);
    }
}
