package frame;

import com.orsoncharts.Chart3D;
import com.orsoncharts.Chart3DFactory;
import com.orsoncharts.Colors;
import com.orsoncharts.data.xyz.XYZDataset;
import com.orsoncharts.data.xyz.XYZSeries;
import com.orsoncharts.data.xyz.XYZSeriesCollection;
import com.orsoncharts.graphics3d.Dimension3D;
import com.orsoncharts.graphics3d.ViewPoint3D;
import com.orsoncharts.label.StandardXYZLabelGenerator;
import com.orsoncharts.plot.XYZPlot;
import com.orsoncharts.renderer.xyz.ScatterXYZRenderer;

import java.util.ArrayList;

/**
 * Created by fish123 on 2016/12/7.
 */
public class ScatterPlot3d {
        public ScatterPlot3d() {
        }

        public static Chart3D createChart(XYZDataset dataset) {
            Chart3D chart = Chart3DFactory.createScatterChart("Chart", "", dataset, "X", "Y", "Z");
            XYZPlot plot = (XYZPlot)chart.getPlot();
            plot.setDimensions(new Dimension3D(10.0D, 4.0D, 4.0D));
            plot.setLegendLabelGenerator(new StandardXYZLabelGenerator("%s (%2$,d)"));
            ScatterXYZRenderer renderer = (ScatterXYZRenderer)plot.getRenderer();
            renderer.setSize(0.15D);
            renderer.setColors(Colors.createIntenseColors());
            chart.setViewPoint(ViewPoint3D.createAboveLeftViewPoint(40.0D));
            return chart;
        }

        public static XYZDataset<String> createDataset(ArrayList<Double [][]> datas) {
            XYZSeriesCollection dataset = new XYZSeriesCollection();
            for(int i = 0;i<datas.size();i++)
            {
                XYZSeries s1 = createRandomSeries("S"+i, datas.get(i).length,datas.get(i));
                dataset.add(s1);
            }
            return dataset;
        }

        private static XYZSeries<String> createRandomSeries(String name, int count,Double[][] datas) {
            XYZSeries s = new XYZSeries(name);

            for(int i = 0; i < count; ++i) {
                s.add(datas[i][0], datas[i][1],  datas[i][2]);//放入xyz数据
//                System.out.println(datas[i][0]+"  "+datas[i][1]+"  "+datas[i][2]);
            }

            return s;
        }
}
