package com.slc.appdatabase.service;



import com.alibaba.android.arouter.facade.template.IProvider;
import com.slc.appdatabase.msg.MsgBody;

import java.util.List;

import io.reactivex.Observable;


public interface MsgBodyDaoService extends DaoService<MsgBody>, IProvider {
    /**
     * 根据消息id查询消息主体
     *
     * @param msgId
     * @return
     */
    MsgBody findByMsgId(String msgId);

}
