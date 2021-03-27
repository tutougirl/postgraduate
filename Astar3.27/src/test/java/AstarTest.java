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
        Map map = new Map("F:\\人脸识别项目\\Astar\\src\\main\\resources\\yard.json");
        Astar astar = new Astar();
        ArrayList<Node> list = astar.search(map,1300,360,1500,400);
        System.out.println("通过A*算法得到路径为：");
        for (Node i: list){
            System.out.println("["+i.getX()+","+i.getY()+"]");
        }

    }
}