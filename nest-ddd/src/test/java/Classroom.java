/**
 * Created by Jove on 2016/8/29.
 */
public class Classroom {
    public String classroom;

    public void teach(Teacher teacher, String arg) {

        System.out.println(teacher.getSubject() + "老师" + teacher.getActor().getName() + "正在" + classroom + "上课");
        System.out.println("产生一个上课记录");

    }
}
