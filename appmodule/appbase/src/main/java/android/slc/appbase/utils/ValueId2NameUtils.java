package android.slc.appbase.utils;

import android.slc.appbase.R;
import android.slc.appbase.data.config.ConstantsBase;

import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.TimeUtils;

/**
 * @author slc
 * @date 2020-08-25 10:17
 */
public class ValueId2NameUtils {
    /**
     * 日期转UI日期
     *
     * @param date
     * @return
     */
    public static String toUiMonth(String date) {
        if (StringUtils.isEmpty(date)) {
            return "";
        }
        return TimeUtils.millis2String(TimeUtils.string2Millis(date, ConstantsBase.Value.VALUE_TIME_PATTERN_BY_DATE_BY_THICK_LINE), ConstantsBase.Value.VALUE_TIME_PATTERN_BY_MONTH);
        //return SlcTimeCompatUtils.getDateStringByPattern(ConstantsBase.Value.VALUE_TIME_PATTERN_BY_DATE, SlcTimeCompatUtils.getDateByPattern("yyyy-MM-dd", date), "");
    }

    /**
     * 日期转UI日期
     *
     * @param date
     * @return
     */
    public static String toUiDate(String date) {
        if (StringUtils.isEmpty(date)) {
            return "";
        }
        return TimeUtils.millis2String(TimeUtils.string2Millis(date, ConstantsBase.Value.VALUE_TIME_PATTERN_BY_DATE_BY_THICK_LINE), ConstantsBase.Value.VALUE_TIME_PATTERN_BY_DATE);
        //return SlcTimeCompatUtils.getDateStringByPattern(ConstantsBase.Value.VALUE_TIME_PATTERN_BY_DATE, SlcTimeCompatUtils.getDateByPattern("yyyy-MM-dd", date), "");
    }

    /**
     * 日期转UI日期+时间
     *
     * @param date
     * @return
     */
    public static String toUiDateTime(String date) {
        if (StringUtils.isEmpty(date)) {
            return "";
        }
        return TimeUtils.millis2String(TimeUtils.string2Millis(date, ConstantsBase.Value.VALUE_TIME_PATTERN_BY_DATE_TIME_BY_THICK_LINE), ConstantsBase.Value.VALUE_TIME_PATTERN_BY_MINUTE);
        //return SlcTimeCompatUtils.getDateStringByPattern(ConstantsBase.Value.VALUE_TIME_PATTERN_BY_DATE, SlcTimeCompatUtils.getDateByPattern("yyyy-MM-dd", date), "");
    }

    /**
     * 日期转时间
     *
     * @param date
     * @return
     */
    public static String date2UiTime(String date) {
        if (StringUtils.isEmpty(date)) {
            return "";
        }
        return TimeUtils.millis2String(TimeUtils.string2Millis(date), ConstantsBase.Value.VALUE_TIME_PATTERN_BY_TIME);

    }

    public static String toAmOrPm(int index) {
        return index == 0 ? StringUtils.getString(R.string.label_am) : StringUtils.getString(R.string.label_pm);
    }

    public static String toUiAndAmOrPm(String date, int index) {
        return toUiDate(date) + toAmOrPm(index);
    }
}
