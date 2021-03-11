package android.slc.appbase.data.repository.remote;

import android.slc.appbase.data.api.ApiConfig;
import android.slc.appbase.data.api.main.callback.SlcObserver;
import android.slc.appbase.data.api.main.callback.po.SlcEntity;
import android.slc.appbase.data.entity.AttFileInfo;
import android.slc.appbase.ui.utils.attachment.Attachment;
import android.slc.appbase.ui.utils.attachment.FileInfoImp;
import android.slc.appbase.ui.widget.attchment.AttachmentItemUtils;
import android.slc.attachment.bean.Progress;
import android.slc.attachment.loading.OnAttachmentOperatingListener;
import android.slc.or.FileRequestBody;
import android.slc.or.OnResultsListener;
import android.slc.or.SlcCallbackConfig;
import android.slc.or.SlcNu;
import android.slc.or.SlcParam;
import android.slc.rx.SlcRxJavaUtils;

import com.blankj.utilcode.util.CollectionUtils;

import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.Body;

public class AttachmentServiceRepository {

    public static String getImgPathById(String id) {
        return ApiConfig.BASE_URL + "file/getImgStream?idFile=" + id;
    }

    public static String getFilePathById(String id) {
        return ApiConfig.BASE_URL + "file/download?fileName=" + id;
    }

    /**
     * 上传文件
     *
     * @param multipartBody
     * @return
     */
    public static Observable<SlcEntity<AttFileInfo>> uploadFile(@Body MultipartBody multipartBody) {
        return SlcNu.getInstance().create(AttachmentService.class)
                .uploadFile(multipartBody);
    }

    public static void uploadAttachmentListByFilter(List<Attachment> attachmentList, OnResultsListener<Object> onResultsListener) {
        uploadAttachmentList(AttachmentItemUtils.removeNetAttachment(attachmentList), onResultsListener);
    }

    public static void uploadAttachment(Attachment attachment, OnResultsListener<Object> onResultsListener) {
        List<Attachment> attachmentList = new ArrayList<>();
        attachmentList.add(attachment);
        uploadAttachmentList(attachmentList, 0, new OnResultsListener<Object>() {
            @Override
            public void onSucceed(Object data) {
                if (onResultsListener != null) {
                    onResultsListener.onSucceed(data);
                }
            }

            @Override
            public void onError(int errorCode, String errorMessage) {
                if (onResultsListener != null) {
                    onResultsListener.onError(errorCode, errorMessage);
                }
            }
        });
    }

    public static void uploadAttachmentList(List<Attachment> attachmentList, OnResultsListener<Object> onResultsListener) {
        uploadAttachmentList(attachmentList, 0, onResultsListener);
    }

    /**
     * 递归上传文件
     *
     * @param attachmentList
     * @param index
     * @param onResultsListener
     */
    public static void uploadAttachmentList(List<Attachment> attachmentList, int index, OnResultsListener<Object> onResultsListener) {
        if (attachmentList.size() == index) {//当上传完最后一个附件时
            for (Attachment attachment : attachmentList) {
                if (!attachment.isNetBody()) {
                    onResultsListener.onError(ApiConfig.RESULT_UPLOAD_FAILURE, ApiConfig.RESULT_UPLOAD_FAILURE_MSG);
                    return;
                }
            }
            onResultsListener.onSucceed(null);
            return;
        }
        Attachment attachment = attachmentList.get(index);
        WeakReference<OnAttachmentOperatingListener<FileInfoImp, Progress>> weakReference = new WeakReference<>(attachment.getOnAttachmentOperatingListener());
        File attachmentFile = new File(attachment.getLocalPath());
        AttachmentServiceRepository.uploadFile(SlcParam.newBuilder()
                .put("name", attachmentFile.getName())
                .addPart(SlcParam.createPart("file", attachmentFile, new FileRequestBody.SimpleFileUploadListener() {
                    @Override
                    public void onProgressChange(int progress, long bytesWritten, long contentLength) {
                        OnAttachmentOperatingListener<FileInfoImp, Progress> attachmentOperatingListener = weakReference.get();
                        if (attachmentOperatingListener != null) {
                            Progress progressTemp = new Progress();
                            progressTemp.status = Progress.LOADING;
                            progressTemp.totalSize = contentLength;
                            progressTemp.fraction = progress;
                            attachmentOperatingListener.onProgress(progressTemp);
                        }
                    }
                })).toMultipartBody())
                .compose(SlcRxJavaUtils.applyOtAndroidSchedulers())
                .subscribe(new SlcObserver<AttFileInfo>(SlcCallbackConfig.newBuilder().showDialog(false).showToast(false).build()) {
                    @Override
                    protected void onStart() {
                        super.onStart();
                        OnAttachmentOperatingListener<FileInfoImp, Progress> attachmentOperatingListener = weakReference.get();
                        if (attachmentOperatingListener != null) {
                            Progress progressTemp = new Progress();
                            progressTemp.status = Progress.WAITING;
                            attachmentOperatingListener.onStart(progressTemp);
                        }
                    }

                    @Override
                    protected void onSucceed(AttFileInfo data) {
                        String absoluteNetPath = AttachmentServiceRepository.getFilePathById(data.getRealName());
                        data.setPath(absoluteNetPath);
                        attachment.setNetBody(data);
                        OnAttachmentOperatingListener<FileInfoImp, Progress> attachmentOperatingListener = weakReference.get();
                        if (attachmentOperatingListener != null) {
                            Progress progressTemp = new Progress();
                            progressTemp.status = Progress.FINISH;
                            progressTemp.fraction = 100;
                            attachmentOperatingListener.onFinish(absoluteNetPath, progressTemp);
                            FileInfoImp fileInfoImp = new FileInfoImp();
                            fileInfoImp.setFileName(data.getName());
                            fileInfoImp.setFilePath(absoluteNetPath);
                            attachmentOperatingListener.onNext(fileInfoImp);
                        }
                        uploadAttachmentList(attachmentList, index + 1, onResultsListener);
                    }

                    @Override
                    protected void onFailed(int errorCode, String errorMessage) {
                        weakReference.get().onError(errorCode, errorMessage);
                        uploadAttachmentList(attachmentList, index + 1, onResultsListener);
                    }
                });
    }

    /**
     * 根据linkId获取附件
     *
     * @param ids
     * @return
     */
    public static Observable<SlcEntity<List<Attachment>>> getAttachmentByIds(Long[] ids) {
        return SlcNu.getInstance().create(AttachmentService.class)
                .findListById(ids)
                .map(slcEntity -> {
                    List<Attachment> attachmentList = fileInfoToAttachment(slcEntity.getData());
                    SlcEntity<List<Attachment>> listSlcEntity = new SlcEntity<>();
                    listSlcEntity.setData(attachmentList);
                    listSlcEntity.setCode(slcEntity.getCode());
                    listSlcEntity.setMsg(slcEntity.getMsg());
                    return listSlcEntity;
                })
                .compose(SlcRxJavaUtils.applyOtAndroidSchedulers());
    }

    /**
     * 同步执行根据linkid获取附件信息
     *
     * @param ids
     * @return
     */
    public static List<Attachment> getAttachmentByIdsSynchronous(Long[] ids) throws IOException {
        SlcEntity<List<AttFileInfo>> slcEntity = SlcNu.getInstance().create(AttachmentService.class)
                .findListByIdByCall(ids)
                .execute().body();
        if (slcEntity != null && slcEntity.isSuccess()) {
            return fileInfoToAttachment(slcEntity.getData());
        }
        return null;
    }

    /**
     * 文件信息转附件
     *
     * @param attFileInfoList
     * @return
     */
    public static List<Attachment> fileInfoToAttachment(List<AttFileInfo> attFileInfoList) {
        List<Attachment> attachmentList = new ArrayList<>();
        CollectionUtils.forAllDo(attFileInfoList, (index, item) -> {
            item.setPath(AttachmentServiceRepository.getFilePathById(item.getRealName()));
            Attachment attachment = new Attachment(item);
            attachmentList.add(attachment);
        });
        return attachmentList;
    }

}