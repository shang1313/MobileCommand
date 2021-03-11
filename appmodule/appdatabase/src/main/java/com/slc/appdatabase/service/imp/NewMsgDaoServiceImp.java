package com.slc.appdatabase.service.imp;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.slc.appdatabase.DaoServiceConstant;
import com.slc.appdatabase.ObjectBox;
import com.slc.appdatabase.msg.NewMsg;
import com.slc.appdatabase.msg.NewMsg_;
import com.slc.appdatabase.service.NewMsgDaoService;

import io.objectbox.Box;

@Route(path = DaoServiceConstant.PATH_NEW_MSG)
public class NewMsgDaoServiceImp extends DaoServiceImp<NewMsg> implements NewMsgDaoService {

    @Override
    public Box<NewMsg> getBox() {
        return ObjectBox.getBox(NewMsg.class);
    }

    @Override
    public NewMsg getNewMsgByMsgId(String msgId) {
        return getBox().query().equal(NewMsg_.msgId, msgId).build().findUnique();
    }

    @Override
    protected NewMsg ensure(NewMsg data) {
        return data;
    }

    @Override
    protected String getEntityName() {
        return "newMsg";
    }
}
