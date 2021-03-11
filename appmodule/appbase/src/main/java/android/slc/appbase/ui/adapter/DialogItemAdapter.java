package android.slc.appbase.ui.adapter;

import android.slc.appbase.R;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class DialogItemAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public DialogItemAdapter(List<String> dataList) {
        super(R.layout.app_dialog_select_item, dataList);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder viewHolder, String s) {
        viewHolder.setText(R.id.tv_title, s);
    }
}
