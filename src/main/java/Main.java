import com.google.common.base.Strings;
import org.apache.commons.lang3.StringUtils;
import taobao.TaobaoService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 * van使用 [vim ~/.bashrc][source ~/.bashrc]
 *
 * @author yaoyuan
 */
public class Main extends Base {

    private static List<String> commands = new ArrayList<>();

    private static String cmdFile = "/Users/yaoyuan/MyProject/van/commands.txt";

    static {
        readCmd();
    }

    public static void main(String[] args) {
        if (args.length == 0) {
            welcome();
            showCmd();
            System.out.print("请选择执行命令:\n");
            while (true) {
                String input = getInput();
                excuteCmd(input.split(" "));
            }
        } else {
            excuteCmd(args);
        }

    }

    private static void excuteCmd(String[] args) {
        switch (args[0]) {
            case "ll":
                showCmd();
                return;
            case "add":
                writeCmd(args[1]);
                Log.suc("添加成功！");
                return;
            case "rm":
                int no = Integer.valueOf(args[1]) - 1;
                if (no >= commands.size()) {
                    Log.err("没有该指令！");
                    return;
                }
                removeCmd(no);
                Log.suc("删除成功！");
                return;
            case "tb":
                new TaobaoService().coupon(args[1]).forEach(coupon ->
                        System.out.println(String.format("[%s]%s", coupon.getTitle(), coupon.getBuyUrl())));
                return;
            case "init":
                addAlias();
                return;
            default:
                if (StringUtils.isNumeric(args[0])) {
                    exec(commands.get(Integer.valueOf(args[0]) - 1).split(" "));
                    System.exit(0);
                } else {
                    Log.err("未知命令");
                }
        }
    }


    private static String getInput() {
        while (true) {
            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine();
            if (!Strings.isNullOrEmpty(input)) {
                checkIsExit(input);
                return input;
            }
        }
    }

    private static void showCmd() {
        for (int i = 0; i < commands.size(); i++) {
            System.out.println(String.format("[%d] %s", i + 1, commands.get(i)));
        }
    }

    private static void readCmd() {
        try {
            commands = Files.lines(Paths.get(cmdFile)).collect(java.util.stream.Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void writeCmd(String command) {
        try {
            Files.write(Paths.get(cmdFile), Collections.singletonList(command), StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void removeCmd(int no) {
        try {
            String cmd = commands.remove(no);
            Files.write(Paths.get(cmdFile), commands);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
