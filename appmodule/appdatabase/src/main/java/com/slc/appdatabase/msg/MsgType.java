package com.slc.appdatabase.msg;

public class MsgType {
    public final static int TYPE_UNKNOWN = 0;//未知消息
    public final static int TYPE_TEXT = 100;//文字
    public final static int TYPE_IMG = 101;//图片
    public final static int TYPE_SYS_MSG = 102;//公告
    public final static String MSG_STATUS_ONLINE = "online";//在线
    public final static String MSG_STATUS_OFFLINE = "offline";//离线

    public static String getTypeTextStr() {
        return getTypeStrByType(TYPE_TEXT);
    }

    public static String getTypeImgStr() {
        return getTypeStrByType(TYPE_IMG);
    }

    public static String getTypeSysMsgStr() {
        return getTypeStrByType(TYPE_SYS_MSG);
    }

    public static String getTypeStrByType(int type) {
        return String.valueOf(type);
    }
}
