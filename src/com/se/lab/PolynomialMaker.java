package com.se.lab;

public class PolynomialMaker {
	/**
     * 读入字符串并转化为表达式,并输出表达式或错误.
     *
     * @param longs 输入表达式
     * @return 多项式
     */
	public Polynomial make(final String longs)
	{
		Polynomial poly = new Polynomial();

        final int INPUTNUM = 0;
        final int INPUTVAR = 1;
        final int INPUTMUL = 2;
        final int INPUTNEW = 3;
        final int INPUTPOW = 4;
        int state = INPUTNEW;
        int tempnum = 0;
        String tempvar = "";
        Monomial mono = new Monomial();


        int longi = 0;
        if (longs.charAt(0) == '-') {
            mono.setCoeff(mono.getCoeff()*-1);
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
                        mono.setCoeff(mono.getCoeff() * tempnum);
                        tempnum = 0;
                        state = INPUTMUL;
                    } else if (longch >= '0' && longch <= '9') {
                        tempnum = tempnum * 10 + (longch - '0');
                    } else if (longch == '+' || longch == '-') {
                        mono.setCoeff(mono.getCoeff()*tempnum);
                        tempvar = "";
                        tempnum = 0;
                        poly.addMonomial(mono);
                        mono = new Monomial();
                        state = INPUTNEW;
                        if (longch == '-') {
                        	mono.setCoeff(mono.getCoeff()*-1);
                        }
                    } else if (longch >= 'a' && longch <= 'z'
                            || longch >= 'A' && longch <= 'Z') {
                    	mono.setCoeff(mono.getCoeff()*tempnum);
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
                        if (mono.getVariable().containsKey(tempvar)) {
                            mono.getVariable().put(tempvar,
                                    mono.getVariable().get(tempvar) + 1);
                        } else {
                            mono.getVariable().put(tempvar, 1);
                        }
                        tempvar = "";
                        tempnum = 0;
                        poly.addMonomial(mono);
                        mono = new Monomial();
                        state = INPUTNEW;
                        if (longch == '-') {
                            mono.setCoeff(mono.getCoeff()*-1);
                        }
                    } else if (longch == '*') {
                        if (mono.getVariable().containsKey(tempvar)) {
                            mono.getVariable().put(tempvar,
                                    mono.getVariable().get(tempvar) + 1);
                        } else {
                            mono.getVariable().put(tempvar, 1);
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

                        Integer curPow = mono.getVariable().get(tempvar);
                        int cur = curPow == null ? 0 : curPow;
                        mono.getVariable().put(tempvar, tempnum + cur);
                        if (longch == '*') {
                            tempvar = "";
                            tempnum = 0;
                            state = INPUTMUL;

                        } else {
                            tempvar = "";
                            tempnum = 0;
                            poly.addMonomial(mono);
                            mono = new Monomial();
                            state = INPUTNEW;
                            if (longch == '-') {
                                mono.setCoeff(mono.getCoeff() * -1);
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
                if (mono.getVariable().containsKey(tempvar)) {
                    mono.getVariable().put(tempvar,
                            mono.getVariable().get(tempvar) + 1);
                } else {
                    mono.getVariable().put(tempvar, 1);
                }
                break;
            case INPUTNUM:
            	mono.setCoeff(mono.getCoeff() * tempnum);
                break;
            case INPUTPOW:
                if (tempnum == 0) {
                    System.out.println("Syntax error!");
                    return null;
                }
                Integer curPow = mono.getVariable().get(tempvar);
                int cur = curPow == null ? 0 : curPow;
                mono.getVariable().put(tempvar, tempnum + cur);
                break;
            default:
                break;
        }
        poly.addMonomial(mono);
        return poly;
    }

}
