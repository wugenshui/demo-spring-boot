package com.github.wugenshui.baseutil.baseutil.data.structure;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * TireTree 字典树,  快速找词、字符串分词、词语补全
 *
 * @author : chenbo
 * @date : 2021-10-27
 */
@SpringBootTest
public class TireTreeOldTest {
    @Test
    public void apiTest() {
        TreeNodeOld root = new TreeNodeOld();
        TireTreeOld tree = new TireTreeOld();

        //String[] strs = {"public", "static", "void", "main", "maia", "maib", "maiccc", "NIHAO", "asb_wq", "webxr_z", "AZmai"};
        String[] strs = {"maiccc", "maid"};
        for (int i = 0, j = strs.length; i < j; i++) {
            tree.createTireTree(root, strs[i]);
        }

        System.out.println("tree.find(root, \"main\")" + tree.find(root, "main"));
        System.out.println("tree.find(root, \"ai\") = " + tree.find(root, "ai"));

        String str1 = "publicstaticvoidmain";
        List<String> list1 = tree.stringToWords(root, str1);
        if (list1 != null) {
            Iterator iter1 = list1.iterator();
            while (iter1.hasNext()) {
                System.out.println(iter1.next());
            }
        }

        System.out.println("************************************************ wordFill");

        String str2 = "ma";
        List<String> list2 = tree.wordFill(root, str2);
        Iterator iter2 = list2.iterator();
        while (iter2.hasNext()) {
            System.out.println(iter2.next());
        }
    }
}

class TireTreeOld {

    private static final char startChar = '0';

    // 在字典树中创建词子树
    public void createTireTree(TreeNodeOld node, String str) {
        char[] chars = str.toCharArray();
        int loc = 0;

        for (int i = 0, j = chars.length; i < j; i++) {

            // 字符映射下标
            loc = chars[i] - startChar;
            if (node.childs[loc] == null) {
                node.childs[loc] = new TreeNodeOld(chars[i]);
            }
            node = node.childs[loc];
        }
        node.isEnd = true;
    }


    // 在字典树中查找某单词
    public boolean find(TreeNodeOld node, String str) {
        char[] chars = str.toCharArray();
        int loc = 0;
        for (int i = 0, j = chars.length; i < j; i++) {
            loc = chars[i] - startChar;
            if (node.childs[loc] != null) {
                node = node.childs[loc];
            } else {
                return false;
            }

        }
        return node.isEnd;
    }


    // 连续字符串分词
    public List<String> stringToWords(TreeNodeOld node, String str) {
        char[] chars = str.toCharArray();
        TreeNodeOld p = node;

        List<String> list = new ArrayList<String>();
        StringBuffer sb = new StringBuffer();

        int loc = 0;
        for (int i = 0, j = chars.length; i < j; i++) {
            loc = chars[i] - startChar;
            if (p.childs[loc] != null) {
                sb.append(chars[i]);
                if (p.childs[loc].isEnd) {  // 如果单词结束
                    list.add(String.valueOf(sb));
                    sb.delete(0, sb.length());
                    p = node;
                } else {
                    p = p.childs[loc];
                }
            } else {
                return null;
            }
        }

        return list;
    }


    // 单词补齐
    public List<String> wordFill(TreeNodeOld node, String str) {
        char[] chars = str.toCharArray();
        int loc = 0;

        List<String> list = new ArrayList<String>();
        StringBuffer sb = new StringBuffer();

        // 找到字符串末字符在字典树中的位置
        for (int i = 0, j = chars.length; i < j; i++) {
            loc = chars[i] - startChar;
            if (node.childs[loc] != null) {
                sb.append(chars[i]);
                node = node.childs[loc];
            } else {
                return null;
            }
        }

        // 子树将单词补齐
        scanFind(node, String.valueOf(sb), list);

        return list;
    }


    // 搜索子树
    public void scanFind(TreeNodeOld node, String prefix, List<String> list) {
        for (int i = 0, j = node.childs.length; i < j; i++) {
            if (node.childs[i] != null) {
                if (node.childs[i].isEnd) {
                    list.add(prefix + node.childs[i].data);
                }
                scanFind(node.childs[i], prefix + node.childs[i].data, list);
            }
        }
    }
}

class TreeNodeOld {

    static final int MAX_SIZE = 75;
    char data;
    TreeNodeOld[] childs;
    boolean isEnd;

    public TreeNodeOld() {
        this.childs = new TreeNodeOld[MAX_SIZE];
        this.isEnd = false;
    }

    public TreeNodeOld(char ch) {
        this.data = ch;
        this.childs = new TreeNodeOld[MAX_SIZE];
        this.isEnd = false;
    }
}