package com.mengbai.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.mengbai.entity.User;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * UserMapper
 * <功能详细描述>
 *
 * @author 赵长春
 * @version [版本号, 2021/4/1 9:14]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Repository
public interface UserMapper  extends BaseMapper<User> {


}
