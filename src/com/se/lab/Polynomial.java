package com.se.lab;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 多项式类<br>
 * 封装了一些多项式方法，如解析、合并、求导、求值。并包含了主方法
 */
public class Polynomial {
    /**
     * 多项式（单项式列表）.
     */
    private ArrayList<Monomial> monoList;
    
    public Polynomial()
    {
    	monoList = new ArrayList<Monomial>();
    }
    
    public Polynomial(Polynomial poly)
    {
    	monoList = new ArrayList<Monomial>(poly.monoList);
    }

    /**
     * 合并同类项.
     */
    private void merge() 
    {
        for (int i = 0; i < monoList.size(); i++) {
            Monomial mono = monoList.get(i);
            boolean flag = false;
            for (int j = i + 1; j < monoList.size(); j++) {
                if (mono.getVariable().equals(monoList.get(j).getVariable())) {
                	monoList.get(j).setCoeff(monoList.get(j).getCoeff() + mono.getCoeff());
                    flag = true;
                    break;
                }
            }
            if (flag) {
            	monoList.remove(mono);
                i--;
            }
        }
    }

    /**
     * 转化为字符串
     */
    public String toString()
    {
        merge();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < monoList.size(); i++) {
            Monomial mono = monoList.get(i);
            if (mono.getCoeff() > 0 && i > 0) {
                //System.out.print('+');
                sb.append('+');
            }
            boolean flag = false;
            if (mono.getCoeff() != 1 || mono.getVariable().isEmpty()) {
                if (mono.getCoeff() - (int) mono.getCoeff() == 0) {
                    sb.append((int) mono.getCoeff());
                } else {
                    sb.append(mono.getCoeff());
                }
                flag = true;
            }
            for (HashMap.Entry<String, Integer> entry
                    : mono.getVariable().entrySet()) {
                sb.append((flag ? '*' : "") + entry.getKey());
                flag = true;
                if (entry.getValue() != 1) {
                    sb.append('^' + "" + entry.getValue());
                }
            }
        }
        return sb.toString();
    }

    /**
     * 获取单项式个数
     * @return 单项式个数
     */
    public int size()
    {
    	return monoList.size();
    }
    
    /**
     * 添加单项式
     * @param mono 单项式
     */
    public void addMonomial(Monomial mono)
    {
    	monoList.add(mono);
    }
    
    /**
     * 获取index编号的单项式
     * @param index 编号
     * @return index编号的单项式
     */
    public Monomial getMonomial(int index)
    {
    	return monoList.get(index);
    }
    
    /**
     * 删除index编号的单项式
     * @param index 编号
     */
    public void removeMonomial(int index)
    {
    	monoList.remove(index);
    }
}
