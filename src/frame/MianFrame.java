package frame;
/**
 * Created by zzh on 2016/12/7.
 */


import java.awt.*;
import java.util.AbstractMap.SimpleEntry;
import java.util.Map.Entry;

import com.smooth.gui.SmoothGUI;
import javafx.application.Application;
import javafx.embed.swing.SwingNode;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import javafx.scene.effect.Glow;
import javafx.scene.effect.SepiaTone;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javax.swing.*;

import static javax.swing.SwingUtilities.updateComponentTreeUI;

public class MianFrame extends Application {

    public static void main(String[] args) {
        System.setProperty( "javafx.userAgentStylesheetUrl", "CASPIAN" );
        launch(args);
    }

    @Override
    public void start(Stage stage) {

        stage.setTitle("算法软件");
        final Stage primaryStage = new Stage();
        Scene scene = new Scene(new VBox(), 800, 600);

        final SwingNode swingNode = new SwingNode();
        StackPane pane = new StackPane();
        MenuBar menuBar = new MenuBar();

        // --- Menu File
        Menu menuFile = new Menu("文件");

        // --- Menu Edit
        Menu menuPre = new Menu("预处理");

        // --- Menu analyze
        Menu menuAnal = new Menu("分析算法");

        // --- Menu Classification
        Menu menuClassif = new Menu("分类算法");

        // --- Menu Regression
        Menu menuRegression = new Menu("回归算法");

        // --- Menu Maping
        Menu menuMaping = new Menu("Maping");

        //添加数据预处理菜单
        MenuItem menuItemPre = new MenuItem("数据预处理");
        menuPre.getItems().addAll(menuItemPre);

        //添加文件子菜单
        MenuItem addTrainSet = new MenuItem("打开训练集");
        MenuItem addTestSet = new MenuItem("打开测试集");
        MenuItem saveTrainSet = new MenuItem("保存训练集");
        MenuItem saveTestSet = new MenuItem("保存测试集");

        menuFile.getItems().addAll(addTrainSet,addTestSet,saveTrainSet,saveTestSet);

        //添加分析算法子菜单
        MenuItem addPLS = new MenuItem("偏最小二乘");
        MenuItem addPCA = new MenuItem("主成分分析");
        menuAnal.getItems().addAll(addPCA,addPLS);

        //添加分类算法子菜单
        MenuItem addSVM = new MenuItem("支持向量机");
        MenuItem addKNN = new MenuItem("最近邻");
        MenuItem addHCA = new MenuItem("层次聚类");
        menuClassif.getItems().addAll(addSVM,addKNN,addHCA);

        //添加回归算法子菜单
        MenuItem addBPNN = new MenuItem("人工神经网络");
        menuRegression.getItems().addAll(addBPNN);

        //添加Maping子菜单
        MenuItem addPrintPic = new MenuItem("画图");
        MenuItem addKmeans = new MenuItem("K-聚类");
        menuMaping.getItems().addAll(addPrintPic,addKmeans);

        //添加各菜单到菜单栏

        menuBar.getMenus().addAll(menuFile,menuPre,menuAnal,menuClassif,menuRegression,menuMaping);

        //Action事件监听
        menuItemPre.setOnAction((ActionEvent t) -> {
            new SmoothGUI();
        });


        ((VBox) scene.getRoot()).getChildren().addAll(menuBar);

        stage.setScene(scene);
        stage.show();
    }




}

