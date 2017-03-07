package frame;

/**
 * Created by fish123 on 2016/12/8.
 */

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.effect.FloatMap;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

import static javafx.application.Application.launch;


public class RectanglePicture {
    int rownum = 8;
    int colnum = 5;
    private Double [] fileType;

    public HashMap<Integer,Float[]> hashMap = new HashMap<Integer, Float[]>();
    public HashSet<Float> hashSetR = new HashSet<Float>();
    public HashSet<Float> hashSetG = new HashSet<Float>();
    public HashSet<Float> hashSetB = new HashSet<Float>();

    //获取RGB随机值，并保证不重复。
    public Float[] getRandom(int type)
    {
        if(hashMap.get(type)!=null)
        {
            return hashMap.get(type);
        }
        else
        {
            Float[] rgb = new Float[3];
            Random random = new Random();
            Float temp = random.nextFloat();
            while(hashSetR.contains(temp))
            {
                temp = random.nextFloat();
            }
            hashSetR.add(temp);
            rgb[0] = temp;
            temp = random.nextFloat();
            while(hashSetG.contains(temp))
            {
                temp = random.nextFloat();
            }
            hashSetG.add(temp);
            rgb[1] = temp;
            temp = random.nextFloat();
            while(hashSetB.contains(temp))
            {
                temp = random.nextFloat();
            }
            hashSetB.add(temp);
            rgb[2] = temp;
            hashMap.put(type,rgb);
            return rgb;
        }

    }

    public Scene start() {
        Group root = new Group();
        Scene scene = new Scene(root, 500, 500, Color.WHITE);
//        System.out.println("fileType"+fileType.length);
        int id=0;
        for(int i = 0;i<rownum;i++)
        {
            for(int j = 0;j<colnum;j++)
            {
                double h = (scene.getHeight()-50) /rownum;
                double w = (scene.getWidth()-50) / colnum;
                Rectangle r = new Rectangle(25+ w*j, 25+h*i,w ,h);
//                System.out.println(Integer.valueOf(fileType[id].intValue()));
                Float[] rgbColor = getRandom(Integer.valueOf(fileType[id].intValue()));
                Color color = new Color(rgbColor[0], rgbColor[1], rgbColor[2], 0.9);//RGB颜色，参数4最后为透明性
                r.setFill(color);
                root.getChildren().add(r);
                id++;
            }
        }
        return scene;
//        stage.setTitle("Recangle Picture ");
//        stage.setScene(scene);
//        stage.show();
    }

    public void drawRectangle(Double [] datas, int row, int col)
    {
        colnum = col;
        rownum = row;
        fileType = datas;
        System.out.println("fileType"+fileType.length);
    }

    public static void main(String[] args) {
//        launch(args);
        Double d = 1.0;
//        System.out.println( Integer.valueOf(d.intValue()));

    }
}
