package com.slc.appdatabase.msg;

import androidx.annotation.IntDef;

/**
 * @author slc
 * @date 2019/10/29 15:02
 */

public class MsgBodyState {
    public static final int NONE = 0;         //无状态
    public static final int WAITING = 1;      //等待
    public static final int LOADING = 2;      //加载中
    public static final int PAUSE = 3;        //暂停
    public static final int ERROR = 4;        //错误
    public static final int FINISH = 5;       //完成

    @IntDef({MsgBodyState.NONE, MsgBodyState.WAITING, MsgBodyState.LOADING, MsgBodyState.PAUSE, MsgBodyState.ERROR, MsgBodyState.FINISH})
    public @interface State {

    }
}
