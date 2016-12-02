package test;

import static org.junit.Assert.*;

import com.se.lab.CommandView;
import org.junit.Test;

public class TestCase1 {

	@Test
	public void test1() {
		CommandView view = new CommandView();
		view.command("x");
		String result = view.command("!d/d x");
		assertEquals("1",result);
	}
	
	@Test
	public void test2() {
		CommandView view = new CommandView();
		view.command("3var^3-2var*x+x");
		String result = view.command("!d/d var");
		assertEquals("9*var^2-2*x",result);
	}
	
	@Test
	public void test3() {
		CommandView view = new CommandView();
		view.command("2var + 3var * x + x");
		String result = view.command("!d/d var");
		assertEquals("2+3*x",result);
	}
	
	@Test
	public void test4() {
		CommandView view = new CommandView();
		view.command("2var*y+3var^3*x*y-x*y");
		String result = view.command("!d/d var");
		assertEquals("2*y+9*x*y*var^2",result);
	}
	
	@Test
	public void test5() {
		CommandView view = new CommandView();
		String result = view.command("!d/d x");
		assertEquals("Command ERROR!",result);
	}
	
	@Test
	public void test6() {
		CommandView view = new CommandView();
		view.command("x");
		String result = view.command("!d/d y");
		assertEquals("Variable not found!",result);
	}

	@Test
	public void test7() {
		CommandView view = new CommandView();
		view.command("x");
		String result = view.command("!d/dx");
		assertEquals("Command ERROR!",result);
	}
	
	@Test
	public void test8() {
		CommandView view = new CommandView();
		view.command("2xy");
		String result = view.command("!d/d x y");
		assertEquals("Command ERROR!",result);
	}
	
	@Test
	public void test9() {
		CommandView view = new CommandView();
		view.command("2*x*y");
		String result = view.command("!d/d x y");
		assertEquals("Command ERROR!",result);
	}
	
	@Test
	public void test10() {
		CommandView view = new CommandView();
		view.command("2*xy");
		String result = view.command("d/dxy");
		assertEquals("Syntax error!",result);
	}
}
