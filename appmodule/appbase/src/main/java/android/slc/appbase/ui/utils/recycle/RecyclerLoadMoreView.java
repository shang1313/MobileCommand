package android.slc.appbase.ui.utils.recycle;

import android.slc.appbase.R;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.loadmore.BaseLoadMoreView;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;

public final class RecyclerLoadMoreView extends BaseLoadMoreView {

    public RecyclerLoadMoreView() {
    }

    @NotNull
    @Override
    public View getLoadComplete(@NotNull BaseViewHolder baseViewHolder) {
        return baseViewHolder.findView(R.id.load_more_load_complete_view);
    }

    @NotNull
    @Override
    public View getLoadEndView(@NotNull BaseViewHolder baseViewHolder) {
        return baseViewHolder.findView(R.id.load_more_load_end_view);
    }

    @NotNull
    @Override
    public View getLoadFailView(@NotNull BaseViewHolder baseViewHolder) {
        return baseViewHolder.findView(R.id.load_more_load_fail_view);
    }

    @NotNull
    @Override
    public View getLoadingView(@NotNull BaseViewHolder baseViewHolder) {
        return baseViewHolder.findView(R.id.load_more_loading_view);
    }

    @NotNull
    @Override
    public View getRootView(@NotNull ViewGroup parent) {
        return LayoutInflater.from(parent.getContext()).inflate(R.layout.brvah_quick_view_load_more, parent, false);
    }
}
