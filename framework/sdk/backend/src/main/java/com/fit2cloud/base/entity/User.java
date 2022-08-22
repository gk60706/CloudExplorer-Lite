package com.fit2cloud.base.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 用户
 * </p>
 *
 * @author fit2cloud
 * @since 
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("user")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId("id")
    private String id;

    @TableField("username")
    private String username;

    @TableField("_name")
    private String name;

    @TableField("enabled")
    private Boolean enabled;

    @TableField("email")
    private String email;

    @TableField("phone")
    private String phone;

    @TableField("password")
    private String password;
}