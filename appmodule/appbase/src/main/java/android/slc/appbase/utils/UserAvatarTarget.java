package android.slc.appbase.utils;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.request.target.DrawableImageViewTarget;
import com.bumptech.glide.request.transition.Transition;

public class UserAvatarTarget extends DrawableImageViewTarget {
    private final TextView textView;

    public UserAvatarTarget(ImageView view, TextView textView) {
        super(view);
        this.textView=textView;
    }

    @Override
    public void onLoadFailed(@Nullable Drawable errorDrawable) {
        super.onLoadFailed(null);
        textView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
        super.onResourceReady(resource, transition);
        textView.setVisibility(View.INVISIBLE);
    }
}
