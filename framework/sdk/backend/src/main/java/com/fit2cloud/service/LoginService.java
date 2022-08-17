package com.fit2cloud.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fit2cloud.base.entity.User;
import com.fit2cloud.base.mapper.UserMapper;
import com.fit2cloud.base.service.IUserService;
import com.fit2cloud.common.utils.JwtTokenUtils;
import com.fit2cloud.common.utils.MD5Util;
import com.fit2cloud.dto.UserDto;
import com.fit2cloud.request.LoginRequest;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class LoginService {

    @Resource
    private IUserService userService;

    public String login(LoginRequest loginRequest) {

        if (StringUtils.isBlank(loginRequest.getUsername())) {
            throw new RuntimeException("用户名为空");
        }

        if (StringUtils.isBlank(loginRequest.getPassword())) {
            throw new RuntimeException("密码为空");
        }

        UserDto user = getUserByIdOrEmail(loginRequest.getUsername());
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }

        if (!checkPassword(user, loginRequest.getPassword())) {
            throw new RuntimeException("密码错误");
        }

        return JwtTokenUtils.createJwtToken(user);
    }

    public UserDto getUserByIdOrEmail(String username) {
        User user = getUserById(username);
        if (user == null) {
            user = getUserByEmail(username);
        }

        UserDto dto = new UserDto();
        BeanUtils.copyProperties(user, dto);
        return dto;
    }


    public User getUserById(String userId) {
        return userService.getOne(
                new LambdaQueryWrapper<User>()
                        .eq(User::getUsername, userId)
        );
    }

    public User getUserByEmail(String email) {
        return userService.getOne(
                new LambdaQueryWrapper<User>()
                        .eq(User::getEmail, email)
        );
    }

    public boolean checkPassword(User user, String password) {
        UserMapper userMapper = (UserMapper) userService.getBaseMapper();
        return userMapper.checkPassword(user.getUsername(), MD5Util.md5(password));
    }

}
