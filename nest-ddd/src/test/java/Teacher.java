import com.ywkj.nest.ddd.EntityObject;
import com.ywkj.nest.ddd.AbstractRole;

/**
 * Created by Jove on 2016/8/29.
 */
public class Teacher extends AbstractRole<Person> {



    public String subject;

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

}
