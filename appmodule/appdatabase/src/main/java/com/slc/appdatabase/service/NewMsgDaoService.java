package com.slc.appdatabase.service;

import com.alibaba.android.arouter.facade.template.IProvider;
import com.slc.appdatabase.msg.MsgBody;
import com.slc.appdatabase.msg.NewMsg;

import java.util.List;

import io.objectbox.reactive.SubscriptionBuilder;
import io.reactivex.Observable;
import io.reactivex.Single;

/**
 * 新消息盒子
 */

public interface NewMsgDaoService extends DaoService<NewMsg>, IProvider {
    /**
     * 根据消息id查询新消息
     *
     * @param msgId
     * @return
     */
    NewMsg getNewMsgByMsgId(String msgId);

}
