package hackathon;

import com.oracle.truffle.api.interop.TruffleObject;
import java.util.List;


public interface Connection {
    public void connect();
    
    public void findName(int emp_no, int salary);
    public <T> void query(String sql, Callback<T> callback);
    public <T> void query(String sql, TruffleObject callback);

    @FunctionalInterface
    public static interface Callback<T> {
        void onResult(Object err, List<T> data, Object fields);
    }
}
