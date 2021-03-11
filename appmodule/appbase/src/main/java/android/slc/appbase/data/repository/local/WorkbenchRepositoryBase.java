package android.slc.appbase.data.repository.local;

import android.slc.appbase.R;
import android.slc.appbase.data.entity.wb.WbGroup;
import android.slc.appbase.data.entity.wb.WbItem;
import android.slc.appbase.data.utils.OsColorUtils;

import com.blankj.utilcode.util.CollectionUtils;

import java.util.List;
import java.util.Map;

import io.reactivex.functions.Function;

/**
 * @author slc
 * @date 2020/10/19 11:13
 */
public class WorkbenchRepositoryBase {
    public static Function<List<WbGroup>, List<WbGroup>> formatWbGroup(Map<String, Integer> iconByCodeMap) {
        return wbGroups -> {
            CollectionUtils.forAllDo(wbGroups, (index, wbGroup) -> {
                wbGroup.setSpan(3);
                try {
                    wbGroup.setColor(OsColorUtils.colorOsArray[index]);
                } catch (ArrayIndexOutOfBoundsException e) {
                    wbGroup.setColor(OsColorUtils.colorOsArray[0]);
                }
                CollectionUtils.forAllDo(wbGroup.getWbItemList(), (index1, item) -> {
                    Integer iconId = iconByCodeMap.get(item.getItemCode());
                    if (iconId == null) {
                        iconId = R.mipmap.ic_launcher;
                    }
                    item.setIconRes(iconId);
                });
            });
            return wbGroups;
        };
    }

    public static void fillWbGroupData(List<WbGroup> wbGroups, Map<String, Integer> iconByCodeMap) {
        if (CollectionUtils.isNotEmpty(wbGroups)) {
            int colorIndex = 0;
            for (WbGroup wbGroup : wbGroups) {
                wbGroup.setSpan(3);
                if (CollectionUtils.isNotEmpty(wbGroup.getWbItemList())) {
                    for (WbItem wbItem : wbGroup.getWbItemList()) {
                        Integer iconId = iconByCodeMap.get(wbItem.getItemCode());
                        if (iconId == null) {
                            iconId = R.mipmap.ic_launcher;
                        }
                        wbItem.setIconRes(iconId);
                        try {
                            wbItem.setColor(OsColorUtils.colorOsArray[colorIndex++]);
                        } catch (ArrayIndexOutOfBoundsException e) {
                            colorIndex = 0;
                            wbItem.setColor(OsColorUtils.colorOsArray[colorIndex++]);
                        }
                    }
                }
            }
        }
        /*CollectionUtils.forAllDo(wbGroups, (index, wbGroup) -> {
            wbGroup.setSpan(3);
            try {
                wbGroup.setColor(OsColorUtils.colorOsArray[index]);
            } catch (ArrayIndexOutOfBoundsException e) {
                wbGroup.setColor(OsColorUtils.colorOsArray[0]);
            }
            CollectionUtils.forAllDo(wbGroup.getWbItemList(), (index1, item) -> {
                Integer iconId = iconByCodeMap.get(item.getItemCode());
                if (iconId == null) {
                    iconId = R.mipmap.ic_launcher;
                }
                item.setResIcon(iconId);
                try {
                    wbGroup.setColor(OsColorUtils.colorOsArray[index]);
                } catch (ArrayIndexOutOfBoundsException e) {
                    wbGroup.setColor(OsColorUtils.colorOsArray[0]);
                }
            });
        });*/
    }
}
