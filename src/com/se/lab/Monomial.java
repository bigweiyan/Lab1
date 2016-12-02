package com.se.lab;

/**
 * 单项式类，封装了系数和变量.
 */
public class Monomial {
    /**
     * 系数.
     */
    private float coefficient; 
    /**
     * 变量及其次数.
     */
    private VariableMap variable; 

    /**
     * 初始化多项式：为1.
     */
    Monomial() {
        coefficient = 1;
        variable = new VariableMap();
    }

    /**
     * 复制构造函数.
     * @param mono 被复制的单项式
     */
    Monomial(final Monomial mono) {
        coefficient = mono.coefficient;
        variable = new VariableMap(mono.variable);
    }
    
    public float getCoeff()
    {
    	return coefficient;
    }
    
    public void setCoeff(float coefficient)
    {
    	this.coefficient=coefficient;
    }
    
    public VariableMap getVariable()
    {
    	return variable;
    }
    
    public void setVariable(VariableMap variable)
    {
    	this.variable=variable;
    }
}
