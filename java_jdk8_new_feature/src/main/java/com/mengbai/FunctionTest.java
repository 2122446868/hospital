package com.mengbai;

import org.junit.Test;

import java.util.function.Consumer;

/**
 * FunctionTest
 * <函数式接口>
 * java内置的四大核心函数式接口
 * 消费型接口 Consumer<T>  void(T t)
 * 供给型接口 supplier<T> T get()
 * 函数型接口 Function<T,T> R apply(T t)
 * 断定型接口 Predicate<T>  boolean test(T t)
 *
 * @author mengbai
 * @version [版本号, 2021/4/20/14:27]
 */
public class FunctionTest {

	@Test
	public void testHaplyTime() {
		System.out.println("********传统写法********");
		haplyTime(100, new Consumer<Double>() {
			@Override
			public void accept(Double aDouble) {
				System.out.println("想暴富" + aDouble);

			}
		});
		System.out.println("********Lambda写法*******");
		haplyTime(100, money -> System.out.println("我也想" + money));
	}

	public void haplyTime(double money, Consumer<Double> con) {
		con.accept(money);
	}

}
