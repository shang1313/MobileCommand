package android.slc.app;

import android.app.Application;
import android.slc.app.startup.TaskUtils;
import android.slc.commonlibrary.util.ViewModelProviderFactory;

import com.effective.android.anchors.AnchorsManager;


public class AppApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        //BootstrapUtils.init(this);
        AnchorsManager.getInstance().start(TaskUtils.getAppTask(this));;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        ViewModelProviderFactory.getAppViewModelStore().clear();
    }
}
