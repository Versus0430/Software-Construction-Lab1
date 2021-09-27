package P3;

import java.util.*;

public class FriendshipGraph {
    public List<Person> people = new ArrayList<Person>();
    public List<String> names = new ArrayList<String>();

    /**
     * HINT:添加顶点，并防止出现有重复对象
     * @param newone
     */
    public void addVertex(Person newone)
    {
        if(names.contains(newone.Name))
        {
            System.out.println("error：有重复对象！");
            System.exit(0);
        }
        else
        {
            people.add(newone);
            names.add(newone.Name);
        }
    }

    /**
     * HINT:添加边
     * @param p1
     * @param p2
     */
    public void addEdge(Person p1, Person p2)
    {
        p1.newFriend(p2);
    }

    /**
     * HINT:使用广度优先求最短路径，其中采用了队列来存对象与集合来标记已访问过的对象
     * @param p1
     * @param p2
     * @return 两个对象p1、p2的距离（正整数\0\-1）
     */
    public int getDistance(Person p1, Person p2)
    {
        int distance=0;
        Queue<Person> q = new LinkedList<>();
        Set<Person> set = new HashSet<>();
        if(p1 == p2)
            return distance;
        distance++;
        q.offer(p1);
        set.add(p1);
        Person head=null;
        while(!q.isEmpty())
        {
            head = q.poll(); //head等于q删除的队首
            if(head.friend.contains(p2))
                return distance;
            else
            {
                for(Person p: head.friend)
                    if (!set.contains(p)) {
                        q.offer(p);
                        set.add(p);
                    }
                distance++;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        System.out.println("实验样例:");
        FriendshipGraph graph = new FriendshipGraph();
        Person rachel = new Person("Rachel");
        Person ross = new Person("Ross");
        Person ben = new Person("Ben");
        Person kramer = new Person("Kramer");
        graph.addVertex(rachel);
        graph.addVertex(ross);
        graph.addVertex(ben);
        graph.addVertex(kramer);
        graph.addEdge(rachel, ross);
        graph.addEdge(ross, rachel);
        graph.addEdge(ross, ben);
        graph.addEdge(ben, ross);
        System.out.println("(rachel,ross)之间的距离：" + graph.getDistance(rachel, ross)); //should print 1
        System.out.println("(rachel,ben)之间的距离:" + graph.getDistance(rachel, ben));//should print 2
        System.out.println("(rachel,rachel)之间的距离:" + graph.getDistance(rachel, rachel));//should print 0
        System.out.println("(rachel,kramer)之间的距离:" + graph.getDistance(rachel, kramer));//should print -1

        System.out.println();
        System.out.println("若rachel与ross只存在单向关系ross->rachel：");
        graph = new FriendshipGraph();
        rachel = new Person("Rachel");
        ross = new Person("Ross");
        ben = new Person("Ben");
        kramer = new Person("Kramer");
        graph.addVertex(rachel);
        graph.addVertex(ross);
        graph.addVertex(ben);
        graph.addVertex(kramer);
//        graph.addEdge(rachel, ross);
        graph.addEdge(ross, rachel);
        graph.addEdge(ross, ben);
        graph.addEdge(ben, ross);
        System.out.println("(rachel,ross)之间的距离：" + graph.getDistance(rachel, ross)); //should print 1
        System.out.println("(rachel,ben)之间的距离:" + graph.getDistance(rachel, ben));//should print 2
        System.out.println("(rachel,rachel)之间的距离:" + graph.getDistance(rachel, rachel));//should print 0
        System.out.println("(rachel,kramer)之间的距离:" + graph.getDistance(rachel, kramer));//should print -1

        System.out.println();
        System.out.println("若将ross替换为rachel:");
        graph.addVertex(new Person("Rachel"));
    }
}
