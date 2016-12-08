package frame;

/**
 * Created by fish123 on 2016/12/8.
 */

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import org.apache.poi.hssf.util.HSSFColor;


public class RectanglePicture extends Application  {
    @Override
    public void start(Stage stage) {
        Group root = new Group();
        Scene scene = new Scene(root, 500, 500, Color.WHITE);
        Rectangle r = new Rectangle(25,25,scene.getWidth()/5,scene.getHeight()/5);
        Color color = new Color(0.5,0.5,0.5,0.5);
        r.setFill(color);
        root.getChildren().add(r);

        stage.setTitle("JavaFX Scene Graph ");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
