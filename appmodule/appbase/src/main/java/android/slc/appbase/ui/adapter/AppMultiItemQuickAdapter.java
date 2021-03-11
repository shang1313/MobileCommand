package android.slc.appbase.ui.adapter;

import android.util.SparseIntArray;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * @author slc
 * @date 2021/1/14 15:44
 */
public abstract class AppMultiItemQuickAdapter<T, VH extends BaseViewHolder> extends BaseQuickAdapter<T, VH> {
    protected static final int DEFAULT_VIEW_TYPE = -0xff;
    public static final int TYPE_NOT_FOUND = -404;
    protected final SparseIntArray layouts = new SparseIntArray();

    public AppMultiItemQuickAdapter(@Nullable List<T> data) {
        super(0, data);
    }

    /**
     * 重写该方法返回itemType对应的布局
     *
     * @param parent
     * @param viewType
     * @return
     */
    @NotNull
    @Override
    protected VH onCreateDefViewHolder(@NotNull ViewGroup parent, int viewType) {
        int layoutResId = layouts.get(viewType);
        if (layoutResId == 0) {
            throw new IllegalArgumentException("ViewType: $viewType found layoutResId，please use addItemType() first!");
        }
        return createBaseViewHolder(parent, layoutResId);
    }

    /**
     * 调用此方法，设置多布局
     *
     * @param type        Int
     * @param layoutResId Int
     */
    protected void addItemType(int type, @LayoutRes int layoutResId) {
        layouts.put(type, layoutResId);
    }

}
