/**
 * Created by Jove on 2017/1/6.
 */
public class TestSpringAop {


    public void test(){}

    public class TestDM {
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

        private TestDM() {
        }
    }
}
