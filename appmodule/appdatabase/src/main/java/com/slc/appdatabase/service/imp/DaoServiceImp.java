package com.slc.appdatabase.service.imp;

import android.content.Context;

import com.slc.appdatabase.DaoMaster;
import com.slc.appdatabase.service.DaoService;
import com.slc.appdatabase.version.TableDdVersion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Nullable;

import io.objectbox.Box;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

public abstract class DaoServiceImp<T> implements DaoService<T> {
    private final Set<OnDbChangeListener<T>> onDbChangeListenerSet = new HashSet<>();

    @Override
    public void init(Context context) {
    }

    private Iterator<OnDbChangeListener<T>> getDbChangeListenerIterator() {
        synchronized (onDbChangeListenerSet) {
            return onDbChangeListenerSet.iterator();
        }
    }

    /**
     * 通知添加
     *
     * @param list
     */
    private void notifyPut(List<T> list) {
        if (list == null || list.isEmpty()) {
            return;
        }
        getBox().getStore().internalThreadPool().submit(() -> {
            Iterator<OnDbChangeListener<T>> iterator = getDbChangeListenerIterator();
            while (iterator.hasNext()) {
                OnDbChangeListener<T> onDbChangeListener = iterator.next();
                if (onDbChangeListener != null
                        && (onDbChangeListener.getAction().contains(Action.ALL)
                        || onDbChangeListener.getAction().contains(Action.PUT))) {
                    onDbChangeListener.onChange(Action.PUT, list);
                }
            }
        });
    }

    /**
     * 通知更新
     *
     * @param list
     */
    private void notifyUpdate(List<T> list) {
        if (list == null || list.isEmpty()) {
            return;
        }
        getBox().getStore().internalThreadPool().submit(() -> {
            Iterator<OnDbChangeListener<T>> iterator = getDbChangeListenerIterator();
            while (iterator.hasNext()) {
                OnDbChangeListener<T> onDbChangeListener = iterator.next();
                if (onDbChangeListener != null
                        && (onDbChangeListener.getAction().contains(Action.ALL)
                        || onDbChangeListener.getAction().contains(Action.UPDATE))) {
                    onDbChangeListener.onChange(Action.UPDATE, list);
                }
            }
        });
    }

    private void notifyDelete(List<T> list) {
        if (list == null || list.isEmpty()) {
            return;
        }
        getBox().getStore().internalThreadPool().submit(() -> {
            Iterator<OnDbChangeListener<T>> iterator = getDbChangeListenerIterator();
            while (iterator.hasNext()) {
                OnDbChangeListener<T> onDbChangeListener = iterator.next();
                if (onDbChangeListener != null
                        && (onDbChangeListener.getAction().contains(Action.ALL)
                        || onDbChangeListener.getAction().contains(Action.DELETE))) {
                    onDbChangeListener.onChange(Action.DELETE, list);
                }
            }
        });
    }

    @Override
    public void put(@Nullable T... entities) {
        getBox().put(entities);
    }

    @Override
    public void putNotify(@Nullable T... entities) {
        if (entities != null && entities.length != 0) {
            List<T> updateList = new ArrayList<>();
            List<T> putList = new ArrayList<>();
            for (T t : entities) {
                //此处查找方式不准确，但也是没有办法的办法
                long idTemp = getBox().getId(t);
                if (idTemp != 0 && getBox().get(idTemp) != null) {
                    updateList.add(getBox().get(put(t)));
                } else {
                    put(t);
                    putList.add(t);
                }
            }
            notifyUpdate(updateList);
            notifyPut(putList);
        }
    }

    @Override
    public long put(T entity) {
        return getBox().put(entity);
    }

    @Override
    public long putNotify(T entity) {
        boolean isUpdate;
        //此处查找方式不准确，但也是没有办法的办法
        long id = getBox().getId(entity);
        isUpdate = id != 0 && getBox().get(id) != null;
        id = put(entity);
        if (isUpdate) {
            notifyUpdate(Collections.singletonList(entity));
        } else {
            notifyPut(Collections.singletonList(entity));
        }
        return id;
    }

    @Override
    public void put(@Nullable Collection<T> entities) {
        getBox().put(entities);
    }

    @Override
    public void putNotify(@Nullable Collection<T> entities) {
        if (entities != null && !entities.isEmpty()) {
            List<T> updateList = new ArrayList<>();
            List<T> putList = new ArrayList<>();
            for (T t : entities) {
                //此处查找方式不准确，但也是没有办法的办法
                long idTemp = getBox().getId(t);
                if (idTemp != 0 && getBox().get(idTemp) != null) {
                    updateList.add(getBox().get(put(t)));
                } else {
                    put(t);
                    putList.add(t);
                }
            }
            notifyUpdate(updateList);
            notifyPut(putList);
        }
    }

    @Override
    public long getId(T entity) {
        return getBox().getId(entity);
    }

    @Override
    public T get(long id) {
        return getBox().get(id);
    }

    @Override
    public List<T> get(Iterable<Long> ids) {
        return getBox().get(ids);
    }

    @Override
    public List<T> get(long[] ids) {
        return getBox().get(ids);
    }

    @Override
    public Map<Long, T> getMap(Iterable<Long> ids) {
        return getBox().getMap(ids);
    }

    @Override
    public long count() {
        return getBox().count();
    }

    @Override
    public long count(long maxCount) {
        return getBox().count(maxCount);
    }

    @Override
    public boolean isEmpty() {
        return getBox().isEmpty();
    }

    @Override
    public List<T> getAll() {
        return getBox().getAll();
    }

    @Override
    public void remove(long id) {
        getBox().remove(id);
    }

    @Override
    public void removeNotify(long id) {
        T data = get(id);
        remove(id);
        notifyDelete(Collections.singletonList(data));
    }

    @Override
    public void remove(@Nullable long... ids) {
        getBox().remove(ids);
    }

    @Override
    public void removeNotify(@Nullable long... ids) {
        List<T> data = get(ids);
        remove(ids);
        notifyDelete(data);
    }

    @Override
    public void removeByIds(@Nullable Collection<Long> ids) {
        getBox().removeByIds(ids);
    }

    @Override
    public void removeByIdsNotify(@Nullable Collection<Long> ids) {
        List<T> data = get(ids);
        removeByIds(ids);
        notifyDelete(data);
    }

    @Override
    public void remove(T object) {
        getBox().remove(object);
    }

    @Override
    public void removeNotify(T object) {
        remove(object);
        notifyDelete(Collections.singletonList(object));
    }

    @Override
    public void remove(@Nullable T... objects) {
        getBox().remove(objects);
    }

    @Override
    public void removeNotify(@Nullable T... objects) {
        remove(objects);
        notifyDelete(Arrays.asList(objects));
    }

    @Override
    public void remove(@Nullable Collection<T> objects) {
        getBox().remove(objects);
    }

    @Override
    public void removeNotify(@Nullable Collection<T> objects) {
        remove(objects);
        notifyDelete((List<T>) objects);
    }

    @Override
    public void removeAll() {
        List<T> allList = getAll();
        getBox().removeAll();
        notifyDelete(allList);
    }

    @Override
    public void updateDdVersion(List<TableDdVersion<T>> tableDdVersionList, DaoMaster.DaoElementListener<T> daoElementListener) {
        if (!tableDdVersionList.isEmpty()) {
            int newVersion = tableDdVersionList.get(tableDdVersionList.size() - 1).getDataDiversityVersion();
            int oldVersion = DaoMaster.getVersionByTableName(getEntityName());
            for (TableDdVersion<T> tTableDdVersion : tableDdVersionList) {
                if (tTableDdVersion.getDataDiversityVersion() >= oldVersion) {
                    forAllDo(tTableDdVersion.getRemove(), (index, item) -> {
                        item = ensure(item);
                        if (item != null) {
                            if (newVersion > oldVersion) {
                                remove(item);
                            }
                            daoElementListener.onRemove(tTableDdVersion.getDataDiversityVersion(), item);
                        }
                    });
                    forAllDo(tTableDdVersion.getPut(), (index, item) -> {
                        long id;
                        if (newVersion > oldVersion) {
                            id = putNotify(item);
                        } else {
                            id = getId(ensure(item));
                        }
                        daoElementListener.onPut(tTableDdVersion.getDataDiversityVersion(), get(id));
                    });
                }
            }
            DaoMaster.updateVersionByName(getEntityName(), newVersion);
        }
        daoElementListener.onComplete();
    }

    protected abstract T ensure(T data);

    protected abstract String getEntityName();

    private <E> void forAllDo(Collection<E> collection, Closure<E> closure) {
        if (collection == null || closure == null) return;
        int index = 0;
        for (E e : collection) {
            closure.execute(index++, e);
        }
    }

    @Override
    public void addDbChangeListener(OnDbChangeListener<T> onDbChangeListener) {
        onDbChangeListenerSet.add(onDbChangeListener);
    }

    @Override
    public void removeOnDbChangeListener(OnDbChangeListener<T> onDbChangeListener) {
        onDbChangeListenerSet.remove(onDbChangeListener);
    }

    interface Closure<E> {
        void execute(int index, E item);
    }
}
