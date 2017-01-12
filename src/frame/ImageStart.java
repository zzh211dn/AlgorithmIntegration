package frame;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.StageStyle;

import java.util.concurrent.Callable;
import java.util.concurrent.Exchanger;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

public class ImageStart extends Application {

    @Override
    public void start(Stage primaryStage) {

        // Create Image and ImageView objects


        Image image = new Image("http://docs.oracle.com/javafx/"
                + "javafx/images/javafx-documentation.png");
        ImageView imageView = new ImageView();
        imageView.setImage(image);

        // Display image on screen
        StackPane root = new StackPane();
        root.getChildren().add(imageView);
        Scene scene = new Scene(root, 500, 450);
        primaryStage.setScene(scene);
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.show();
        Platform.runLater(new Runnable() {
            public void run() {
              try{Thread.sleep(5000);}
              catch (Exception e)
              {
                  System.out.println(e.toString());
              }
                primaryStage.close();
                new MianFrame().start(new Stage());
            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
} 