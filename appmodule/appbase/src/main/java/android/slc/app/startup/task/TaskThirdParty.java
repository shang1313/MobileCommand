package android.slc.app.startup.task;

import android.slc.app.startup.TaskConstant;
import android.slc.appbase.data.config.ConstantsBase;
import android.util.Log;

import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.ProcessUtils;
import com.blankj.utilcode.util.Utils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.effective.android.anchors.task.Task;
import com.slc.appdatabase.ObjectBox;

import org.jetbrains.annotations.NotNull;

public class TaskThirdParty extends Task {

    public TaskThirdParty() {
        super(TaskConstant.TASK_THIRD_PART, false);
    }

    @Override
    protected void run(@NotNull String s) {
        if (ProcessUtils.isMainProcess()) {
            if (!ConstantsBase.VALUE_IS_ON_LINE) { // 这两行必须写在init之前，否则这些配置在init过程中将无效
                ARouter.openLog();// 打印日志
                ARouter.openDebug();// 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
            }
            ARouter.init(Utils.getApp());
            ObjectBox.init(Utils.getApp());
            Glide.init(Utils.getApp(), new GlideBuilder().setLogLevel(Log.ERROR));
        }
    }
}
