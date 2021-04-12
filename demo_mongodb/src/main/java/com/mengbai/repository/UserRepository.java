package com.mengbai.repository;

import com.mengbai.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @author zcc
 * @version 2021/04/12/16:42
 * @description <描述>
 * @create 2021/04/12/16:42
 */
@Repository
public interface UserRepository extends MongoRepository<User,String> {
}
