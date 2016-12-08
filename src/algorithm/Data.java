package algorithm;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.CheckBox;

import java.time.LocalDate;

/**表格数据类
 * Created by zzh on 2016/12/7.
 */
public class Data {

    private  ObjectProperty<Double[]> data;
    private ObjectProperty<CheckBox> check;


    public Data(Double[] date,CheckBox check) {

        this.date = new SimpleObjectProperty<LocalDate>( date);

        this.value1 = new SimpleDoubleProperty( value1);
        this.value2 = new SimpleDoubleProperty( value2);
        this.value3 = new SimpleDoubleProperty( value3);
        this.check = new SimpleObjectProperty<CheckBox>(check);

    }

    /**
     * 取数方法 系统调用
     * @return
     */
    public final ObjectProperty<LocalDate> dateProperty() {
        return this.date;
    }
    public final LocalDate getDate() {
        return this.dateProperty().get();
    }
    public final void setDate(final LocalDate date) {
        this.dateProperty().set(date);
    }
    public final SimpleDoubleProperty value1Property() {
        return this.value1;
    }
    public final double getValue1() {
        return this.value1Property().get();
    }
    public final void setValue1(final double value1) {
        this.value1Property().set(value1);
    }
    public final SimpleDoubleProperty value2Property() {
        return this.value2;
    }
    public final double getValue2() {
        return this.value2Property().get();
    }
    public final void setValue2(final double value2) {
        this.value2Property().set(value2);
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

    public final SimpleDoubleProperty value3Property() {
        return this.value3;
    }

    public final double getValue3() {
        return this.value3Property().get();
    }
    public final void setValue3(final double value3) {
        this.value3Property().set(value3);
    }


}
