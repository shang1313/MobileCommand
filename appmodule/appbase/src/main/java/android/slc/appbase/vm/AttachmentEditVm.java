package android.slc.appbase.vm;

import android.app.Application;
import android.os.Bundle;
import android.slc.appbase.ui.utils.attachment.Attachment;
import android.slc.appbase.ui.utils.attachment.AttachmentUtils;
import android.slc.code.ui.views.ViewDelegate;
import android.slc.mp.SlcMp;
import android.slc.mp.SlcMpConfig;
import android.slc.mp.po.i.IBaseItem;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;

import com.blankj.utilcode.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 附件view model
 *
 * @author slc
 * @date 2020-09-08 11:09
 */
public class AttachmentEditVm extends AttachmentReviewVm {
    protected List<Attachment> attachmentList = new ArrayList<>();//所有附件列表
    public ObservableInt attachmentListSize = new ObservableInt(0);
    protected ActivityResultLauncher<Bundle> pickerActivityResultLauncher;

    public AttachmentEditVm(@NonNull Application application) {
        super(application);
    }

    @Override
    public void initViewDelegate(ViewDelegate viewDelegate) {
        super.initViewDelegate(viewDelegate);
        pickerActivityResultLauncher = SlcMp.getInstance().registerPicker(viewDelegate.getActivityResultCaller(), this::onAddAttachmentListener);
    }

    @Override
    protected void onGetAttachmentSucceed(List<Attachment> data) {
        if (!CollectionUtils.isEmpty(data)) {
            attachmentList.addAll(data);
            updateAttachmentListSize();
            CollectionUtils.forAllDo(data, (index, item) -> item.setIsAllowEditStatus(AttachmentUtils.IS_ALLOW_EDIT_STATUS_AGREE));
            super.onGetAttachmentSucceed(data);
        }
    }

    /**
     * 添加附件监听
     *
     * @param iBaseItems
     */
    protected void onAddAttachmentListener(List<IBaseItem> iBaseItems) {
        if (iBaseItems != null) {
            List<Attachment> attachmentListTemp = new ArrayList<>();
            for (IBaseItem iBaseItem : iBaseItems) {
                attachmentListTemp.add(new Attachment(iBaseItem));
            }
            onGetAttachmentSucceed(attachmentListTemp);
        }
    }

    public List<Attachment> getAttachmentList() {
        return attachmentList;
    }

    /**
     * 选择附件
     */
    public void selectAtt() {
        SlcMp.getInstance()
                .with(getViewDelegate().getActivityContext())
                .setMpConfig(new SlcMpConfig
                        .Builder()
                        .loadPhoto()
                        .loadAudio()
                        .loadVideo()
                        .loadFile(AttachmentUtils.TYPE_WORD, AttachmentUtils.TYPE_NAME_WORD, AttachmentUtils.Word)
                        .loadFile(AttachmentUtils.TYPE_EXCEL, AttachmentUtils.TYPE_NAME_EXCEL, AttachmentUtils.Excel)
                        .loadFile(AttachmentUtils.TYPE_PPT, AttachmentUtils.TYPE_NAME_PPT, AttachmentUtils.Ppt)
                        .loadFile(AttachmentUtils.TYPE_PDF, AttachmentUtils.TYPE_NAME_PDF, AttachmentUtils.Pdf)
                        .loadFile(AttachmentUtils.TYPE_COMPRESSION, AttachmentUtils.TYPE_NAME_COMPRESSION, AttachmentUtils.Compression)
                        .setMaxPicker(9 - attachmentList.size())
                        .build())
                .setPickerActivityResultLauncher(pickerActivityResultLauncher)
                .build();
    }

    public void updateAttachmentListSize() {
        attachmentListSize.set(attachmentList.size());
    }

    public void removeAtt(List<Attachment> attachmentList) {
        CollectionUtils.forAllDo(attachmentList, (index, item) -> removeAtt(item));
    }

    public void removeAtt(Attachment attachment) {
        attachmentList.remove(attachment);
        updateAttachmentListSize();
    }
}
