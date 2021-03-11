package android.slc.appbase.ui.utils.attachment;


import android.slc.attachment.bean.FileInfo;

/**
 * @author slc
 * @date 2020-09-07 15:23
 */
public class FileInfoImp implements FileInfo {
    private String fileName;
    private String filePath;

    @Override
    public String getFileName() {
        return fileName;
    }

    @Override
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public String getFilePath() {
        return filePath;
    }

    @Override
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
