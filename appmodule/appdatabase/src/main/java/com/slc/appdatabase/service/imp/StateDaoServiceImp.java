package com.slc.appdatabase.service.imp;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.slc.appdatabase.DaoServiceConstant;
import com.slc.appdatabase.ObjectBox;
import com.slc.appdatabase.service.StateDaoService;
import com.slc.appdatabase.state.State;

import io.objectbox.Box;

@Route(path = DaoServiceConstant.PATH_STATE)
public class StateDaoServiceImp extends DaoServiceImp<State> implements StateDaoService {

    @Override
    protected State ensure(State data) {
        return get(data.getId());
    }

    @Override
    protected String getEntityName() {
        return "state";
    }

    @Override
    public Box<State> getBox() {
        return ObjectBox.getBox(State.class);
    }
}
