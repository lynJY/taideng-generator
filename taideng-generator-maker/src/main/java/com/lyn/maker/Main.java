package com.lyn.maker;

import com.lyn.maker.generator.main.GenerateTemplate;
import freemarker.template.TemplateException;

import java.io.IOException;
public class Main extends GenerateTemplate{

    public static void main(String[] args) throws TemplateException, IOException, InterruptedException {
        new Main().doGenerate();
    }
}
