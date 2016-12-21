package algorithm;

import frame.Charting;
import frame.PrintScatterChart;
import frame.RectanglePicture;
import javafx.scene.Scene;

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
    public Scene getChatingResult(ArrayList<Double [][]> datas1)
    {

        Random r = new Random();
        ArrayList<Double[][]> arrayList = new ArrayList<>();
        for(int m = 0;m<3;m++) {
            Double[][] datas = new Double[4][2];
            for (int i = 0; i < datas.length; i++) {
                for (int j = 0; j < datas[0].length; j++) {
                    datas[i][j] = r.nextDouble();
                    System.out.print(datas[i][j]+"    ");
                }
                System.out.println();
            }
            arrayList.add(datas);
        }

        try {
            Charting charting = new Charting();
            charting.drawChat(arrayList);
            Scene sceneChart = charting.start();
            return sceneChart;
        }
        catch (Exception e)
        {
            System.out.print(e.toString());
        }
        return null;
    }
    /**
     * @param datas 传入二维数组，实际只有一个文件，为了格式统一
     * datas.get(i)[j][0]为标签项
     * @param row 需要生成的行数
     * @param  col 需要生成的列数
     * */
    public Scene getRectangleResult(ArrayList<Double [][]> datas,int row,int col)
    {
        RectanglePicture rectanglePicture = new RectanglePicture();
         rectanglePicture.drawRectangle(datas,row,col);
        Scene sceneRect= rectanglePicture.start();
        return sceneRect;
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
        for(int m = 0;m<3;m++) {
            Double[][] datas = new Double[25][3];
            for (int i = 0; i < datas.length; i++) {
                for (int j = 0; j < datas[0].length; j++) {
                    datas[i][j] = r.nextDouble();
                    System.out.print(datas[i][j]+"    ");
                }
                System.out.println();
            }
            arrayList.add(datas);
        }
//        System.out.println("next");
        new PictureAPI().getScatterResult(arrayList);

    }
}
