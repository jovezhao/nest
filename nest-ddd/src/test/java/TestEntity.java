import com.ywkj.nest.ddd.EntityObject;
import com.ywkj.nest.ddd.builder.FactoryBuilder;
import com.ywkj.nest.ddd.builder.IBuilder;

/**
 * Created by zhaofujun on 2017/3/18.
 */

public class TestEntity extends EntityObject {
    boolean ss;

    public boolean isSs() {
        return ss;
    }

    public static void main(String[] args) throws CloneNotSupportedException {
        IBuilder<TestEntity> builder=new FactoryBuilder<>(TestEntity.class);
        TestEntity entity = builder.build("id");
        entity.toString();
    }
}
