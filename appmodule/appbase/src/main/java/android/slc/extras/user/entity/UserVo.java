package android.slc.extras.user.entity;

import com.slc.appdatabase.dept.Dept;
import com.slc.appdatabase.user.User;

public class UserVo extends User {
    private Dept dept;//查看部门对象

    public Dept getDept() {
        return dept;
    }

    public void setDept(Dept dept) {
        this.dept = dept;
    }
}
