package com.lyn.maker.generator.file;

import com.lyn.maker.model.DataModel;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.IOException;

/**
 * 核心生成器
 */
public class FileGenerator {

    /**
     * 生成
     *
     * @param model 数据模型
     * @throws TemplateException
     * @throws IOException
     */
    public static void doGenerate(Object model) throws TemplateException, IOException {
        String projectPath = System.getProperty("user.dir");
        // 整个项目的根路径
        File parentFile = new File(projectPath).getParentFile();
//        System.out.println(parentFile);
//        System.out.println("projectPath: " + projectPath);
        // 输入路径
        String inputPath = new File(projectPath, "taideng-generator-demo-projects" + File.separator+ "acm-template").getAbsolutePath();
        System.out.println(inputPath);
        String outputPath = projectPath + File.separator+"taideng-generator-maker";
        System.out.println(outputPath);
        // 生成静态文件
        StaticFileGenerator.copyFilesByHutool(inputPath, outputPath);
        // 生成动态文件
        String inputDynamicFilePath = projectPath + File.separator + "taideng-generator-maker" +  File.separator+"src/main/resources/templates/MainTemplate.java.ftl";
        String outputDynamicFilePath = outputPath + File.separator+"acm-template/src/com/lyn/acm/MainTemplate.java";
        System.out.println(outputDynamicFilePath);
        DynamicFileGenerator.doGenerate(inputDynamicFilePath, outputDynamicFilePath, model);
    }

    public static void main(String[] args) throws TemplateException, IOException {
        DataModel mainTemplateConfig = new DataModel();
        mainTemplateConfig.setAuthor("lyn");
        mainTemplateConfig.setLoop(false);
        mainTemplateConfig.setOutputText("求和结果：");
        doGenerate(mainTemplateConfig);
    }
}

