package test;

import com.google.common.collect.Lists;

import java.util.Iterator;
import java.util.List;

/**
 * @program: van
 * @description:
 * @author: 遥远
 * @create: 2020-05-09 09:07
 */
public class ListTest {

    public static void main(String[] args) {
        List<String> list = Lists.newArrayList("a", "b", "c");
         for (Iterator iter = list.iterator(); iter.hasNext(); ) {
            String str = (String)iter.next();
            System.out.println(str);        }
    }
}
