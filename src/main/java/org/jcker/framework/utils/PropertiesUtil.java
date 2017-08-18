package org.jcker.framework.utils;

import java.io.*;
import java.util.*;

/**
 * Created by Alan Turing on 2017/7/16.
 * properties util class
 */
public class PropertiesUtil {

    private static Properties props = null;

    static {
        props = new Properties();
        reLoad();
    }

    //加载属性文件
    private static void reLoad() {
        try {
            InputStream input = PropertiesUtil.class.getResourceAsStream("/interests.properties");
            props.load(input);
        } catch (Exception ex) {
            System.out.println("exceptiom:" + ex);
        }
    }

    //获取当前文件的路径，返回成FileOutputStream
    public static FileOutputStream getOutputStream() {
        FileOutputStream output = null;
        try {
            output = new FileOutputStream(PropertiesUtil.class.getClassLoader().getResource("/").getPath()+"interests.properties");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return output;
    }

    //获取某个键的属性
    public static String getString(String name) {
        try {
            return props.getProperty(name);
        } catch (Exception ex) {
            System.out.println("exceptiom:" + ex);
            return null;
        }
    }

    /**
     * @param name 名称
     * @param def  参数
     * @return 结果
     */
    public static String getString(String name, String def) {
        String retval = null;
        try {
            retval = props.getProperty(name);
            retval = retval == null ? def : retval;
        } catch (Exception ex) {
//            logger.error("", ex);
            ex.printStackTrace();
        }
        return retval;
    }

    /**
     * 对interests.properties进行遍历,获取全部的键值和属性值
     */
    public static HashMap<String, String> getAll() {
        Set<String> names = props.stringPropertyNames();
        HashMap<String, String> map = new HashMap<String, String>();
        for (String name : names) {
            map.put(name, props.getProperty(name));
        }
        return map;
    }

    /**
     * 写入一对属性到 interests.properties文件中
     *
     * @param name
     * @param value
     */
    public static void setValue(String name, String value) {
        props.setProperty(name, value); // 设置属性
        try {
            props.store(getOutputStream(), "放入一对属性");
        } catch (IOException e) {
            e.printStackTrace();
        } // 保存属性到普通文件
    }

    //同时写入多个属性到 interests.properties文件中
    public static void setAllValue(Map<String, String> map) {
        for (Map.Entry<String, String> me : map.entrySet()) {
            props.setProperty(me.getKey(), me.getValue()); // 设置属性
        }

        try {
            props.store(getOutputStream(), "放入多个属性");
        } catch (IOException e) {
            e.printStackTrace();
        } // 保存属性到普通文件
    }

    /**
     * add new key word to properties
     * @param prop
     * @param addValue
     */
    public static void addValueToProperty(String prop, String addValue) {
        try {
            props.setProperty(prop, props.getProperty(prop) + "|" + addValue);
            props.store(getOutputStream(), "addValueToProperty");
            System.out.println("props = " + props.getProperty("qq.interests.tags"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * delete key word from properties
     * @param prop
     * @param delValue
     */
    public static void delValueToProperty(String prop, String delValue) {
        try {
            props.setProperty(prop, props.getProperty(prop).replace("|" + delValue, ""));
            props.store(getOutputStream(), "delValueToProperty");
            System.out.println("props = " + props.getProperty("qq.interests.tags"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String printProperties() {
        System.out.println("props = " + props.getProperty("qq.interests.tags"));
        return props.getProperty("qq.interests.tags");
    }

    public static List<String> getTags(){
        return Arrays.asList(getString("qq.interests.tags").split("\\|"));
    }

    public static void setGroup(String groups){
        try {
            props.setProperty("web.groups", groups);
            props.store(getOutputStream(), "setGroup");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
/*        addValueToProperty("web.interests.tags", "管理员");
        System.out.println(getString("web.interests.tags"));*/
        System.out.println(PropertiesUtil.class.getResource("/").getPath());
    }
}
