import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: 何艳莹
 * @Date: 2021/02/23/21:44
 * @Description:
 */
public class Astar {
    /**
     * 使用ArrayList数组作为“开启列表”和“关闭列表”
     */
    ArrayList<Node> open = new ArrayList<Node>();
    ArrayList<Node> close = new ArrayList<Node>();
    /**
     * 获取H值
     * @param currentNode：当前节点
     * @param endNode：终点
     * @return
     */
    public double getHValue(Node currentNode,Node endNode){
        return (Math.abs(currentNode.getX() - endNode.getX()) + Math.abs(currentNode.getY() - endNode.getY()))*10;
    }
    /**
     * 获取G值
     * @param currentNode：当前节点
     * @return
     */
    public double getGValue(Node currentNode){
        if(currentNode.getPNode()!=null){
            if(currentNode.getX()==currentNode.getPNode().getX()||currentNode.getY()==currentNode.getPNode().getY()){
                //判断当前节点与其父节点之间的位置关系（水平？对角线）
                return currentNode.getGValue()+10;
            }
            return currentNode.getGValue()+14;
        }
        return currentNode.getGValue();
    }
    /**
     * 获取F值 ： G + H
     * @param currentNode
     * @return
     */
    public double getFValue(Node currentNode){
        return currentNode.getGValue()+currentNode.getHValue();
    }
    /**
     * 将选中节点周围的节点添加进“开启列表”
     * @param node
     * @param map
     */
    public void inOpen(Node node,Map map){
        int x = node.getX();
        int y = node.getY();
         for (int i = 0;i<3;i++){
            for (int j = 0;j<3;j++){
                //判断条件为：节点为可到达的（即不是障碍物，不在关闭列表中），开启列表中不包含，不是选中节点
                if(x - 1 + i>=0&&y - 1 + j>=0) {
                    if (map.getMap()[x - 1 + i][y - 1 + j].isReachable() && !open.contains(map.getMap()[x - 1 + i][y - 1 + j]) && !(x == (x - 1 + i) && y == (y - 1 + j))) {
                        map.getMap()[x - 1 + i][y - 1 + j].setPNode(map.getMap()[x][y]);
                        //将选中节点作为父节点
                        map.getMap()[x - 1 + i][y - 1 + j].setGValue(getGValue(map.getMap()[x - 1 + i][y - 1 + j]));
                        map.getMap()[x - 1 + i][y - 1 + j].setHValue(getHValue(map.getMap()[x - 1 + i][y - 1 + j], map.getEndNode()));
                        map.getMap()[x - 1 + i][y - 1 + j].setFValue(getFValue(map.getMap()[x - 1 + i][y - 1 + j]));
                        open.add(map.getMap()[x - 1 + i][y - 1 + j]);
                    }
                }
            }
        }
    }
    /**
     * 使用冒泡排序将开启列表中的节点按F值从小到大排序
     * @param arr
     */
    public void sort(ArrayList<Node> arr){
        for (int i = 0;i<arr.size()-1;i++){
            for (int j = i+1;j<arr.size();j++){
                if(arr.get(i).getFValue() > arr.get(j).getFValue()){
                    Node tmp = new Node();
                    tmp = arr.get(i);
                    arr.set(i, arr.get(j));
                    arr.set(j, tmp);
                }
            }
        }
    }
    /**
     * 将节点添加进”关闭列表“
     * @param node
     * @param open
     */
    public void inClose(Node node,ArrayList<Node> open){
        if(open.contains(node)){
            node.setReachable(false);
            //设置为不可达
            open.remove(node);
            close.add(node);
        }
    }
    public ArrayList<Node> search(Map map,int starti,int startj,int endi,int endj){
        Map.setStartNode(starti,startj);
        Map.setEndNode(endi,endj);
        //对起点即起点周围的节点进行操作
        inOpen(map.getMap()[Map.getStartNode().getX()][Map.getStartNode().getY()],map);
        close.add(map.getMap()[Map.getStartNode().getX()][Map.getStartNode().getY()]);
        map.getMap()[Map.getStartNode().getX()][Map.getStartNode().getY()].setReachable(false);
        map.getMap()[Map.getStartNode().getX()][Map.getStartNode().getY()].setPNode(map.getMap()[Map.getStartNode().getX()][Map.getStartNode().getY()]);
        sort(open);
        //重复步骤
        do{
            inOpen(open.get(0), map);
            inClose(open.get(0), open);
            sort(open);
        }
        while(!open.contains(map.getMap()[Map.getEndNode().getX()][Map.getEndNode().getY()]));
        //知道开启列表中包含终点时，循环退出
        inClose(map.getMap()[Map.getEndNode().getX()][Map.getEndNode().getY()], open);
        for (Node i: close){
            i.setValue(".");
        }

            return close;

    }
}
