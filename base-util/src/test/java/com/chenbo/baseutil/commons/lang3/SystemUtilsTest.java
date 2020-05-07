package com.chenbo.baseutil.commons.lang3;

import org.apache.commons.lang3.SystemUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author : chenbo
 * @date : 2020-05-07
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class SystemUtilsTest {

    @Test
    public void Test() {
        System.out.println(SystemUtils.OS_NAME);
        System.out.println(SystemUtils.getHostName());
        System.out.println(SystemUtils.USER_NAME);

        System.out.println(SystemUtils.FILE_ENCODING);
        System.out.println(SystemUtils.getJavaHome());
        System.out.println(SystemUtils.getJavaIoTmpDir());
        System.out.println(SystemUtils.getUserDir());
        System.out.println(SystemUtils.getUserHome());
        System.out.println(SystemUtils.getEnvironmentVariable("JAVA_HOME", "213"));
    }

}
