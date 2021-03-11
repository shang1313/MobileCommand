package com.slc.appdatabase;

import android.content.Context;

import com.orhanobut.logger.Logger;

import io.objectbox.Box;
import io.objectbox.BoxStore;
import io.objectbox.android.AndroidObjectBrowser;

public class ObjectBox {
    private static BoxStore boxStore;

    public static void init(Context context) {
        init(context, true);
    }

    public static void init(Context context, boolean isDebug) {
        boxStore = MyObjectBox.builder()
                .androidContext(context.getApplicationContext())
                .build();
        if (isDebug) {
            Logger.t("boxStore").i(String.format("Using ObjectBox %s (%s)", BoxStore.getVersion(), BoxStore.getVersionNative()));
            new AndroidObjectBrowser(boxStore).start(context.getApplicationContext());
        }
    }

    /**
     * 根据类型获取盒子对象
     *
     * @param tClass
     * @param <T>
     * @return
     */
    public static <T> Box<T> getBox(Class<T> tClass) {
        return boxStore.boxFor(tClass);
    }

    /**
     * 获取盒子
     *
     * @return
     */
    public static BoxStore get() {
        return boxStore;
    }

}