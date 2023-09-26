package com.ianmu.mall.service;

import com.ianmu.mall.model.pojo.User;

/**
 * UserService 用户
 *
 * @author darre
 * @version 2023/09/17 10:24
 **/
public interface UserService {

    User getUser();

    void register(String userName, String password);

    User login(String userName, String password);

    void updateInformation(User user);

    boolean checkAdminRole(User user);
}
