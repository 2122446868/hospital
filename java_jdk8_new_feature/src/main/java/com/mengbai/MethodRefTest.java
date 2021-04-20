package com.mengbai;

import com.entity.Employee;
import org.junit.Test;

import java.io.PrintStream;
import java.util.Comparator;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * MethodRefTest
 * 方法引用
 * <p>
 * 使用情景：当要传递给Lambda体的操作，已经有实现的方法了，可以使用方法引用
 * 使用格式    类（或对象）：：方法名
 * 具体分为三种情况：
 * 情况1  对象::非静态方法
 * 情况2  类::静态方法
 * 情况3  类::非静态方法
 * 使用要求：要求接口中的抽象方法的形参列表和返回值类型，要和方法引用的方法的形参列表和返回值类型相同！(针对情况1  情况2)
 *
 * @author megnbai
 * @version [版本号, 2021/4/20/15:07]
 */
public class MethodRefTest {

	/***
	 * 情况1：对象::实例方法
	 * Consumer  中的 void accept(T t)
	 * PrintStream 中的  void pringln(T t)
	 */
	@Test
	public void test1() {
		System.out.println("********传统写法********");
		Consumer<String> consumer1 = new Consumer<String>() {
			@Override
			public void accept(String s) {
				System.out.println(s);
			}
		};
		consumer1.accept("你是猪！");
		System.out.println("********lambda写法********");
		Consumer<String> consumer2 = s -> System.out.println(s);
		consumer2.accept("你才是猪！");

		System.out.println("********方法引用********");
		PrintStream out = System.out;
		Consumer<String> consumer3 = out::println;
		// 升级版
		// Consumer<String> consumer3 =  System.out::println;
		consumer3.accept("你是！");
	}

	/***
	 *
	 * Supplier 中的T get()
	 * Employee 中的String getName()
	 */
	@Test
	public void test2() {
		System.out.println("********传统写法********");
		Employee employee1 = new Employee(1001, "TOM", 21, 4000);
		Supplier supplier1 = new Supplier() {
			@Override
			public Object get() {
				return employee1.getName();
			}
		};
		System.out.println(supplier1.get());

		System.out.println("********lambda写法********");
		Supplier supplier2 = () -> employee1.getName();
		System.out.println(supplier2.get());

		System.out.println("********方法引用********");
		Supplier supplier3 = employee1::getName;
		System.out.println(supplier3.get());
	}

	/***
	 * 情况2：类::静态方法
	 * Comparator 中的 int  compare(T t1,T t2)
	 * Integer 中的 int compare(T t1,T t2)
	 */
	@Test
	public void test3() {
		System.out.println("********传统写法********");
		Comparator<Integer> comparator1 = new Comparator<Integer>() {
			@Override
			public int compare(Integer o1, Integer o2) {
				return Integer.compare(o1, o2);
			}
		};
		System.out.println(comparator1.compare(11, 12));

		System.out.println("********lambda写法********");
		Comparator<Integer> comparator2 = ((o1, o2) -> Integer.compare(o1, o2));
		System.out.println(comparator2.compare(12, 13));

		System.out.println("********方法引用********");
		Comparator<Integer> comparator3 = Integer::compare;
		System.out.println(comparator3.compare(13, 14));

	}

	/***
	 * 情况3：类：：实例方法（有难度）
	 * Compartor 中的  int compare(T t1,T t2)
	 * String   中的 int t1.cpmpareTo(t2)
	 */
	@Test
	public void  test4(){
		System.out.println("********传统写法********");
		Comparator<String> comparator1 = new Comparator<String>() {
			@Override
			public int compare(String o1, String o2) {
				return o1.compareTo(o2);
			}
		};
		System.out.println(comparator1.compare("abc", "qer"));

		System.out.println("********lambda写法********");
		Comparator<String> comparator2 = (s1,s2) ->s1.compareTo(s2);
		System.out.println(comparator2.compare("3", "2"));

		System.out.println("********方法引用********");
		Comparator<String> comparator3 =String::compareTo;
		System.out.println(comparator3.compare("2", "3"));
	}


}
