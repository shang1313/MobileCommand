package android.slc.app.startup;

import android.app.Application;
import android.slc.app.startup.task.TaskAndroid;
import android.slc.app.startup.task.TaskFirst;
import android.slc.app.startup.task.TaskMataData;
import android.slc.app.startup.task.TaskSlc;
import android.slc.app.startup.task.TaskThirdParty;

import com.effective.android.anchors.task.Task;
import com.effective.android.anchors.task.TaskCreator;
import com.effective.android.anchors.task.project.Project;

import org.jetbrains.annotations.NotNull;

public class TaskAppCreate implements TaskCreator {
    private final Application application;

    public TaskAppCreate(Application application) {
        this.application = application;
    }

    @NotNull
    @Override
    public Task createTask(@NotNull String s) {
        switch (s) {
            case TaskConstant.TASK_FIRST:
                return new TaskFirst(application);
            case TaskConstant.TASK_ANDROID:
                return new TaskAndroid();
            case TaskConstant.TASK_MATA_DATA:
                return new TaskMataData();
            case TaskConstant.TASK_THIRD_PART:
                return new TaskThirdParty();
            case TaskConstant.TASK_SLC:
                return new TaskSlc();
        }
        throw new IllegalStateException("task name is not defined");
    }
}
