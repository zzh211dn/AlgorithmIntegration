package frame;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.StageStyle;

import java.util.concurrent.Callable;
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
        Callable callable =  new Callable<Object>() {
            @Override
            public Object call() {
                try {
                    Thread.currentThread().sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("hhh");
                return 1;
            }
        };
        FutureTask f = new FutureTask(callable);
        new Thread(f).start();
//       if(f.get()!=null)
//       {
//           System.out.println("hrere");
//           primaryStage.close();
//       }

    }

    public static void main(String[] args) {
        launch(args);
    }
} 