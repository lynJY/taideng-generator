package com.lyn.model;

import lombok.Data;

@Data
public class DataModel {


        /**
        * 是否生成循环
        */
    private boolean loop = false;

        /**
        * 作者注释
        */
    private String author = "lyn";

        /**
        * 输出信息
        */
    private String outputText = "sum = ";
}

