package algorithm;

import org.encog.Encog;
import org.encog.ml.MLCluster;
import org.encog.ml.data.MLData;
import org.encog.ml.data.MLDataPair;
import org.encog.ml.data.MLDataSet;
import org.encog.ml.data.basic.BasicMLData;
import org.encog.ml.data.basic.BasicMLDataPair;
import org.encog.ml.data.basic.BasicMLDataSet;
import org.encog.ml.kmeans.KMeansClustering;

import java.util.*;

/**
 * Created by fish123 on 2016/12/12.
 */
public class Kmeans {
    /*
    * @param k 表示最终聚类个数，DATA是需要的数据集
    *
    * */

    public Integer[] computeKmeans(double[][] DATA,int k) {

        final BasicMLDataSet set = new BasicMLDataSet();
        LinkedHashMap<String,Integer> linkedHashMap = new LinkedHashMap<String,Integer>();
        for (final double[] element : DATA) {
            set.add(new BasicMLData(element));
            linkedHashMap.put(Arrays.toString(element),0);
        }


        final KMeansClustering kmeans = new KMeansClustering(k, set);

        kmeans.iteration(100);

        int i = 1;
        for (final MLCluster cluster : kmeans.getClusters()) {
            final MLDataSet ds = cluster.createDataSet();
            final MLDataPair pair = BasicMLDataPair.createPair(
                    ds.getInputSize(), ds.getIdealSize());
            for (int j = 0; j < ds.getRecordCount(); j++) {
                ds.getRecord(j, pair);
                linkedHashMap.put(Arrays.toString(pair.getInputArray()),i);
            }
            i++;
        }
        Encog.getInstance().shutdown();

        System.out.println();
        Set<Map.Entry<String, Integer>> entry1= linkedHashMap.entrySet();
        Integer[] result = new Integer[linkedHashMap.size()];
        i=0;
        for(Map.Entry<String,Integer> en:entry1)
        {
            result[i] = en.getValue();
            System.out.println(en.getKey()+"----cluster:"+ result[i]);
            i++;

        }



        return result;
    }

    public static void main(String[] args) {
        double[][] DATA = { { 28, 15, 22,22 }, { 16, 15, 32 ,32},
            { 32, 20, 44,44 }, { 1, 2, 3 ,3}, { 3, 2, 1,1 } };
        new Kmeans().computeKmeans(DATA,3);

    }


}
