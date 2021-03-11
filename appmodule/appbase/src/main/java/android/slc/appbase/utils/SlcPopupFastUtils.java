package android.slc.appbase.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.slc.adapter.CommonlyRecycler;
import android.slc.adapter.CommonlySwipeRecycler;
import android.slc.appbase.R;
import android.slc.appbase.ui.adapter.DialogItemAdapter;
import android.slc.slcdialog.SlcPopup;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.StringUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import java.util.Arrays;

/**
 * @author slc
 * @date 2020-08-25 14:21
 */
public class SlcPopupFastUtils {
    public static void showItemDialog(DialogInterface.OnClickListener onClickListener, String... itemArray) {
        showItemDialog(ActivityUtils.getTopActivity(), onClickListener, itemArray);
    }

    public static void showItemDialog(Context context, DialogInterface.OnClickListener onClickListener, String... itemArray) {
        showItemDialog(context, onClickListener, false, itemArray);
    }

    public static void showItemDialog(Context context, DialogInterface.OnClickListener onClickListener, boolean isShowDefButton, String... itemArray) {
        showItemDialog(context, onClickListener, null, isShowDefButton, itemArray);
    }

    public static void showItemDialog(Context context, DialogInterface.OnClickListener onClickListener, String title, boolean isShowDefButton, String... itemArray) {
        SlcPopup.BottomNativeAlertDialogBuilder bottomNativeAlertDialogBuilder = new SlcPopup.BottomNativeAlertDialogBuilder(context);
        if (!StringUtils.isEmpty(title)) {
            bottomNativeAlertDialogBuilder.setTitle(title);
        }
        if (isShowDefButton) {
            bottomNativeAlertDialogBuilder.setDefPositiveAndNegativeButton();
        }
        bottomNativeAlertDialogBuilder.setItems(itemArray);
        bottomNativeAlertDialogBuilder.setOnClickListener(onClickListener);
        bottomNativeAlertDialogBuilder.create().show();
    }

    public static void showItemDialogCenter(Context context, DialogInterface.OnClickListener onClickListener, String... itemArray) {
        View contentView = LayoutInflater.from(context).inflate(R.layout.app_dialog_select_view, null);
        CommonlyRecycler<String> commonlySwipeRecycler = new CommonlyRecycler.CommonlyRecyclerBuilder<String>(contentView)
                .build(() -> {
                    DialogItemAdapter dialogItemAdapter = new DialogItemAdapter(Arrays.asList(itemArray));
                    dialogItemAdapter.setOnItemClickListener((adapter, view, position) -> {
                        SlcPopup.getOperateByKey("showItemDialogCenter").dismiss();
                        onClickListener.onClick(null, position);
                    });
                    return dialogItemAdapter;
                });
        commonlySwipeRecycler.refresh();
        new SlcPopup.BottomNativeAlertDialogBuilder(context).setView(contentView).setKey("showItemDialogCenter").create().show();
    }
}
