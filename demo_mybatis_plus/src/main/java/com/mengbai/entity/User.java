package com.mengbai.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * User
 * <功能详细描述>
 *
 * @author 赵长春
 * @version [版本号, 2021/3/31 18:02]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    //    这是默认的 不写也是这个
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;
    private String name;
    private Integer age;
    private String email;

//    自动填充
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private java.util.Date updateTime;

//    用于乐观锁 版本
    @TableField(fill = FieldFill.INSERT)
    @Version
    private  Integer version;


    @TableLogic
    private Integer deleted;
}
