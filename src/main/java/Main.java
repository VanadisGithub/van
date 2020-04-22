import com.alibaba.fastjson.JSON;
import taobao.TaobaoBuyUrl;
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
//        addAlias();
        if (args.length == 0) {
            welcome();
            showCmd();
            System.out.print("请选择执行命令:\n");
            String input = getInput();
            int no = Integer.valueOf(input) - 1;
            exec(commands.get(no).split(" "));
            return;
        } else {
            switch (args[0]) {
                case "list":
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
                    List<TaobaoBuyUrl> s = new TaobaoService().coupon("https://detail.tmall.com/item.htm?id=547717653043");
                    System.out.println(JSON.toJSONString(s));
                    return;
                default:
                    exec(commands.get(Integer.valueOf(args[0]) - 1).split(" "));
                    System.exit(0);
            }
        }
    }

    private static String getInput() {
        while (true) {
            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine();
            if (input == null || "".equals(input)) {
                continue;
            }
            checkIsExit(input);
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
