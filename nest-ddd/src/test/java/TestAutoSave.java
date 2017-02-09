import com.ywkj.nest.ddd.EntityObjectFactory;

import com.ywkj.nest.ddd.IBuilder;
import com.ywkj.nest.ddd.IRoleBuilder;
import com.ywkj.nest.ddd.RoleRepositoryLoader;
import org.junit.Test;

/**
 * Created by Jove on 2017/1/9.
 */
public class TestAutoSave {

    @Test
    public void autoSave() {
        IRoleBuilder<Teacher,Person> bu = new RoleRepositoryLoader<Teacher, Person>();
        Teacher t = bu.build(Teacher.class,Person.class,"ss");

    }

}
