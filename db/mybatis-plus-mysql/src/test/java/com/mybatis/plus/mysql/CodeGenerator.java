package com.mybatis.plus.mysql;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.Collections;
import java.util.HashMap;

/**
 * @author : chenbo
 * @date : 2021-12-17
 */
public class CodeGenerator {
    public static void main(String[] args) {
        String projectPath = System.getProperty("user.dir") + "/db/mybatis-plus-mysql/";
        FastAutoGenerator.create("jdbc:p6spy:mysql://localhost:3306/mybatis-plus-mysql?useUnicode=true&characterEncoding=utf-8&useSSL=true&serverTimezone=GMT%2B8", "root", "root")
                .globalConfig(builder -> {
                    builder
                            .author("chenbo") // 设置作者
                            .enableSwagger()
                            .disableOpenDir()
                            .dateType(DateType.ONLY_DATE)
                            .outputDir(projectPath + "src/main/java");
                })
                .packageConfig(builder -> {
                    builder
                            .parent("com.mybatis.plus.mysql") // 设置父包名
                            .controller("controller")
                            .entity("entity")
                            .mapper("mapper")
                            .service("service")
                            .serviceImpl("service.impl")
                            .xml("mapper.xml")
                            .pathInfo(Collections.singletonMap(OutputFile.xml, projectPath + "src/main/resources/mapper/"))

                    ;
                })
                .strategyConfig(builder -> {
                    builder.addInclude("sims_college", "sims_class", "sims_student", "sims_teacher") // 设置需要生成的表名
                            .addTablePrefix("sims_") // 设置过滤表前缀
                            .controllerBuilder().enableFileOverride().enableRestStyle()
                            // 设置实体父类与父类字段
                            .entityBuilder().superClass(BaseEntity.class).addSuperEntityColumns("creater", "createTime", "updater", "updateTime", "tenantId").enableFileOverride().disableSerialVersionUID().enableLombok()
                            .mapperBuilder().enableFileOverride()
                            .serviceBuilder().enableFileOverride().formatServiceFileName("%sService")
                    ;
                })
                // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .templateEngine(new FreemarkerTemplateEngine())
                .execute();
    }
}
