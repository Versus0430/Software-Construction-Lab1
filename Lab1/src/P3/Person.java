package P3;

import java.util.ArrayList;
import java.util.List;

public class Person {
    public String Name;
    public List<Person> friend = new ArrayList<Person>();

    /**
     * HINT:给Person类的变量Name赋值
     * @param name
     */
    public Person(String name)
    {
        Name = name;
    }

    /**
     * HINT:给Person类的friend列表添加新朋友
     * @param p
     */
    public void newFriend(Person p)
    {
        friend.add(p);
    }
}
