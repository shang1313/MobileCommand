package android.slc.appbase.vm;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.slc.appbase.data.config.ConstantsBase;
import android.slc.code.ui.views.ViewDelegate;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.ObservableField;


import java.io.Serializable;

/**
 * 基础常用的盒子vm
 * @author slc
 * @date 2020/12/29 10:37
 */
public abstract class BaseCommonListShelfVm<T extends Serializable> extends AppBaseViewModel {
    protected ActivityResultLauncher<T> addResultLauncher;
    public ObservableField<T> notifyRefresh = new ObservableField<>();

    public BaseCommonListShelfVm(@NonNull Application application) {
        super(application);
    }

    @Override
    public void initViewDelegate(ViewDelegate viewDelegate) {
        super.initViewDelegate(viewDelegate);
        initAddLauncher();
    }

    /**
     * 初始化添加界面
     */
    protected void initAddLauncher() {
        if (getAddActivityClass() != null) {
            addResultLauncher = getViewDelegate().getActivityResultCaller().registerForActivityResult(new ActivityResultContract<T, T>() {
                @NonNull
                @Override
                public Intent createIntent(@NonNull Context context, T input) {
                    Intent intent = new Intent(context, getAddActivityClass());
                    intent.putExtra(ConstantsBase.Key.KEY_INTENT_DATA, input);
                    return intent;
                }

                @Override
                public T parseResult(int resultCode, @Nullable Intent intent) {
                    if (resultCode == Activity.RESULT_OK && intent != null) {
                        return (T) intent.getSerializableExtra(ConstantsBase.Key.KEY_INTENT_DATA);
                    }
                    return null;
                }
            }, this::notifyRefresh);
        }
    }

    /**
     * 获取添加界面
     * @return
     */
    protected abstract Class<?> getAddActivityClass();

    /**
     * 通知刷新
     * @param data
     */
    protected void notifyRefresh(T data) {
        if (data != null) {
            notifyRefresh.set(data);
        }
    }

    /**
     * 显示添加界面
     */
    public void showAddUi() {
        addResultLauncher.launch(null);
    }
}
