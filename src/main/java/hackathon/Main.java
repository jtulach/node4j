package hackathon;

import com.oracle.truffle.api.interop.TruffleObject;
import com.oracle.truffle.api.interop.java.JavaInterop;
import java.util.List;

public class Main {
    public static TruffleObject init() {
        return JavaInterop.asTruffleObject(new Main());
    }
    
    
    public void connect(Connection connection){
        connection.connect();
    }
    
    public void results(List<Salaries> obj, Connection connection) {
        for (Salaries salary : obj) {
            connection.findName(salary.emp_no(), salary.salary());
        }
    }
    
    public void names(int emp_no, Employees emp, int salary) {
        System.err.println("{name:\"" + emp.first_name() + "\",surname: \"" + emp.last_name() + "\", salary: " + salary +"},");
    }
    
    public interface Salaries {
        int emp_no();
        int salary();
    }
    
    public interface Employees {
        String first_name();
        String last_name();
    }
}
