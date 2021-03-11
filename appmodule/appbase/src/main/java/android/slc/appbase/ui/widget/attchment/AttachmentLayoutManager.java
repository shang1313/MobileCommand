package android.slc.appbase.ui.widget.attchment;

import android.content.DialogInterface;
import android.slc.appbase.R;
import android.slc.appbase.ui.utils.attachment.Attachment;
import android.slc.attachment.AttachmentOperatingEvent;
import android.slc.attachment.BaseAttachmentsLayoutManage;
import android.slc.attachment.IBaseAttachmentItem;
import android.slc.commonlibrary.util.compat.SlcIntentCompatUtils;
import android.slc.slcdialog.SlcPopup;
import android.widget.LinearLayout;

import androidx.collection.ArrayMap;

import java.util.List;
import java.util.Map;

public class AttachmentLayoutManager extends BaseAttachmentsLayoutManage<Attachment, LinearLayout>
        implements IBaseAttachmentItem.OnAttachmentActionListener<Attachment> {
    public AttachmentLayoutManager(LinearLayout attachmentsLayout) {
        super(attachmentsLayout);
    }

    @Override
    public void addAttachmentItem(Attachment attachment) {
        if (attachment != null) {
            AttachmentItem attachmentItem = new AttachmentItem(mAttachmentsLayout.getContext(), attachment);
            attachmentItem.setOnAttachmentActionListener(this);
            mAttachmentsLayout.addView(attachmentItem.getView());
            mAttachmentItemList.put(attachment, attachmentItem);
            if (mOnAttachmentActionListener != null) {
                Map<String, Object> arrayMap = new ArrayMap<>();
                arrayMap.put("count", this.mAttachmentsLayout.getChildCount());
                mOnAttachmentActionListener.onOperating(AttachmentOperatingEvent.EVENT_CODE_ADD, attachment, arrayMap);
            }
        }
    }

    @Override
    public void addAttachments(List<Attachment> attachmentList) {
        if (attachmentList != null && !attachmentList.isEmpty()) {
            for (Attachment attachment : attachmentList) {
                addAttachmentItem(attachment);
            }
        }
    }

    @Override
    public void onOperating(int eventCode, Attachment attachment, Map<String, Object> extra) {
        Map<String, Object> extraTemp = extra != null ? extra : new ArrayMap<>();
        switch (eventCode) {
            case AttachmentOperatingEvent.EVENT_CODE_REMOVE:
                this.mAttachmentsLayout.removeView(mAttachmentItemList.get(attachment).getView());
                mAttachmentItemList.remove(attachment);
                extraTemp.put("count", this.mAttachmentsLayout.getChildCount());
                break;
            case AttachmentOperatingEvent.EVENT_CODE_PREVIEW:
                if (attachment.isLocalBody()) {
                    SlcIntentCompatUtils.openAndroidFile(attachment.isLocalBody() ? attachment.getLocalPath() : attachment.getNetPath());
                } else {
                    if (mOnAttachmentActionListener != null) {
                        mOnAttachmentActionListener.onOperating(AttachmentOperatingEvent.EVENT_CODE_DOWNLOAD, attachment, extraTemp);
                        return;
                    }
                }
                break;
            case AttachmentOperatingEvent.EVENT_CODE_ERROR:
                if (attachment.isLocalBody() && !attachment.isNetBody()) {
                    new SlcPopup.BottomNativeAlertDialogBuilder(mAttachmentsLayout.getContext())
                            .setItems(R.array.attachmentOperatingLocalArray).setOnClickListener((DialogInterface dialog, int which) -> {
                        dialog.dismiss();
                        switch (which) {
                            case 0://错误信息
                                new SlcPopup.NativeAlertDialogBuilder(mAttachmentsLayout.getContext())
                                        .setMessage((CharSequence) extraTemp.get("errorMessage")).create().show();
                                break;
                            case 1://重新上传
                                if (mOnAttachmentActionListener != null) {
                                    mOnAttachmentActionListener.onOperating(AttachmentOperatingEvent.EVENT_CODE_UPLOAD, attachment, extraTemp);
                                }
                                break;
                            case 2://移除
                                onOperating(AttachmentOperatingEvent.EVENT_CODE_REMOVE, attachment, null);
                                break;
                        }
                    }).create().show();
                } else {
                    new SlcPopup.BottomNativeAlertDialogBuilder(mAttachmentsLayout.getContext())
                            .setItems(R.array.attachmentOperatingNetArray).setOnClickListener((DialogInterface dialog, int which) -> {
                        dialog.dismiss();
                        switch (which) {
                            case 0://错误信息
                                new SlcPopup.NativeAlertDialogBuilder(mAttachmentsLayout.getContext())
                                        .setMessage((CharSequence) extraTemp.get("errorMessage")).create().show();
                                break;
                            case 1://重新下载
                                if (mOnAttachmentActionListener != null) {
                                    mOnAttachmentActionListener.onOperating(AttachmentOperatingEvent.EVENT_CODE_DOWNLOAD, attachment, extraTemp);
                                }
                                break;
                        }
                    }).create().show();
                }
                break;
        }
        if (mOnAttachmentActionListener != null) {
            mOnAttachmentActionListener.onOperating(eventCode, attachment, extraTemp);
        }
    }
}
