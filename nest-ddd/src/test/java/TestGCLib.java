import com.ywkj.nest.ddd.EntityObjectMethodInterceptor;
import org.aspectj.apache.bcel.generic.Type;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.ParameterizedType;

/**
 * Created by Jove on 2017/1/21.
 */
public class TestGCLib {


    @Test
    public void test() {
        int i = 2;
        int result = 0;
        switch (i) {
            case 1:
                result = result + i;
        }


    }


    @Test
    public void testCreate() throws InstantiationException, IllegalAccessException {
//        EntityObjectMethodInterceptor interceptor = new EntityObjectMethodInterceptor();
//        testClass t = (testClass) interceptor.getProxy(testClass.class);

        SS<testClass> ss = new SS<>();
        testClass c = ss.getT();
    }

    public class testClass {


        private String a;
        private String b;

        public String getA() {
            return a;
        }

        public void setA(String a) {
            this.a = a;
        }

        public String getB() {
            return b;
        }

        public void setB(String b) {
            this.b = b;
        }
    }


    public class SS<T> {
        public T getT() throws IllegalAccessException, InstantiationException {

            Class<T> entityClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
            return entityClass.newInstance();
        }
    }
}
