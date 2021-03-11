package android.slc.user.vm;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;

import android.slc.appbase.vm.AppBaseViewModel;
import android.slc.appbase.vm.GlobalDataVm;

import com.slc.appdatabase.user.User;

/**
 * @author slc
 * @date 2020-07-28 15:48
 */
public class UserInfoVm extends AppBaseViewModel {
    public ObservableField<User> userOf = new ObservableField<>(GlobalDataVm.getInstance().getUser());

    public UserInfoVm(@NonNull Application application) {
        super(application);
    }

    public void editPassWord() {
        //EditPassWordActivity.show(getViewDelegate().getActivityContext());
    }
}
