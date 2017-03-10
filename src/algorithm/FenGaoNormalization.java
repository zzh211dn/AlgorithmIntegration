package algorithm;

import model.FileAction;

import java.io.File;

/**
 * Created by fish123 on 2017/3/10.
 */
public class FenGaoNormalization {

    public boolean fengaoNormalize( Double[][] trianTableVales,String dirName,String fileListName)
    {
        Double max = -19999.0;
        String[][] tempValues = new String[trianTableVales.length][2];
        for(int i = 0;i<trianTableVales.length;i++)
        {
            if(max<trianTableVales[i][1] )
                max = trianTableVales[i][1] ;
        }
        for(int i = 0;i<trianTableVales.length;i++)
        {
            tempValues[i][0] = trianTableVales[i][0].toString();
            Double temp1 =  trianTableVales[i][1] /max;
            tempValues[i][1] = temp1.toString();
        }
        return saveFile(tempValues,dirName,fileListName);

    }

    public boolean saveFile(String[][] trainTableVales,String dirName,String fileName)
    {
        String tempPath  = dirName+"\\nomal";
        File f = new File(tempPath);
        if(!f.exists())
        {
            f.mkdir();
        }
        File resultFile = new File(tempPath+"\\"+fileName);
        FileAction fa = new FileAction();
        return fa.saveData(resultFile,trainTableVales);

    }

}
