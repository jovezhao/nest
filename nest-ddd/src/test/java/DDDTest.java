import com.ywkj.nest.core.utils.SpringUtils;
import com.ywkj.nest.ddd.AbstractUnitOfWork;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by Jove on 2016/8/29.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:test.xml"})
public class DDDTest {
    @Autowired
    ApplicationContext applicationContext;
    @Autowired
    AbstractUnitOfWork appcontext;
    @Before
    public void befor(){
        SpringUtils.setApplicationContext(applicationContext);
    }
    @After
    public void after(){
        appcontext.commit();
    }
    @Test
    public void TestPerson() {

        Person person = new Person();
        person.setId("zhangsan");
        person.setName("张三");
        person.setAge(20);
        Teacher teacher = person.act(Teacher.class, null);
        teacher.setSubject("语文");
        Classroom context = new Classroom();
        context.classroom = "一年级一班";
        context.teach(teacher, null);

        appcontext.addEntityObject(person);
        appcontext.addEntityObject(teacher);
    }
}
