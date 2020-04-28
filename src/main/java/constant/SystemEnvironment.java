package constant;

/**
 * @program: van
 * @description:
 * @author: 遥远
 * @create: 2020-04-28 14:15
 */
public class SystemEnvironment {

    public static final String USER_HOME = System.getProperty("user.home");

    public static final String PROJECT_ROOT = System.getProperty("user.dir");

    public static void main(String[] args) {
        System.out.println(USER_HOME);
        System.out.println(PROJECT_ROOT);
    }
}
