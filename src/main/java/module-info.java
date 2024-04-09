module com.admin.canarysoundsphereadmin {
    requires javafx.controls;
    requires javafx.fxml;
    requires android.json;


    opens com.admin.canarysoundsphereadmin to javafx.fxml;
    exports com.admin.canarysoundsphereadmin;
    exports com.admin.canarysoundsphereadmin.controllers;
    opens com.admin.canarysoundsphereadmin.controllers to javafx.fxml;
    exports com.admin.canarysoundsphereadmin.models;
    opens com.admin.canarysoundsphereadmin.models to javafx.fxml;
}