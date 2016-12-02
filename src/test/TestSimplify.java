package test;

import static org.junit.Assert.*;

import org.junit.Test;

import com.se.lab.CommandView;

public class TestSimplify {
	@Test
	public void test1() {
		CommandView view = new CommandView();
		view.command("5*x");
		String result = view.command("!simplify");
		assertEquals("5*x",result);
	}
	
	@Test
	public void test2() {
		CommandView view = new CommandView();
		String result = view.command("!simplify z=2");
		assertEquals("Command ERROR!",result);
	}
	
	@Test
	public void test3() {
		CommandView view = new CommandView();
		view.command("x*3");
		String result = view.command("!simplify y=1");
		assertEquals("Variable not found!",result);
	}
	
	@Test
	public void test4() {
		CommandView view = new CommandView();
		view.command("x*3*z");
		String result = view.command("!simplify x=4");
		assertEquals("12*z",result);
	}
	
	@Test
	public void test5() {
		CommandView view = new CommandView();
		view.command("x*x*y");
		String result = view.command("!simplify x=3");
		assertEquals("9*y",result);
	}
}
