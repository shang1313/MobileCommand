package android.slc.appbase.utils;

import android.content.Context;
import android.slc.adapter.LoadMoreDateFormat2;
import android.slc.adapter.i.SwipeRecycler;

/**
 * @author slc
 * @date 2020-12-27 20:09
 */
public class LoadMoreDateFormatApp extends LoadMoreDateFormat2 {
    public LoadMoreDateFormatApp(Context applicationContext, SwipeRecycler swipeRecycler) {
        super(applicationContext, swipeRecycler);
        offset = 0;
    }

    @Override
    public void refresh() {
        offset = 0;
        swipeRecycler.setRefreshing(true);
    }
}
