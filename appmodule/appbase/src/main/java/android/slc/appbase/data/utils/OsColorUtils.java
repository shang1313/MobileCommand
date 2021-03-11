package android.slc.appbase.data.utils;

import android.slc.appbase.R;

import com.blankj.utilcode.util.ColorUtils;

/**
 * @author slc
 * @date 2020-08-19 13:06
 */
public class OsColorUtils {

    public static final int[] colorOsArray = {
            ColorUtils.getColor(R.color.colorGreen1),
            ColorUtils.getColor(R.color.colorOrange1),
            ColorUtils.getColor(R.color.colorBlue1),
            ColorUtils.getColor(R.color.colorYellow1),
            ColorUtils.getColor(R.color.colorRed1)
    };

    public static class OsColorSelect {
        private int index;

        public synchronized int getColor() {
            try {
                return OsColorUtils.colorOsArray[index++];
            } catch (ArrayIndexOutOfBoundsException e) {
                index = 0;
                return OsColorUtils.colorOsArray[index++];
            }
        }
    }
}
