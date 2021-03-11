package android.slc.extras.user.repository.local;

import android.slc.appbase.R;
import android.slc.appbase.data.api.main.callback.po.SlcEntity;
import android.slc.appbase.data.config.ConstantsBase;
import android.slc.extras.user.config.ConstantsUser;
import android.slc.extras.user.entity.UserVo;

import com.blankj.utilcode.util.ArrayUtils;
import com.blankj.utilcode.util.StringUtils;
import com.slc.appdatabase.DaoMaster;
import com.slc.appdatabase.dept.Dept;
import com.slc.appdatabase.service.UserDaoService;
import com.slc.appdatabase.user.User;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Single;
import io.reactivex.functions.Function;

public class UserDaoRepository {
    private final static UserDaoService userDaoService = DaoMaster.getService(UserDaoService.class);
    private final static Map<Long, String> userIconMap = new HashMap<>();
    private final static Map<Long, String> userNameMap = new HashMap<>();

    static {
        userIconMap.put(ConstantsUser.Key.KEY_ANNOUNCEMENT_INT, ConstantsBase.Value.PATH_SYS_USER_ICON + File.separator + "ic_msg_announcement.png");
        userIconMap.put(ConstantsUser.Key.KEY_SYSTEM_NOTIFY_INT, ConstantsBase.Value.PATH_SYS_USER_ICON + File.separator + "ic_msg_notify.png");
        userNameMap.put(ConstantsUser.Key.KEY_ANNOUNCEMENT_INT, StringUtils.getString(R.string.label_announcement));
        userNameMap.put(ConstantsUser.Key.KEY_SYSTEM_NOTIFY_INT, StringUtils.getString(R.string.label_system_information));
    }

    public static UserDaoService getUserDaoService() {
        return userDaoService;
    }

    /**
     * 创建系统的通知消息用户
     */
    @Deprecated
    public static void makeSysUser() {
        ArrayUtils.forAllDo(ConstantsUser.Key.KEY_ARRAY_MSG_INFO_TYPE_INT, (ArrayUtils.Closure<Long>) (index, item) -> {
            if (UserDaoRepository.itExist(item)) {
                User user = User.newSysUser(item);
                user.setAvatar(userIconMap.get(item));
                user.setUserName(userNameMap.get(item));
                UserDaoRepository.saveUser(user);
            }
        });
    }

    public static Single<List<User>> makeSysUser2() {
        return Single.create(emitter -> {
            List<User> sysUserList = new ArrayList<>();
            ArrayUtils.forAllDo(ConstantsUser.Key.KEY_ARRAY_MSG_INFO_TYPE_INT, (ArrayUtils.Closure<Long>) (index, item) -> {
                User user = userDaoService.findById(item);
                if (user == null) {
                    user = User.newSysUser(item);
                    user.setId(item);
                    user.setPhoneNo(String.valueOf(item));
                    user.setAvatar(userIconMap.get(item));
                    user.setUserName(userNameMap.get(item));
                    UserDaoRepository.saveUser(user);
                }
                sysUserList.add(user);
            });
            emitter.onSuccess(sysUserList);
        });

    }

    public static boolean itExist(long id) {
        return userDaoService.userExistById(id);
    }

    public static void saveUser(User user) {
        userDaoService.put(user);
    }

    public static Function<SlcEntity<UserVo>, SlcEntity<UserVo>> saveUserByMap() {
        return userVoSlcEntity -> {
            if (userVoSlcEntity != null && userVoSlcEntity.isSuccess() && userVoSlcEntity.getData() != null) {
                fillAndSaveUser(userVoSlcEntity.getData());
            }
            return userVoSlcEntity;
        };
    }

    public static Function<SlcEntity<UserVo>, SlcEntity<UserVo>> fillUserDetailsByMap() {
        return userVoSlcEntity -> {
            if (userVoSlcEntity != null && userVoSlcEntity.isSuccess() && userVoSlcEntity.getData() != null) {
                fillUserDetails(userVoSlcEntity.getData());
            }
            return userVoSlcEntity;
        };
    }

    public static void fillUserDetails(UserVo user) {
        if (user != null && user.getDept() != null) {
            Dept dept = user.getDept();
            user.setDeptName(dept.getFullname());
            user.setDeptSimpleName(dept.getSimplename());
        }
    }

    public static void fillAndSaveUser(UserVo user) {
        if (user != null) {
            fillUserDetails(user);
            saveUser(user);
        }
    }
}
