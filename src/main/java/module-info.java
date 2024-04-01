module com.admin.canarysoundsphereadmin {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.admin.canarysoundsphereadmin to javafx.fxml;
    exports com.admin.canarysoundsphereadmin;
}