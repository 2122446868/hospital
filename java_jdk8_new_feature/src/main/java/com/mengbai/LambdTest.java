package com.mengbai;

import org.junit.Test;

import java.sql.SQLOutput;
import java.util.Comparator;
import java.util.function.Consumer;

/**
 * LambdTest
 * Lambd表达式的使用
 * 举例   (o1,o2) -> Inter.compare(o1,o2);
 * 格式：
 * ->:lambd操作符 或  箭头操作符
 * ->左边：lanbd的形参列表 （其实就是借口中的抽象方法的形参列表）
 * ->右边: lambd体 （其实就是重写的抽象方法的方法体）
 * 总结：
 * ->左边： lambda形参列表的参数类型可以省略（类型推断） ，如果lambda形参列表只有一个参数，大括号可以省略
 * ->右边: lambd体应该用｛｝包住，如果lambd体只有一条执行语句（可能是return语句），｛｝和return 可以省略
 *
 * @author mengbai
 * @version [版本号, 2021/4/20/12:05]
 */
public class LambdTest {

	/***
	 * 语法1：无参数、无返回值
	 */
	@Test
	public void lambdTest1() {
		System.out.println("********传统写法********");
		Runnable runnable1 = new Runnable() {
			@Override
			public void run() {
				System.out.println("123");
			}

		};
		runnable1.run();
		System.out.println("********lambd写法********");
		Runnable runnable2 = () -> {
			System.out.println("1213123133");
		};
		runnable2.run();
	}

	;


	/***
	 * 语法2：有一个参数、无返回值
	 */
	@Test
	public void lambdTest2() {
		System.out.println("********传统写法********");
		Consumer<String> consumer1 = new Consumer<String>() {
			@Override
			public void accept(String s) {
				System.out.println(s);
			}
		};
		consumer1.accept("谎言和誓言的区别");

		System.out.println("********lambd写法********");
		Consumer<String> consumer2 = (String s) -> {
			System.out.println(s);
		};
		consumer2.accept("一个是听的人当了真，一个是说的人当了真");
	}


	/***
	 * 语法3：数据类型可以省略（类型推断）
	 */
	@Test
	public void lambdTest3() {
		System.out.println("********语法2写法********");
		Consumer<String> consumer1 = (String s) -> {
			System.out.println(s);
		};
		consumer1.accept("一个是听的人当了真，一个是说的人当了真");

		System.out.println("********语法3写法********");
		Consumer<String> consumer2 = (s) -> {
			System.out.println(s);
		};
		consumer2.accept("一个是听的人当了真，一个是说的人当了真");
	}


	/***
	 * 语法4：Lambd只有一个参数时，参数的小括号可以省略
	 */
	@Test
	public void lambdTest4() {
		System.out.println("********语法3********");
		Consumer<String> consumer1 = (s) -> {
			System.out.println(s);
		};
		consumer1.accept("一个是听的人当了真，一个是说的人当了真");

		System.out.println("********优化语法3********");
		Consumer<String> consumer2 = s -> {
			System.out.println(s);
		};
		consumer2.accept("一个是听的人当了真，一个是说的人当了真");
	}

	/***
	 * 语法格式5：lambda需要连个或以上参数、多条执行语句、并且可以有返回值
	 */
	@Test
	public void lambdTest5() {
		System.out.println("********传统写法********");
		Comparator<Integer> comparator1 = new Comparator<Integer>() {
			@Override
			public int compare(Integer o1, Integer o2) {
				System.out.println(o1);
				System.out.println(o2);
				return o1.compareTo(o2);
			}

		};
		System.out.println(comparator1.compare(12, 11));
		System.out.println("********lambd写法********");
		Comparator<Integer> comparator2 = (o1, o2) -> {
			System.out.println(o1);
			System.out.println(o2);
			return o1.compareTo(o2);
		};
		System.out.println(comparator2.compare(11, 11));


	}


	/***
	 * 语法格式6：当lambda体只有一条语句时，return 与 ｛｝若有 都可以省略
	 */
	@Test
	public void lambdaTest6() {
		System.out.println("********传统写法********");
		Comparator<Integer> comparator1 = new Comparator<Integer>() {
			@Override
			public int compare(Integer o1, Integer o2) {
				return o1.compareTo(o2);
			}

		};
		System.out.println(comparator1.compare(12, 11));
		System.out.println("********lambd写法********");
		Comparator<Integer> comparator2 = (o1, o2) -> o1.compareTo(o2);

		System.out.println(comparator2.compare(11, 11));

	}
}






