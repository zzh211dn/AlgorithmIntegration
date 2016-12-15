package frame;

import algorithm.algorithmAPI;

import javax.swing.*;
import java.awt.event.*;

public class svmChooser extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JComboBox svmTypeBox ;
    private JComboBox coreTypeBox ;
    private Double[][] trianData;
    private Double[][] testData;
    algorithmAPI algorithmAPI = new algorithmAPI();


    public svmChooser(Double[][] trianData,Double[][]testData) {
        this.trianData = trianData;
        this.testData = testData;
        try {
            UIManager.setLookAndFeel(
                    "com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }
        SwingUtilities.updateComponentTreeUI(this);


        svmTypeBox.setBorder(BorderFactory.createTitledBorder("选择SVM类型?"));
        svmTypeBox.addItem(" SupportVectorClassification");
        svmTypeBox.addItem(" NewSupportVectorClassification");
        svmTypeBox.addItem(" SupportVectorOneClass");
        svmTypeBox.addItem(" EpsilonSupportVectorRegression");
        svmTypeBox.addItem(" NewSupportVectorRegression");

        coreTypeBox.setBorder(BorderFactory.createTitledBorder("选择核函数类型?"));
        coreTypeBox.addItem("Linear");
        coreTypeBox.addItem("Poly");
        coreTypeBox.addItem("RadialBasisFunction");
        coreTypeBox.addItem("Sigmoid");
        coreTypeBox.addItem("Precomputed");


        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                algorithmAPI.getSVMResult(trianData,svmTypeBox.getSelectedIndex()+1,coreTypeBox.getSelectedIndex()+1,testData);
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

            }
        });



    }



    public static void main(String[] args) {
        svmChooser dialog = new svmChooser(null,null);
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
