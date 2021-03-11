package android.slc.pickerview.po;

import android.content.Context;
import android.slc.pickerview.listener.OnOptionsSelectChangeListener;
import android.slc.pickerview.listener.OnOptionsSelectListener;
import android.slc.pickerview.listener.OnTimeSelectChangeListener;
import android.slc.pickerview.listener.OnTimeSelectListener;
import android.view.Gravity;
import android.view.View;

import com.contrarywind.view.WheelView;

import java.util.Calendar;

public class PickerOptions {

    //constant
    private static final int PICKER_VIEW_BTN_COLOR_NORMAL = 0xFF057dff;
    private static final int PICKER_VIEW_BG_COLOR_TITLE = 0xFFf5f5f5;
    private static final int PICKER_VIEW_COLOR_TITLE = 0xFF000000;
    private static final int PICKER_VIEW_BG_COLOR_DEFAULT = 0xFFFFFFFF;

    public static final int TYPE_PICKER_OPTIONS = 1;
    public static final int TYPE_PICKER_TIME = 2;

    public OnOptionsSelectListener optionsSelectListener;
    public OnTimeSelectListener timeSelectListener;
    public View.OnClickListener cancelListener;

    public OnTimeSelectChangeListener timeSelectChangeListener;
    public OnOptionsSelectChangeListener optionsSelectChangeListener;


    //options picker
    public String label1, label2, label3;//单位字符
    public int option1, option2, option3;//默认选中项
    public int x_offset_one, x_offset_two, x_offset_three;//x轴偏移量

    public boolean cyclic1 = false;//是否循环，默认否
    public boolean cyclic2 = false;
    public boolean cyclic3 = false;

    public boolean isRestoreItem = false; //切换时，还原第一项


    //time picker
    public boolean[] type = new boolean[]{true, true, true, false, false, false};//显示类型，默认显示： 年月日

    public Calendar date;//当前选中时间
    public Calendar startDate;//开始时间
    public Calendar endDate;//终止时间
    public int startYear=2010;//开始年份
    public int endYear=2030;//结尾年份

    public boolean cyclic = false;//是否循环
    public boolean isLunarCalendar = false;//是否显示农历

    public String label_year, label_month, label_day, label_hours, label_minutes, label_seconds;//单位
    public int x_offset_year, x_offset_month, x_offset_day, x_offset_hours, x_offset_minutes, x_offset_seconds;//单位



    //******* general field ******//
    public View parentView;
    public int textGravity = Gravity.CENTER;
    public Context context;


    public int bgColorWheel = PICKER_VIEW_BG_COLOR_DEFAULT;//滚轮背景颜色

    public int textSizeContent = 14;//内容文字大小

    public int textColorOut = 0xFF848484; //分割线以外的文字颜色
    public int textColorCenter = 0xde000000; //分割线之间的文字颜色
    public int dividerColor = 0xFFd5d5d5; //分割线的颜色
    public int outSideColor = -1; //显示时的外部背景色颜色,默认是灰色

    public float lineSpacingMultiplier = 1.8f; // 条目间距倍数 默认1.6

    public boolean cancelable = true;//是否能取消
    public boolean isCenterLabel = false;//是否只显示中间的label,默认每个item都显示
    public WheelView.DividerType dividerType = WheelView.DividerType.FILL;//分隔线类型

}