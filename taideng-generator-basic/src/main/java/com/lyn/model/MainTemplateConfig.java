package com.lyn.model;

import lombok.Data;

@Data
public class MainTemplateConfig {
    /**
     * 是否生成循环
     */
    private boolean loop;

    /**
     * 作者注释
     */
    private String author = "lyn";

    /**
     * 输出信息
     */
    private String outputText = "输出结果";

}
