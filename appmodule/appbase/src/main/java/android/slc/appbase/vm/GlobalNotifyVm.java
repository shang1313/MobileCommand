package android.slc.appbase.vm;

import android.app.Application;
import android.slc.appbase.data.entity.DownloadInfo;
import android.slc.commonlibrary.util.ViewModelProviderFactory;

import androidx.annotation.NonNull;
import androidx.databinding.Observable;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;

/**
 * 全局通知viewModel
 */
public class GlobalNotifyVm extends AppBaseViewModel {
    //是否登录
    public final ObservableBoolean isLoginOf = new ObservableBoolean(false);
    //新消息刷新通知 此处为工作台的各个图标新消息的刷新
    public ObservableField<Object> updateUpcomingCount = new ObservableField<>();
    //新消息刷新通知 此处为工作台的各个图标新消息的刷新
    public ObservableInt imUnReadCount = new ObservableInt();
    //通知更新系统消息
    public final ObservableField<Object> notifyUploadUserInfoOf = new ObservableField<>();
    public final ObservableField<Object> notifyUpdateLocationOf = new ObservableField<>();
    //app安装、卸载、更新通知
    public ObservableField<DownloadInfo> notifyDownloadOf = new ObservableField<>();
    //app安装、卸载、更新通知广播

    public static synchronized GlobalNotifyVm getInstance() {
        return ViewModelProviderFactory.getAppViewModelProvider().get(GlobalNotifyVm.class);
    }

    public GlobalNotifyVm(@NonNull Application application) {
        super(application);
        isLoginOf.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                boolean isLogin = isLoginOf.get();
                if (isLogin) {

                } else {
                }
            }
        });
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }
}
