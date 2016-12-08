package algorithm;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.CheckBox;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Data for primary table rows.
 */
public class Data {

    private ObjectProperty<Double[][]> data;
    private ObjectProperty<CheckBox> check;

    public Data(Double[][] data,CheckBox check) {


        this.data = new SimpleObjectProperty<Double[][]>(data);
        this.check = new SimpleObjectProperty<CheckBox>(check);

    }

    public final ObjectProperty<Double[][]> dataProperty() {
        return this.data;
    }
    public final Double[][] getData() {
        return this.dataProperty().get();
    }
    public final void setData(final Double[][] data) {
        this.dataProperty().set(data);
    }

    public final ObjectProperty<CheckBox> checkProperty() {
        return this.check;
    }

    public final CheckBox getcheck() {
        return this.checkProperty().get();
    }
    public final void setcheck(final CheckBox check) {
        this.checkProperty().set(check);
    }

}
