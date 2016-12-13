package algorithm;

import org.encog.ml.MLCluster;
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

    public TreeMap computeKmeans(double[][] DATA,int k) {

        final BasicMLDataSet set = new BasicMLDataSet();
        TreeMap<String,Integer> treeMap = new TreeMap<String,Integer>();
        for (final double[] element : DATA) {
            set.add(new BasicMLData(element));
            treeMap.put(Arrays.toString(element),0);
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
                treeMap.put(Arrays.toString(pair.getInputArray()),i);
            }
            i++;
        }


        System.out.println();
        Set<Map.Entry<String, Integer>> entry = treeMap.entrySet();
        for(Map.Entry<String,Integer> en:entry)
        {
            System.out.println(en.getKey()+"----cluster:"+en.getValue());
        }
        return treeMap;
    }

    public static void main(String[] args) {
        double[][] DATA = { { 28, 15, 22,22 }, { 16, 15, 32 ,32},
            { 32, 20, 44,44 }, { 1, 2, 3 ,3}, { 3, 2, 1,1 } };
        new Kmeans().computeKmeans(DATA,3);

    }


}
