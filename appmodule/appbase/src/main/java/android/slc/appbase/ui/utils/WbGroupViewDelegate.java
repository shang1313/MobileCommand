package android.slc.appbase.ui.utils;

import android.content.Context;
import android.slc.appbase.R;
import android.slc.appbase.data.entity.wb.WbGroup;
import android.slc.appbase.data.entity.wb.WbItem;
import android.slc.appbase.utils.GlideUtils;
import android.slc.widget.SlcClickProxy;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Space;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.blankj.utilcode.util.CollectionUtils;
import com.blankj.utilcode.util.ResourceUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.blankj.utilcode.util.ViewUtils;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

/**
 * @author slc
 * @date 2020-10-15 15:05
 */
public class WbGroupViewDelegate {
    private Context context;
    private ViewGroup viewParent;
    private WbItemOnClickListener wbItemOnClickListener;
    private final List<List<FrameLayout>> wbItemLauncherListParent = new ArrayList<>();

    public WbGroupViewDelegate(Context context, ViewGroup viewParent, WbItemOnClickListener wbItemOnClickListener) {
        this.context = context;
        this.viewParent = viewParent;
        this.wbItemOnClickListener = wbItemOnClickListener;
    }

    public void loadByWbGroup(List<WbGroup> wbGroupList) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        CollectionUtils.forAllDo(wbGroupList, (index, item) -> {
            CardView cardView = (CardView) layoutInflater.inflate(R.layout.app_wb_item_group, viewParent, false);
            TextView groupName = cardView.findViewById(R.id.tv_group_name);
            groupName.setText(item.getGroupName());
            LinearLayout llLauncherParentItem = cardView.findViewById(R.id.ll_launcher_parent_item);
            final int wbItemSize = item.getWbItemList().size();
            CollectionUtils.forAllDo(item.getWbItemList(), (index1, item1) -> {
                LinearLayout wbItemLauncherParent = (LinearLayout) llLauncherParentItem.getChildAt(1 + (index1 / item.getSpan()));
                if (wbItemLauncherParent == null) {
                    wbItemLauncherParent = (LinearLayout) layoutInflater.inflate(R.layout.app_wb_item_launcher_parent, llLauncherParentItem, false);
                    llLauncherParentItem.addView(wbItemLauncherParent);
                }
                LinearLayout launcherParent = wbItemLauncherParent.findViewById(R.id.ll_launcher_parent);
                FrameLayout wbItemLauncher = (FrameLayout) layoutInflater.inflate(R.layout.app_wb_item_launcher, launcherParent, false);
                wbItemLauncher.setOnClickListener(new SlcClickProxy(v -> wbItemOnClickListener.onClick(item1)));
                fillItem(wbItemLauncher, item, item1);
                /*if (index1 % item.getSpan() != 0) {
                    View view = new View(context);
                    view.setLayoutParams(new LinearLayout.LayoutParams(SizeUtils.dp2px(0.5f), LinearLayout.LayoutParams.MATCH_PARENT));
                    view.setBackgroundColor(ColorUtils.getColor(R.color.divider));
                    launcherParent.addView(view);
                }*/
                if (wbItemLauncherListParent.size() <= index) {
                    wbItemLauncherListParent.add(new ArrayList<>());
                }
                wbItemLauncherListParent.get(index).add(wbItemLauncher);
                launcherParent.addView(wbItemLauncher);
            });
            int overCount = item.getSpan() - wbItemSize % item.getSpan();
            if (overCount != item.getSpan()) {
                LinearLayout launcherParent = llLauncherParentItem.getChildAt(llLauncherParentItem.getChildCount() - 1).findViewById(R.id.ll_launcher_parent);
                for (int i = 0; i < overCount; i++) {
                    /*View view = new View(context);
                    view.setBackgroundColor(ColorUtils.getColor(R.color.divider));
                    view.setLayoutParams(new LinearLayout.LayoutParams(SizeUtils.dp2px(0.5f), LinearLayout.LayoutParams.MATCH_PARENT));
                    launcherParent.addView(view);*/
                    Space viewEmpty = new Space(context);
                    viewEmpty.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1));
                    launcherParent.addView(viewEmpty);
                }
            }
            viewParent.addView(cardView);
        });
    }

    public void clearItemData() {
        wbItemLauncherListParent.clear();
    }

    public void refreshBy(List<WbGroup> wbGroupList) {
        CollectionUtils.forAllDo(wbGroupList, (index, item) -> CollectionUtils.forAllDo(item.getWbItemList(), (index1, item1) -> {
            fillItem(wbItemLauncherListParent.get(index).get(index1), item, item1);
        }));
    }

    private void fillItem(FrameLayout wbItemLauncher, WbGroup wbGroup, WbItem wbItem) {
        CardView cvLauncher = ((CardView) wbItemLauncher.findViewById(R.id.cv_launcher));
        ImageView ivIcon = wbItemLauncher.findViewById(R.id.iv_icon);
        if (wbItem.isShowBigIcon()) {
            ivIcon.setPadding(0,0,0,0);
        } else {
            int padding = SizeUtils.dp2px(12);
            ivIcon.setPadding(padding,padding,padding,padding);
            cvLauncher.setCardBackgroundColor(wbItem.getColor());
        }
        /*if (wbItem.isShowBigIcon()) {
            cvLauncher.setBackgroundResource(wbItem.getIconRes());
        } else {
            cvLauncher.setCardBackgroundColor(wbItem.getColor());
        }*/
        Glide.with(wbItemLauncher).load(wbItem.getIconPath())
                .apply(GlideUtils.getAppStoreIconOptions())
                .error(wbItem.getIconRes() == 0 ? R.mipmap.ic_def_app_store_icon : wbItem.getIconRes())
                .into(ivIcon);
        //ivIcon.setImageResource(wbItem.getIconRes());
        TextView itemName = wbItemLauncher.findViewById(R.id.tv_name);
        itemName.setText(wbItem.getName());
        TextView tv_count = wbItemLauncher.findViewById(R.id.tv_count);
        tv_count.setVisibility(wbItem.haveUpcoming() ? View.VISIBLE : View.INVISIBLE);
        tv_count.setText(String.valueOf(wbItem.getUpcomingCount()));
    }

    public interface WbItemOnClickListener {
        void onClick(WbItem wbItem);
    }
}
