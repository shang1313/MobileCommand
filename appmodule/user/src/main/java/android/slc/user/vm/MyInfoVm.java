package android.slc.user.vm;

import android.app.Application;
import android.slc.appbase.vm.AppBaseViewModel;
import android.slc.appbase.vm.GlobalDataVm;
import android.slc.code.ui.views.ViewDelegate;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;

import com.slc.appdatabase.user.User;

/**
 * @author slc
 * @date 2021/1/12 16:53
 */
public class MyInfoVm extends AppBaseViewModel {
    public final ObservableField<User> userOf = new ObservableField<>();

    public MyInfoVm(@NonNull Application application) {
        super(application);
    }

    @Override
    public void initViewDelegate(ViewDelegate viewDelegate) {
        super.initViewDelegate(viewDelegate);
        userOf.set(GlobalDataVm.getInstance().getUser());
    }
}
