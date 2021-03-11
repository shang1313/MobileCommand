package android.slc.appbase.ui.widget.wheel;

import android.slc.appbase.R;
import android.slc.appbase.data.entity.DateSource;
import android.view.View;

import com.contrarywind.view.WheelView;

import java.util.List;

/**
 * @author slc
 * @date 2020-07-14 14:23
 */
public class DateSourceViewDelegate {
    private View parentView;
    private List<DateSource> dateSourceList;
    private WheelView wvDate;
    private int indexDate = 0;
    private OnItemSelectedListener onItemSelectedListener;

    public DateSourceViewDelegate(View parentView, List<DateSource> dateSourceList) {
        this.parentView = parentView;
        this.dateSourceList = dateSourceList;
    }

    public void init() {
        wvDate = parentView.findViewById(R.id.wv_date);
        wvDate.setCyclic(false);
        wvDate.setAdapter(new DateSourceAdapter(dateSourceList));
        wvDate.setOnItemSelectedListener(index -> {
            indexDate = index;
            if(onItemSelectedListener!=null){
                onItemSelectedListener.onItemSelected(index);
            }
        });

    }

    public void setOnItemSelectedListener(OnItemSelectedListener onItemSelectedListener) {
        this.onItemSelectedListener = onItemSelectedListener;
    }

    public DateSource getCurrentDateSource() {
        return dateSourceList.get(indexDate);
    }

    public interface OnItemSelectedListener {
        void onItemSelected(int index);
    }
}
