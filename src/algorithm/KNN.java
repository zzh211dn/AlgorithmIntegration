package algorithm;

import java.util.*;

/**
 * KNN算法主体类
 * @author
 */
public class KNN {

    public ArrayList<ArrayList<Double>> Double2List(Double[][] data)
    {
        ArrayList<ArrayList<Double>> result = new ArrayList<>();

        for(int i = 0;i<data.length;i++)
        {
            ArrayList<Double> list = new ArrayList<>();
            for (int j = 0;j<data[0].length;j++)
            {
                list.add(data[i][j]);
            }
            result.add(list);
        }
        return result;
    }

    public ArrayList<String> Double2StringList(Double[][] data)
    {
        ArrayList<String> result = new ArrayList<>();

        for(int i = 0;i<data.length;i++)
        {
            result.add(data[i][0].toString());
        }
        return result;
    }




    /**
     * 设置优先级队列的比较函数，距离越大，优先级越高
     */
    private Comparator<KNNNode> comparator = new Comparator<KNNNode>() {
        public int compare(KNNNode o1, KNNNode o2) {
            if (o1.getDistance() >= o2.getDistance()) {
                return 1;
            } else {
                return 0;
            }
        }
    };
    /**
     * 获取K个不同的随机数
     * @param k 随机数的个数
     * @param max 随机数最大的范围
     * @return 生成的随机数数组
     */
    public List<Integer> getRandKNum(int k, int max) {
        List<Integer> rand = new ArrayList<Integer>(k);
        for (int i = 0; i < k; i++) {
            int temp = (int) (Math.random() * max);
            if (!rand.contains(temp)) {
                rand.add(temp);
            } else {
                i--;
            }
        }
        return rand;
    }
    /**
     * 计算测试元组与训练元组之前的距离
     * @param d1 测试元组
     * @param d2 训练元组
     * @return 距离值
     */
    public double calDistance(List<Double> d1, List<Double> d2) {
        double distance = 0.00;
        int min;
        if(d1.size()<d2.size()) {
            min = d1.size();
        }
        else
            min = d2.size();
        for (int i = 0; i < min; i++) {
            distance += (d1.get(i) - d2.get(i)) * (d1.get(i) - d2.get(i));
        }
        return distance;
    }
    /**
     * 执行KNN算法，获取测试元组的类别
     * @param datas 训练数据集
     * @param testData 测试元组
     * @param k 设定的K值
     * @return 测试元组的类别
     */
    public String knn(ArrayList<ArrayList<Double>> datas, ArrayList<Double> testData,ArrayList<String> Label, int k) {
        PriorityQueue<KNNNode> pq = new PriorityQueue<KNNNode>(k, comparator);
        List<Integer> randNum = getRandKNum(k, datas.size());
        for (int i = 0; i < k; i++) {
            int index = randNum.get(i);
            List<Double> currData = datas.get(index);
            String c = Label.get(index);
            KNNNode node = new KNNNode(index, calDistance(testData, currData), c);
            pq.add(node);
        }
        for (int i = 0; i < datas.size(); i++) {
            List<Double> t = datas.get(i);
            double distance = calDistance(testData, t);
            KNNNode top = pq.peek();
            if (top.getDistance() > distance) {
                pq.remove();
                pq.add(new KNNNode(i, distance, Label.get(i)));
            }
        }

        return getMostClass(pq);
    }
    /**
     * 获取所得到的k个最近邻元组的多数类
     * @param pq 存储k个最近近邻元组的优先级队列
     * @return 多数类的名称
     */
    private String getMostClass(PriorityQueue<KNNNode> pq) {
        Map<String, Integer> classCount = new HashMap<String, Integer>();
        for (int i = 0; i < pq.size(); i++) {
            KNNNode node = pq.remove();
            String c = node.getC();
            if (classCount.containsKey(c)) {
                classCount.put(c, classCount.get(c) + 1);
            } else {
                classCount.put(c, 1);
            }
        }
        int maxIndex = -1;
        int maxCount = 0;
        Object[] classes = classCount.keySet().toArray();
        for (int i = 0; i < classes.length; i++) {
            if (classCount.get(classes[i]) > maxCount) {
                maxIndex = i;
                maxCount = classCount.get(classes[i]);
            }
        }
        return classes[maxIndex].toString();
    }
}