package com.slc.appdatabase.service;

import com.slc.appdatabase.user.User;

/**
 * 用户盒子服务
 */
public interface UserDaoService extends DaoService<User> {
    /**
     * 根据id查询用户是否存在
     *
     * @param id
     * @return
     */
    boolean userExistById(long id);

    /**
     * 根据账户id查找
     *
     * @param id
     * @return
     */
    User findById(long id);

    User findByPhone(String phone);
}
