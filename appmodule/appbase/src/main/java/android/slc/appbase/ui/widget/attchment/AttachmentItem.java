package android.slc.appbase.ui.widget.attchment;

import android.content.Context;
import android.content.res.ColorStateList;
import android.slc.appbase.R;
import android.slc.appbase.data.entity.AttFileInfo;
import android.slc.appbase.ui.utils.attachment.Attachment;
import android.slc.appbase.ui.utils.attachment.AttachmentUtils;
import android.slc.appbase.ui.utils.attachment.FileInfoImp;
import android.slc.appbase.utils.GlideUtils;
import android.slc.attachment.AttachmentOperatingEvent;
import android.slc.attachment.bean.Progress;
import android.slc.attachment.loading.LoadingAttachmentItem;
import android.slc.commonlibrary.util.compat.SlcFileCompatUtils;
import android.slc.mp.SlcMp;
import android.slc.mp.po.BaseItem;
import android.slc.mp.po.FileItem;
import android.slc.widget.SlcClickProxy;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.collection.ArrayMap;
import androidx.core.content.ContextCompat;
import androidx.core.widget.ImageViewCompat;

import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ThreadUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.dinuscxj.progressbar.CircleProgressBar;

import java.io.File;
import java.util.Map;

public class AttachmentItem extends LoadingAttachmentItem<Attachment, FileInfoImp, Progress> implements View.OnClickListener {
    //private Context mContext;
    private ImageButton mActionBtn;
    private ViewGroup mProgressBarParent;
    private CircleProgressBar mCircleProgressBar;
    private Progress mProgress;
    private Map<String, Object> errorMap;

    public AttachmentItem(Context context, Attachment attachment) {
        //mContext = context;
        mAttachment = attachment;
        mAttachment.setOnAttachmentOperatingListener(this);
        mItemView = LayoutInflater.from(context).inflate(R.layout.app_item_attachment, null);
        mItemView.setOnClickListener(new SlcClickProxy(this));
        mItemView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        TextView textView = mItemView.findViewById(R.id.tv_title);
        textView.setText(mAttachment.getAttachmentName());
        TextView tv_info = mItemView.findViewById(R.id.tv_info);
        if (mAttachment.isLocalBody()) {
            String info = SlcFileCompatUtils.byte2FitMemorySize(mAttachment.getLocalBody().getSize());
            info += "，";
            info += TimeUtils.millis2String(mAttachment.getLocalBody().getModified(),"yy-MM-dd:hh:mm");
            tv_info.setText(info);
        } else {
            tv_info.setText(StringUtils.getString(R.string.label_click_preview));
        }
        mProgressBarParent = mItemView.findViewById(R.id.progress_bar_parent);
        mCircleProgressBar = mItemView.findViewById(R.id.progress_bar);
        mCircleProgressBar.setMax(100);
        mCircleProgressBar.setProgress(0);
        mActionBtn = mItemView.findViewById(R.id.iv_action);
        mActionBtn.setOnClickListener(new SlcClickProxy(this));
        mActionBtn.setVisibility(mAttachment.isAllowEdit() ? View.VISIBLE : View.GONE);
        ImageView iv_icon = mItemView.findViewById(R.id.iv_icon);
        int mediaType = mAttachment.isLocalBody() ? mAttachment.getLocalBody().getMediaTypeTag()
                : AttachmentUtils.expandName2MediaType(mAttachment.getExpandName());
        switch (mediaType) {
            case SlcMp.MEDIA_TYPE_PHOTO:
                if (mAttachment.isLocalBody()) {
                    Glide.with(iv_icon).load(attachment.getLocalPath()).apply(GlideUtils.getCenterFitCenterOptions()).into(iv_icon);
                } else {
                    Glide.with(iv_icon).load(attachment.getNetPath()).apply(GlideUtils.getCenterFitCenterOptions()).into(iv_icon);
                }
                break;
            default:
                ImageViewCompat.setImageTintList(iv_icon, ColorStateList.valueOf(ContextCompat.getColor(context, R.color.colorAccent)));
                switch (mediaType) {
                    case SlcMp.MEDIA_TYPE_AUDIO:
                        iv_icon.setImageResource(R.drawable.slc_mp_ic_audiotrack);
                        break;
                    case SlcMp.MEDIA_TYPE_VIDEO:
                        iv_icon.setImageResource(R.drawable.slc_mp_ic_videocam);
                        break;
                    case SlcMp.MEDIA_TYPE_FILE:
                        iv_icon.setImageResource(R.drawable.slc_mp_ic_unknown);
                        break;
                    case AttachmentUtils.TYPE_WORD:
                        iv_icon.setImageResource(R.drawable.slc_mp_ic_word);
                        break;
                    case AttachmentUtils.TYPE_EXCEL:
                        iv_icon.setImageResource(R.drawable.slc_mp_ic_excel);
                        break;
                    case AttachmentUtils.TYPE_PPT:
                        iv_icon.setImageResource(R.drawable.slc_mp_ic_powerpoint);
                        break;
                    case AttachmentUtils.TYPE_PDF:
                        iv_icon.setImageResource(R.drawable.slc_mp_ic_pdf);
                        break;
                    case AttachmentUtils.TYPE_COMPRESSION:
                        iv_icon.setImageResource(R.drawable.slc_mp_ic_cs);
                        break;
                }
                break;
        }
    }

    @Override
    public void onStart(Progress progress) {
        this.mProgress = progress;
        this.mProgress.status = Progress.WAITING;
        ThreadUtils.runOnUiThread(() -> mProgressBarParent.setVisibility(View.VISIBLE));
    }

    @Override
    public void onProgress(Progress progress) {
        this.mProgress = progress;
        this.mProgress.status = Progress.LOADING;
        ThreadUtils.runOnUiThread(() -> {
            if (progress.totalSize == -1) {
                mCircleProgressBar.setProgress(50);
            } else if (progress.totalSize == 0) {
                mCircleProgressBar.setProgress(100);
            } else
                mCircleProgressBar.setProgress(progress.fraction);
        });
    }


    @Override
    public void onFinish(String s, Progress progress) {
        this.mProgress = progress;
        this.mProgress.status = Progress.FINISH;
        mActionBtn.setImageResource(R.mipmap.ic_attachment_delete);
        //mActionBtn.setVisibility(mAttachment.isLocalBody() ? View.VISIBLE : View.GONE);
        ThreadUtils.runOnUiThread(() -> mCircleProgressBar.setProgress(100));
    }

    @Override
    public void onError(int errorCode, String errorMessage) {
        Progress progress = new Progress();
        progress.status = Progress.ERROR;
        this.mProgress = progress;
        mCircleProgressBar.setProgress((progress.fraction));
        mProgressBarParent.setVisibility(View.GONE);
        mActionBtn.setImageResource(R.mipmap.ic_attchment_error);
        mActionBtn.setVisibility(View.VISIBLE);
        errorMap = new ArrayMap<>();
        errorMap.put("errorCode", errorCode);
        errorMap.put("errorMessage", errorMessage);
    }

    @Override
    public void onNext(FileInfoImp data) {
        if (mAttachment.isLocalBody()) {
            AttFileInfo attFileInfo = mAttachment.getNetBody();
            if (attFileInfo == null) {
                attFileInfo = new AttFileInfo();
                mAttachment.setNetBody(attFileInfo);
            }
            attFileInfo.setPath(data.getFilePath());
        } else {
            File file = FileUtils.getFileByPath(data.getFilePath());
            if (file != null) {
                BaseItem baseItem = new FileItem();
                baseItem.setSize(file.length());
                baseItem.setExtension(FileUtils.getFileExtension(file));
                baseItem.setDisplayName(file.getName());
                baseItem.setPath(file.getPath());
                baseItem.setModified(FileUtils.getFileLastModified(file));
                baseItem.setMediaTypeTag(AttachmentUtils.expandName2MediaType(baseItem.getExtension()));
                mAttachment.setLocalBody(baseItem);
            }
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.ll_attachments_item_parent) {
            if (mOnAttachmentActionListener != null) {//TODO 写到此处 防止重复点击预览 此处位置需要更改
                if (this.mProgress == null || mAttachment.isLocalBody() || this.mProgress.status != Progress.WAITING && this.mProgress.status != Progress.LOADING) {
                    mOnAttachmentActionListener.onOperating(AttachmentOperatingEvent.EVENT_CODE_PREVIEW, mAttachment, null);
                } else if (mAttachment.isNetBody()) {
                    ToastUtils.showShort(R.string.label_please_wait_for_the_download_to_complete);
                }
            }
        } else if (id == R.id.iv_action) {
            if (mOnAttachmentActionListener != null) {
                if (mProgress == null || mProgress.status == Progress.NONE || mProgress.status == Progress.FINISH) {
                    mOnAttachmentActionListener.onOperating(AttachmentOperatingEvent.EVENT_CODE_REMOVE, mAttachment, null);
                } else if (mProgress.status == Progress.ERROR) {
                    mOnAttachmentActionListener.onOperating(AttachmentOperatingEvent.EVENT_CODE_ERROR, mAttachment, errorMap);
                }
            }
        }
    }

}
