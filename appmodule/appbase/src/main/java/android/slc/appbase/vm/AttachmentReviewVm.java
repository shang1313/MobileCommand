package android.slc.appbase.vm;

import android.app.Application;
import android.slc.appbase.R;
import android.slc.appbase.data.api.main.callback.SlcObserver;
import android.slc.appbase.data.repository.remote.AttachmentServiceRepository;
import android.slc.appbase.ui.utils.attachment.Attachment;
import android.slc.or.SlcCallbackConfig;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;

import java.util.List;

/**
 * 附件view model
 *
 * @author slc
 * @date 2020-09-08 11:09
 */
public class AttachmentReviewVm extends AppBaseViewModel {
    protected Long[] ids;//关联的id
    public ObservableField<List<Attachment>> addAttachmentListOf = new ObservableField<>();//初始化的附件列表

    public AttachmentReviewVm(@NonNull Application application) {
        super(application);
    }

    /**
     * 初始化
     *
     * @param ids
     */
    public void setAttLinkId(Long[] ids) {
        this.ids = ids;
    }

    /**
     * 初始化
     *
     * @param ids
     * @param attachmentList 携带的附件列表
     */
    public void setAttLinkIdAndAtt(Long[] ids, List<Attachment> attachmentList) {
        this.ids = ids;
        onGetAttachmentSucceed(attachmentList);
    }

    /**
     * 初始化
     *
     * @param ids
     */
    public void setLinkIdAndFindAtt(Long[] ids) {
        setAttLinkId(ids);
        findAttachmentData();
    }

    protected void findAttachmentData() {
        AttachmentServiceRepository.getAttachmentByIds(ids)
                .compose(bindToLifecycle())
                .subscribe(new SlcObserver<List<Attachment>>(SlcCallbackConfig.newBuilder().setToastRes(R.string.label_get_attachments_error).build()) {
                    @Override
                    protected void onSucceed(List<Attachment> data) {
                        onGetAttachmentSucceed(data);
                    }

                    @Override
                    protected void onFailed(int errorCode, String errorMessage) {
                        onGetAttachmentFailed(errorCode, errorMessage);
                    }
                });
    }

    protected void onGetAttachmentSucceed(List<Attachment> data) {
        addAttachmentListOf.set(data);
    }

    protected void onGetAttachmentFailed(int errorCode, String errorMessage) {
    }
}
