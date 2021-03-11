package android.slc.app.startup.task;

import android.slc.app.startup.TaskConstant;

import androidx.multidex.MultiDex;

import com.blankj.utilcode.util.Utils;
import com.effective.android.anchors.task.Task;

import org.jetbrains.annotations.NotNull;

public class TaskAndroid extends Task {

    public TaskAndroid() {
        super(TaskConstant.TASK_ANDROID, false);
    }

    @Override
    protected void run(@NotNull String s) {
        MultiDex.install(Utils.getApp());
    }
}
