package frame;/* ===================
 * Orson Charts - Demo
 * ===================
 *
 * Copyright (c) 2013-2016, Object Refinery Limited.
 * All rights reserved.
 *
 * http://www.object-refinery.com/orsoncharts/index.html
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *   - Neither the name of the Object Refinery Limited nor the
 *     names of its contributors may be used to endorse or promote products
 *     derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL OBJECT REFINERY LIMITED BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * Note that the above terms apply to the demo source only, and not the
 * Orson Charts library.
 *
 */

import java.awt.BorderLayout;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;


import com.orsoncharts.Chart3D;
import com.orsoncharts.Chart3DPanel;
import com.orsoncharts.data.xyz.XYZDataset;
import com.orsoncharts.demo.swing.DemoPanel;
import com.orsoncharts.demo.swing.ExitOnClose;
import com.orsoncharts.demo.swing.OrsonChartsDemo;
import com.orsoncharts.demo.swing.ScatterPlot3DDemo1;
import com.orsoncharts.graphics3d.swing.DisplayPanel3D;

/**
 * A demonstration of a scatter plot in 3D.
 */
public class PrintScatterChart extends JFrame {

    /**
     * Creates a new test app.
     *
     * @param title  the frame title.
     */
    public PrintScatterChart(String title) {
        super(title);
//        addWindowListener(new ExitOnClose());
    }

    public void getDataSet(ArrayList<Double [][]> datas)
    {
        getContentPane().add(createDemoPanel(datas));
    }

    /**
     * Returns a panel containing the content for the demo.  This method is
     * used across all the individual demo applications to allow aggregation
     * into a single "umbrella" demo (OrsonChartsDemo).
     *
     * @return A panel containing the content for the demo.
     */
    public static JPanel createDemoPanel(ArrayList<Double [][]> datas) {
        DemoPanel content = new DemoPanel(new BorderLayout());
        content.setPreferredSize(OrsonChartsDemo.DEFAULT_CONTENT_SIZE);
        XYZDataset dataset = ScatterPlot3d.createDataset(datas);
        Chart3D chart = ScatterPlot3d.createChart(dataset);
        Chart3DPanel chartPanel = new Chart3DPanel(chart);
        content.setChartPanel(chartPanel);
        chartPanel.zoomToFit(OrsonChartsDemo.DEFAULT_CONTENT_SIZE);
        content.add(new DisplayPanel3D(chartPanel));
        return content;
    }

    /**
     * Starting point for the app.
     *
     * @param args  command line arguments (ignored).
     */
    public static void main(String[] args) {
//        PrintScatterChart app = new PrintScatterChart("散点图");
//        app.getDataSet();
//        app.pack();
//        app.setVisible(true);
    }
}