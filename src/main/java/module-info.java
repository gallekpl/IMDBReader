module pl.com.harta {
    requires javafx.controls;
    requires javafx.fxml;
    requires okhttp;
    requires org.jsoup;

    opens pl.com.harta to javafx.fxml;
    exports pl.com.harta;
}