package android.slc.appbase.vm;

import android.app.Application;
import android.slc.commonlibrary.util.ViewModelProviderFactory;
import android.slc.network.DownloadConcurrentHashMap;
import android.slc.network.DownloadState;
import android.slc.network.SimpleDownloadManager;

import androidx.annotation.NonNull;
import androidx.databinding.Observable;
import androidx.databinding.ObservableArrayMap;
import androidx.databinding.ObservableField;
import androidx.lifecycle.AndroidViewModel;

import com.blankj.utilcode.util.CacheMemoryUtils;
import com.liulishuo.okdownload.DownloadTask;
import com.slc.appdatabase.user.User;

import java.util.HashMap;
import java.util.Map;

/**
 * 全局数据存储viewModel
 *
 * @author slc
 * @date 2020-08-18 17:37
 */
public class GlobalDataVm extends AndroidViewModel {
    //基础数据缓存
    private final CacheMemoryUtils baseCacheMemoryUtils = CacheMemoryUtils.getInstance("baseCache", 512);
    //用户数据
    public final ObservableField<User> userOf = new ObservableField<>();
    //新消息个数 此处新消息是关联工作台的红点角标 与聊天无关
    public final ObservableField<Map<String, Integer>> upcomingCountOf = new ObservableField<>(new HashMap<>());
    public final ObservableArrayMap<DownloadTask, DownloadState> downloadStateArrayMapOf = new ObservableArrayMap<>();
    /**
     * 下载服务的监听
     */
    private final DownloadConcurrentHashMap.OnMapChangedCallback onDownloadMapChangedCallback = downloadStateArrayMapOf::put;

    public static synchronized GlobalDataVm getInstance() {
        return ViewModelProviderFactory.getAppViewModelProvider().get(GlobalDataVm.class);
    }

    public GlobalDataVm(@NonNull Application application) {
        super(application);
        userOf.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                User user = userOf.get();
                GlobalNotifyVm.getInstance().isLoginOf.set(user != null);
            }
        });
        SimpleDownloadManager.getInstance().downloadStateArrayMapOf.addOnMapChangedCallback(onDownloadMapChangedCallback);
    }

    /**
     * 根据key在缓存里获取一个信息
     *
     * @param key
     * @param <T>
     * @return
     */
    public <T> T getData(String key) {
        return baseCacheMemoryUtils.get(key);
    }

    /**
     * 根据key在缓存里获取一个信息
     *
     * @param key
     * @param defValue 默认值
     * @param <T>
     * @return
     */
    public <T> T getData(String key, T defValue) {
        return baseCacheMemoryUtils.get(key, defValue);
    }

    /**
     * 填充一个信息
     *
     * @param key
     * @param value
     */
    public void putData(String key, Object value) {
        baseCacheMemoryUtils.put(key, value);
    }

    public User getUser() {
        return userOf.get();
    }

    /**
     * 根据key获取未读消息个数
     *
     * @param key
     * @return
     */
    public int getUpcomingCountByKey(String key) {
        Integer value = upcomingCountOf.get().get(key);
        if (value != null) {
            return value;
        }
        return 0;
    }

    @Override
    protected void onCleared() {
        SimpleDownloadManager.getInstance().downloadStateArrayMapOf.removeOnMapChangedCallback(onDownloadMapChangedCallback);
        super.onCleared();
    }
}