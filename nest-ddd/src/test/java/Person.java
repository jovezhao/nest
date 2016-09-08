import com.ywkj.nest.ddd.EntityObject;

/**
 * 人是一个实体
 * Created by Jove on 2016/8/29.
 */
public class Person  extends EntityObject{
    private String name;
    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
