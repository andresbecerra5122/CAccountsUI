module com.CAccounts {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.caccounts to javafx.fxml;
    exports com.example.caccounts;
    exports com.CAccounts;
    exports com.CAccounts.view;
    opens com.CAccounts to javafx.fxml;
    opens com.CAccounts.view to javafx.fxml;
}

