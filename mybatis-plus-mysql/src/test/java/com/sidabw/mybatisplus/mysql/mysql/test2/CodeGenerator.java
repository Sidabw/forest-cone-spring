/**
 * Copyright (C), 2018-2020, zenki.ai
 * FileName: CodeGenerator
 * Author:   feiyi
 * Date:     2020/12/15 4:56 PM
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.sidabw.mybatisplus.mysql.mysql.test2;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.sidabw.mybatisplus.mysql.basic.BasicEntity;
import com.sidabw.mybatisplus.mysql.util.JacksonUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * 〈一句话功能简述〉:
 * 〈mybatis-plus代码生成器。快速生成 Entity、Mapper、Mapper XML、Service、Controller 等各个模块的代码
 *   如果在项目中用的话，这个还是不应该放到test目录下。放到main下xx.xx.xx.nonruntime下
 * 〉
 *
 * @author feiyi
 * @create 2020/12/15
 * @since 1.0.0
 */
public class CodeGenerator {

    private static final String tableName = "model_change_log";
    private static final String genCodePackage = "com.sidabw.forest.cone.mybatisplus.generator";
    private static final String url = "jdbc:mysql://localhost:3306/mysql8_first_db?useUnicode=true&characterEncoding=utf-8&serverTimezone=GMT";
    //mysql6以下使用com.mysql.jdbc.Driver
    private static final String driverName = "com.mysql.cj.jdbc.Driver";
    private static final String username = "root";
    private static final String password = "root";

    public static void main(String[] args) {
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();
        // 全局配置
        GlobalConfig globalConfig = getGlobalConfig();
        // 数据源配置
        DataSourceConfig dataSourceConfig = getDataSource();
        // 包配置
        PackageConfig packageConfig = getPackageConfig();
        // 自定义配置（xml配置）
        InjectionConfig injectionConfig = getInjectionConfig(packageConfig);
        // 配置模板/模版引擎（空的）
        TemplateConfig templateConfig = getTemplateConfig();
        // 策略配置
        StrategyConfig strategyConfig = getStrategyConfig(packageConfig);

        mpg.setGlobalConfig(globalConfig);
        mpg.setDataSource(dataSourceConfig);
        mpg.setPackageInfo(packageConfig);
        mpg.setCfg(injectionConfig);
        mpg.setTemplate(templateConfig);
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        mpg.setStrategy(strategyConfig);

        mpg.execute();
    }

    private static GlobalConfig getGlobalConfig() {
        String projectPath = System.getProperty("user.dir");
        GlobalConfig gc = new GlobalConfig();
        gc.setOutputDir(projectPath + "/src/main/java");
        gc.setAuthor("feiyi");
        gc.setOpen(false);
        // gc.setSwagger2(true); 实体属性 Swagger2 注解
        return gc;
    }

    private static DataSourceConfig getDataSource() {
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl(url);
        // dsc.setSchemaName("public");
        dsc.setDriverName(driverName);
        dsc.setUsername(username);
        dsc.setPassword(password);
        return dsc;
    }

    private static PackageConfig getPackageConfig() {
        PackageConfig pc = new PackageConfig();
//        pc.setModuleName("model");
        pc.setParent(genCodePackage);
        return pc;
    }

    private static InjectionConfig getInjectionConfig(PackageConfig packageConfig) {
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };

        String templatePath = "/templates/mapper.xml.ftl";
        // 自定义输出配置
        List<FileOutConfig> focList = new ArrayList<>();
        // 自定义配置会被优先输出
        focList.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                String projectPath = System.getProperty("user.dir");
                return projectPath + "/src/main/resources/mapper/" + packageConfig.getModuleName()
                        + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });
        cfg.setFileOutConfigList(focList);
        return cfg;
    }

    private static TemplateConfig getTemplateConfig() {
        TemplateConfig templateConfig = new TemplateConfig();
        templateConfig.setXml(null);
        return templateConfig;
    }

    private static StrategyConfig getStrategyConfig(PackageConfig packageConfig) {
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setEntityLombokModel(true);
        strategy.setRestControllerStyle(true);
        // 写于父类中的公共字段
        //对，没错，这里说的是数据库的字段名
        strategy.setSuperEntityClass(BasicEntity.class);
//        String[] superEntityColumns = getSuperEntityColumns(BasicEntity.class);
//        strategy.setSuperEntityColumns(superEntityColumns);
        strategy.setSuperEntityColumns("id", "gmt8_created", "gmt8_modified", "is_delete", "created_user_id");

        // 公共父类控制器
//        strategy.setSuperControllerClass("你自己的父类控制器,没有就不用设置!");

        //表名，多个英文逗号分割
        strategy.setInclude(tableName);
        strategy.setControllerMappingHyphenStyle(true);
        strategy.setTablePrefix(packageConfig.getModuleName() + "_");
        return strategy;
    }

    private static String[] getSuperEntityColumns(Class<BasicEntity> basicEntityClass) {
        Field[] declaredFields = basicEntityClass.getDeclaredFields();
        String[] res = new String[declaredFields.length];
        for (int i = 0; i< res.length; i++) {
            Field declaredField = declaredFields[i];
            res[i] = declaredField.getName();
        }
        System.out.println("basic entity fields: " + JacksonUtils.convertToJsonStr(res));
        return res;
    }
}
