package android.slc.appbase.data.entity.wb;

/**
 * @author slc
 * @date 2020-08-19 12:22
 */
public class WbItem {
    private String itemCode;
    private String name;
    private int iconRes;
    private String iconPath;
    private int upcomingCount;
    private int color;
    private boolean isShowBigIcon = true;

    public WbItem() {

    }

    public WbItem(String itemCode, String name, int iconRes, int color) {
        this.itemCode = itemCode;
        this.name = name;
        this.iconRes = iconRes;
        this.upcomingCount = upcomingCount;
        this.color = color;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIconRes() {
        return iconRes;
    }

    public void setIconRes(int iconRes) {
        this.iconRes = iconRes;
    }

    public String getIconPath() {
        return iconPath;
    }

    public void setIconPath(String iconPath) {
        this.iconPath = iconPath;
    }

    public int getUpcomingCount() {
        return upcomingCount;
    }

    public void setUpcomingCount(int upcomingCount) {
        this.upcomingCount = upcomingCount;
    }

    public boolean haveUpcoming() {
        return this.upcomingCount != 0;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public boolean isShowBigIcon() {
        return isShowBigIcon;
    }

    public void setShowBigIcon(boolean showBigIcon) {
        isShowBigIcon = showBigIcon;
    }
}
