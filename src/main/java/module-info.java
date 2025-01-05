module com.example.twodemofx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.json;

    requires lombok;

    opens com.example.twodemofx to javafx.fxml;
    opens com.example.twodemofx.Controllers to javafx.fxml;
    exports com.example.twodemofx;
}