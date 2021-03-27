

import com.google.gson.Gson;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

public class Map {
    /**
     * 货场json文件中入口，出口，仓库，货架坐标一定按照左上坐标，右上坐标，右下坐标，左下坐标的顺序来写
     */
    private List<List<Integer>> boundary;
    private List<List<List<Integer>>> shelves;
    //节点数组
    public static Node[][] map;
    public static Node startNode;
    public static Node endNode;

    public Map(String filePath) {
        loadMapFromFile(filePath);
    }

    public Node[][] getMap() {
        return map;
    }

    public void setMap(Node[][] map) {
        this.map = map;
    }

    public static Node getStartNode() {
        return startNode;
    }

    public static void setStartNode(int starti,int startj) {
        Map.startNode = map[starti][startj];
    }

    public static Node getEndNode() {
        return endNode;
    }

    public static void setEndNode(int endi,int endj) {
        Map.endNode = map[endi][endj];
    }

    public static Map loadMapFromFile (String filePath) {


        String jsonData = Map.readFileToString(filePath);
        Gson gson = new Gson();
        Map res=gson.fromJson(jsonData,Map.class);
        return initMap(res);
    }
    private static String readFileToString(String fileName) {
        String encoding = "UTF-8";
        File file = new File(fileName);
        long filelength = file.length();
        byte[] filecontent = new byte[(int) filelength];
        try {
            FileInputStream in = new FileInputStream(file);
            in.read(filecontent);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            return new String(filecontent, encoding);
        } catch (UnsupportedEncodingException e) {
            System.err.println("The OS does not support " + encoding);
            e.printStackTrace();
            return null;
        }
    }
    public static Map initMap(Map res){
        int i;
        int wide = res.boundary.get(res.boundary.size() - 2).get(0) + 1;
        int length = res.boundary.get(res.boundary.size() - 2).get(1) + 1;
        map=new Node[wide][length];

        for (i = res.boundary.get(0).get(0); i <= res.boundary.get(1).get(0); i++) {
            for (int j = res.boundary.get(0).get(1); j <= res.boundary.get(3).get(1); j++) {
                map[i][j] = new Node(i, j, " ", true);
            }
        }

        //边界
        for (i = res.boundary.get(0).get(1); i <= res.boundary.get(3).get(1); i++) {
            map[res.boundary.get(0).get(0)][i].setValue("*");
            map[res.boundary.get(0).get(0)][i].setReachable(false);
        }
        for (i = res.boundary.get(1).get(1); i <= res.boundary.get(2).get(1); i++) {
            map[res.boundary.get(1).get(0)][i].setValue("*");
            map[res.boundary.get(1).get(0)][i].setReachable(false);
        }
        for (i = res.boundary.get(0).get(0); i <= res.boundary.get(1).get(0); i++) {
            map[i][res.boundary.get(0).get(1)].setValue("*");
            map[i][res.boundary.get(0).get(1)].setReachable(false);
        }
        for (i = res.boundary.get(3).get(0); i <= res.boundary.get(2).get(0); i++) {
            map[i][res.boundary.get(3).get(1)].setValue("*");
            map[i][res.boundary.get(3).get(1)].setReachable(false);
        }
        //   货架
        for (List<List<Integer>> list : res.shelves) {

            for (i = list.get(0).get(0); i <= list.get(1).get(0); i++) {
                for (int j = list.get(0).get(1); j <= list.get(3).get(1); j++) {
                    map[i][j].setValue("#");
                    map[i][j].setReachable(false);
                }
            }
        }
        //显示map
        for ( i = res.boundary.get(0).get(0); i <= res.boundary.get(1).get(0); i++) {
            for (int j = res.boundary.get(0).get(1); j <= res.boundary.get(3).get(1); j++) {
                System.out.print(map[i][j].getValue() + " ");
            }
            System.out.println("");
        }
        return res;
    }

}
