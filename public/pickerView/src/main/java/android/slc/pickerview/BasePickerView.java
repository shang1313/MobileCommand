package android.slc.pickerview;

import android.content.Context;
import android.slc.pickerview.po.PickerOptions;

public class BasePickerView {
    protected PickerOptions mPickerOptions;
    protected Context context;

    public BasePickerView(Context context) {
        this.context = context;
    }
}
