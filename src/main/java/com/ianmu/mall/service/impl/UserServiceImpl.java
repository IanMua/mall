package com.ianmu.mall.service.impl;

import com.ianmu.mall.common.Assert;
import com.ianmu.mall.enums.RoleEnum;
import com.ianmu.mall.exception.MallExceptionEnum;
import com.ianmu.mall.model.dao.UserMapper;
import com.ianmu.mall.model.pojo.User;
import com.ianmu.mall.service.UserService;
import com.ianmu.mall.util.MD5Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;

/**
 * UserServiceImpl 用户
 *
 * @author darre
 * @version 2023/09/17 10:24
 **/
@Service
public class UserServiceImpl implements UserService {

    private Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    UserMapper userMapper;

    @Override
    public User getUser() {
        return userMapper.selectByPrimaryKey(1);
    }

    @Override
    public void register(String userName, String password) {
        // 查询用户名是否存在，不允许重名
        User queryUser = userMapper.selectByName(userName);
        Assert.isNotNull(queryUser, MallExceptionEnum.NAME_EXISTED);

        // 写到数据库
        User user = new User();
        user.setUsername(userName);
        try {
            user.setPassword(MD5Utils.getMD5Str(password));
        } catch (NoSuchAlgorithmException ex) {
            log.error("密码加密失败: ", ex);
        }
        int count = userMapper.insertSelective(user);
        // 如果结果是0，抛出
        Assert.isZero(count, MallExceptionEnum.INSERT_FAILED);
    }

    @Override
    public User login(String userName, String password) {
        String md5Password = null;
        try {
            md5Password = MD5Utils.getMD5Str(password);
        } catch (NoSuchAlgorithmException ex) {
            log.error("密码加密失败: ", ex);
        }

        User user = userMapper.selectLogin(userName, md5Password);
        Assert.isNull(user, MallExceptionEnum.USERNAME_OR_PASSWORD_MISTAKEN);

        return user;
    }

    @Override
    public void updateInformation(User user) {
        //更新个信签名
        int updateCount = userMapper.updateByPrimaryKeySelective(user);
        Assert.isZero(updateCount, MallExceptionEnum.UPDATE_FAILED);
    }

    @Override
    public boolean checkAdminRole(User user) {
        return user.getRole().equals(RoleEnum.ADMIN.getCode());
    }
}
