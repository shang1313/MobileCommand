package android.slc.appbase.data.api;

import android.slc.appbase.R;
import android.slc.appbase.data.repository.remote.AttachmentServiceRepository;

import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.StringUtils;

/**
 * Created by on the way on 2018/9/18.
 */

public class ApiConfig {
    /**
     * 当前项目信息 init
     */
    public static String API_DS = "ds";
    public static String API_MAIN_UPLOAD = "mainUpload";
    public static String BASE_URL = "http://101.132.144.109:8085/fire.mobile/";
    public static String BASE_URL_DS = "http://61.190.53.185:8099/";
    public static String BASE_URL_DS_FILE = "http://61.190.53.185:8089";
    public static String SOCKET_HOST = "101.132.144.109";
    public static int SOCKET_PORT = 7611;
    public static final String KEY_TOKEN = "token";
    public static final int DEF_PAGE_SIZE = 15;//默认的每页个数
    /**
     * 当前项目信息 end
     */
    /**
     * 返回码 init
     */
    //后台定义
    public static final int SUCCEED = 200;//成功
    public static final int USER_INFO_ERROR = 400;//
    public static final int NORMAL_ERROR = 500;//后台提示
    //客户端定义
    public static final int RESULT_UPLOAD_FAILURE = 30003;//上传文件失败
    public static final String RESULT_UPLOAD_FAILURE_MSG = StringUtils.getString(R.string.label_upload_file_failed);//返回结果list为空提示
    /**
     * 返回码 end
     */
    /**
     * 确认url
     *
     * @param path
     * @return
     */
    public static String resolvePath(String path) {
        if (StringUtils.isTrimEmpty(path)) {
            return "";
        }
        if (path.startsWith("http")) {
            return path;
        }
        if (FileUtils.isFileExists(path)) {
            return path;
        }
        return AttachmentServiceRepository.getFilePathById(path);
    }

}
