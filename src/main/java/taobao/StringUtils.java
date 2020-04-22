package taobao;


import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author yaoyuan
 */
public class StringUtils {

    private static String HOST_PATTERN = "(http://|https://)?([^/]*)";

    private static String ID_PATTERN = "[?&]id=(\\d+)";

    public static void main(String[] args) {
//        String test = ".abc";
//        String test1 = "abc";
//        List<String> list = new ArrayList(test, test1);
//        System.out.println(list.stream().filter(l -> l.startsWith(".")).findFirst().get());
//        System.out.println(test.startsWith("."));
//        System.out.println(test.startsWith("1"));
    }

    /**
     * url 获取host
     *
     * @param url
     * @return
     */
    public static String urlGetHost(String url) {
        Pattern p = Pattern.compile(HOST_PATTERN, Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(url);
        return m.find() ? m.group(2) : url;
    }

    /**
     * url 获取id
     *
     * @param url
     * @return
     */
    public static String urlGetId(String url) {
        Pattern p = Pattern.compile(ID_PATTERN, Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(url);
        while (m.find()) {
            return m.group(1);
        }
        return null;
    }

    /**
     * 正则匹配
     *
     * @param soap
     * @param regx
     * @return
     */
    public static String getSubUtilSimple(String soap, String regx) {
        Pattern pattern = Pattern.compile(regx);
        Matcher m = pattern.matcher(soap);
        while (m.find()) {
            return m.group(1);
        }
        return "";
    }

    /**
     * Unicode转中文
     *
     * @param dataStr
     * @return
     */
    public static String decodeUnicode(final String dataStr) {
        int start = 0;
        int end = 0;
        final StringBuffer buffer = new StringBuffer();
        while (start > -1) {
            end = dataStr.indexOf("\\u", start + 2);
            String charStr = "";
            if (end == -1) {
                charStr = dataStr.substring(start + 2, dataStr.length());
            } else {
                charStr = dataStr.substring(start + 2, end);
            }
            /* 16进制parse整形字符串。 */
            char letter = (char) Integer.parseInt(charStr, 16);
            buffer.append(Character.toString(letter));
            start = end;
        }
        return buffer.toString();
    }

    /**
     * 获取随机字符串
     *
     * @param length
     * @return
     */
    public static String getRandomString(int length) {
        String keyString = "abcdefghijklmnopqrstuvwxyz0123456789";
        return getRandomString(keyString, length);
    }

    /**
     * 获取16进制随机字符串
     *
     * @param length
     * @return
     */
    public static String getRandomString16(int length) {
        String keyString = "abcdef0123456789";
        return getRandomString(keyString, length);

    }

    /**
     * 获取随机字符串
     *
     * @param length
     * @return
     */
    public static String getRandomString(String keyString, int length) {
        StringBuffer sb = new StringBuffer();
        int len = keyString.length();
        for (int i = 0; i < length; i++) {
            sb.append(keyString.charAt((int) Math.round(Math.random() * (len - 1))));
        }
        return sb.toString();
    }

    /**
     * 转换风格 驼峰转下划线
     *
     * @return
     */
    public static String snakeCase(String str) {
        Pattern humpPattern = Pattern.compile("[A-Z]");
        Matcher matcher = humpPattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, "_" + matcher.group(0).toLowerCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    /**
     * 转换风格 下划线转驼峰
     *
     * @return
     */
    public static String camelCase(String str) {
        Pattern humpPattern = Pattern.compile("_[a-z]");
        Matcher matcher = humpPattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, matcher.group(0).replace("_", "").toUpperCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    /**
     * ${}参数替换工具
     *
     * @param template
     * @param params
     * @return
     */
    public static String replaceParams(String template, Map<String, Object> params) {
        StringBuffer sb = new StringBuffer();
        Matcher m = Pattern.compile("\\$\\{\\w+\\}").matcher(template);
        while (m.find()) {
            String param = m.group();
            Object value = params.get(param.substring(2, param.length() - 1));
            m.appendReplacement(sb, value == null ? "" : value.toString());
        }
        m.appendTail(sb);
        return sb.toString();
    }

    /**
     * 读取控制台内容
     */
    public static String scanner(String tip) throws Exception {
        Scanner scanner = new Scanner(System.in);
        StringBuilder help = new StringBuilder();
        help.append("请输入" + tip + "：");
        System.out.println(help.toString());
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (ipt != null) {
                return ipt;
            }
        }
        throw new Exception("请输入正确的" + tip + "！");
    }
}
