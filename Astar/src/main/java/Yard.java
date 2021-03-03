

import com.google.gson.Gson;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

public class Yard {
    /**
     * 货场json文件中入口，出口，仓库，货架坐标一定按照左上坐标，右上坐标，左下坐标，右下坐标的顺序来写
     */
    private List<Integer> entrance;
    private List<List<Integer>> boundary;
    private List<List<List<Integer>>> shelves;
    //节点数组
    public static Node[][] yard;
    public static Node startNode;
    public static Node endNode;

    public Yard(String filePath) {
        loadYardFromFile(filePath);
    }

    public Node[][] getYard() {
        return yard;
    }

    public void setYard(Node[][] yard) {
        this.yard = yard;
    }

    public static Node getStartNode() {
        return startNode;
    }

    public static void setStartNode(Node startNode) {
        Yard.startNode = startNode;
    }

    public static Node getEndNode() {
        return endNode;
    }

    public static void setEndNode(int endi,int endj) {
        Yard.endNode = yard[endi][endj];
    }

    public static Yard loadYardFromFile (String filePath) {


        String jsonData = Yard.readFileToString(filePath);
        Gson gson = new Gson();
        Yard res=gson.fromJson(jsonData,Yard.class);
        return initYard(res);
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
    public static Yard initYard(Yard res){
        int i;
        int wide = res.boundary.get(res.boundary.size() - 1).get(0) + 1;
        int length = res.boundary.get(res.boundary.size() - 1).get(1) + 1;
        yard=new Node[wide][length];

        for (i = res.boundary.get(0).get(0); i <= res.boundary.get(2).get(0); i++) {
            for (int j = res.boundary.get(0).get(1); j <= res.boundary.get(1).get(1); j++) {
                yard[i][j] = new Node(i, j, " ", true);
            }
        }

        Yard.setStartNode(yard[res.entrance.get(0)][res.entrance.get(1)]);

        for (i = res.boundary.get(0).get(1); i <= res.boundary.get(1).get(1); i++) {
            yard[res.boundary.get(0).get(0)][i].setValue("*");
            yard[res.boundary.get(0).get(0)][i].setReachable(false);
        }
        for (i = res.boundary.get(2).get(1); i <= res.boundary.get(3).get(1); i++) {
            yard[res.boundary.get(3).get(0)][i].setValue("*");
            yard[res.boundary.get(3).get(0)][i].setReachable(false);
        }
        for (i = res.boundary.get(0).get(0); i <= res.boundary.get(2).get(0); i++) {
            yard[i][res.boundary.get(0).get(1)].setValue("*");
            yard[i][res.boundary.get(0).get(1)].setReachable(false);
        }
        for (i = res.boundary.get(1).get(0); i <= res.boundary.get(3).get(0); i++) {
            yard[i][res.boundary.get(3).get(1)].setValue("*");
            yard[i][res.boundary.get(3).get(1)].setReachable(false);
        }
//        货架

        for (List<List<Integer>> list : res.shelves) {

            for (i = list.get(0).get(0); i <= list.get(2).get(0); i++) {
                for (int j = list.get(0).get(1); j <= list.get(1).get(1); j++) {
                    yard[i][j].setValue("#");
                    yard[i][j].setReachable(false);
                }
            }
        }
        for ( i = res.boundary.get(0).get(0); i <= res.boundary.get(2).get(0); i++) {
            for (int j = res.boundary.get(0).get(1); j <= res.boundary.get(1).get(1); j++) {
                System.out.print(yard[i][j].getValue() + " ");
            }
            System.out.println("");
        }
        return res;
    }

}
