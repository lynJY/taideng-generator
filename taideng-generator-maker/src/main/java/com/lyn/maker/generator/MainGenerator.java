package com.lyn.maker.generator;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.resource.ClassPathResource;
import cn.hutool.core.util.StrUtil;
import com.lyn.maker.generator.file.DynamicFileGenerator;
import com.lyn.maker.meta.Meta;
import com.lyn.maker.meta.MetaManager;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class MainGenerator {
    public static void main(String[] args) throws TemplateException, IOException, InterruptedException {
        Meta meta = MetaManager.getMetaObject();
        //System.out.println(meta);

        //输出根路径
        String projectPath = System.getProperty("user.dir");
        String outputPath = projectPath + File.separator +"generated" + File.separator + meta.getName();
        //System.out.println(outputPath);
        if(!FileUtil.exist(outputPath)){
            FileUtil.mkdir(outputPath);
        }

        //复制源模版
        String sourceRootPath = meta.getFileConfig().getSourceRootPath();
        String sourceCopyPath = outputPath + File.separator + ".source";
        FileUtil.copy(sourceRootPath,sourceCopyPath,false);

        //读取resources目录
        ClassPathResource classPathResource = new ClassPathResource("");
        String inputResourcesPath = classPathResource.getAbsolutePath();
        //system.out.println(inputResourcesPath);
        //java包的基础路径
        String outputBasePackage = meta.getBasePackage();
        String outputBasePackagePath = StrUtil.join("/", StrUtil.split(outputBasePackage, "."));
        //System.out.println(outputBasePackagePath);

        String outputBaseJavaPackagePath =outputPath+File.separator+"src/main/java/" + outputBasePackagePath;
        //System.out.println(outputBaseJavaPackagePath);

        String inputFilePath;
        String outputFilePath;

        //Model.DataModel
        inputFilePath = inputResourcesPath + File.separator +"templates/java/model/DataModel.java.ftl";
        outputFilePath = outputBaseJavaPackagePath + "/model/DataModel.java";
        DynamicFileGenerator.doGenerate(inputFilePath,outputFilePath,meta);

        //ConfigCommand
        inputFilePath = inputResourcesPath + File.separator +"templates/java/cli/command/ConfigCommand.java.ftl";
        outputFilePath = outputBaseJavaPackagePath + "/cli/command/ConfigCommand.java";
        DynamicFileGenerator.doGenerate(inputFilePath,outputFilePath,meta);

        //GenerateCommand
        inputFilePath = inputResourcesPath + File.separator + "templates/java/cli/command/GenerateCommand.java.ftl";
        outputFilePath = outputBaseJavaPackagePath + "/cli/command/GenerateCommand.java";
        DynamicFileGenerator.doGenerate(inputFilePath,outputFilePath,meta);

        //ListCommand
        inputFilePath = inputResourcesPath + File.separator +"templates/java/cli/command/ListCommand.java.ftl";
        outputFilePath = outputBaseJavaPackagePath + "/cli/command/ListCommand.java";
        DynamicFileGenerator.doGenerate(inputFilePath,outputFilePath,meta);

        //CommandExecutor
        inputFilePath = inputResourcesPath + File.separator +"templates/java/cli/CommandExecutor.java.ftl";
        outputFilePath = outputBaseJavaPackagePath + "/cli/CommandExecutor.java";
        DynamicFileGenerator.doGenerate(inputFilePath,outputFilePath,meta);

        //Main
        inputFilePath = inputResourcesPath + File.separator +"templates/java/Main.java.ftl";
        outputFilePath = outputBaseJavaPackagePath + "/Main.java";
        DynamicFileGenerator.doGenerate(inputFilePath,outputFilePath,meta);

        //MainGenerator
        inputFilePath = inputResourcesPath + File.separator +"templates/java/generator/MainGenerator.java.ftl";
        outputFilePath = outputBaseJavaPackagePath + "/generator/MainGenerator.java";
        DynamicFileGenerator.doGenerate(inputFilePath,outputFilePath,meta);

        //DynamicGenerator
        inputFilePath = inputResourcesPath + File.separator +"templates/java/generator/DynamicGenerator.java.ftl";
        outputFilePath = outputBaseJavaPackagePath + "/generator/DynamicGenerator.java";
        DynamicFileGenerator.doGenerate(inputFilePath,outputFilePath,meta);

        //StaticGenerator
        inputFilePath = inputResourcesPath + File.separator +"templates/java/generator/StaticGenerator.java.ftl";
        outputFilePath = outputBaseJavaPackagePath + "/generator/StaticGenerator.java";
        DynamicFileGenerator.doGenerate(inputFilePath,outputFilePath,meta);

        //pom.xml
        inputFilePath = inputResourcesPath + File.separator + "templates/pom.xml.ftl";
        outputFilePath = outputPath +File.separator+ "/pom.xml";
        DynamicFileGenerator.doGenerate(inputFilePath,outputFilePath,meta);

        //打jar包
        JarGenerator.doGenerate(outputPath);

        // 封装脚本
        String shellOutputFilePath = outputPath + File.separator + "generator";
        String jarName = String.format("%s-%s-jar-with-dependencies.jar", meta.getName(), meta.getVersion());
        String jarPath = "target/" + jarName;
        ScriptGenerator.doGenerate(shellOutputFilePath, jarPath);

        //README.md
        inputFilePath = inputResourcesPath + File.separator + "templates/README.md.ftl";
        outputFilePath = outputPath +File.separator+ "/README.md";
        DynamicFileGenerator.doGenerate(inputFilePath,outputFilePath,meta);

        //生成精简版的代码程序（产物包）
        String disOutputPath = outputPath + "-dist";
        //拷贝JAR包
        String targetAbsolutePath = disOutputPath + File.separator +"target";
        FileUtil.mkdir(targetAbsolutePath);
        String jarAbsolutePath = outputPath + File.separator + jarPath;
        FileUtil.copy(jarAbsolutePath,targetAbsolutePath,true);
        // 拷贝脚本文件
        FileUtil.copy(shellOutputFilePath,disOutputPath,true);
        FileUtil.copy(shellOutputFilePath + ".bat",disOutputPath,true);
        //拷贝源模版文件
        FileUtil.copy(sourceCopyPath,disOutputPath,true);

        // 初始化git仓库
        // 提示用户是否初始化Git仓库
        Scanner scanner = new Scanner(System.in);
        System.out.println("是否要初始化Git仓库？(true/false)");
        boolean initGitRepo = scanner.nextBoolean();
        if (initGitRepo) {
            Scanner scanner1 = new Scanner(System.in);
            System.out.println("初始化完整版还是精简版？或者全部初始化？(1:完整版 2:精简版 3:ALL)");
            int initChose = scanner1.nextInt();
            scanner.close();
            if(1 == initChose){
                gitGenerator.doGenerate(outputPath);
                inputFilePath = inputResourcesPath + File.separator + "templates/.gitignore.ftl";
                outputFilePath = outputPath + File.separator + "/.gitignore";
                DynamicFileGenerator.doGenerate(inputFilePath, outputFilePath, meta);
            }else if(initChose == 2){
                gitGenerator.doGenerate(outputPath + "-dist");
                inputFilePath = inputResourcesPath + File.separator + "templates/.gitignore.ftl";
                outputFilePath = outputPath + "-dist" + File.separator + "/.gitignore";
                DynamicFileGenerator.doGenerate(inputFilePath, outputFilePath, meta);
            }
            else{
                gitGenerator.doGenerate(outputPath);
                inputFilePath = inputResourcesPath + File.separator + "templates/.gitignore.ftl";
                outputFilePath = outputPath + File.separator + "/.gitignore";
                DynamicFileGenerator.doGenerate(inputFilePath, outputFilePath, meta);
                gitGenerator.doGenerate(outputPath + "-dist");
                inputFilePath = inputResourcesPath + File.separator + "templates/.gitignore.ftl";
                outputFilePath = outputPath + "-dist" + File.separator + "/.gitignore";
                DynamicFileGenerator.doGenerate(inputFilePath, outputFilePath, meta);
            }
        }else{
            System.out.println("未初始化Git仓库");
        }
    }

}
