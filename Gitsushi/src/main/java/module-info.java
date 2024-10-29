module it.unipi.gitsushi {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    requires java.sql;
    requires java.base;
    requires java.desktop;
    requires org.apache.logging.log4j;
    requires org.mybatis;

    opens it.unipi.gitsushi to javafx.fxml;
    exports it.unipi.gitsushi;
}
