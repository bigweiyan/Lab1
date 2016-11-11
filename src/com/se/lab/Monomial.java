package com.se.lab;

import java.util.HashMap;

/**
 * 单项式类，封装了系数和变量.
 */
public class Monomial {
    /**
     * 系数.
     */
    float longk; 
    /**
     * 变量及其次数.
     */
    /* default */HashMap<String, Integer> variable; 

    /**
     * 初始化多项式：为1.
     */
    Monomial() {
        longk = 1;
        variable = new HashMap<String, Integer>();
    }

    /**
     * 复制构造函数.
     *
     * @param mono 被复制的单项式
     */
    Monomial(final Monomial mono) {
        longk = mono.longk;
        variable = new HashMap<String, Integer>(mono.variable);
    }

}
