package algorithm;

import model.FileAction;

import java.io.File;

/**
 * Created by zzh on 2017/3/10.
 */
public class AreaNormalization {
    public boolean areaNormalize(int start, int end,int n, Double[][] tableVales, String dirName, String fileListName) {
        double oneNum = 0;
        double twoNum = 0;
        double thrNum = 0;
        double sum = 0;
        String[][] tempValues = new String[tableVales.length][2];
        oneNum = tableVales[start][1] + tableVales[end][1];
        for (int i = start; i < end; i++) {
            if ((start - i) % 2 != 0) {
                twoNum += 2 * tableVales[i][1];
            } else {
                thrNum += 4 * tableVales[i][1];
            }
        }
        sum = Math.abs((end-start)*(oneNum+twoNum+thrNum)/(3*n));
        for (int i = 0; i < tableVales.length; i++) {
            tempValues[i][0] = tableVales[i][0].toString();
            Double temp1 = tableVales[i][1] / sum;
            tempValues[i][1] = temp1.toString();
        }
        return saveFile(tempValues, dirName, fileListName);

    }

    public boolean saveFile(String[][] trainTableVales, String dirName, String fileName) {
        String tempPath = dirName + "\\area";
        File f = new File(tempPath);
        if (!f.exists()) {
            f.mkdir();
        }
        File resultFile = new File(tempPath + "\\" + fileName);
        FileAction fa = new FileAction();
        return fa.saveData(resultFile, trainTableVales);

    }
}
