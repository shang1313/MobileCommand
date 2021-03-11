package android.slc.appbase.data.repository.local;


import com.slc.appdatabase.DaoMaster;
import com.slc.appdatabase.service.StateDaoService;
import com.slc.appdatabase.state.State;
import com.slc.appdatabase.state.State_;

/**
 * 状态管理数据库
 * 如已读未读
 *
 * @author slc
 * @date 2020-09-03 17:03
 */
public class StateDaoServiceDelegate {
    private static final StateDaoService stateDaoService = DaoMaster.getService(StateDaoService.class);

    public static State getState(String hostName, String hostId) {
        return stateDaoService.getBox().query().equal(State_.hostName, hostName).and().equal(State_.hostId, hostId).build().findUnique();
    }

    public static State getStateNotNull(String hostName, String hostId) {
        State state = getState(hostName, hostId);
        if (state == null) {
            state = putState(hostName, hostId, 0);
        }
        return state;
    }

    public static State putState(Class classValue, String hostId, int stateValue) {
        return putState(classValue.getName(), hostId, stateValue);
    }

    public static State putState(String hostName, String hostId, int stateValue) {
        State state = getState(hostName, hostId);
        if (state == null) {
            state = new State();
            state.setHostName(hostName);
            state.setHostId(hostId);
        }
        state.setSaveDate(System.currentTimeMillis());
        state.setState(stateValue);
        stateDaoService.put(state);
        return state;
    }

}
