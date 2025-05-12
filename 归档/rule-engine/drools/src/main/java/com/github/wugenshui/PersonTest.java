package com.github.wugenshui;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

/**
 * @author : chenbo
 * @date : 2024-01-21
 */
public class PersonTest {

    public static void main(String[] args) {

        try {
            // load up the knowledge base
            KieServices ks = KieServices.Factory.get();
            KieContainer kContainer = ks.getKieClasspathContainer();
            KieSession kSession = kContainer.newKieSession("ksession-person");

            Person p = new Person();
            p.setAge(28);
            p.setSex("男");
            kSession.insert(p);

            kSession.fireAllRules();

            System.out.println("返回消息msg=" + p.getMsg());
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    public static class Person {

        // 年龄
        private int age;
        // 性别
        private String sex;
        // 信息
        private String msg;

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        @Override
        public String toString() {
            return "Person [age=" + age + ", sex=" + sex + ", msg=" + msg + "]";
        }

    }
}