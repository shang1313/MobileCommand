package android.slc.appbase.ui.widget.attchment;

import android.slc.appbase.R;
import android.slc.appbase.data.api.ApiConfig;
import android.slc.appbase.data.entity.AttFileInfo;
import android.slc.appbase.data.repository.remote.AttachmentServiceRepository;
import android.slc.appbase.ui.utils.attachment.Attachment;
import android.slc.appbase.ui.utils.attachment.AttachmentUtils;
import android.slc.appbase.ui.utils.attachment.FileInfoImp;
import android.slc.appbase.utils.LoadingUtils;
import android.slc.attachment.AttachmentOperatingEvent;
import android.slc.attachment.IBaseAttachmentItem;
import android.slc.attachment.bean.Progress;
import android.slc.attachment.loading.OnAttachmentOperatingListener;
import android.slc.commonlibrary.util.compat.SlcIntentCompatUtils;
import android.slc.network.SimpleDownloadByOk;
import android.slc.network.SimpleDownloadByOkListener;
import android.slc.or.OnResultsListener;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.PathUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author slc
 * @date 2019/11/6 16:27
 */
public class AttachmentLoadDelegate implements IBaseAttachmentItem.OnAttachmentActionListener<Attachment> {
    private AttachmentLayoutManager attachmentLayoutManager;
    private List<Attachment> attachmentList = new ArrayList<>();
    //private List<Integer> downloadIdList = new ArrayList<>();
    private OnAttachmentActionListener onAttachmentActionListener;

    public AttachmentLoadDelegate(AttachmentLayoutManager manager) {
        this.attachmentLayoutManager = manager;
        this.attachmentLayoutManager.setOnAttachmentActionListener(this);
    }

    public void setOnAttachmentActionListener(OnAttachmentActionListener onAttachmentActionListener) {
        this.onAttachmentActionListener = onAttachmentActionListener;
    }

    public void setBlanketAttachmentList(List<Attachment> attachmentList) {
        this.attachmentList = attachmentList;
    }

    public void loadByNetBodyList(List<AttFileInfo> attFileInfoList) {
        if (attFileInfoList != null && !attFileInfoList.isEmpty()) {
            loadByAttachmentList(AttachmentUtils.netPathToAttachment(attFileInfoList));
        }
    }

    public void loadByNetBodyList(List<AttFileInfo> attFileInfoList, int isAllowEditStatus) {
        if (attFileInfoList != null && !attFileInfoList.isEmpty()) {
            loadByAttachmentList(AttachmentUtils.netPathToAttachment(attFileInfoList, isAllowEditStatus));
        }
    }

    /**
     * 此方法在此项目是无效的
     * 目前这么写只是为了兼容测试数据
     *
     * @param stringList
     */
    @Deprecated
    public void loadByStringList(List<String> stringList) {
        if (stringList != null && !stringList.isEmpty()) {
            List<AttFileInfo> attFileInfoList = new ArrayList<>();
            for (String data : stringList) {
                AttFileInfo netBody = new AttFileInfo();
                netBody.setName(data);
                netBody.setPath(data);
                attFileInfoList.add(netBody);
            }
            loadByAttachmentList(AttachmentUtils.netPathToAttachment(attFileInfoList));
        }
    }

    public void loadByAttachmentList(List<Attachment> attachmentList) {
        if (attachmentList != null && !attachmentList.isEmpty()) {
            attachmentLayoutManager.addAttachments(attachmentList);
        }
    }

    public void removeAllAttachmentItem() {
        attachmentLayoutManager.removeAllAttachmentItem();
    }

    @Override
    public void onOperating(int eventCode, Attachment attachment, Map<String, Object> extra) {
        if (onAttachmentActionListener == null || !onAttachmentActionListener.onOperating(eventCode, attachment, extra)) {
            switch (eventCode) {
                case AttachmentOperatingEvent.EVENT_CODE_REMOVE:
                    if (this.attachmentList != null) {
                        this.attachmentList.remove(attachment);
                    }
                    break;
                case AttachmentOperatingEvent.EVENT_CODE_DOWNLOAD:
                    WeakReference<OnAttachmentOperatingListener<FileInfoImp, Progress>> weakReference
                            = new WeakReference<>(attachment.getOnAttachmentOperatingListener());

                    SimpleDownloadByOk.newBuilder(attachment.getNetPath(), PathUtils.getExternalAppDownloadPath(),attachment.getNetBody().getName())
                            .setHeaderMapFields(SimpleDownloadByOk.createHeadBuilder()
                                    //.add(ApiConfig.KEY_TOKEN, GlobalDataVm.getInstance().getToken())
                                    .build())
                            .toCommonlyConfig()
                            .build()
                            .getDownloadTask().enqueue(new SimpleDownloadByOkListener() {
                        @Override
                        protected void started(@NonNull com.liulishuo.okdownload.DownloadTask task) {
                            if (weakReference.get() != null) {
                                Progress progress = new Progress();
                                progress.status = Progress.WAITING;
                                weakReference.get().onStart(progress);
                            }
                        }

                        @Override
                        protected void progress(@NonNull com.liulishuo.okdownload.DownloadTask task, int percentage, long currentOffset, long totalLength) {
                            if (weakReference.get() != null) {
                                Progress progressTemp = new Progress();
                                progressTemp.status = Progress.LOADING;
                                progressTemp.totalSize = totalLength == 0 ? -1 : totalLength;
                                progressTemp.fraction = percentage;
                                progressTemp.currentSize = currentOffset;
                                weakReference.get().onProgress(progressTemp);
                            }
                        }

                        @Override
                        protected void completed(@NonNull com.liulishuo.okdownload.DownloadTask task) {
                            if (weakReference.get() != null) {
                                Progress progressTemp = new Progress();
                                progressTemp.status = Progress.FINISH;
                                progressTemp.fraction = 100;
                                weakReference.get().onFinish(task.getFile().getAbsolutePath(), progressTemp);
                                FileInfoImp fileInfoImp = new FileInfoImp();
                                fileInfoImp.setFilePath(task.getFile().getAbsolutePath());
                                fileInfoImp.setFileName(FileUtils.getFileName(task.getFile().getAbsolutePath()));
                                weakReference.get().onNext(fileInfoImp);
                                SlcIntentCompatUtils.openAndroidFile(task.getFile().getAbsolutePath());
                            }
                        }

                        @Override
                        protected void error(@NonNull com.liulishuo.okdownload.DownloadTask task, @NonNull Exception e) {
                            if (weakReference.get() != null) {
                                String errorMessage = StringUtils.getString(R.string.label_download_file_failed);
                                weakReference.get().onError(500, errorMessage);
                            }
                        }

                    });
                    break;
                case AttachmentOperatingEvent.EVENT_CODE_UPLOAD:
                    LoadingUtils.showLoadingDialog(R.string.label_is_submit_ing);
                    AttachmentServiceRepository.uploadAttachment(attachment, new OnResultsListener<Object>() {
                        @Override
                        public void onSucceed(Object data) {
                            LoadingUtils.dismissLoadingDialog();
                        }

                        @Override
                        public void onError(int errorCode, String errorMessage) {
                            LoadingUtils.dismissLoadingDialog();
                            if (ApiConfig.NORMAL_ERROR == errorCode || ApiConfig.RESULT_UPLOAD_FAILURE == errorCode) {
                                ToastUtils.showShort(errorMessage);
                            } else {
                                ToastUtils.showShort(R.string.label_upload_file_failed);
                            }
                        }
                    });
                    break;
            }
        }
    }


    /**
     * 销毁
     */
    public void destroy() {
        /*for (int downloadId : downloadIdList) {
            FileDownloader.getImpl().pause(downloadId);
        }*/
        attachmentLayoutManager.destroy();
    }

    public interface OnAttachmentActionListener {
        /**
         * 对附件的操作事件
         *
         * @param eventCode  事件代码 参考{@link AttachmentOperatingEvent}
         * @param attachment 附件
         */
        boolean onOperating(int eventCode, Attachment attachment, Map<String, Object> extra);
    }
}
