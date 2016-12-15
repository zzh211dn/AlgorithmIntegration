package algorithm;

import frame.Charting;

import java.util.ArrayList;

/**
 * Created by syxya on 2016/12/15.
 */
public class PictureAPI {
    public void getChatingResult(ArrayList<Double [][]> datas)
    {
        Charting charting = new Charting();
        charting.drawChat(datas);
    }

    public void getRectangleResult(ArrayList<Double [][]> datas)
    {
        Charting charting = new Charting();
        charting.drawChat(datas);
    }

    public void getScatterResult(ArrayList<Double [][]> datas)
    {
        Charting charting = new Charting();
        charting.drawChat(datas);
    }


}
