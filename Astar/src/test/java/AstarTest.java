import org.junit.Test;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: 何艳莹
 * @Date: 2021/02/23/22:10
 * @Description:
// */
public class AstarTest {

    @Test
    public void search() {
        Yard yard = new Yard("F:\\JAVA\\javastudy\\Astar\\src\\main\\resources\\货场坐标.json");
        Astar astar = new Astar();
        ArrayList<Node> list = astar.search(yard,4,5);
        System.out.println("通过A*算法得到路径为：");
        for (Node i: list){
            System.out.println("["+i.getX()+","+i.getY()+"]");
        }

    }
}