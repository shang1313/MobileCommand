package com.slc.appdatabase.service.imp;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.slc.appdatabase.DaoServiceConstant;
import com.slc.appdatabase.ObjectBox;
import com.slc.appdatabase.service.UserDaoService;
import com.slc.appdatabase.user.User;
import com.slc.appdatabase.user.User_;

import io.objectbox.Box;

@Route(path = DaoServiceConstant.PATH_USER)
public class UserDaoServiceImp extends DaoServiceImp<User> implements UserDaoService {

    @Override
    public boolean userExistById(long id) {
        return findById(id) != null;
    }

    @Override
    public User findById(long id) {
        return getBox().get(id);
    }

    @Override
    public User findByPhone(String phone) {
        return getBox().query().equal(User_.phoneNo,phone).build().findUnique();
    }

    @Override
    public Box<User> getBox() {
        return ObjectBox.getBox(User.class);
    }

    @Override
    protected User ensure(User data) {
        return findById(data.getId());
    }

    @Override
    protected String getEntityName() {
        return "user";
    }
}
