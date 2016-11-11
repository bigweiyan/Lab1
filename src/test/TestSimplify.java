package test;

import static org.junit.Assert.*;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Test;

import com.se.lab.Monomial;
import com.se.lab.Polynomial;

public class TestSimplify {
	@Test
	public void test1() {
		List<Monomial> poly = Polynomial.expression("5*x");
		HashMap<String, Float> map = new HashMap<String, Float>();
		String result = Polynomial.simplify(poly, map);
		assertEquals("5*x",result);
	}
	
	@Test
	public void test2() {
		List<Monomial> poly = new ArrayList<Monomial>();
		HashMap<String, Float> map = new HashMap<String, Float>();
		map.put("z", Float.parseFloat("2"));
		String result = Polynomial.simplify(poly, map);
		assertEquals("Variable not found!",result);
	}
	
	@Test
	public void test3() {
		List<Monomial> poly = Polynomial.expression("x*3");
		HashMap<String, Float> map = new HashMap<String, Float>();
		map.put("y", Float.parseFloat("1"));
		String result = Polynomial.simplify(poly, map);
		assertEquals("Variable not found!",result);
	}
	
	@Test
	public void test4() {
		List<Monomial> poly = Polynomial.expression("x*3*z");
		HashMap<String, Float> map = new HashMap<String, Float>();
		map.put("x", Float.parseFloat("4"));
		String result = Polynomial.simplify(poly, map);
		assertEquals("12*z",result);
	}
	
	@Test
	public void test5() {
		List<Monomial> poly = Polynomial.expression("x*x*y");
		HashMap<String, Float> map = new HashMap<String, Float>();
		map.put("x", Float.parseFloat("3"));
		String result = Polynomial.simplify(poly, map);
		assertEquals("9*y",result);
	}
}
