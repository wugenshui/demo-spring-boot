package com.github.wugenshui.mybatisplus.generator.generator;

import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.FileOutConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.TemplateConfig;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.VelocityTemplateEngine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * 代码生成：演示例子，执行 main 方法控制台输入模块表名回车自动生成对应项目目录中
 *
 * @author : chenbo
 * @date : 2019/11/27
 */
public class CodeGenerator {

    public static void main(String[] args) {
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir") + "/db/mybatis-plus-generator";
        // 【输出文件路径】
        gc.setOutputDir(projectPath + "/src/main/java");
        // 是否覆盖已有文件,谨慎开启
        gc.setFileOverride(true);
        gc.setAuthor("chenbo");
        gc.setOpen(false);
        // 实体属性 Swagger2 注解
        gc.setSwagger2(true);
        gc.setBaseResultMap(true);
        gc.setBaseColumnList(true);
        gc.setServiceName("%sService");
        mpg.setGlobalConfig(gc);


        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://localhost:3306/best_practice?useUnicode=true&characterEncoding=utf-8&useSSL=true&serverTimezone=UTC");
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("root");
        mpg.setDataSource(dsc);

        // 包配置
        PackageConfig pc = new PackageConfig();
        //pc.setModuleName(scanner("模块名"));
        pc.setParent("com.github.wugenshui.mybatisplus.generator");
        mpg.setPackageInfo(pc);

        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                Map<String, Object> map = new HashMap<>(2);
                map.put("VOPackageName", this.getConfig().getPackageInfo().get("Entity").replace("entity", "vo"));
                //List<TableInfo> tableInfoList = this.getConfig().getTableInfoList();
                //Map<String, Set<String>> maps = new HashMap<>();
                //for (int i = 0; i < tableInfoList.size(); i++) {
                //    TableInfo table = tableInfoList.get(i);
                //    Set<String> packages = table.getImportPackages();
                //    packages.removeAll(Arrays.asList("com.baomidou.mybatisplus.annotation.IdType", "com.baomidou.mybatisplus.annotation.TableId"));
                //    maps.put(table.getName(), packages);
                //}
                //map.put("VOImportPackage", maps);
                this.setMap(map);
            }
        };
        mpg.setCfg(cfg);

        // 自定义输出配置
        List<FileOutConfig> focList = new ArrayList<>();
        // xml文件模板
        // 如果模板引擎是 freemarker
        //String templatePath = "/templates/mapper.xml.ftl";
        // 如果模板引擎是 velocity
        String mapTemplatePath = "/templates/mapper.xml.vm";
        // 自定义配置会被优先输出
        focList.add(new FileOutConfig(mapTemplatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                return projectPath + "/src/main/resources/mapper/" + (StringUtils.isNotBlank(pc.getModuleName()) ? pc.getModuleName() + "/" : "")
                        + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });
        String voTemplatePath = "/templates/vo.java.vm";
        focList.add(new FileOutConfig(voTemplatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出路径
                return projectPath + "/src/main/java/" + pc.getParent().replace('.', '/')
                        + (StringUtils.isNotBlank(pc.getModuleName()) ? pc.getModuleName() + "/" : "/")
                        + "vo/" + tableInfo.getEntityName() + "VO" + StringPool.DOT_JAVA;
            }
        });
        cfg.setFileOutConfigList(focList);

        // 配置模板
        TemplateConfig templateConfig = new TemplateConfig();

        // 配置自定义输出模板
        // 指定自定义模板路径，注意不要带上.ftl/.vm, 会根据使用的模板引擎自动识别
        // 自定义实体层模板路径
        templateConfig.setEntity("templates/entity2.java");
        // templateConfig.setService(); // 自定义服务层模板路径
        // templateConfig.setController(); // 自定义控制器层模板路径

        templateConfig.setXml(null);
        mpg.setTemplate(templateConfig);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setEntityLombokModel(true);
        strategy.setRestControllerStyle(true);
        // 写于父类中的公共字段
        // strategy.setSuperEntityColumns("id");  // 设置父类属性，例如id等公用属性不需要每个实体写一次
        strategy.setInclude("tb_user");
        //strategy.setLikeTable(new LikeTable(scanner("表名,模糊匹配")));
        //strategy.setInclude(scanner("表名，多个英文逗号分割").split(","));
        strategy.setControllerMappingHyphenStyle(true);
        strategy.setTablePrefix(pc.getModuleName() + "_", "oauth_", "tb_");
        mpg.setStrategy(strategy);
        mpg.setTemplateEngine(new VelocityTemplateEngine());
        mpg.execute();
    }

    /**
     * <p>
     * 读取控制台内容
     * </p>
     */
    private static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder help = new StringBuilder();
        help.append("请输入" + tip + "：");
        System.out.println(help.toString());
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (StringUtils.isNotBlank(ipt)) {
                return ipt;
            }
        }
        throw new MybatisPlusException("请输入正确的" + tip + "！");
    }

}