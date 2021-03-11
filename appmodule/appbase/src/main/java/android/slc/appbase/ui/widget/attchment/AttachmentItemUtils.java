package android.slc.appbase.ui.widget.attchment;


import android.slc.appbase.ui.utils.attachment.Attachment;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AttachmentItemUtils {

    public static List<Attachment> removeNetAttachment(List<Attachment> attachmentList) {
        List<Attachment> attachmentListTemp = new ArrayList<>(attachmentList);
        Iterator<Attachment> iterator = attachmentListTemp.iterator();
        while (iterator.hasNext()) {
            Attachment attachment = iterator.next();
            if (attachment.isNetBody() && !attachment.isLocalBody()) {
                iterator.remove();
            }
        }
        return attachmentListTemp;
    }
}
