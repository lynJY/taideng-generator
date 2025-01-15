package com.lyn.cli.command;

import cn.hutool.core.bean.BeanUtil;
import com.lyn.generator.MainGenerator;
import com.lyn.model.MainTemplateConfig;
import freemarker.template.TemplateException;
import lombok.Data;
import picocli.CommandLine.Option;
import picocli.CommandLine.Command;

import java.io.IOException;
import java.util.concurrent.Callable;

@Command(name = "generate", mixinStandardHelpOptions = true)
@Data
public class GeneratorCommand implements Callable<Integer> {

    @Override
    public Integer call() throws Exception {
        MainTemplateConfig mainTemplateConfig = new MainTemplateConfig();
        BeanUtil.copyProperties(this, mainTemplateConfig);
        System.out.println("配置信息：" + mainTemplateConfig);
        MainGenerator.doGenerate(mainTemplateConfig);
        return 0;
    }

    /**
     * 是否生成循环
     */
    @Option(names = {"-l", "--loop"},arity = "0..1", echo = true, description = "是否循环", interactive = true)
    private boolean loop;

    /**
     * 作者注释
     */
    @Option(names = {"-a", "--author"},arity = "0..1", echo = true,description = "作者姓名", interactive = true)
    private String author = "lyn";

    /**
     * 输出信息
     */
    @Option(names = {"-o", "--outputText"},arity = "0..1", echo = true,description = "结果展示", interactive = true)
    private String outputText = "输出结果";
}
