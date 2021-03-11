package android.slc.appbase.vm;

import android.app.Application;
import android.slc.appbase.data.config.ConstantsBase;
import android.slc.codelifecycle.vm.RxViewModel;

import androidx.annotation.NonNull;

import java.io.Serializable;

/**
 * @author slc
 * @date 2020/3/12 13:40
 * @email sunlunchang@gmail.com
 */
public class AppBaseViewModel extends RxViewModel {
    public AppBaseViewModel(@NonNull Application application) {
        super(application);
    }

    protected <T extends Serializable> T getDataByIntent() {
        return (T) getViewDelegate().getActivityContext().getIntent().getSerializableExtra(ConstantsBase.Key.KEY_INTENT_DATA);
    }
}
