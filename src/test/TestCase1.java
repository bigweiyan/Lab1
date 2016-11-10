package test;

import static org.junit.Assert.*;
import com.se.lab.Polynomial;
import org.junit.Test;

public class TestCase1 {

	@Test
	public void test1() {
		Polynomial.setPoly(Polynomial.expression("x"));
		String result = Polynomial.command("!d/d x");
		assertEquals("1",result);
	}
	
	@Test
	public void test2() {
		Polynomial.setPoly(Polynomial.expression("3var^3-2var*x+x"));
		String result = Polynomial.command("!d/d var");
		assertEquals("9*var^2-2*x",result);
	}
	
	@Test
	public void test3() {
		Polynomial.setPoly(Polynomial.expression("2var + 3var * x + x"));
		String result = Polynomial.command("!d/d var");
		assertEquals("2+3*x",result);
	}
	
	@Test
	public void test4() {
		Polynomial.setPoly(Polynomial.expression("2var*y+3var^3*x*y-x*y"));
		String result = Polynomial.command("!d/d var");
		assertEquals("2*y+9*x*y*var^2",result);
	}
	
	@Test
	public void test5() {
		Polynomial.setPoly(null);
		String result = Polynomial.command("!d/d x");
		assertEquals("Command ERROR!",result);
	}
	
	@Test
	public void test6() {
		Polynomial.setPoly(Polynomial.expression("x"));
		String result = Polynomial.command("!d/d y");
		assertEquals("Variable not found!",result);
	}

	@Test
	public void test7() {
		Polynomial.setPoly(Polynomial.expression("x"));
		String result = Polynomial.command("!d/dx");
		assertEquals("Command ERROR!",result);
	}
	
	@Test
	public void test8() {
		Polynomial.setPoly(Polynomial.expression("2xy"));
		String result = Polynomial.command("!d/d x y");
		assertEquals("Command ERROR!",result);
	}
	
	@Test
	public void test9() {
		Polynomial.setPoly(Polynomial.expression("2*x*y"));
		String result = Polynomial.command("!d/d x y");
		assertEquals("Command ERROR!",result);
	}
	
	@Test
	public void test10() {
		Polynomial.setPoly(Polynomial.expression("2*xy"));
		String result = Polynomial.command("d/dxy");
		assertEquals("Command ERROR!",result);
	}
}
