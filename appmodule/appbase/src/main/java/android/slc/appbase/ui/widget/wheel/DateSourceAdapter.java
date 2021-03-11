package android.slc.appbase.ui.widget.wheel;


import android.slc.appbase.data.entity.DateSource;

import com.contrarywind.adapter.WheelAdapter;

import java.util.List;

/**
 * @author slc
 * @date 2020-07-14 14:17
 */
public class DateSourceAdapter implements WheelAdapter<String> {
    private List<DateSource> dateSourceList;

    public DateSourceAdapter(List<DateSource> dateSourceList) {
        this.dateSourceList = dateSourceList;
    }

    @Override
    public int getItemsCount() {
        return this.dateSourceList.size();
    }

    @Override
    public String getItem(int index) {
        return this.dateSourceList.get(index).getDate();
    }

    @Override
    public int indexOf(String o) {
        return this.dateSourceList.indexOf(o);
    }
}
