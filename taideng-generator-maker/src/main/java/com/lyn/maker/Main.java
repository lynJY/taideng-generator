package com.lyn.maker;

import com.lyn.maker.generator.template.GenerateTemplate;
import com.lyn.maker.meta.Meta;
import freemarker.template.TemplateException;

import java.io.IOException;
public class Main extends GenerateTemplate{

    @Override
    protected void buildGit(String outputPath, String inputResourcesPath, Meta meta) throws IOException, InterruptedException, TemplateException {
        System.out.println("不进行git初始化了");
    }

    public static void main(String[] args) throws TemplateException, IOException, InterruptedException {
        new Main().doGenerate();
    }
}
