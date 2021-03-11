package android.slc.app.startup;

import android.app.Application;

import com.blankj.utilcode.util.Utils;
import com.effective.android.anchors.task.project.Project;

public class TaskUtils {
    public static Project getAppTask(Application application) {
        Project.Builder builder = new Project.Builder(TaskConstant.PROJECT_APP_PROJECT, new Project.TaskFactory(new TaskAppCreate(application)));
        builder.add(TaskConstant.TASK_FIRST);
        builder.add(TaskConstant.TASK_ANDROID).dependOn(TaskConstant.TASK_FIRST);
        builder.add(TaskConstant.TASK_MATA_DATA).dependOn(TaskConstant.TASK_FIRST);
        return builder.build();
    }

    public static Project.Builder delayTaskBuilder() {
        Project.Builder builder = new Project.Builder(TaskConstant.PROJECT_DELAY_PROJECT, new Project.TaskFactory(new TaskAppCreate(Utils.getApp())));
        builder.add(TaskConstant.TASK_THIRD_PART);
        builder.add(TaskConstant.TASK_SLC);
        return builder;
    }
}
