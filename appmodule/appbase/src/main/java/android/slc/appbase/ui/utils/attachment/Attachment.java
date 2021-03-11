package android.slc.appbase.ui.utils.attachment;

import android.slc.appbase.R;
import android.slc.appbase.data.entity.AttFileInfo;
import android.slc.attachment.bean.Progress;
import android.slc.attachment.loading.LoadingAttachment;
import android.slc.mp.po.i.IBaseItem;
import android.text.TextUtils;

import com.blankj.utilcode.util.StringUtils;

import java.io.File;

public class Attachment extends LoadingAttachment<IBaseItem, AttFileInfo, FileInfoImp, Progress> {
    private int isAllowEditStatus = AttachmentUtils.IS_ALLOW_EDIT_STATUS_EDF;

    public Attachment(AttFileInfo netBody) {
        this.mNetBody = netBody;
    }

    public Attachment(IBaseItem iBaseItem) {
        this.mLocalBody = iBaseItem;
    }

    @Override
    public void setLocalPath(String path) {
        if (mLocalBody != null) {
            mLocalBody.setPath(path);
        }
    }

    @Override
    public String getLocalPath() {
        if (mLocalBody != null) {
            return mLocalBody.getPath();
        }
        return null;
    }

    @Override
    public void setNetPath(String path) {
        mNetBody.setPath(path);
    }

    @Override
    public String getNetPath() {
        return mNetBody.getPath();
    }

    /**
     * 获取附件名称
     *
     * @return
     */
    public String getAttachmentName() {
        if (isLocalBody()) {
            return getLocalBody().getDisplayName();
        }
        if (isNetBody() && !TextUtils.isEmpty(getNetBody().getName())) {
            return getNetBody().getName();
        }
        return StringUtils.getString(R.string.label_unknown_file);
    }

    public String getExpandName() {
        if (isLocalBody()) {
            return getLocalBody().getExtension();
        }
        if (isNetBody() && !TextUtils.isEmpty(getNetBody().getName())) {
            int lastPoi = getNetBody().getName().lastIndexOf('.');
            int lastSep = getNetBody().getName().lastIndexOf(File.separator);
            if (lastPoi == -1 || lastSep >= lastPoi) return "";
            return getNetBody().getName().substring(lastPoi + 1);
        }
        return "";
    }

    public void setIsAllowEditStatus(int isAllowEditStatus) {
        this.isAllowEditStatus = isAllowEditStatus;
    }

    public int getIsAllowEditStatus() {
        return isAllowEditStatus;
    }

    public boolean isAllowEdit() {
        switch (isAllowEditStatus) {
            case AttachmentUtils.IS_ALLOW_EDIT_STATUS_EDF:
                return isLocalBody();
            case AttachmentUtils.IS_ALLOW_EDIT_STATUS_AGREE:
                return true;
            case AttachmentUtils.IS_ALLOW_EDIT_STATUS_DISAGREE:
                return false;
            default:
                return isLocalBody();
        }
    }
}
