package android.slc.appbase.data.entity;

/**
 * @author slc
 * @date 2020-08-21 11:40
 */
public class DateSource {
    private long timeMillisecond;
    private String date;

    public long getTimeMillisecond() {
        return timeMillisecond;
    }

    public void setTimeMillisecond(long timeMillisecond) {
        this.timeMillisecond = timeMillisecond;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
