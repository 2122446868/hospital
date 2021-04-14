package com.mengbai;

import com.mengbai.entity.User;
import com.mengbai.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.mapping.TextScore;

import java.util.*;

/**
 * @author zcc
 * @version 2021/04/12/16:44
 * @description <描述>
 * @create 2021/04/12/16:44
 */
@SpringBootTest
public class MongoRepositoryApplicationTests {

	@Autowired
	private UserRepository userRepository;

	/***
	 * 添加
	 */
	@Test
	void createUser() {
		User user = new User();
		user.setEmail("2122446868@qq.com");
		user.setAge(12);
		user.setName("晓红");

		User updateUser = userRepository.save(user);
		System.out.println(updateUser);
	}

	/***
	 * 查询所有
	 */
	@Test
	void findAll() {
		List<User> userList = userRepository.findAll();
		System.out.println(userList);

	}

	/***
	 * 根据id查询
	 */
	@Test
	void findById() {
		User user = userRepository.findById("6074090fd3e4e506d01fbf9b").get();
		System.out.println(user);
	}

	/***
	 * 条件查询
	 */
	@Test
	void findUserList() {
		User user = new User();
		user.setName("李四");
		Example<User> userExample = Example.of(user);
		List<User> userList = userRepository.findAll(userExample);
		System.out.println(userList);

	}

	/***
	 * 模糊查询
	 */
	@Test
	void findLikeUserList() {
		//创建匹配器，即如何使用查询条件
		ExampleMatcher matcher =
				ExampleMatcher
						.matching()
						.withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)//改变默认字符串匹配方式：模糊查询
						.withIgnoreCase(true);//改变默认大小写忽略方式：忽略大小写
		User user = new User();
		user.setName("晓");
		Example<User> userExample = Example.of(user, matcher);
		List<User> userList = userRepository.findAll(userExample);
		System.out.println(userList);
	}

	/***
	 * 分页查询
	 */
	@Test
	void findUserPage() {

		Pageable pageable = PageRequest.of(0, 5, Sort.by(Sort.Direction.DESC, "age"));

		ExampleMatcher matcher = ExampleMatcher.matching().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING).withIgnoreCase(true);
		User user = new User();
		user.setName("晓");
		Example<User> example = Example.of(user, matcher);
		Page<User> userPage = userRepository.findAll(example, pageable);
		System.out.println(userPage);
	}

	/***
	 * 修改
	 */
	@Test
	void updateUser() {
		User user = userRepository.findById("6074090fd3e4e506d01fbf9b").get();
		user.setName("张三_1");
		user.setAge(25);
		user.setEmail("883220990@qq.com");
		User save = userRepository.save(user);
		System.out.println(save);
	}

	//删除
	@Test
	public void delete() {
		userRepository.deleteById("5ffbfe8197f24a07007bd6ce");
	}



}
