package frame;
/**
 * Created by zzh on 2016/12/7.
 */

import java.util.AbstractMap.SimpleEntry;
import java.util.Map.Entry;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import javafx.scene.effect.Glow;
import javafx.scene.effect.SepiaTone;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MianFrame extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        stage.setTitle("算法软件");
        Scene scene = new Scene(new VBox(), 800, 600);

        MenuBar menuBar = new MenuBar();

        // --- Menu File
        Menu menuFile = new Menu("文件");

        // --- Menu Edit
        Menu menuEdit = new Menu("数据预处理");

        // --- Menu View
        Menu menuView = new Menu("分类算法");

        // --- Menu Classification
        Menu menuClassif = new Menu("分类算法");

        // --- Menu Regression
        Menu menuRegression = new Menu("回归算法");

        menuBar.getMenus().addAll(menuFile, menuEdit, menuView, menuClassif,menuRegression);


        ((VBox) scene.getRoot()).getChildren().addAll(menuBar);

        stage.setScene(scene);
        stage.show();
    }


    private class PageData {
        public String name;
        public String description;
        public String binNames;
        public Image image;
        public PageData(String name, String description, String binNames) {
            this.name = name;
            this.description = description;
            this.binNames = binNames;

        }
    }
}