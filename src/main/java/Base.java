import org.fusesource.jansi.Ansi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * @program: vanadis
 * @description:
 * @author: 遥远
 * @create: 2020-04-19 00:34
 */
class Base {

    public static List<String> exit = Arrays.asList("0", "exit","q");

    private final static String LOGO = " __     __                    _ _     \n" +
            " \\ \\   / /_ _ _ __   __ _  __| (_)___ \n" +
            "  \\ \\ / / _` | '_ \\ / _` |/ _` | / __|\n" +
            "   \\ V / (_| | | | | (_| | (_| | \\__ \\\n" +
            "    \\_/ \\__,_|_| |_|\\__,_|\\__,_|_|___/  v1.0.0\n" +
            "                                      ";

    public static void welcome() {
        Ansi.Color color = Ansi.Color.values()[new Random().nextInt(9)];
        System.out.println(Ansi.ansi().fg(color).bold().a(LOGO).reset());
    }

    public static void checkIsExit(String input) {
        if (exit.contains(input)) {
            Log.info("Vanadis exit ...");
            System.exit(0);
        }
    }

    public static void exec(String[] command) {
        try {
            Process pro = Runtime.getRuntime().exec(command);
            pro.waitFor();
            InputStream in = pro.getInputStream();
            BufferedReader read = new BufferedReader(new InputStreamReader(in));
            String result;
            while ((result = read.readLine()) != null) {
                Log.info(result);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void addAlias() {
        try {
            String userHomeBash = System.getProperty("user.home") + "/.bashrc";
            String alias = "/Users/yaoyuan/.m2/repository/vanadis/van/1.0-SNAPSHOT/van-1.0-SNAPSHOT.jar";
            Files.write(Paths.get(userHomeBash), Collections.singletonList(alias), StandardOpenOption.APPEND);
            exec(new String[]{"source", userHomeBash});
            List<String> alise = Files.lines(Paths.get(userHomeBash)).collect(java.util.stream.Collectors.toList());
            System.out.println(alise);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
