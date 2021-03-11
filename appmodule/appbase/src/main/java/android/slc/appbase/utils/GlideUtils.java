package android.slc.appbase.utils;

import android.slc.appbase.R;

import com.bumptech.glide.request.RequestOptions;

public class GlideUtils {
    private static RequestOptions headRequestOptions;
    private static RequestOptions centerCropequestOptions;
    private static RequestOptions appStoreIconOptions;
    private static RequestOptions photoReviewOptions;
    private static RequestOptions msgOptions;
    private static RequestOptions bannerOptions;

    /**
     * 获取用户头像的RequestOptions
     *
     * @return
     */
    public static synchronized RequestOptions getHeadRequestOptions() {
        if (headRequestOptions == null) {
            headRequestOptions = new RequestOptions()
                    .circleCrop()
                    .placeholder(R.drawable.slc_mp_ic_loading_anim)                //加载成功之前占位图
                    .error(R.mipmap.ic_def_user_head);
        }
        return headRequestOptions;
    }

    public static synchronized RequestOptions getCenterFitCenterOptions() {
        if (centerCropequestOptions == null) {
            centerCropequestOptions = new RequestOptions()
                    .fitCenter()
                    .placeholder(R.drawable.slc_mp_ic_loading_anim)                //加载成功之前占位图
                    .error(R.drawable.slc_mp_ic_loading_failure);
        }
        return centerCropequestOptions;
    }

    public static synchronized RequestOptions getAppStoreIconOptions() {
        if (appStoreIconOptions == null) {
            appStoreIconOptions = new RequestOptions()
                    .fitCenter()
                    //.dontAnimate()
                    .placeholder(R.drawable.slc_mp_ic_loading_anim)                //加载成功之前占位图
                    .error(R.mipmap.ic_def_app_store_icon);
        }
        return appStoreIconOptions;
    }

    public static synchronized RequestOptions getPhotoReviewOptions() {
        if (photoReviewOptions == null) {
            photoReviewOptions = new RequestOptions()
                    .placeholder(R.drawable.slc_mp_ic_loading_anim)                //加载成功之前占位图
                    .error(R.drawable.slc_mp_ic_loading_failure)
                    .skipMemoryCache(false);
        }
        return photoReviewOptions;
    }

    public static synchronized RequestOptions getMsgOptions() {
        if (msgOptions == null) {
            msgOptions = new RequestOptions()
                    .placeholder(R.drawable.slc_mp_ic_loading_anim)                //加载成功之前占位图
                    .error(R.drawable.slc_mp_ic_loading_failure)
                    .skipMemoryCache(false);
        }
        return msgOptions;
    }

    public static synchronized RequestOptions getWorkCircleOptions() {
        if (msgOptions == null) {
            msgOptions = new RequestOptions()
                    .placeholder(R.drawable.slc_mp_ic_loading_anim)                //加载成功之前占位图
                    .error(R.drawable.slc_mp_ic_loading_failure)
                    .skipMemoryCache(false);
        }
        return msgOptions;
    }

    public static synchronized RequestOptions getBannerOptionsOptions() {
        if (bannerOptions == null) {
            bannerOptions = new RequestOptions()
                    .centerCrop()
                    .placeholder(R.drawable.slc_mp_ic_loading_anim)                //加载成功之前占位图
                    .error(R.drawable.slc_mp_ic_loading_failure)
                    .skipMemoryCache(false);
        }
        return bannerOptions;
    }
}
