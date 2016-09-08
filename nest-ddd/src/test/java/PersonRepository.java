import com.ywkj.nest.ddd.IRepository;
import com.ywkj.nest.ddd.IRoleRepository;

import java.util.*;

/**
 * Created by Jove on 2016/8/29.
 */
public class PersonRepository implements IRepository<Person> {
    static HashMap<String, Person> list = new HashMap<>();

    @Override
    public Person getEntityById(String id) {
        return list.get(id);
    }

    @Override
    public void save(Person person) {
        list.put(person.getId(), person);
    }

    @Override
    public void remove(Person person) {
        list.remove(person.getId());
    }
}

class TeacherRepository implements IRoleRepository<Teacher> {
    static HashMap<String, Teacher> list = new HashMap<>();

    @Override
    public Set<String> getRoleIds(String actorId) {
        Set<String> ids = new HashSet<>();
        for (Map.Entry<String, Teacher> map : list.entrySet()) {
            if (actorId.equals(map.getValue().getActor().getId())) {
                ids.add(map.getValue().getId());
            }
        }
        return ids;
    }

    @Override
    public Teacher getEntityById(String id) {
        return list.get(id);
    }

    @Override
    public void save(Teacher teacher) {
        list.put(teacher.getId(), teacher);
    }

    @Override
    public void remove(Teacher teacher) {
        list.remove(teacher.getId());
    }
}
