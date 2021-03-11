package android.slc.appbase.data.entity.wb;

/**
 * @author slc
 * @date 2021/1/4 16:53
 */
public class WbItemLauncher<T> extends WbItem {
    private T attach;

    public WbItemLauncher() {
        super();
    }

    public WbItemLauncher(String itemCode, String name, int resIcon, int color) {
        super(itemCode, name, resIcon, color);
    }

    public T getAttach() {
        return attach;
    }

    public void setAttach(T attach) {
        this.attach = attach;
    }
}
