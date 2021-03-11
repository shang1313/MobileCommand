package android.slc.app.startup.task;

import android.app.Application;
import android.slc.app.startup.TaskConstant;

import com.blankj.utilcode.util.Utils;
import com.effective.android.anchors.task.Task;

import org.jetbrains.annotations.NotNull;

public class TaskFirst extends Task {
    private final Application application;

    public TaskFirst(Application application) {
        super(TaskConstant.TASK_FIRST, false);
        this.application = application;

    }

    @Override
    protected void run(@NotNull String s) {
        Utils.init(application);//初始化工具类
    }
}
