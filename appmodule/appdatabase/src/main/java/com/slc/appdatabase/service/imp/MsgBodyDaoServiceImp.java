package com.slc.appdatabase.service.imp;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.slc.appdatabase.DaoServiceConstant;
import com.slc.appdatabase.ObjectBox;
import com.slc.appdatabase.msg.MsgBody;
import com.slc.appdatabase.msg.MsgBody_;
import com.slc.appdatabase.service.MsgBodyDaoService;

import io.objectbox.Box;

@Route(path = DaoServiceConstant.PATH_MSG_BODY)
public class MsgBodyDaoServiceImp extends DaoServiceImp<MsgBody> implements MsgBodyDaoService {

    @Override
    public Box<MsgBody> getBox() {
        return ObjectBox.getBox(MsgBody.class);
    }

    @Override
    public MsgBody findByMsgId(String msgId) {
        return getBox().query().equal(MsgBody_.msgId, msgId).build().findUnique();
    }

    @Override
    protected MsgBody ensure(MsgBody data) {
        return data;
    }

    @Override
    protected String getEntityName() {
        return "msgBody";
    }
}
