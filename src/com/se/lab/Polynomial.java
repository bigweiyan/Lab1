package com.se.lab;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
//import java.io.*;

/**
 * 多项式类<br>
 * 封装了一些多项式方法，如解析、合并、求导、求值。并包含了主方法
 */
public class Polynomial {

    /**
     * 多项式（单项式列表）.
     */
    private static ArrayList<Monomial> poly;
    
    public static void setPoly(ArrayList<Monomial> thePoly) {
    	poly = thePoly;
    }
    
    public static ArrayList<Monomial> getPoly() {
    	return poly;
    }

    /**
     * 主方法，程序入口.
     *
     * @param args args需注释
     */
    public static void main(final String[] args) {

        boolean stay = true;
        Scanner scan = new Scanner(System.in, "utf8");

        //test huge file input
        //try{scan=new Scanner(new FileInputStream("input.txt"));}
        // catch(Exception e){}
        //Caculate time
        //long totTime = 0;
        //long STime = System.currentTimeMillis();
        while (stay) {

            String line;
            line = scan.nextLine();

            while (line.equals("")) {
                line = scan.nextLine();
            }

            if (line.equals("!bye")) {
                stay = false;
            } else if (line.charAt(0) == '!') {
                //long startTime = System.currentTimeMillis();
                System.out.println(command(line));
                //long endTime = System.currentTimeMillis();

                //System.out.println("Command StartTime = " + startTime);
                //System.out.println("Command EndTime = " + endTime);
                //System.out.println("Command UseTime = "
                //+ (float)(endTime-startTime)/1000.0 + 's');
                //totTime+=endTime-startTime;
            } else {
                //long startTime = System.currentTimeMillis();
                poly = expression(line);
                //long endTime = System.currentTimeMillis();

                //System.out.println("Expression StartTime = " + startTime);
                //System.out.println("Expression EndTime = " + endTime);
                //System.out.println("Expression UseTime = "
                // + (float)(endTime-startTime)/1000.0 + 's');
                //totTime+=endTime-startTime;
            }
        }
        //long ETime = System.currentTimeMillis();
        //System.out.println("Start Time = " + STime);
        //System.out.println("End Time = " + ETime);
        //System.out.println("TOT Time = " + (float)(ETime-STime)/1000.0 + 's');
        //System.out.println("TOT Run Time = "+(float)totTime/1000.0 + 's');
        scan.close();
    }

    /**
     * 解析命令并调用化简和求导函数.
     *
     * @param longs1 命令
     */
    public static String command(final String longs1) {
        String longs = longs1;
        longs = longs.substring(1).trim();
        boolean iscommand = false;

        if (longs.startsWith("d/d ")) {
            iscommand = true;
            longs = longs.substring(4).trim();
            return derivative(poly, longs);
        } else {
            HashMap<String, Float> map = new HashMap<String, Float>();
            iscommand = true;
            if (longs.startsWith("simplify")) {
                longs = longs.substring(8).trim();
                while (!longs.equals("")) {
                    int spacepos = longs.indexOf(' ');
                    if (spacepos == -1) {
                        spacepos = longs.length();
                    }

                    int equalpos = longs.indexOf('=');
                    if (equalpos == -1) {
                        equalpos = longs.length();
                    }

                    if (equalpos < spacepos) {
                        if (equalpos >= longs.length() - 1) {
                            iscommand = false;
                            break;
                        }
                        String var = longs.substring(0, equalpos).trim();
                        String value = longs.substring(equalpos + 1,
                                spacepos).trim();
                        char[] cvalue = value.toCharArray();
                        for (char i : cvalue) {
                            if (i != '-' && i != '.' && (i < '0' || i > '9')) {
                                iscommand = false;
                                break;
                            }
                        }
                        float longa;
                        try {
                            longa = Float.parseFloat(value);
                        } catch (NumberFormatException e) {
                            return "Command ERROR!";
                        }
                        map.put(var, longa);
                        if (spacepos == longs.length()) {
                            break;
                        } else {
                            longs = longs.substring(spacepos + 1);
                        }
                    } else {
                        iscommand = false;
                        break;
                    }
                }
            } else {
                iscommand = false;
            }

            if (iscommand) {
                return simplify(poly, map);
            } else {
                return "Command ERROR!";
            }
        }
    }

    /**
     * 读入字符串并转化为表达式,并输出表达式或错误.
     *
     * @param longs 输入表达式
     * @return 多项式
     */
    public static ArrayList<Monomial> expression(final String longs) {
        final int INPUTNUM = 0;
        final int INPUTVAR = 1;
        final int INPUTMUL = 2;
        final int INPUTNEW = 3;
        final int INPUTPOW = 4;
        int state = INPUTNEW;
        int tempnum = 0;
        String tempvar = "";
        Monomial mono = new Monomial();
        ArrayList<Monomial> poly = new ArrayList<Monomial>();

        int longi = 0;
        if (longs.charAt(0) == '-') {
            mono.longk *= -1;
            longi++;
        }

        for (; longi < longs.length(); longi++) {
            char longch = longs.charAt(longi);
            if (longch == ' ' || longch == '\t') {
                continue;
            }
            switch (state) {
                case INPUTNUM:
                    if (longch == '*') {
                        mono.longk *= tempnum;
                        tempnum = 0;
                        state = INPUTMUL;
                    } else if (longch >= '0' && longch <= '9') {
                        tempnum = tempnum * 10 + (longch - '0');
                    } else if (longch == '+' || longch == '-') {
                        mono.longk *= tempnum;
                        tempvar = "";
                        tempnum = 0;
                        poly.add(mono);
                        mono = new Monomial();
                        state = INPUTNEW;
                        if (longch == '-') {
                            mono.longk *= -1;
                        }
                    } else if (longch >= 'a' && longch <= 'z'
                            || longch >= 'A' && longch <= 'Z') {
                        mono.longk *= tempnum;
                        tempnum = 0;
                        tempvar = longch + "";
                        state = INPUTVAR;
                    } else {
                        System.out.println("Syntax error!");
                        return null;
                    }
                    break;
                case INPUTVAR:
                    if (longch >= 'a' && longch <= 'z' || longch >= 'A' && longch <= 'Z') {
                        tempvar += longch;
                    } else if (longch == '+' || longch == '-') {
                        if (mono.variable.containsKey(tempvar)) {
                            mono.variable.put(tempvar,
                                    mono.variable.get(tempvar) + 1);
                        } else {
                            mono.variable.put(tempvar, 1);
                        }
                        tempvar = "";
                        tempnum = 0;
                        poly.add(mono);
                        mono = new Monomial();
                        state = INPUTNEW;
                        if (longch == '-') {
                            mono.longk *= -1;
                        }
                    } else if (longch == '*') {
                        if (mono.variable.containsKey(tempvar)) {
                            mono.variable.put(tempvar,
                                    mono.variable.get(tempvar) + 1);
                        } else {
                            mono.variable.put(tempvar, 1);
                        }
                        tempvar = "";
                        state = INPUTMUL;
                    } else if (longch == '^') {
                        state = INPUTPOW;
                    } else {
                        System.out.println("Syntax error!");
                        return null;
                    }
                    break;
                case INPUTMUL:
                    if (longch >= '0' && longch <= '9') {
                        tempnum = longch - '0';
                        state = INPUTNUM;
                    } else if (longch >= 'a' && longch <= 'z'
                            || longch >= 'A' && longch <= 'Z') {
                        tempvar = "" + longch;
                        state = INPUTVAR;
                    } else {
                        System.out.println("Syntax error!");
                        return null;
                    }
                    break;
                case INPUTNEW:
                    if (longch >= '0' && longch <= '9') {
                        tempnum = tempnum * 10 + (longch - '0');
                        state = INPUTNUM;
                    } else if (longch >= 'a' && longch <= 'z'
                            || longch >= 'A' && longch <= 'Z') {
                        tempvar += longch;
                        state = INPUTVAR;
                    } else {
                        System.out.println("Syntax error!");
                        return null;
                    }
                    break;
                case INPUTPOW:
                    if (longch == '*' || longch == '+' || longch == '-') {
                        if (tempnum == 0) {
                            System.out.println("Syntax error!");
                            return null;
                        }

                        Integer curPow = mono.variable.get(tempvar);
                        int cur = curPow == null ? 0 : curPow;
                        mono.variable.put(tempvar, tempnum + cur);
                        if (longch == '*') {
                            tempvar = "";
                            tempnum = 0;
                            state = INPUTMUL;

                        } else {
                            tempvar = "";
                            tempnum = 0;
                            poly.add(mono);
                            mono = new Monomial();
                            state = INPUTNEW;
                            if (longch == '-') {
                                mono.longk *= -1;
                            }
                        }
                    } else if (longch >= '0' && longch <= '9') {
                        tempnum = tempnum * 10 + (longch - '0');
                    } else {
                        System.out.println("Syntax error!");
                        return null;
                    }
                    break;
                default:
                    return null;
            }
        }
        switch (state) {
            case INPUTNEW:
            case INPUTMUL:
                System.out.println("Syntax error!");
                return null;
            case INPUTVAR:
                if (mono.variable.containsKey(tempvar)) {
                    mono.variable.put(tempvar,
                            mono.variable.get(tempvar) + 1);
                } else {
                    mono.variable.put(tempvar, 1);
                }
                break;
            case INPUTNUM:
                mono.longk *= tempnum;
                break;
            case INPUTPOW:
                if (tempnum == 0) {
                    System.out.println("Syntax error!");
                    return null;
                }
                Integer curPow = mono.variable.get(tempvar);
                int cur = curPow == null ? 0 : curPow;
                mono.variable.put(tempvar, tempnum + cur);
                break;
            default:
                break;
        }
        poly.add(mono);
        poly = merge(poly);
        System.out.println(printPoly(poly));
        return poly;
    }

    /**
     * 将多项式进行带入化简，并输出结果表达式或错误.
     *
     * @param poly 多项式
     * @param map  代入的变量及其值
     */
    public static String simplify(
            final List<Monomial> poly, final HashMap<String, Float> map) {
        ArrayList<Monomial> newpoly = new ArrayList<Monomial>();

        for (Monomial i : poly) {
            Monomial longj = new Monomial(i);
            newpoly.add(longj);
        }

        for (String variable : map.keySet()) {
            //System.out.println("TEST+"+variable+"+");
            boolean appear = false;
            for (int i = 0; i < newpoly.size(); i++) {
                Monomial mono = newpoly.get(i);

                if (mono.variable.containsKey(variable)) {
                    appear = true;
                    int value = mono.variable.get(variable);
                    for (int tims = 1; tims <= value; tims++) {
                        mono.longk *= map.get(variable);
                    }
                    mono.variable.remove(variable);
                }
            }
            if (!appear) {
                return "Variable not found!";
            }
        }
        return printPoly(newpoly);
    }

    /**
     * 将多项式进行求导运算.
     *
     * @param poly     多项式
     * @param variable 求导变量
     */
    public static String derivative(final ArrayList<Monomial> poly,
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
    	//copy the arraylist
        boolean variableappear = false;
        ArrayList<Monomial> newpoly = new ArrayList<Monomial>();
        for (Monomial i : poly) {
            Monomial longj = new Monomial(i);
            newpoly.add(longj);
        }

        for (int i = 0; i < newpoly.size(); i++) {
            Monomial mono = newpoly.get(i);
            if (mono.variable.containsKey(variable)) {
                variableappear = true;
                mono.longk *= mono.variable.get(variable);
                if (mono.variable.get(variable) - 1 == 0) {
                    mono.variable.remove(variable);
                } else {
                    mono.variable.replace(variable,
                            mono.variable.get(variable) - 1);
                }

            } else {
                newpoly.remove(i);
                i--;
            }
        }
        if (variableappear) {
            return printPoly(newpoly);
        } else {
            return "Variable not found!";
        }
    }

    /**
     * 将多项式进行格式化输出.
     *
     * @param poly1 多项式
     */
    public static String printPoly(final ArrayList<Monomial> poly1) {
        ArrayList<Monomial> poly = poly1;
        poly = merge(poly);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < poly.size(); i++) {
            Monomial mono = poly.get(i);
            if (mono.longk > 0 && i > 0) {
                //System.out.print('+');
                sb.append('+');
            }
            boolean flag = false;
            if (mono.longk != 1 || mono.variable.isEmpty()) {
                if (mono.longk - (int) mono.longk == 0) {
                    //System.out.print((int) mono.longk);
                    sb.append((int) mono.longk);
                } else {
                    //System.out.print(mono.longk);
                    sb.append(mono.longk);
                }
                flag = true;
            }
            for (HashMap.Entry<String, Integer> entry
                    : mono.variable.entrySet()) {
                //System.out.print();
                sb.append((flag ? '*' : "") + entry.getKey());
                flag = true;
                if (entry.getValue() != 1) {
                    //System.out.print('^' + "" + entry.getValue());
                    sb.append('^' + "" + entry.getValue());
                }
            }
        }
        return sb.toString();
    }

    /**
     * 合并同类项.
     *
     * @param poly 多项式（可能含同类项）
     * @return 无同类项的多项式
     */
    public static ArrayList<Monomial> merge(final ArrayList<Monomial> poly) {
        for (int i = 0; i < poly.size(); i++) {
            Monomial mono = poly.get(i);
            boolean flag = false;
            for (int j = i + 1; j < poly.size(); j++) {
                if (mono.variable.equals(poly.get(j).variable)) {
                    poly.get(j).longk += mono.longk;
                    flag = true;
                    break;
                }
            }
            if (flag) {
                poly.remove(mono);
                i--;
            }
        }
        return poly;
    }

}

/**
 * 单项式类，封装了系数和变量.
 */
class Monomial {
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
