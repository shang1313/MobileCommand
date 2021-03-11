package com.slc.appdatabase.service.imp;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.slc.appdatabase.DaoServiceConstant;
import com.slc.appdatabase.ObjectBox;
import com.slc.appdatabase.msg.MsgAttach;
import com.slc.appdatabase.service.MsgAttachDaoService;

import io.objectbox.Box;

/**
 * @author slc
 * @date 2019/10/29 15:48
 */
@Route(path = DaoServiceConstant.PATH_MSG_ATTACH)
public class MsgAttachDaoServiceImp extends DaoServiceImp<MsgAttach> implements MsgAttachDaoService {
    @Override
    public Box<MsgAttach> getBox() {
        return ObjectBox.getBox(MsgAttach.class);
    }

    @Override
    protected MsgAttach ensure(MsgAttach data) {
        return data;
    }
    @Override
    protected String getEntityName() {
        return "msgAttach";
    }
}
