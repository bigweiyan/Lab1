package com.se.lab;

import java.util.HashMap;

/**
 * 多项式运算类，包含求导和求值的逻辑
 * @author abgnwl
 *
 */
public class PolynomialMath {

	/**
     * 将多项式进行带入化简，返回带入后的表达式或错误信息
     *
     * @param poly 多项式
     * @param map  代入的变量及其值
     */
    public String simplify(
            final Polynomial poly, final HashMap<String, Float> map) {
    	Polynomial newpoly = new Polynomial();

        for (int i = 0; i < poly.size(); i++ ) 
        {
            Monomial longj = new Monomial(poly.getMonomial(i));
            newpoly.addMonomial(longj);
        }

        for (String variable : map.keySet()) {
            //System.out.println("TEST+"+variable+"+");
            boolean appear = false;
            for (int i = 0; i < newpoly.size(); i++) {
                Monomial mono = newpoly.getMonomial(i);

                if (mono.getVariable().containsKey(variable)) {
                    appear = true;
                    int value = mono.getVariable().get(variable);
                    for (int tims = 1; tims <= value; tims++) {
                        mono.setCoeff(mono.getCoeff() * map.get(variable));
                    }
                    mono.getVariable().remove(variable);
                }
            }
            if (!appear) {
                return "Variable not found!";
            }
        }
        return newpoly.toString();
    }
    

    /**
     * 将多项式进行求导运算，，返回带入后的表达式或错误信息
     *
     * @param poly     多项式
     * @param variable 求导变量
     */
    public String derivative(final Polynomial poly,
                                  final String variable) {
        if (poly == null) {
        	return "Command ERROR!";
        }
        for (int i = 0; i < variable.length(); i++){
        	char ch = variable.charAt(i);
        	if (ch < 'a' || ch > 'z') {
        		return "Command ERROR!";
        	}
        }

        boolean variableappear = false;
        Polynomial newpoly = new Polynomial();

        for (int i = 0; i < poly.size(); i++ ) 
        {
            Monomial longj = new Monomial(poly.getMonomial(i));
            newpoly.addMonomial(longj);
        }

        for (int i = 0; i < newpoly.size(); i++) {
            Monomial mono = newpoly.getMonomial(i);
            if (mono.getVariable().containsKey(variable)) {
                variableappear = true;
                mono.setCoeff(mono.getCoeff() * mono.getVariable().get(variable));
                if (mono.getVariable().get(variable) - 1 == 0) {
                    mono.getVariable().remove(variable);
                } else {
                    mono.getVariable().replace(variable,
                            mono.getVariable().get(variable) - 1);
                }

            } else {
                newpoly.removeMonomial(i);
                i--;
            }
        }
        if (variableappear) {
            return newpoly.toString();
        } else {
            return "Variable not found!";
        }
    }
}

