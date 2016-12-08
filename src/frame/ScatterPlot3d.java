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

/**
 * Created by fish123 on 2016/12/7.
 */
public class ScatterPlot3d {
        public ScatterPlot3d() {
        }

        public static Chart3D createChart(XYZDataset dataset) {
            Chart3D chart = Chart3DFactory.createScatterChart("HAHA ", "HEHE", dataset, "X", "Y", "Z");
            XYZPlot plot = (XYZPlot)chart.getPlot();
            plot.setDimensions(new Dimension3D(10.0D, 4.0D, 4.0D));
            plot.setLegendLabelGenerator(new StandardXYZLabelGenerator("%s (%2$,d)"));
            ScatterXYZRenderer renderer = (ScatterXYZRenderer)plot.getRenderer();
            renderer.setSize(0.15D);
            renderer.setColors(Colors.createIntenseColors());
            chart.setViewPoint(ViewPoint3D.createAboveLeftViewPoint(40.0D));
            return chart;
        }

        public static XYZDataset<String> createDataset() {
            XYZSeries s1 = createRandomSeries("S1", 20);
            XYZSeries s2 = createRandomSeries("S2", 50);
            XYZSeries s3 = createRandomSeries("S3", 150);
            XYZSeriesCollection dataset = new XYZSeriesCollection();
            dataset.add(s1);
            dataset.add(s2);
            dataset.add(s3);
            return dataset;
        }

        private static XYZSeries<String> createRandomSeries(String name, int count) {
            XYZSeries s = new XYZSeries(name);

            for(int i = 0; i < count; ++i) {
                s.add(Math.random() * 100.0D, Math.random() / 100.0D, Math.random() * 100.0D);
            }

            return s;
        }



}
