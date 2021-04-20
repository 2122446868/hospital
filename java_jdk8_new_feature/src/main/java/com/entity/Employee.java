package com.entity;

import java.util.Objects;

/**
 * Employee
 * <功能描述>
 *
 * @author mb
 * @version [版本号, 2021/4/20/15:22]
 */
public class Employee {
	private Integer id ;
	private String name;
	private Integer age;
	private  long salary;

	public Employee() {
	}

	public Employee(Integer id, String name, Integer age, long salary) {
		this.id = id;
		this.name = name;
		this.age = age;
		this.salary = salary;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public long getSalary() {
		return salary;
	}

	public void setSalary(long salary) {
		this.salary = salary;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Employee)) return false;
		Employee employee = (Employee) o;
		return getSalary() == employee.getSalary() && Objects.equals(getId(), employee.getId()) && Objects.equals(getName(), employee.getName()) && Objects.equals(getAge(), employee.getAge());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getId(), getName(), getAge(), getSalary());
	}

	@Override
	public String toString() {
		return "Employee{" +
				"id=" + id +
				", name='" + name + '\'' +
				", age=" + age +
				", salary=" + salary +
				'}';
	}
}
