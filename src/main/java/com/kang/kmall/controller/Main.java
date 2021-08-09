package com.kang.kmall.controller;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.po.TableFill;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import java.util.ArrayList;
import java.util.List;

/**
 * 代码生成器，使用velocity模版引擎，自动生成mapper、service、controller等文件
 * @author Kangshitao
 * @date 2021年7月31日 下午1:36
 */
public class Main {
    //使用mybatis-plus-generator  自动生成代码
    public static void main(String[] args) {
        AutoGenerator autoGenerator = new AutoGenerator();
        //数据源配置
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setDbType(DbType.MYSQL);
        dataSourceConfig.setDriverName("com.mysql.cj.jdbc.Driver");
        dataSourceConfig.setUsername("root");
        dataSourceConfig.setPassword("kangshitao");
        dataSourceConfig.setUrl("jdbc:mysql://localhost:3306/kmall?useSSL=false&characterEncoding=UTF-8");
        autoGenerator.setDataSource(dataSourceConfig);

        //全局配置
        GlobalConfig globalConfig = new GlobalConfig();
        globalConfig.setOpen(false);  //生成后是否打开资源管理器
        globalConfig.setOutputDir(System.getProperty("user.dir")+"/src/main/java");
        globalConfig.setAuthor("kangshitao");
        globalConfig.setServiceName("%sService"); //去掉Service接口的首字母I（基于习惯和适配一些技术接口不加I）
        autoGenerator.setGlobalConfig(globalConfig);

        //包配置
        PackageConfig packageConfig = new PackageConfig();
        packageConfig.setParent("com.kang.kmall");
        packageConfig.setEntity("entity");
        packageConfig.setMapper("mapper");
        packageConfig.setController("controller");
        packageConfig.setService("service");
        packageConfig.setServiceImpl("service.impl");
        autoGenerator.setPackageInfo(packageConfig);

        //策略配置
        StrategyConfig strategyConfig = new StrategyConfig();
//        strategyConfig.setEntityLombokModel(true);  //是否使用lombok
        strategyConfig.setNaming(NamingStrategy.underline_to_camel);  //驼峰命名规则
        strategyConfig.setColumnNaming(NamingStrategy.underline_to_camel);

        //自动填充create_time和update_time
        List<TableFill> list = new ArrayList<>();
        TableFill tableFill = new TableFill("create_time",FieldFill.INSERT);
        TableFill tableFill2 = new TableFill("update_time",FieldFill.INSERT_UPDATE);
        list.add(tableFill);
        list.add(tableFill2);
        strategyConfig.setTableFillList(list);  //表填充字段，用于自动填充字段
        autoGenerator.setStrategy(strategyConfig);
        autoGenerator.execute();

    }
}
