package com.mengbai.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author zcc
 * @version 2021/04/12/14:47
 * @description <描述>
 * @create 2021/04/12/14:47
 */
@Data
@Document("User")
public class User {
	@Id
	private String id;
	private String name;
	private Integer age;
	private String email;
	private String createDate;
}
