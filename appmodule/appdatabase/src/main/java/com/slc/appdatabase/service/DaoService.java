package com.slc.appdatabase.service;


import androidx.annotation.IntDef;

import com.alibaba.android.arouter.facade.template.IProvider;
import com.slc.appdatabase.DaoMaster;
import com.slc.appdatabase.version.TableDdVersion;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import io.objectbox.Box;

import javax.annotation.Nullable;

/**
 * 基础的盒子服务
 *
 * @param <T>
 */
public interface DaoService<T> extends IProvider {
    /**
     * 获取盒子
     *
     * @return
     */
    Box<T> getBox();

    void put(@Nullable T... entities);

    /**
     * 较为消耗性能
     *
     * @param entities
     */
    void putNotify(@Nullable T... entities);

    long put(T entity);

    /**
     * 较为消耗性能
     *
     * @param entity
     * @return
     */
    long putNotify(T entity);

    void put(@Nullable Collection<T> entities);

    /**
     * 较为消耗性能
     *
     * @param entities
     */
    void putNotify(@Nullable Collection<T> entities);

    long getId(T entity);

    T get(long id);

    List<T> get(Iterable<Long> ids);

    List<T> get(long[] ids);

    Map<Long, T> getMap(Iterable<Long> ids);

    long count();

    long count(long maxCount);

    boolean isEmpty();

    List<T> getAll();

    void remove(long id);

    void removeNotify(long id);

    void remove(@Nullable long... ids);

    void removeNotify(@Nullable long... ids);

    void removeByIds(@Nullable Collection<Long> ids);

    void removeByIdsNotify(@Nullable Collection<Long> ids);

    void remove(T object);

    void removeNotify(T object);

    void remove(@Nullable T... objects);

    void removeNotify(@Nullable T... objects);

    void remove(@Nullable Collection<T> objects);

    void removeNotify(@Nullable Collection<T> objects);

    void removeAll();

    void updateDdVersion(List<TableDdVersion<T>> tableDdVersionList, DaoMaster.DaoElementListener<T> daoElementListener);

    void addDbChangeListener(OnDbChangeListener<T> onDbChangeListener);

    void removeOnDbChangeListener(OnDbChangeListener<T> onDbChangeListener);

    interface OnDbChangeListener<T> {
        void onChange(@Action int action, List<T> data);

        @Action
        List<Integer> getAction();
    }

    interface DaoElementListener<T> {
        void onRemove(int dataDiversityVersion, T data);

        void onPut(int dataDiversityVersion, T data);
    }

    @IntDef({Action.PUT, Action.UPDATE, Action.DELETE})
    @interface Action {
        int ALL = -100;
        int PUT = -101;
        int UPDATE = -102;
        int DELETE = -103;
    }
}
