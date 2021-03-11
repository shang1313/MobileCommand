package android.slc.appbase.ui.widget.wheel;

import android.content.Context;
import android.content.DialogInterface;
import android.slc.appbase.R;
import android.slc.pickerview.TimePickerBuilder;
import android.slc.pickerview.TimePickerView;
import android.slc.pickerview.listener.OnTimeSelectListener;
import android.slc.slcdialog.SlcPopup;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.SlcBottomSheetAlertDialog;

import java.util.Calendar;

/**
 * @author slc
 * @date 2020-08-25 10:57
 */
public class TimeSelectUtils {
    public static void showSelectDateDialog(Context context, Calendar start, Calendar end, OnTimeSelectListener onTimeSelectListener) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        FrameLayout frameLayout = new FrameLayout(context);
        frameLayout.addView(layoutInflater.inflate(R.layout.item_my_time_picker_select, null));
        TimePickerView timePickerView = new TimePickerBuilder(context, onTimeSelectListener).setParentView(frameLayout).setRangDate(start, end).setDate(end).build();
        new SlcPopup.BottomNativeAlertDialogBuilder(context).setView(frameLayout)
                .setCancelable(true).setBottomSheetCallback(new SlcBottomSheetAlertDialog.SlcBottomSheetAlertDialogCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet,
                                       @BottomSheetBehavior.State int newState) {
                //判断为向下拖动行为时，则强制设定状态为展开
                if (bottomSheet.getLayoutParams() instanceof CoordinatorLayout.LayoutParams) {
                    if (newState == BottomSheetBehavior.STATE_DRAGGING) {
                        BottomSheetBehavior<FrameLayout> bottomSheetBehavior = (BottomSheetBehavior<FrameLayout>) ((CoordinatorLayout.LayoutParams) bottomSheet.getLayoutParams()).getBehavior();
                        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                    } else if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                        SlcBottomSheetAlertDialog bottomSheetAlertDialog = sheetAlertDialogWr.get();
                        bottomSheetAlertDialog.cancel();
                    }
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
            }
        }).setDefPositiveAndNegativeButton().setOnClickListener((dialog, which) -> {
            switch (which) {
                case DialogInterface.BUTTON_POSITIVE:
                    timePickerView.returnData();
                    break;
            }
        }).create().show();
    }

    public static void showSelectDateDialog(Context context, TimePickerBuilder timePickerBuilder) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        FrameLayout frameLayout = new FrameLayout(context);
        frameLayout.addView(layoutInflater.inflate(R.layout.item_my_time_picker_select, null));
        TimePickerView timePickerView = timePickerBuilder.setParentView(frameLayout).build();
        new SlcPopup.BottomNativeAlertDialogBuilder(context).setView(frameLayout)
                .setCancelable(true).setBottomSheetCallback(new SlcBottomSheetAlertDialog.SlcBottomSheetAlertDialogCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet,
                                       @BottomSheetBehavior.State int newState) {
                //判断为向下拖动行为时，则强制设定状态为展开
                if (bottomSheet.getLayoutParams() instanceof CoordinatorLayout.LayoutParams) {
                    if (newState == BottomSheetBehavior.STATE_DRAGGING) {
                        BottomSheetBehavior<FrameLayout> bottomSheetBehavior = (BottomSheetBehavior<FrameLayout>) ((CoordinatorLayout.LayoutParams) bottomSheet.getLayoutParams()).getBehavior();
                        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                    } else if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                        SlcBottomSheetAlertDialog bottomSheetAlertDialog = sheetAlertDialogWr.get();
                        bottomSheetAlertDialog.cancel();
                    }
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
            }
        }).setDefPositiveAndNegativeButton().setOnClickListener((dialog, which) -> {
            switch (which) {
                case DialogInterface.BUTTON_POSITIVE:
                    timePickerView.returnData();
                    break;
            }
        }).create().show();
    }
}
