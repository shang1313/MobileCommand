 package android.slc.appbase.utils;

import android.slc.appbase.R;
import android.slc.appbase.data.config.ConstantsBase;
import android.slc.commonlibrary.util.compat.SlcTimeCompatUtils;

import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.TimeUtils;

import java.util.Calendar;
import java.util.Date;

/**
 * @author slc
 * @date 2020-08-25 10:17
 */
public class TimeStrangeFormatUtils {

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

    /**
     * 时间人性化
     *
     * @param timeString
     * @return
     */
    public static String timeHumanize(String timeString) {
        return timeHumanize(timeString, ConstantsBase.Value.VALUE_TIME_PATTERN_BY_DATE_TIME_BY_THICK_LINE);
    }

    /**
     * 时间人性化
     *
     * @param timeString
     * @param pattern
     * @return
     */
    public static String timeHumanize(String timeString, String pattern) {
        return timeHumanize(TimeUtils.string2Millis(timeString, pattern));
    }

    /**
     * 时间人性化
     *
     * @param time
     * @return
     */
    public static String timeHumanize(long time) {
        Date timeOfDate = new Date(time);
        long currentTimeInMillis = System.currentTimeMillis();
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(currentTimeInMillis);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        long formatTimeInMillis = calendar.getTimeInMillis();
        if (time >= formatTimeInMillis) {
            return SlcTimeCompatUtils.getDateStringByPattern("HH:mm", timeOfDate);
        }
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        formatTimeInMillis = calendar.getTimeInMillis();
        if (time >= formatTimeInMillis) {
            return StringUtils.getString(R.string.label_yesterday);
        }
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        formatTimeInMillis = calendar.getTimeInMillis();
        if (time >= formatTimeInMillis) {
            return StringUtils.getString(R.string.label_the_day_before_yesterday);
        }
        calendar.add(Calendar.DAY_OF_MONTH, 2);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.MONTH, 0);
        formatTimeInMillis = calendar.getTimeInMillis();
        if (time >= formatTimeInMillis) {
            return SlcTimeCompatUtils.getDateStringByPattern("MM月dd号", timeOfDate);
        }
        return SlcTimeCompatUtils.getDateStringByPattern("yyyy年MM月dd号", timeOfDate);
    }
}
