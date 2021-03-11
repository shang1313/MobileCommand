package android.slc.app.startup.task;

import android.slc.app.startup.TaskConstant;
import android.slc.appbase.data.api.ApiConfig;
import android.slc.appbase.data.config.ConstantsBase;

import com.blankj.utilcode.util.MetaDataUtils;
import com.blankj.utilcode.util.ProcessUtils;
import com.effective.android.anchors.task.Task;

import org.jetbrains.annotations.NotNull;

public class TaskMataData extends Task {
    public TaskMataData() {
        super(TaskConstant.TASK_MATA_DATA, false);
    }

    @Override
    protected void run(@NotNull String s) {
        if (ProcessUtils.isMainProcess()) {
            ConstantsBase.VALUE_IS_ON_LINE = Boolean.parseBoolean(MetaDataUtils.getMetaDataInApp("ON_LINE"));
            ApiConfig.BASE_URL = MetaDataUtils.getMetaDataInApp("API");
            ApiConfig.BASE_URL_DS = MetaDataUtils.getMetaDataInApp("API_DS");
            ApiConfig.BASE_URL_DS_FILE = MetaDataUtils.getMetaDataInApp("API_DS_FILE");
            ApiConfig.SOCKET_HOST = MetaDataUtils.getMetaDataInApp("SOCKET_HOST");
            ApiConfig.SOCKET_PORT = Integer.parseInt(MetaDataUtils.getMetaDataInApp("SOCKET_PORT"));
        }
    }
}
