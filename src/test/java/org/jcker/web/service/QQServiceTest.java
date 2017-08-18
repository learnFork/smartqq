package org.jcker.web.service;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by Alan Turing on 2017/7/15.
 */
public class QQServiceTest {
    private static List<String> INTEREST_TAGS = new CopyOnWriteArrayList<>();

    public static void main(String[] args) {
        String s = "abc123";
        System.out.println("s.indexOf(\"abc\") = " + s.indexOf("abc"));

        /*
        ResourceBundle CFG = ResourceBundle.getBundle("interests");
        INTEREST_TAGS = Arrays.asList(CFG.getString("web.interests.tags").split("\\|"));
        System.out.println("INTEREST_TAGS = " + INTEREST_TAGS);
        String fmtStr = "StringOf123啊爱Spring的色放,String";
        boolean flag = false;
        for (String tag : INTEREST_TAGS){
            if (StringUtils.containsIgnoreCase(fmtStr,tag)){
                flag = true;
                break;
            }
        }
        System.out.println("flag = " + flag);
        System.out.println("fmtStr = " + fmtStr);
        */
    }

}
