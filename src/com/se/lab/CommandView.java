package com.se.lab;

import java.util.HashMap;
import java.util.Scanner;

public class CommandView {
	private PolynomialMaker polynomialMaker;
	private PolynomialMath polynomialMath;
	private Polynomial poly;
	
	public CommandView()
	{
		polynomialMaker = new PolynomialMaker();
		polynomialMath = new PolynomialMath();
	}
	
	 /**
     * 解析命令并调用化简、求导和解析函数函数.
     *
     * @param longs1 命令
     */
    public String command(String input) {
        String longs = input;
        if(input.length() == 0) return "";
        if(input.charAt(0) != '!'){
            poly = polynomialMaker.make(input);
            if(poly != null) return poly.toString();
            else return "Syntax error!";
        }
        //判断空输入和输入表达式的情况
        
        longs = longs.substring(1).trim();
        boolean iscommand = false;

        if (longs.startsWith("d/d ")) {
            iscommand = true;
            longs = longs.substring(3).trim();
            return polynomialMath.derivative(poly, longs);
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
            	if (poly != null)
            		return polynomialMath.simplify(poly, map);
            	else
            		return "Command ERROR!";
            } else {
                return "Command ERROR!";
            }
        }
    }
    
	public static void main(String[] args) {
		boolean stay = true;
        
        Scanner scan = new Scanner(System.in, "utf8");
        CommandView view = new CommandView();
        while (stay) 
        {
            String line;
            line = scan.nextLine();

            while (line.equals("")) {
                line = scan.nextLine();
            }

            if (line.equals("!bye")) {
                stay = false;
            } else {
                System.out.println(view.command(line));
            }
        }
        scan.close();
	}

}
