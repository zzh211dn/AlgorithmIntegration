package algorithm;

import frame.Charting;
import frame.PrintScatterChart;
import frame.RectanglePicture;
import frame.Scatter2D;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by syxya on 2016/12/15.
 */
public class PictureAPI {

    /**
     * @param datas1 传入二维数组，多个文件，
     * datas.get(i)[j][0]为横坐标，datas.get(i)[j][1]为纵坐标
     *
     * */
    public Scene getChatingResult(ArrayList<Double [][]> datas1,String[] fileListName)
    {

        try {
            Charting charting = new Charting();
            charting.drawChat(datas1,fileListName);
            Scene sceneChart = charting.start();
            return sceneChart;
        }
        catch (Exception e)
        {
            System.out.print(e.toString());
        }
        return null;
    }



    public Scene getScatter2DResult(ArrayList<Double [][]> datas1,String[] fileListName)
    {

        try {
            Scatter2D charting2D = new Scatter2D();
            charting2D.drawChat(datas1,fileListName);
            Scene sceneChart = charting2D.start();
            return sceneChart;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param datas 传入二维数组，实际只有一个文件，为了格式统一
     * datas.get(i)[j][0]为标签项
     * @param row 需要生成的行数
     * @param  col 需要生成的列数
     * */
    public Scene getRectangleResult(Double [] datas,int row,int col)
    {
        try {
            RectanglePicture rectanglePicture = new RectanglePicture();
            rectanglePicture.drawRectangle(datas,row,col);
            Scene sceneRect= rectanglePicture.start();
            return sceneRect;
        }
        catch (Exception e)
        {
          e.printStackTrace();
        }
        return null;
    }


    /**
     * @param datas 输入一个三维数组，多个文件。
     * */
    public void getScatterResult(ArrayList<Double [][]> datas)
    {
        PrintScatterChart app = new PrintScatterChart("散点图");
        app.getDataSet(datas);
        app.pack();
        app.setVisible(true);
    }

    //test
    public static void main(String[] args) {

        Random r = new Random();
        ArrayList<Double[][]> arrayList = new ArrayList<>();
        Double[] datass = new Double[25];
        for(int m = 0;m<3;m++) {
            Double[][] datas = new Double[25][3];

            for (int i = 0; i < datas.length; i++) {
                for (int j = 0; j < datas[0].length; j++) {
                    datas[i][j] = r.nextDouble();
//                    System.out.print(datas[i][j]+"    ");
                }
                datass[i] =  r.nextDouble();
//                System.out.println();
            }
            arrayList.add(datas);
        }
//        Stage chartStage = new Stage();
//        chartStage.setScene(new PictureAPI().getChatingResult(arrayList));
//        chartStage.show();
//        new PictureAPI().getRectangleResult(datass,5,5);

    }
}
