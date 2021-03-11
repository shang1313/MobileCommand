package android.slc.app.startup.task;

import android.slc.app.startup.TaskConstant;
import android.slc.appbase.data.api.SlcApi;
import android.slc.appbase.utils.OnCrashListenerImp;
import android.slc.commonlibrary.util.compat.SlcCrashUtils;
import android.slc.mediaglide.BrowsePhotoLoad;
import android.slc.mediaglide.FolderLoad;
import android.slc.mediaglide.ImageLoad;
import android.slc.mp.SlcMp;
import android.slc.mp.SlcMpConfig;

import com.blankj.utilcode.util.ProcessUtils;
import com.blankj.utilcode.util.Utils;
import com.effective.android.anchors.task.Task;

import org.jetbrains.annotations.NotNull;

public class TaskSlc extends Task {

    public TaskSlc() {
        super(TaskConstant.TASK_SLC, false);
    }

    @Override
    protected void run(@NotNull String s) {
        if (ProcessUtils.isMainProcess()) {
            SlcCrashUtils.init(new OnCrashListenerImp());//闪退处理
            SlcApi.init(Utils.getApp());
            SlcMp.getInstance().initMp(Utils.getApp());
            SlcMp.getInstance().initGlobalMpConfig(new SlcMpConfig.Builder().loadPhoto().loadVideo().loadAudio().setImageLoad(new ImageLoad())
                    .setFolderLoad(new FolderLoad()).setBrowsePhotoLoad(new BrowsePhotoLoad()).setSpanCount(3).build());
        }
    }
}
