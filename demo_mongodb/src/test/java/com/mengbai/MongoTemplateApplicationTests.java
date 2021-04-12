package com.mengbai;

import com.mengbai.entity.User;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import javax.lang.model.element.VariableElement;
import java.util.*;
import java.util.regex.Pattern;

@SpringBootTest
class MongoTemplateApplicationTests {
	@Autowired
	private MongoTemplate mongoTemplate;

	/***
	 * 查询全部
	 */
	@Test
	void findAll() {
		List<User> all = mongoTemplate.findAll(User.class);
		System.out.println(all);
	}

	/***
	 * 根据ID查询
	 */
	@Test
	void findById() {
		User byId = mongoTemplate.findById("6073e1a5a4b1b90e166ec1b6", User.class);
		System.out.println(byId);
	}

	/**
	 * 条件查询
	 */
	@Test
	void findUserList() {
		Query query = new Query(Criteria.where("name").is("张三"));

		List<User> users = mongoTemplate.find(query, User.class);
		System.out.println(users);
	}

	/***
	 * 模糊查询
	 */
	@Test
	void findLikeUserList() {
		String name = "张";
		String regex = String.format("%s%s%s", "^.*", name, ".*$");
		Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
		Query query = new Query(Criteria.where("name").regex(pattern));
		List<User> users = mongoTemplate.find(query, User.class);
		System.out.println(users);

	}

	//分页查询
	@Test
	public void findUsersPage() {
		String name = "张";
		int pageNo = 1;
		int pageSize = 10;

		Query query = new Query();
		String regex = String.format("%s%s%s", "^.*", name, ".*$");
		Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
		query.addCriteria(Criteria.where("name").regex(pattern));
		int totalCount = (int) mongoTemplate.count(query, User.class);
		List<User> userList = mongoTemplate.find(query.skip((pageNo - 1) * pageSize).limit(pageSize), User.class);

		Map<String, Object> pageMap = new HashMap<>();
		pageMap.put("list", userList);
		pageMap.put("totalCount", totalCount);
		System.out.println(pageMap);
	}

	/***
	 * 添加
	 */
	@Test
	void insertUser() {
		User user = new User();
		user.setAge(20);
		user.setName("王五");
		User insert = mongoTemplate.insert(user);
		System.out.println(insert);

	}

	/***
	 * 修改
	 */
	@Test
	void updateUser() {
		User user = mongoTemplate.findById("6073e1a5a4b1b90e166ec1b6", User.class);
		user.setAge(111);
		user.setEmail("22@qq.com");
		Query query = new Query(Criteria.where("id").is(user.getId()));
		Update update = new Update();
		update.set("name", user.getAge());
		update.set("email", user.getEmail());
		UpdateResult result = mongoTemplate.upsert(query, update, User.class);
		long count = result.getModifiedCount();
		System.out.println(count);
	}

	/***
	 * 删除
	 */
	@Test
	void removeUser(){
		Query query = new Query(Criteria.where("id").is("6073f486b065ba12358911c4"));
		DeleteResult result = mongoTemplate.remove(query, User.class);
		long count = result.getDeletedCount();

		System.out.println(count);
	}


}
