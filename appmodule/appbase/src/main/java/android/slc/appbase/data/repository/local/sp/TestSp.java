package android.slc.appbase.data.repository.local.sp;


import com.blankj.utilcode.util.SPUtils;

public class TestSp {
    public static String SP_NAME = "test";

    public static SPUtils getInstance() {
        return SPUtils.getInstance(SP_NAME);
    }
}
