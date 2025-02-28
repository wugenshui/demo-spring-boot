package com.wugenshui.github.proguard;

/**
 * @author : chenbo
 * @date : 2025-02-28
 */
public class Util {
    public static String test() {
        return "test";
    }

    public static String test2(String name) {
        return name;
    }

    public static String test3(String name, String age) {
        return test4(name, age, 0);
    }

    public static String test4(String name, String age, Integer sex) {
        return name + age + sex;
    }


}
