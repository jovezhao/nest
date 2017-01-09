import com.ywkj.nest.ddd.EntityObjectFactory;

import org.junit.Test;

/**
 * Created by Jove on 2017/1/9.
 */
public class TestAutoSave {

    @Test
    public void autoSave() {
        Person person = EntityObjectFactory.create(Person.class);
        Teacher teacher= person.act(Teacher.class,null);
        teacher.setSubject("fff");
    }

}
