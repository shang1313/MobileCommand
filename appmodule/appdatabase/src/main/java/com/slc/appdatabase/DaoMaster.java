package com.slc.appdatabase;

import com.alibaba.android.arouter.launcher.ARouter;
import com.slc.appdatabase.service.DaoService;
import com.slc.appdatabase.version.TableVersion;
import com.slc.appdatabase.version.TableVersion_;

import java.util.concurrent.ConcurrentHashMap;

public class DaoMaster {
    private final static ConcurrentHashMap<Class, DaoService> serviceMap = new ConcurrentHashMap<>();

    /**
     * 获取服务
     *
     * @param tClass
     * @param <T>
     * @return
     */
    public static <T extends DaoService> T getService(Class<T> tClass) {
        T service = (T) serviceMap.get(tClass);
        if (service == null) {
            synchronized (DaoMaster.class) {
                service = (T) serviceMap.get(tClass);
                if (service == null) {
                    service = ARouter.getInstance().navigation(tClass);
                    serviceMap.put(tClass, service);
                }
            }
        }
        return service;
    }

    public static int getVersionByTableName(String tableName) {
        return getTableVersionByName(tableName).getVersion();
    }

    public static void updateVersionByName(String tableName, int version) {
        TableVersion tableVersion = getTableVersionByName(tableName);
        if (tableVersion.getVersion() >= version) {
            throw new IllegalStateException("新的version必须大于当前的version");
        }
        tableVersion.setVersion(version);
        ObjectBox.getBox(TableVersion.class).put(tableVersion);
    }

    public static TableVersion getTableVersionByName(String tableName) {
        if (tableName == null) {
            throw new NullPointerException("tableName 不能为空");
        }
        TableVersion tableVersion = ObjectBox.getBox(TableVersion.class).query().equal(TableVersion_.tableName, tableName).build().findFirst();
        if (tableVersion == null) {
            tableVersion = new TableVersion();
            tableVersion.setVersion(0);
            tableVersion.setTableName(tableName);
        }
        return tableVersion;
    }

    public interface DaoElementListener<T> {
        void onRemove(int dataDiversityVersion, T data);

        void onPut(int dataDiversityVersion, T data);

        void onComplete();
    }
}