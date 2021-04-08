package com.mengbai;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mengbai.entity.User;
import com.mengbai.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * SpringBootApplitionTests
 * <功能详细描述>
 *
 * @author 赵长春
 * @version [版本号, 2021/4/1 9:18]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@SpringBootTest
public class SpringBootApplitionTests {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void findAllUser() {
        List<User> users = userMapper.selectList(null);
        System.out.println(users);

    }

    @Test
    public void addUser() {
        User user = new User();
        user.setAge(20);
        user.setEmail("2122446868@qq.com");
        user.setName("乐观锁123");
        int insert = userMapper.insert(user);

    }

    @Test
    public void updateUserById() {
        User user = new User();
        user.setId(1377465448402292737L);
        user.setName("小梦白");
        int i = userMapper.updateById(user);
        System.out.println(i);

    }

    /***
     * 乐观锁测试
     */
    @Test
    public void testOptimisticLocking() {
        User user = userMapper.selectById(1377468031179227138L);
        user.setAge(11);
        int i = userMapper.updateById(user);
        System.out.println(i);

    }

    @Test
    public void testSelect1() {
        List<User> users = userMapper.selectBatchIds(Arrays.asList(1, 2, 3));
        System.out.println(users);

    }

    //    简单条件查询
    @Test
    public void testSelect2() {
        Map<String, Object> map = new HashMap<>();
        map.put("name", "小梦白");
        map.put("age", "1");
        userMapper.selectByMap(map);

    }

    @Test
    public void testSelect3() {
        Page<User> page = new Page<>(1, 3);
        Page<User> userPage = userMapper.selectPage(page, null);

        //返回对象得到分页所有数据
        long pages = userPage.getPages(); //总页数
        long current = userPage.getCurrent(); //当前页
        List<User> records = userPage.getRecords(); //查询数据集合
        long total = userPage.getTotal(); //总记录数
        boolean hasNext = userPage.hasNext();  //下一页
        boolean hasPrevious = userPage.hasPrevious(); //上一页

        System.out.println(pages);
        System.out.println(current);
        System.out.println(records);
        System.out.println(total);
        System.out.println(hasNext);

    }

    @Test
    public void delete1l(){
        int i = userMapper.deleteById(1377517601808490498L);
        System.out.println(i);

    }

    @Test
    public void delete2(){
        int i = userMapper.deleteBatchIds(Arrays.asList("1377450587186335745", "1377465448402292737"));
        System.out.println(i);

    }

    @Test
    public void delete3(){
        Map<String,Object> map = new HashMap<>();
        map.put("name","乐观锁123");
        int i = userMapper.deleteByMap(map);
        System.out.println(i);
    }


}
